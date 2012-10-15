package com.squirrel.engine.scene.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Set;

import com.squirrel.engine.event.impl.CollisionEvent;
import com.squirrel.engine.game.GameManager;
import com.squirrel.engine.gameobject.Collidable;
import com.squirrel.engine.gameobject.Drawable;
import com.squirrel.engine.gameobject.GameObject;
import com.squirrel.engine.gameobject.Updateable;
import com.squirrel.engine.scene.Layer;
import com.squirrel.engine.statistics.PerformanceStatistics;
import com.squirrel.engine.utils.ApplicationUtils;

/**
 * Repr�sentiert einen Layer in einer Szene. Es soll nicht alles auf einem Layer sein,
 * da unser Konzept vorhieht, dass grunds�tzlich erstmal alles ein GameObject ist
 */
public class LayerImpl implements Layer{

	/**
	 * Der Name des Layer
	 */
	protected String name;
	/**
	 * Die Priorit�t des Layer
	 */
	protected long priority;

	/**
	 * Alle {@link GameObject}s die sich auf diesem Layer befinden
	 */
	protected Set<GameObject> content;
	/**
	 * Alle {@link GameObject}s die beim n�chsten Update des Layer entfernt werden sollen
	 */
	protected Set<GameObject> removeAtUpdate;
	/**
	 * Alle {@link GameObject}s die beim n�chsten Update des Layer hinzugef�gt werden sollen
	 */
	protected Set<GameObject> addAtUpdate;
	
	/**
	 * Eine Referenz auf die {@link PerformanceStatistics}
	 */
	protected PerformanceStatistics ps;
	/**
	 * Eine Referenz auf den {@link GameManager}
	 */
	protected GameManager gm;
	
	/**
	 * Initialisiert den Layer
	 * 
	 * @param name
	 */
	public LayerImpl(String name) {
		this.name = name;
		content = new HashSet<GameObject>();
		removeAtUpdate = new HashSet<GameObject>();
		addAtUpdate = new HashSet<GameObject>();
		
		// die PerformanceStatistics aus dem Kontext laden
		ps = (PerformanceStatistics) ApplicationUtils.getInstance().getBean("performanceStatistics");
		// den GameManager aus dem Kontext laden
		gm = (GameManager) ApplicationUtils.getInstance().getBean("gameManager");
	}
	
	/**
	 * Den Namen des Layer zur�ckgeben
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * F�gt ein {@link GameObject} der ungeordneten Liste hinzu
	 * 
	 * @param obj
	 */
	public void addGameObject(GameObject obj) {
		content.add(obj);
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public void draw(Graphics g) {
		for (GameObject gObj : content) {
			if (gObj instanceof Drawable) {
				((Drawable) gObj).draw(g);				
			}
			
			// Wenn sich das Spiel im Debug-Modus befindet sollen alle Boundingboxen gezeichnet werden
			if (gm.isDebug() && gObj instanceof Collidable) {
				Color color =g.getColor();
				g.setColor(Color.yellow);
				Collidable c = ((Collidable) gObj);
				for (Rectangle rect : c.getCollisionBoxes()) {
					g.drawRect((int) (rect.x + c.getPosx()), (int) (rect.y + c.getPosy()), rect.width, rect.height);
				}
				g.setColor(color);
			}
		}
	}

	@Override
	public void update() {
		// bevor die GameObjects selbst geupdated werden wird erst der Layer
		// aktualisiert
		content.removeAll(removeAtUpdate);
		removeAtUpdate.clear();
		
		content.addAll(addAtUpdate);
		addAtUpdate.clear();
		
		// GameObjects aktualisiseren
		for (GameObject gObj : content) {
			if (gObj instanceof Updateable)
				((Updateable) gObj).update();
			if (gObj instanceof Collidable)
				collisionCheck((Collidable) gObj);
		}
	}

	/**
	 * TODO Dieser collisioncheck ist noch ziehmlich inperformant,
	 * da das �bergebene Objekt mit allen andern Objekten verglichen wird.
	 * Hier muss ein anderer Ansatz her, der es erlaubt, das �bergebene
	 * Objekt nur mit einer Teilmenge der vorhandenen Objekte zu pr�fen
	 */
	@Override
	public void collisionCheck(Collidable c) {
		boolean collided = false;
		for (GameObject gObj1 : content) {
			// Prüft, ob das betrachtete GameObject ein Collidable ist
			if (gObj1 instanceof Collidable) {
				if (gObj1 != c) {
					if (((Collidable) gObj1).collisionCheck(c)) {
						((Collidable) gObj1).onCollision(new CollisionEvent(c));
						c.onCollision(new CollisionEvent(((Collidable) gObj1)));
						// festhalten, dass das Objekt mindestens einmal kollidiert ist
						collided = true;
					}
				}
			}
		}
		
		// Wen das Objekt nicht kollidiert ist
		if (!collided) {
			c.onNoCollision();
		}
	}

	@Override
	public void removeGameObjectAtUpdate(GameObject obj) {
		removeAtUpdate.add(obj);
	}

	@Override
	public void addGameObjectAtUpdate(GameObject obj) {
		addAtUpdate.add(obj);
	}
	

	public long getPriority() {
		return priority;
	}

	public void setPriority(long priority) {
		this.priority = priority;
	}
}

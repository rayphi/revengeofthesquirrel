package com.squirrel.engine.layer.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.squirrel.engine.event.impl.CollisionEvent;
import com.squirrel.engine.game.GameManager;
import com.squirrel.engine.gameobject.Collidable;
import com.squirrel.engine.gameobject.Drawable;
import com.squirrel.engine.gameobject.GameObject;
import com.squirrel.engine.gameobject.Updateable;
import com.squirrel.engine.layer.Layer;
import com.squirrel.engine.scene.impl.SceneArchiveConstants;
import com.squirrel.engine.statistics.PerformanceStatistics;
import com.squirrel.engine.utils.ApplicationUtils;

/**
 * Reprï¿½sentiert einen Layer in einer Szene. Es soll nicht alles auf einem Layer sein,
 * da unser Konzept vorhieht, dass grundsï¿½tzlich erstmal alles ein GameObject ist
 */
public class LayerImpl implements Layer{

	/**
	 * Der Name des Layer
	 */
	protected String name;
	/**
	 * Die Prioritï¿½t des Layer
	 */
	protected long priority;

	/**
	 * Alle {@link GameObject}s die sich auf diesem Layer befinden
	 */
	protected Set<GameObject> content;
	/**
	 * Alle {@link GameObject}s die beim nï¿½chsten Update des Layer entfernt werden sollen
	 */
	protected Set<GameObject> removeAtUpdate;
	/**
	 * Alle {@link GameObject}s die beim nï¿½chsten Update des Layer hinzugefï¿½gt werden sollen
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
	 * Den Namen des Layer zurï¿½ckgeben
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Fï¿½gt ein {@link GameObject} der ungeordneten Liste hinzu
	 * 
	 * @param obj
	 */
	public void addGameObject(GameObject obj) {
		obj.setParent(this);
		content.add(obj);
	}
	
	/**
	 * FŸgt alle {@link GameObject}s der Ÿbergebenen Collection dem 
	 * content hinzu
	 * 
	 * @param gameObjects
	 */
	public void addAllGameObjects(Collection<GameObject> gameObjects) {
		for (GameObject gameObject : gameObjects) {
			addGameObject(gameObject);
		}
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
		
		addAllGameObjects(addAtUpdate);
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
	 * da das ï¿½bergebene Objekt mit allen andern Objekten verglichen wird.
	 * Hier muss ein anderer Ansatz her, der es erlaubt, das ï¿½bergebene
	 * Objekt nur mit einer Teilmenge der vorhandenen Objekte zu prï¿½fen
	 */
	@Override
	public void collisionCheck(Collidable c) {
		boolean collided = false;
		for (GameObject gObj1 : content) {
			// PrÃ¼ft, ob das betrachtete GameObject ein Collidable ist
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

	@SuppressWarnings("unchecked")
	@Override
	public void load(Map<String, Object> layerMap) throws Exception {
		// name (pflicht)
		String name = (String) layerMap.get(SceneArchiveConstants.NAME);
		if (name != null) {
			this.name = name;
		} else {
			throw new Exception("Property 'name' missing");
		}
		
		// priority (pflicht)
		Long priority = (Long) layerMap.get(SceneArchiveConstants.PRIORITY);
		if (priority != null) {
			this.priority = priority;
		} else {
			throw new Exception("Property 'priority' missing");
		}
		
		// content (optional)
		List<GameObject> gos = (List<GameObject>) layerMap.get(SceneArchiveConstants.CONTENT);
		if (gos != null) {
			addAllGameObjects(gos);
		}
	}
}

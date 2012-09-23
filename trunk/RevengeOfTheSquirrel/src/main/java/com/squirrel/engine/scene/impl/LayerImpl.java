package com.squirrel.engine.scene.impl;

import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

import com.squirrel.engine.game.GameManager;
import com.squirrel.engine.gameobject.Collidable;
import com.squirrel.engine.gameobject.Drawable;
import com.squirrel.engine.gameobject.GameObject;
import com.squirrel.engine.gameobject.Updateable;
import com.squirrel.engine.scene.Layer;
import com.squirrel.engine.statistics.PerformanceStatistics;
import com.squirrel.engine.utils.ApplicationUtils;

/**
 * Repräsentiert einen Layer in einer Szene. Es soll nicht alles auf einem Layer sein,
 * da unser Konzept vorhieht, dass grundsätzlich erstmal alles ein GameObject ist
 */
public class LayerImpl implements Layer{

	/**
	 * Der Name des Layer
	 */
	protected String name;
	/**
	 * Alle {@link GameObject}s die sich auf diesem Layer befinden
	 */
	protected Set<GameObject> content;
	/**
	 * Alle {@link GameObject}s die beim nächsten Update des Layer entfernt werden sollen
	 */
	protected Set<GameObject> removeAtUpdate;
	/**
	 * Alle {@link GameObject}s die beim nächsten Update des Layer hinzugefügt werden sollen
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
	 * Den Namen des Layer zurückgeben
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Fügt ein {@link GameObject} der ungeordneten Liste hinzu
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
			if (gObj instanceof Drawable)
				((Drawable) gObj).draw(g);
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
		}
	}

	/**
	 * TODO Dieser collisioncheck ist noch ziehmlich inperformant,
	 * da das übergebene Objekt mit allen andern Objekten verglichen wird.
	 * Hier muss ein anderer Ansatz her, der es erlaubt, das übergebene
	 * Objekt nur mit einer Teilmenge der vorhandenen Objekte zu prüfen
	 */
	@Override
	public void collisionCheck(Collidable c) {
		for (GameObject gObj1 : content) {
			// Prüft, ob das betrachtete GameObject ein Collidable ist
			if (gObj1 instanceof Collidable) {
				if (gObj1 != c) {
					((Collidable) gObj1).collisionCheck(c);
				}
			}
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
	
}

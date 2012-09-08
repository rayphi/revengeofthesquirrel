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
import com.squirrel.engine.utils.SquirrelRevengeUtils;

/**
 * Repräsentiert einen Layer in einer Szene. Es soll nicht alles auf einem Layer sein,
 * da unser Konzept vorhieht, dass grundsätzlich erstmal alles ein GameObject ist
 */
public class LayerImpl implements Layer{

	protected String name;
	protected Set<GameObject> content;
	protected Set<GameObject> removeObjects;
	protected Set<GameObject> addAtUpdate;
	
	protected PerformanceStatistics ps;
	protected GameManager gm;
	
	public LayerImpl(String name) {
		this.name = name;
		content = new HashSet<GameObject>();
		removeObjects = new HashSet<GameObject>();
		addAtUpdate = new HashSet<GameObject>();
		
		ps = (PerformanceStatistics) SquirrelRevengeUtils.getInstance().getBean("performanceStatistics");
		gm = (GameManager) SquirrelRevengeUtils.getInstance().getBean("gameManager");
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * Fügt ein GameObject der ungeordneten Liste hinzu
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
		content.removeAll(removeObjects);
		removeObjects.clear();
		
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
			if (gObj1 instanceof Collidable) {
					if (gObj1 != c)
						((Collidable) gObj1).collisionCheck(c);
			}
		}
	}

	@Override
	public void removeGameObject(GameObject obj) {
		removeObjects.add(obj);
	}

	@Override
	public void addGameObjectAtUpdate(GameObject obj) {
		addAtUpdate.add(obj);
	}
	
	
}

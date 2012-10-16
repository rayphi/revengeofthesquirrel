package com.squirrel.engine.layer;

import java.awt.Graphics;
import java.util.Collection;
import java.util.Map;

import com.squirrel.engine.gameobject.Collidable;
import com.squirrel.engine.gameobject.GameObject;

/**
 * Repräsentiert einen Layer in der Scene
 * @author Shane
 *
 */
public interface Layer {

	/**
	 * Jeder Layer muss einen unique Namen bekommen, welchen man über
	 * diese Methode abfragn kann.
	 * @return
	 */
	String getName();
	
	/**
	 * Gibt die Priorität des Layer zurück
	 * @return
	 */
	long getPriority();
	
	/**
	 * setzt die Priorität des Layer
	 */
	void setPriority(long prio);
	
	/**
	 * Stößt das zeichnen aller enthaltenen GameObjects an
	 * @param g
	 */
	void draw(Graphics g);
	
	/**
	 * Fügt dem Layer ein {@link GameObject} hinzu
	 * @param obj
	 */
	void addGameObject(GameObject obj);
	
	/**
	 * Fügt dem {@link Layer} alle {@link GameObject} Entitäten der übergebenen {@link Collection} hinzu
	 * @param gameObjects
	 */
	void addAllGameObjects(Collection<GameObject> gameObjects);
	
	/**
	 * Fügt bei dem naächsten Update das übergebene {@link GameObject}
	 * hinzu
	 * @param obj
	 */
	void addGameObjectAtUpdate(GameObject obj);
	
	/**
	 * Entfernt das übergebene {@link GameObject} aus dem Layer
	 * @param obj
	 */
	void removeGameObjectAtUpdate(GameObject obj);

	/**
	 * invoked das update aller updateable GameObjects
	 * zusätzlich müssen unter Umständen updates am Layer selbst durchgeführt werden
	 */
	void update();

	/**
	 * Prüft, ob der übergebene {@link Collidable} kollidiert ist
	 */
	void collisionCheck(Collidable c);

	/**
	 * Erzeugt eine instanz der Layerimplementierung auf Basis der übergebenen Map
	 * 
	 * @param layerMap
	 */
	void load(Map<String, Object> layerMap) throws Exception;
}

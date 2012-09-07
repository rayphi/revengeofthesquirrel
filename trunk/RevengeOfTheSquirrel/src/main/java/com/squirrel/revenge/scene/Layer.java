package com.squirrel.revenge.scene;

import java.awt.Graphics;

import com.squirrel.revenge.gameobject.Collidable;
import com.squirrel.revenge.gameobject.GameObject;
import com.squirrel.revenge.gameobject.Mobile;

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
	 * Fügt bei dem naächsten Update das übergebene {@link GameObject}
	 * hinzu
	 * @param obj
	 */
	void addGameObjectAtUpdate(GameObject obj);
	
	/**
	 * Entfernt das übergebene {@link GameObject} aus dem Layer
	 * @param obj
	 */
	void removeGameObject(GameObject obj);

	/**
	 * invoked das update aller updateable GameObjects
	 * zusätzlich müssen unter Umständen updates am Layer selbst durchgeführt werden
	 */
	void update();

	/**
	 * Bewegt alle {@link Mobile} {@link GameObject}s
	 */
	void move();

	/**
	 * Prüft, ob der übergebene {@link Collidable} kollidiert ist
	 */
	void collisionCheck(Collidable c);
}

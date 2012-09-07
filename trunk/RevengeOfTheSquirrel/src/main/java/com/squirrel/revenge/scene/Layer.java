package com.squirrel.revenge.scene;

import java.awt.Graphics;

import com.squirrel.revenge.gameobject.Collidable;
import com.squirrel.revenge.gameobject.GameObject;
import com.squirrel.revenge.gameobject.Mobile;

/**
 * Repr�sentiert einen Layer in der Scene
 * @author Shane
 *
 */
public interface Layer {

	/**
	 * Jeder Layer muss einen unique Namen bekommen, welchen man �ber
	 * diese Methode abfragn kann.
	 * @return
	 */
	String getName();
	
	/**
	 * St��t das zeichnen aller enthaltenen GameObjects an
	 * @param g
	 */
	void draw(Graphics g);
	
	/**
	 * F�gt dem Layer ein {@link GameObject} hinzu
	 * @param obj
	 */
	void addGameObject(GameObject obj);
	
	/**
	 * F�gt bei dem na�chsten Update das �bergebene {@link GameObject}
	 * hinzu
	 * @param obj
	 */
	void addGameObjectAtUpdate(GameObject obj);
	
	/**
	 * Entfernt das �bergebene {@link GameObject} aus dem Layer
	 * @param obj
	 */
	void removeGameObject(GameObject obj);

	/**
	 * invoked das update aller updateable GameObjects
	 * zus�tzlich m�ssen unter Umst�nden updates am Layer selbst durchgef�hrt werden
	 */
	void update();

	/**
	 * Bewegt alle {@link Mobile} {@link GameObject}s
	 */
	void move();

	/**
	 * Pr�ft, ob der �bergebene {@link Collidable} kollidiert ist
	 */
	void collisionCheck(Collidable c);
}

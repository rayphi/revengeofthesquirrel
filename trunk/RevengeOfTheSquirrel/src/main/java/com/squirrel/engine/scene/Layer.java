package com.squirrel.engine.scene;

import java.awt.Graphics;

import com.squirrel.engine.gameobject.Collidable;
import com.squirrel.engine.gameobject.GameObject;

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
	 * Gibt die Priorit�t des Layer zur�ck
	 * @return
	 */
	long getPriority();
	
	/**
	 * setzt die Priorit�t des Layer
	 */
	void setPriority(long prio);
	
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
	void removeGameObjectAtUpdate(GameObject obj);

	/**
	 * invoked das update aller updateable GameObjects
	 * zus�tzlich m�ssen unter Umst�nden updates am Layer selbst durchgef�hrt werden
	 */
	void update();

	/**
	 * Pr�ft, ob der �bergebene {@link Collidable} kollidiert ist
	 */
	void collisionCheck(Collidable c);
}

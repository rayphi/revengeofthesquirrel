package com.squirrel.engine.gameobject;

import java.awt.Rectangle;

import com.squirrel.engine.event.impl.CollisionEvent;

/**
 * Beschreibt, dass das entsprechende {@link GameObject} Physicalisch greifbar ist,
 * also ber�hrt werden kann.
 * 
 * @author Shane
 *
 */
public interface Collidable {

	/**
	 * Pr�ft, ob die beiden GameObjects miteinander Kollidieren
	 * @param c
	 * @return
	 */
	boolean collisionCheck(Collidable c);
	
	/**
	 * Gibt die Boundingboxes zur�ck (m�glicherweise mehrere)
	 * @return
	 */
	Rectangle[] getCollisionBoxes();
	
	/**
	 * Wird ausgel�st, wenn dieses GameObject mit einem
	 * anderen GameObject kollidiert
	 * @param cevt
	 */
	void onCollision(CollisionEvent cevt);
	
	double getPosx();
	double getPosy();
}

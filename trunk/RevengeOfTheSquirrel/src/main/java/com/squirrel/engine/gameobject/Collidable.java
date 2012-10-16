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
	 * 
	 * @param c
	 * @return
	 */
	boolean collisionCheck(Collidable c);
	
	/**
	 * Prüft eine Kollision mit dem einzelnen Rechteck
	 * 
	 * @param rect
	 * @return
	 */
	boolean collisionCheck(Rectangle rect);
	
	/**
	 * Gibt die Boundingboxes zur�ck (m�glicherweise mehrere)
	 * 
	 * @return
	 */
	Rectangle[] getCollisionBoxes();
	
	/**
	 * Wird ausgel�st, wenn dieses GameObject mit einem
	 * anderen GameObject kollidiert
	 * 
	 * @param cevt
	 */
	void onCollision(CollisionEvent cevt);
	
	/**
	 * Diese Methode wird aufgerufen, wenn klar ist, dass das Objekt im aktuellen Frame definitif nicht
	 * kollidiert ist. 
	 */
	void onNoCollision();
	
	/**
	 * Gibt die x-Koordinate der Position des Objektes zur�ck
	 * 
	 * @return
	 */
	double getPosx();
	
	/**
	 * 
	 * Gibt die y-Koordinate der Position des Objektes zur�ck
	 * 
	 * @return
	 */
	double getPosy();
}

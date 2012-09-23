package com.squirrel.engine.gameobject;

import java.awt.Graphics;

/**
 * Alle GameObjects die dieses interface implementieren lassen sich auf der Oberfläche
 * zeichnen.
 * 
 * @author Shane
 *
 */
public interface Drawable {

	/**
	 * Diese Methode zeichnet das GameObject auf der Oberfläche
	 * 
	 * @param g
	 */
	void draw(Graphics g);
}

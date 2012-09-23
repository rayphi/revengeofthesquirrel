package com.squirrel.engine.gameobject;

/**
 * Die Methode update aller Updateables wird direkt vor dem draw aller
 * Drawables ausgef�hrt
 * 
 * Updateable Objekte sind solche, die sich in irgendeiner Weise ver�ndern k�nnen.
 * Zum Beispiel durch Bewegung oder Ver�nderung der Erscheinung
 * 
 * @author Shane
 *
 */
public interface Updateable {

	/**
	 * F�hrt die aktualisierugn des Gameobjects durch
	 */
	void update();
}

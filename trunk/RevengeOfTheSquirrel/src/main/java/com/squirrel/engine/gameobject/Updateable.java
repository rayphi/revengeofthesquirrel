package com.squirrel.engine.gameobject;

/**
 * Die Methode update aller Updateables wird direkt vor dem draw aller
 * Drawables ausgeführt
 * 
 * Updateable Objekte sind solche, die sich in irgendeiner Weise verändern können.
 * Zum Beispiel durch Bewegung oder Veränderung der Erscheinung
 * 
 * @author Shane
 *
 */
public interface Updateable {

	/**
	 * Führt die aktualisierugn des Gameobjects durch
	 */
	void update();
}

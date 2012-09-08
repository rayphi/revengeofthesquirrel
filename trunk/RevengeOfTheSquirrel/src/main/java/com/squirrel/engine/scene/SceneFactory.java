package com.squirrel.engine.scene;

/**
 * Beschreibt die API der SceneFactory
 * 
 * @author Shane
 *
 */
public interface SceneFactory {

	/**
	 * Gibt die aktuelle Scene zur�ck, und erzeugt diese, falls dies noch nicht geschehen ist
	 * 
	 * @return
	 */
	Scene getCurrentScene();
	
	/**
	 * Leert die Scene vollst�ndig
	 */
	void clearScene();
}

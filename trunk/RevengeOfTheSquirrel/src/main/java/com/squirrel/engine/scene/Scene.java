package com.squirrel.engine.scene;

import java.awt.Graphics;

import com.squirrel.engine.gameobject.GameObject;
import com.squirrel.engine.layer.Layer;

/**
 * Beschreibt, was eine Scene k�nnen muss
 * @author Shane
 *
 */
public interface Scene {

	/**
	 * Einen {@link Layer} der {@link Scene} hinzuf�gen 
	 * @param name - Name des {@link Layer}
	 * @param priority - priorit�t des {@link Layer}
	 */
	void addLayer(String name, int priority);
	
	/**
	 * Einen {@link Layer} der {@link Scene} hinzuf�gen
	 * @param priority
	 * @param layer
	 */
	void addLayer(Layer layer);
	
	/**
	 * Ein GameObject einem expliziten {@link Layer} hinzuf�gen
	 * @param layername - Name des {@link Layer}
	 * @param obj - das {@link GameObject}
	 */
	void addGameObject(String layername, GameObject obj);
	
	/**
	 * Einen bestimmten {@link Layer} holen
	 * @param name - Name des {@link Layer}
	 * @return
	 */
	Layer getLayer(String name);
	
	/**
	 * Die {@link Scene} zeichnen
	 * @param g - Das {@link Graphics} Object
	 */
	void draw(Graphics g);

	/**
	 * invoked das update aller Updateables
	 */
	void update();
}

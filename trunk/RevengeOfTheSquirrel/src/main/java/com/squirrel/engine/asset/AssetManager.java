package com.squirrel.engine.asset;

import java.io.InputStream;

import com.squirrel.engine.asset.impl.SpriteAsset;

/**
 * Dieses Interface beschreibt die Funktionalit�t zum Laden und Verwalten von
 * Game Assets, also Dateien, welche hinzugeladen werden m�ssen.
 * 
 * @author Shane
 *
 */
public interface AssetManager {

	/**
	 * L�dt das gew�nschte Asset, falls noch nicht geschehen und gibt eine
	 * {@link Asset} Repr�sentation zur�ck.
	 * 
	 * @param identifier - Der Name unter dem das Asset Abrufbar sein soll
	 * @param path - Der Pfad zu der Datei, welche geladen werden soll
	 * @return {@link Asset} Repr�sentation zum Asset
	 */
	Asset load(String identifier, String path);

	/**
	 * Gibt das gew�nschte Asset zur�ck, wenn es bereits geladen wurde, sost null
	 * 
	 * @param identifier
	 * @return
	 */
	Asset load(String identifier);
	
	/**
	 * erzeugt ein SpriteAsset anhand des �bergebenen InputStreams
	 * 
	 * @param identifier
	 * @param in
	 * @return
	 */
	Asset loadSprite(String identifier, InputStream in);
	
	/**
	 * L�dt ein sortiertes und symmetrisches sprite sheet und erzeugt ein 
	 * Array aus den Einzelbildern
	 * 
	 * @param id
	 * @param path
	 * @param numSprites
	 * @param numRows
	 * @param numsCols
	 * @return
	 */
	SpriteAsset[] loadSpriteSheet(String id, String path, int numSprites, int numRows, int numsCols);
}

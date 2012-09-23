package com.squirrel.engine.asset;

import com.squirrel.engine.asset.impl.SpriteAsset;

/**
 * Dieses Interface beschreibt die Funktionalität zum Laden und Verwalten von
 * Game Assets, also Dateien, welche hinzugeladen werden müssen.
 * 
 * @author Shane
 *
 */
public interface AssetManager {

	/**
	 * Lädt das gewünschte Asset, falls noch nicht geschehen und gibt eine
	 * {@link Asset} Repräsentation zurück.
	 * 
	 * @param identifier - Der Name unter dem das Asset Abrufbar sein soll
	 * @param path - Der Pfad zu der Datei, welche geladen werden soll
	 * @return {@link Asset} Repräsentation zum Asset
	 */
	Asset load(String identifier, String path);

	/**
	 * Gibt das gewünschte Asset zurück, wenn es bereits geladen wurde, sost null
	 * 
	 * @param identifier
	 * @return
	 */
	Asset load(String identifier);
	
	/**
	 * Lädt ein sortiertes und symmetrisches sprite sheet und erzeugt ein 
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

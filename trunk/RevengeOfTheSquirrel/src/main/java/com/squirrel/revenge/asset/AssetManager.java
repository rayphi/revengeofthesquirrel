package com.squirrel.revenge.asset;

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

}

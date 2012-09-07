package com.squirrel.revenge.asset;

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

}

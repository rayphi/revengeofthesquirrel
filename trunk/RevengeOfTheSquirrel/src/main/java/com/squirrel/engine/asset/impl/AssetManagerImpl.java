package com.squirrel.engine.asset.impl;

import com.squirrel.engine.asset.Asset;
import com.squirrel.engine.asset.AssetManager;

/**
 * Implementiert das interface {@link AssetManager}
 * 
 * @author Shane
 *
 */
public class AssetManagerImpl implements AssetManager {

	@Override
	public Asset load(String identifier, String path) {
		// TODO pr�fen, ob es die Datei gibt
		// TODO Datentyp ermitteln (Image/Musikdatei...)
		// TODO in entsprechendes Asset umwandeln und asset zur�ckgeben
		
		// Wenn der Dateityp nicht unterst�tzt wird, dann null zur�ckgeben
		return null;
	}

}

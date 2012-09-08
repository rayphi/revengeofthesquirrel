package com.squirrel.engine.asset.impl;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

import org.apache.commons.io.FilenameUtils;

import com.squirrel.engine.asset.Asset;
import com.squirrel.engine.asset.AssetManager;

/**
 * Implementiert das interface {@link AssetManager}
 * 
 * @author Shane
 *
 */
public class AssetManagerImpl implements AssetManager {

	protected List<String> imageExtensions = Arrays.asList("png", "gif", "bmp", "jpg");
	private Map<String, Asset> assetStore ;
	
	public AssetManagerImpl() {
		assetStore = new HashMap<String, Asset>();
	}
	
	@Override
	public Asset load(String identifier, String path) {
		// prüfen, ob das Asset schon geladen wurde
		if (assetStore.containsKey(identifier))
			return assetStore.get(identifier);
		
		// prüfen, ob es die Datei gibt
		File file = new File("src/main/resources/" + path);
		if (!file.exists()) {
			System.out.println("File does not exsist: " + file.getAbsolutePath());
			return null;
		}
		
		// Datentyp ermitteln (Image/Musikdatei...) und
		// in entsprechendes Asset umwandeln und asset zurückgeben
		String extension = FilenameUtils.getExtension(file.getName());
		Asset asset = null;
		if (imageExtensions.contains(extension)) {
			asset = loadSprite(file);
		} else {
			System.out.println(extension + "is of unsupported Type");
		}
		
		// Wenn ein Asset geladen wurde, dann soll es in den Store gelegt werden
		if (asset != null)
			assetStore.put(identifier, asset);
		
		// Das geladene asset oder null zurückgeben
		return asset;
	}

	private Asset loadSprite(File file) {
		Image image = null ;
		
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new SpriteAsset(image);
	}

}

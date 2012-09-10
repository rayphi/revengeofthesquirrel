package com.squirrel.engine.asset.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
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

	/**
	 * Enthält die Extension aller zulässigen Image Formate
	 */
	protected List<String> imageExtensions = Arrays.asList("png", "gif", "bmp", "jpg");
	/**
	 * Enthält alle gecachten Assets
	 */
	protected Map<String, Asset> assetStore;

	public AssetManagerImpl() {
		assetStore = new HashMap<String, Asset>();
	}

	@Override
	public Asset load(String identifier, String path) {
		// Prüfen, ob es das Asset schon gibt und es ggf. zurückgeben
		Asset asset = load(identifier);
		if (asset != null) return asset;

		// prüfen, ob es die Datei gibt
		File file = new File("src/main/resources/" + path);
		if (!file.exists()) {
			System.out.println("File does not exsist: " + file.getAbsolutePath());
			return null;
		}

		// Datentyp ermitteln (Image/Musikdatei...) und
		// in entsprechendes Asset umwandeln und asset zurückgeben
		String extension = FilenameUtils.getExtension(file.getName());
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

	@Override
	public Asset load(String identifier) {
		Asset asset = null;

		if (assetStore.containsKey(identifier))
			asset = assetStore.get(identifier);

		return asset;
	}

	@Override
	public SpriteAsset[] loadSpriteSheet(String id, String path, int numSprites, int numRows, int numsCols) {
		// Spritesheet laden
		Asset asset = load(id, path);

		if (asset instanceof SpriteAsset) {
			SpriteAsset[] retArr = new SpriteAsset[numSprites];
			SpriteAsset spriteAsset = (SpriteAsset) asset;
			asset = null;

			int spriteWidth = spriteAsset.getImage().getWidth(null) / numsCols;
			int spriteHeight = spriteAsset.getImage().getHeight(null) / numRows;

			if (spriteAsset.getImage() instanceof BufferedImage) {
				for (int i = 0; i < numSprites; i++) {
					retArr[i] = new SpriteAsset(((BufferedImage)spriteAsset.getImage())
							.getSubimage((i%numsCols)*spriteWidth, (i/numRows)*spriteHeight, spriteWidth, spriteHeight));
				}
			} else {
				System.out.println("Not supported Image Type passed with SpriteAsset.");
			}

			return retArr;
		}

		return null;
	}

}

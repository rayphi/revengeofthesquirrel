package com.squirrel.engine.asset.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.squirrel.engine.asset.Asset;
import com.squirrel.engine.asset.AssetManager;

/**
 * Implementiert das interface {@link AssetManager.
 * Diese Implementierung l�dt und cached Assets.
 * 
 * @author Shane
 *
 */
public class AssetManagerImpl implements AssetManager {

	protected static final Logger logger = Logger.getLogger(AssetManagerImpl.class);
	
	/**
	 * Enth�lt die Extension aller zul�ssigen Image Formate
	 */
	protected List<String> imageExtensions = Arrays.asList("png", "gif", "bmp", "jpg");
	/**
	 * Enth�lt alle gecachten Assets
	 */
	protected Map<String, Asset> assetStore;

	/**
	 * Dieser Konstruktor initialisiert den assetStore
	 */
	public AssetManagerImpl() {
		assetStore = new HashMap<String, Asset>();
	}

	@Override
	public Asset load(String identifier, String path) {
		// Pr�fen, ob es das Asset schon gibt und es ggf. zur�ckgeben
		Asset asset = load(identifier);
		if (asset != null) return asset;

		// pr�fen, ob es die Datei gibt
		File file = new File("src/main/resources/" + path);
		if (!file.exists()) {
			logger.error("File does not exsist: " + file.getAbsolutePath());
			return null;
		}

		// Datentyp ermitteln (Image/Musikdatei...) und
		// in entsprechendes Asset umwandeln und asset zur�ckgeben
		String extension = FilenameUtils.getExtension(file.getName());
		if (imageExtensions.contains(extension)) {
			asset = loadSprite(file);
		} else {
			logger.error(extension + "is of unsupported Type");
		}

		// Wenn ein Asset geladen wurde, dann soll es in den Store gelegt werden
		if (asset != null)
			assetStore.put(identifier, asset);

		// Das geladene asset oder null zur�ckgeben
		return asset;
	}

	/**
	 * Eine Bilddatei aus einer Datei laden und in ein {@link SpriteAsset} legen
	 * 
	 * @param file
	 * @return
	 */
	private Asset loadSprite(File file) {
		Image image = null ;

		// Versuchen das Image aus der Datei zu laden
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			logger.error("Exception caught loading image file: ", e);
		}

		// Image in ein SpriteAsset kapseln und zur�ckgeben
		return new SpriteAsset(image);
	}

	@Override
	public Asset load(String identifier) {
		Asset asset = null;

		// Das gew�nschte Asset aus dem Store laden
		asset = assetStore.get(identifier);

		return asset;
	}

	/**
	 * L�dt zun�chst das Spritesheet als {@link SpriteAsset} und zerlegt dieses im Anschlu�
	 * in ein Array von Spriteassets.
	 * 
	 * Beachten: Diese Methode nimmt an, dass alle subsprites gleichgro� sind.
	 */
	@Override
	public SpriteAsset[] loadSpriteSheet(String id, String path, int numSprites, int numRows, int numsCols) {
		// Spritesheet laden
		Asset asset = load(id, path);

		// Pr�fen, ob es sich bei dem geladenen Asset wirklich um ein SpriteAsset handelt
		if (asset instanceof SpriteAsset) {
			SpriteAsset[] retArr = new SpriteAsset[numSprites];
			SpriteAsset spriteAsset = (SpriteAsset) asset;
			asset = null;

			// Dimension der einzelsprites berechnen
			int spriteWidth = spriteAsset.getImage().getWidth(null) / numsCols;
			int spriteHeight = spriteAsset.getImage().getHeight(null) / numRows;

			// Das Sprite zerlegen
			if (spriteAsset.getImage() instanceof BufferedImage) {
				for (int i = 0; i < numSprites; i++) {
					retArr[i] = new SpriteAsset(((BufferedImage)spriteAsset.getImage())
							.getSubimage((i%numsCols)*spriteWidth, (i/numRows)*spriteHeight, spriteWidth, spriteHeight));
				}
			} else {
				logger.error("Not supported Image Type passed with SpriteAsset.");
			}

			return retArr;
		}

		return null;
	}

	@Override
	public Asset loadSprite(String identifier, InputStream in) {
		// TODO Auto-generated method stub
		return null;
	}

}

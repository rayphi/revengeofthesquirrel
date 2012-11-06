package com.squirrel.engine.asset.impl;

import java.awt.Image;

import com.squirrel.engine.asset.Asset;

/**
 * Dieser AssetTyp ehntält ein Image
 * 
 * @author Shane
 *
 */
public class SpriteAsset extends Asset {

	/**
	 * enthält die Type Konstante des ImageAsset
	 */
	public static final String IMAGE_ASSET_TYPE = "image-asset-type";
	
	/**
	 * Das gehaltene Image
	 */
	private Image image;
	
	public SpriteAsset(Image image) {
		this.image = image;
	}
	
	@Override
	public String getType() {
		return IMAGE_ASSET_TYPE;
	}
	
	/**
	 * Gibt das Image zur�ck
	 * 
	 * @return
	 */
	public Image getImage() {
		return image;
	}
	
	/**
	 * Skaliert das Sprite
	 * 
	 * @param scale
	 */
	public void scale(double scale) {
		this.image = image.getScaledInstance((int)(image.getWidth(null) * scale), (int)(image.getHeight(null) * scale), Image.SCALE_DEFAULT);
	}
}

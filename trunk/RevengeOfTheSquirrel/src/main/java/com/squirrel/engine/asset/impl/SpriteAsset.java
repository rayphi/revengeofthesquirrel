package com.squirrel.engine.asset.impl;

import java.awt.Image;

import com.squirrel.engine.asset.Asset;

/**
 * Dieser AssetTyp ehnt�lt ein Image
 * 
 * @author Shane
 *
 */
public class SpriteAsset extends Asset {

	/**
	 * enth�lt die Type Konstante des ImageAsset
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
	 * @return
	 */
	public Image getImage() {
		return image;
	}
}

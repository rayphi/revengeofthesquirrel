package com.squirrel.revenge.asset.impl;

import java.awt.Image;

import com.squirrel.revenge.asset.Asset;

/**
 * Dieser AssetTyp ehnt�lt ein Image
 * 
 * @author Shane
 *
 */
public class ImageAsset extends Asset {

	/**
	 * enth�lt die Type Konstante des ImageAsset
	 */
	public static final String IMAGE_ASSET_TYPE = "image-asset-type";
	
	/**
	 * Das gehaltene Image
	 */
	private Image image;
	
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

package com.squirrel.engine.gameobject.impl;

import java.util.Map;

import com.squirrel.engine.asset.Asset;
import com.squirrel.engine.asset.AssetManager;
import com.squirrel.engine.asset.impl.SpriteAsset;
import com.squirrel.engine.scene.Layer;
import com.squirrel.engine.scene.impl.SceneArchiveConstants;
import com.squirrel.engine.utils.ApplicationUtils;

/**
 * ReprŠsentiert ein animiertes {@link SimplePhysicalGameObject}
 * 
 * @author Shane
 *
 */
public class AnimatedSimplePhysicalGameObject extends SimplePhysicalGameObject {

	/**
	 * Das sortierte Array mit den Einzelbildern der Animation
	 */
	protected SpriteAsset[] spriteArr;
	/**
	 * Die Dauer, fŸr die jedes Einzelbild angezeigt wird
	 */
	protected long frequency = 50;
	/**
	 * Speichert, wann die Animation das letzte Einzelbild aktiviert wurde.
	 */
	private long lastSwitch = System.currentTimeMillis();
	private int nextImage = 0;
	
	public AnimatedSimplePhysicalGameObject(String identifier) {
		super(identifier);
	}
	
	@Override
	public void update() {
		// Die Animation durchschalten
		if (System.currentTimeMillis() - lastSwitch >= frequency) {
			changeImage();
		}
		
		super.update();
	}

	private void changeImage() {
		// NŠchstes Einzelbild
		texture = spriteArr[nextImage++];
		if (nextImage >= spriteArr.length)
			nextImage = 0;
		lastSwitch = System.currentTimeMillis();
	}

	public SpriteAsset[] getSpriteArr() {
		return spriteArr;
	}

	/**
	 * Setzt das Array mit den Einzelbildern
	 * Gleichzeitig wird die textur auf das erste Einzelbild gesetzt
	 * 
	 * @param spriteArr
	 */
	public void setSpriteArr(SpriteAsset[] spriteArr) {
		this.spriteArr = spriteArr;
		if (spriteArr != null && spriteArr.length > 0)
			texture = spriteArr[0];
	}

	public long getFrequency() {
		return frequency;
	}

	/**
	 * Setzt die Frequenz mit der die Einzelbilder durchschalten.
	 * Default sind 50 Millisekunden
	 * @param frequency
	 */
	public void setFrequency(long frequency) {
		this.frequency = frequency;
	}
	
	/**
	 * Setzt den Index der Animationsserie bei dem die Animation beginnen soll.
	 * 
	 * @param startIndex
	 */
	public void setStartIndex(int startIndex) {
		nextImage = startIndex >= 0 && startIndex < spriteArr.length ? startIndex : 0;
		changeImage();
	}

	@Override
	protected void scaleTexture(double scale) {
	}
	
	@Override
	public void scale(double scale) {
		super.scale(scale);
		for (SpriteAsset sa : spriteArr) {
			sa.scale(scale);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void load(Map<String, Object> goMap) throws Exception {
		super.load(goMap);
		
		Map<String, Object> map = (Map<String, Object>) goMap.get(SceneArchiveConstants.MAP);
		if (map != null) {
			
			// TODO spriteArr (nur der Verwies auf das spritesheet)
			
			// TODO frequency
			// TODO nextImage
			
		} else {
			throw new Exception("Map with specifics missing.");
		}
	}
}

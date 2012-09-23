package com.squirrel.engine.gameobject.impl;

import com.squirrel.engine.asset.impl.SpriteAsset;
import com.squirrel.engine.scene.Layer;

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
	
	public AnimatedSimplePhysicalGameObject(String identifier, Layer parent) {
		super(identifier, parent);
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
}

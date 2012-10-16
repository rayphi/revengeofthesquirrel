package com.squirrel.engine.gameobject.impl;

import java.awt.Graphics;
import com.squirrel.engine.asset.impl.SpriteAsset;
import com.squirrel.engine.gameobject.Drawable;
import com.squirrel.engine.gameobject.GameObject;
import com.squirrel.engine.layer.Layer;
import com.squirrel.engine.statistics.PerformanceStatistics;
import com.squirrel.engine.utils.ApplicationUtils;

/**
 * Ein {@link Drawable}  {@link CollidableGameObject}
 * 
 * Hierbei handelt es sich um eine simple implementierung, welche eine Textur und ein
 * Array von Boundingboxes erlaubt.
 * 
 * @author Shane
 *
 */
public class DrawableCollidableGameObject extends CollidableGameObject implements Drawable {

	protected PerformanceStatistics ps = (PerformanceStatistics) ApplicationUtils.getInstance().getBean("performanceStatistics");
	/**
	 * Die Textur für dieses {@link GameObject}
	 */
	protected SpriteAsset texture;
	
	/**
	 * Der Konstruktor initialisiert das {@link DrawableCollidableGameObject}
	 * 
	 * @param identifier
	 * @param parent
	 */
	public DrawableCollidableGameObject(String identifier, Layer parent) {
		super(identifier, parent);
	}

	@Override
	public void draw(Graphics g) {
		// Wenn eine Textur gesetzt ist, dann soll diese gezeichnet
		if (texture != null)
			g.drawImage(texture.getImage(), (int) posx, (int) posy, null);
	}

	/**
	 * Di Textur des {@link DrawableCollidableGameObject} setzen
	 * 
	 * @param texture
	 */
	public void setTexture(SpriteAsset texture) {
		this.texture = texture;
	}
	
	/**
	 * Skaliert das {@link GameObject} entsprechend dem �bergebenen Wert
	 * 
	 * @param scale
	 */
	public void scale(double scale) {
		
		scaleTexture(scale);
		
		scaleSize(scale);
		
		scaleBoundingBoxes(scale);
	}

	/**
	 * Skaliert die Breiten und H�hen Angaben des {@link DrawableCollidableGameObject}
	 * 
	 * @param scale
	 */
	protected void scaleSize(double scale) {
		width *= scale;
		height *= scale;
	}

	/**
	 * Skaliert die Textur des {@link DrawableCollidableGameObject}
	 * 
	 * @param scale
	 */
	protected void scaleTexture(double scale) {
		texture.scale(scale);
	}
}

package com.squirrel.revenge.gameobject;

import java.awt.Rectangle;

import com.squirrel.engine.gameobject.impl.CollidableGameObject;
import com.squirrel.engine.layer.Layer;

/**
 * Stellt einfach nur eine Unsichtbare Wand dar.
 * 
 * @author mills
 *
 */
public class InvisibleWall extends CollidableGameObject {

	public InvisibleWall(String identifier, Layer parent) {
		super(identifier, parent);
	}

	@Override
	public void setDimension(int width, int height) {
		super.setDimension(width, height);
		
		collisionBoxes = new Rectangle[1];
		collisionBoxes[0] = new Rectangle(0,0,width,height);
	}
}

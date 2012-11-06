package com.squirrel.invaders.gameobjects;

import java.awt.Rectangle;
import java.util.UUID;

import com.squirrel.engine.asset.AssetManager;
import com.squirrel.engine.gameobject.impl.UpdateableDrawableCollidableGameObject;
import com.squirrel.engine.layer.Layer;
import com.squirrel.engine.utils.ApplicationUtils;

public class Explosion extends UpdateableDrawableCollidableGameObject {

	public Explosion(Layer parent) {
		super(UUID.randomUUID().toString(), parent);
		
		AssetManager am = (AssetManager) ApplicationUtils.getInstance().getBean("assetManager");
		
		this.spriteArr = am.loadSpriteSheet("explosion", "assets/images/explosion.png", 48, 6, 8);
		
		this.collisionBoxes = new Rectangle[spriteArr.length];
		for (int i=0; i<collisionBoxes.length; i++) {
			collisionBoxes[i] = new Rectangle(0,0,spriteArr[i].getImage().getWidth(null), spriteArr[i].getImage().getHeight(null));
		}
	}

	@Override
	protected void customUpdate() {
		if (nextImage == spriteArr.length - 1)
			parent.removeGameObjectAtUpdate(this);
	}
}

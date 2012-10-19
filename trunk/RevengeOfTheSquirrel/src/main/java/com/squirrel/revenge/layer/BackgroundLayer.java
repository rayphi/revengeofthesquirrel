package com.squirrel.revenge.layer;

import java.awt.Rectangle;

import com.squirrel.engine.asset.AssetManager;
import com.squirrel.engine.asset.impl.SpriteAsset;
import com.squirrel.engine.gameobject.impl.DrawableCollidableGameObject;
import com.squirrel.engine.layer.impl.LayerImpl;
import com.squirrel.engine.utils.ApplicationUtils;

public class BackgroundLayer extends LayerImpl {

	public BackgroundLayer() {
		super("background");
		
		setPriority(0);
		
		AssetManager am = (AssetManager) ApplicationUtils.getInstance().getBean("assetManager");
		DrawableCollidableGameObject bg = new DrawableCollidableGameObject("background", this);
		bg.setTexture((SpriteAsset) am.load("background_cartoonforest", "assets/images/cartoonforest.jpg"));
		Rectangle[] cb = {new Rectangle(0,0,1,1)};
		bg.setCollisionBoxes(cb);
		addGameObject(bg);
	}

}

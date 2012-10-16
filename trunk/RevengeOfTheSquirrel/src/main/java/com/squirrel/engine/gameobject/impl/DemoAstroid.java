package com.squirrel.engine.gameobject.impl;

import java.awt.Rectangle;

import com.squirrel.engine.asset.AssetManager;
import com.squirrel.engine.asset.impl.SpriteAsset;
import com.squirrel.engine.game.Configuration;
import com.squirrel.engine.layer.Layer;
import com.squirrel.engine.statistics.PerformanceStatistics;
import com.squirrel.engine.utils.ApplicationUtils;

public class DemoAstroid extends UpdateableDrawableCollidableGameObject {
	
	private double maxSpeed = 100;
	
	private double speed_x = 0;
	private double speed_y = 0;
	private Configuration config = (Configuration) ApplicationUtils.getInstance().getBean("configuration");
	
	public DemoAstroid(Layer parent) {
		super("DemoAstroid", parent);
		
		AssetManager am = (AssetManager) ApplicationUtils.getInstance().getBean("assetManager");

		SpriteAsset[] animArr = am.loadSpriteSheet("asteroid_sheet", "assets/spritesheets/asteroid.png", 64, 8, 8);
		setSpriteArr(animArr);
		if (animArr != null && animArr.length > 0) {
			Rectangle[] bboxes = {new Rectangle(10,4,animArr[0].getImage().getWidth(null) - 14, animArr[0].getImage().getHeight(null) - 20)};
			setCollisionBoxes(bboxes);
			setDimension(animArr[0].getImage().getWidth(null),animArr[0].getImage().getHeight(null));
		}
	}
	
	public void customUpdate() {
		
		if (ps.getFPS() == 0) return;
		
		posx = posx + (speed_x / ps.getFPS());
		posy = posy + (speed_y / ps.getFPS());
		
		if (posx + width <= 0) posx = config.getScreenWidth() - 1;
		if (posx >= config.getScreenWidth()) posx = -(width-1);
		if (posy + height <= 0) posy = config.getScreenHeight() - 1;
		if (posy >= config.getScreenHeight()) posy = -(height-1);
	}
	
	public void accelerateRight() {
		speed_x = maxSpeed;
	}
	
	public void decelerateRight() {
		speed_x = 0;
	}
	
	public void accelerateLeft() {
		speed_x = -maxSpeed;
	}
	
	public void decelerateLeft() {
		speed_x = 0;
	}
}

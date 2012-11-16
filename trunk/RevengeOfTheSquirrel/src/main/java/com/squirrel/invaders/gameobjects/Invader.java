package com.squirrel.invaders.gameobjects;

import java.awt.Rectangle;

import com.squirrel.engine.asset.AssetManager;
import com.squirrel.engine.asset.impl.SpriteAsset;
import com.squirrel.engine.event.impl.CollisionEvent;
import com.squirrel.engine.game.Configuration;
import com.squirrel.engine.gameobject.Collidable;
import com.squirrel.engine.gameobject.impl.UpdateableDrawableCollidableGameObject;
import com.squirrel.engine.layer.Layer;
import com.squirrel.engine.score.ScoreManager;
import com.squirrel.engine.statistics.PerformanceStatistics;
import com.squirrel.engine.utils.ApplicationUtils;

public class Invader extends UpdateableDrawableCollidableGameObject {
	
	private double maxSpeed = 40.0;
	
	private double sps = 4.0;
	private long lastShot = 0;
	
	public Invader(String identifier, Layer parent) {
		super(identifier, parent);
		
		ps = (PerformanceStatistics) ApplicationUtils.getInstance().getBean("performanceStatistics");
		config = (Configuration) ApplicationUtils.getInstance().getBean("configuration");
		
		AssetManager am = (AssetManager) ApplicationUtils.getInstance().getBean("assetManager");
		
		SpriteAsset[] sArr = {(SpriteAsset) am.load("invader", "assets/images/angry-cat-73x75.png")};
		this.spriteArr = sArr;
		
		Rectangle[] cbArr = {new Rectangle(0,0,sArr[0].getImage().getWidth(null),sArr[0].getImage().getHeight(null))};
		this.collisionBoxes = cbArr;
		
		setDimension(sArr[0].getImage().getWidth(null), sArr[0].getImage().getHeight(null));
		
	}

	@Override
	public void onCollision(CollisionEvent cevt) {
		super.onCollision(cevt);
		
		Collidable target = cevt.getTarget();
		
		if (target instanceof Projectile) {
			parent.removeGameObjectAtUpdate(this);
			
			ScoreManager sm = (ScoreManager) ApplicationUtils.getInstance().getBean("scoreManager");
			sm.addScore(100);
		}
	}
	
	@Override
	protected void customUpdate() {
		super.customUpdate();
		
		long fps = ps.getFPS();
		
		if (fps > 0) {
			posx -= maxSpeed / fps;
		}
	}
}

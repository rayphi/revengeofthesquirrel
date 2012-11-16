package com.squirrel.invaders.gameobjects;

import java.awt.Rectangle;

import com.squirrel.engine.asset.AssetManager;
import com.squirrel.engine.asset.impl.SpriteAsset;
import com.squirrel.engine.event.impl.CollisionEvent;
import com.squirrel.engine.gameobject.Collidable;
import com.squirrel.engine.gameobject.impl.UpdateableDrawableCollidableGameObject;
import com.squirrel.engine.layer.Layer;
import com.squirrel.engine.statistics.PerformanceStatistics;
import com.squirrel.engine.utils.ApplicationUtils;

public class Projectile extends UpdateableDrawableCollidableGameObject {

	private double movement = 300.0;
	
	private PerformanceStatistics ps = null;
	
	public Projectile(String identifier, Layer parent, double direction) {
		super(identifier, parent);
		
		movement = movement * direction;
		
		ps = (PerformanceStatistics) ApplicationUtils.getInstance().getBean("performanceStatistics");
		
		AssetManager am = (AssetManager) ApplicationUtils.getInstance().getBean("assetManager");
		
		SpriteAsset[] sArr = {(SpriteAsset) am.load("rocket", "assets/images/acorn.png")};
		spriteArr = sArr;
		
		Rectangle[] cbArr = {new Rectangle(0,0,sArr[0].getImage().getWidth(null), sArr[0].getImage().getHeight(null))};
		collisionBoxes = cbArr;
		
		movement = direction * movement;
	}
	
	@Override
	protected void customUpdate() {
		long fps = ps.getFPS();
		
		if (fps > 0) {
			posx += movement / ps.getFPS();
			
			if (config.getScreenWidth() < posx)
				parent.removeGameObjectAtUpdate(this);
		}
	}

	@Override
	public void onCollision(CollisionEvent cevt) {
		Collidable target = cevt.getTarget();
		
		if (target instanceof Explosion) {
			return;
		}
		
		// Explosion starten
		Explosion exp = new Explosion(parent);
		
		exp.setPosition(posx - ((exp.getWidth() / 2) + (width / 2)), 
						posy - ((exp.getHeight() / 2) + (height / 2)));
		parent.addGameObjectAtUpdate(exp);
		
		// Projektil entfernen
		parent.removeGameObjectAtUpdate(this);
	}
}

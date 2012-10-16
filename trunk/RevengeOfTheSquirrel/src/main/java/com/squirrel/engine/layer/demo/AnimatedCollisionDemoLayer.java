package com.squirrel.engine.layer.demo;

import java.awt.Rectangle;
import java.util.Random;

import com.squirrel.engine.asset.AssetManager;
import com.squirrel.engine.asset.impl.SpriteAsset;
import com.squirrel.engine.event.impl.CollisionEvent;
import com.squirrel.engine.game.Configuration;
import com.squirrel.engine.gameobject.GameObject;
import com.squirrel.engine.gameobject.impl.UpdateableDrawableCollidableGameObject;
import com.squirrel.engine.layer.impl.LayerImpl;
import com.squirrel.engine.utils.ApplicationUtils;

/**
 * 
 * 
 * @author Shane
 *
 */
public class AnimatedCollisionDemoLayer extends LayerImpl {

	private int numberTargets;
	private Random r;
	
	public AnimatedCollisionDemoLayer() {
		this(10);
	}
	
	public AnimatedCollisionDemoLayer(int count) {
		super("CollisionDemo");
		
		setPriority(100);
		
		numberTargets = count;
		r = new Random(System.currentTimeMillis());
		
		init();
	}

	private void init() {
		for (int i = 0; i < numberTargets; i++) {
			addGameObject(generateObj(String.valueOf(i)));
		}
	}
	
	private GameObject generateObj(String id) {
		final Configuration configuration = (Configuration) ApplicationUtils.getInstance().getBean("configuration");;
		AssetManager am = (AssetManager) ApplicationUtils.getInstance().getBean("assetManager");

		UpdateableDrawableCollidableGameObject obj = new UpdateableDrawableCollidableGameObject(id, this){
			private double maxSpeed = 100;
			private double speed_x = (r.nextDouble() * (maxSpeed * 2)) - maxSpeed;
			private double speed_y = (r.nextDouble() * (maxSpeed * 2)) - maxSpeed;
			private Configuration config = configuration;
			
			@Override
			public void customUpdate() {
				
				if (ps.getFPS() == 0) return;
				
				posx = posx + (speed_x / ps.getFPS());
				posy = posy + (speed_y / ps.getFPS());
				
				if ((posx) + width <= 0) posx = config.getScreenWidth() - 1;
				if ((posx) >= config.getScreenWidth()) posx = -(width-1);
				if ((posy) + height <= 0) posy = config.getScreenHeight() - 1;
				if ((posy) >= config.getScreenHeight()) posy = -(height-1);
			}
			
			@Override
			public void onCollision(CollisionEvent cevt) {
				super.onCollision(cevt);
				removeGameObjectAtUpdate(this);
				addGameObjectAtUpdate(generateObj(identifier));
			}
		};
		
		double minScale = 0.8, maxScale = 1.2;
		
//		obj.setTexture((SpriteAsset) am.load("rock", "assets/images/rock.png"));
		SpriteAsset[] animArr = am.loadSpriteSheet("asteroid_sheet", "assets/spritesheets/asteroid.png", 64, 8, 8);
		obj.setSpriteArr(animArr);
		if (animArr != null && animArr.length > 0) {
			Rectangle[] bboxes = {new Rectangle(10,4,animArr[0].getImage().getWidth(null) - 14, animArr[0].getImage().getHeight(null) - 20)};
			obj.setCollisionBoxes(bboxes);
			obj.setDimension(animArr[0].getImage().getWidth(null), animArr[0].getImage().getHeight(null));
		}
		obj.setStartIndex(r.nextInt(animArr.length));
		obj.scale(minScale + (r.nextDouble() * (maxScale - minScale)));
		obj.setPosition(r.nextDouble() * configuration.getScreenWidth(), r.nextDouble() * configuration.getScreenHeight());
		
		return obj;
	}
}

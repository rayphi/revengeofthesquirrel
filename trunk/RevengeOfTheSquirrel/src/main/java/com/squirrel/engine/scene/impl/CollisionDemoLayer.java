package com.squirrel.engine.scene.impl;

import java.awt.Rectangle;
import java.util.Random;

import com.squirrel.engine.asset.AssetManager;
import com.squirrel.engine.asset.impl.SpriteAsset;
import com.squirrel.engine.event.impl.CollisionEvent;
import com.squirrel.engine.game.Configuration;
import com.squirrel.engine.gameobject.GameObject;
import com.squirrel.engine.gameobject.impl.PhysicalGameObject;
import com.squirrel.engine.utils.ApplicationUtils;

public class CollisionDemoLayer extends LayerImpl {

	private int numberTargets;
	private Random r;
	
	public CollisionDemoLayer(int count) {
		super("CollisionDemo");
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
		
		PhysicalGameObject obj = new PhysicalGameObject(id, this){
			private double maxSpeed = 35;
			private double speed_x = (r.nextDouble() * (maxSpeed * 2)) - maxSpeed;
			private double speed_y = (r.nextDouble() * (maxSpeed * 2)) - maxSpeed;
			private Configuration config = configuration;
			
			@Override
			public void customUpdate() {
				
				if (ps.getFPS() == 0) return;
				
				posx = posx + (speed_x / ps.getFPS());
				posy = posy + (speed_y / ps.getFPS());
				
				if (posx + 5 <= 0) posx = config.getScreenWidth() - 1;
				if (posx >= config.getScreenWidth()) posx = -4;
				if (posy + 5 <= 0) posy = config.getScreenHeight() - 1;
				if (posy >= config.getScreenHeight()) posy = -4;
			}
			
			@Override
			public void onCollision(CollisionEvent cevt) {
				super.onCollision(cevt);
				removeGameObject(this);
				addGameObjectAtUpdate(generateObj(identifier));
			}
		};
		
		obj.setTexture((SpriteAsset) am.load("rock", "assets/images/rock.png"));
		Rectangle[] bboxes = {new Rectangle(0,0,15,14)};
		obj.setCollisionBoxes(bboxes);
		obj.setPosition(r.nextDouble() * configuration.getScreenWidth(), r.nextDouble() * configuration.getScreenHeight());
		
		return obj;
	}

}

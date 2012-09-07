package com.squirrel.revenge.scene.impl;

import java.awt.Rectangle;
import java.util.Random;

import com.squirrel.revenge.event.impl.CollisionEvent;
import com.squirrel.revenge.gameobject.GameObject;
import com.squirrel.revenge.gameobject.impl.PhysicalGameObject;

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
		final CollisionDemoLayer tis = this;
		PhysicalGameObject obj = new PhysicalGameObject(id){
			private double maxSpeed = 20;
			private double speed_x = (r.nextDouble() * (maxSpeed * 2)) - maxSpeed;
			private double speed_y = (r.nextDouble() * (maxSpeed * 2)) - maxSpeed;
			
			@Override
			public void move() {
				
				if (ps.getFPS() == 0) return;
				
				posx = posx + (speed_x / ps.getFPS());
				posy = posy + (speed_y / ps.getFPS());
				
				if (posx + 5 <= 0) posx = 799;
				if (posx > 799) posx = -4;
				if (posy + 5 <= 0) posy = 599;
				if (posy > 599) posy = -4;
				
				tis.collisionCheck(this);
			}
			
			@Override
			public void onCollision(CollisionEvent cevt) {
				super.onCollision(cevt);
				removeGameObject(this);
				addGameObjectAtUpdate(generateObj(identifier));
			}
		};
		
		Rectangle[] bboxes = {new Rectangle(0,0,5,5)};
		obj.setCollisionBoxes(bboxes);
		obj.setPosition(r.nextDouble() * 800, r.nextDouble() * 600);
		
		return obj;
	}

}

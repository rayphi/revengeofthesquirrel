package com.squirrel.invaders.gameobjects;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.UUID;

import com.squirrel.engine.asset.AssetManager;
import com.squirrel.engine.asset.impl.SpriteAsset;
import com.squirrel.engine.event.impl.CollisionEvent;
import com.squirrel.engine.game.Configuration;
import com.squirrel.engine.gameobject.Collidable;
import com.squirrel.engine.gameobject.impl.UpdateableDrawableCollidableGameObject;
import com.squirrel.engine.io.InputManager;
import com.squirrel.engine.io.KeyHandler;
import com.squirrel.engine.layer.Layer;
import com.squirrel.engine.statistics.PerformanceStatistics;
import com.squirrel.engine.utils.ApplicationUtils;

public class Squirrel extends UpdateableDrawableCollidableGameObject {

	private PerformanceStatistics ps = null;
	private Configuration config = null;
	
	private boolean movingUp = false;
	private boolean movingDown = false;
	private boolean shooting = false;
	
	private double maxSpeed = 200.0;
	
	private double sps = 4.0;
	private long lastShot = 0;
	
	public Squirrel(Layer parent) {
		super("Squirrel", parent);
		
		ps = (PerformanceStatistics) ApplicationUtils.getInstance().getBean("performanceStatistics");
		config = (Configuration) ApplicationUtils.getInstance().getBean("configuration");
		
		AssetManager am = (AssetManager) ApplicationUtils.getInstance().getBean("assetManager");
		
		SpriteAsset[] sArr = {(SpriteAsset) am.load("squirrel", "assets/images/Squirrel-75x75.png")};
		this.spriteArr = sArr;
		
		Rectangle[] cbArr = {new Rectangle(0,0,sArr[0].getImage().getWidth(null),sArr[0].getImage().getHeight(null))};
		this.collisionBoxes = cbArr;
		
		setDimension(sArr[0].getImage().getWidth(null), sArr[0].getImage().getHeight(null));
		
		initKeyMapping();
	}

	private void initKeyMapping() {
		InputManager im = (InputManager) ApplicationUtils.getInstance().getBean("inputManager");
		
		// Schießen
		im.addKeyMapping(KeyEvent.VK_SPACE, new KeyHandler() {
			@Override
			public void pressed(Integer keyCode) {
				startShooting();
			}
			@Override
			public void typed(Integer keyCode) { }
			@Override
			public void released(Integer keyCode) {
				stopShooting();
			}
		});
		
		// Nach oben
		im.addKeyMapping(KeyEvent.VK_UP, new KeyHandler() {

			@Override
			public void pressed(Integer keyCode) {
				startMovingUp();	
			}
			@Override
			public void typed(Integer keyCode) { }
			@Override
			public void released(Integer keyCode) {
				stopMovingUp();
			}
		});
		
		// Nach unten
		im.addKeyMapping(KeyEvent.VK_DOWN, new KeyHandler() {
			@Override
			public void pressed(Integer keyCode) {
				startMovingDown();	
			}
			@Override
			public void typed(Integer keyCode) { }
			@Override
			public void released(Integer keyCode) {
				stopMovingDown();
			}
		});
	}

	@Override
	protected void customUpdate() {
		long fps = ps.getFPS();
		if (fps > 0) {
			if (shooting) fire();
			
			double tempMovement = 0.0;
			
			if (movingDown) tempMovement += maxSpeed;
			if (movingUp) tempMovement -= maxSpeed;
			
			posy += tempMovement / fps;
			
			if (posy < 0) posy = 0;
			else if (posy > config.getScreenHeight() - height) posy = config.getScreenHeight() - height;
		}
	}

	@Override
	public void onCollision(CollisionEvent cevt) {
		Collidable target = cevt.getTarget();
		
		if (target instanceof Projectile) {
			// Man wurde durch ein Projektil getroffen
			
			// TODO Schaden nehmen etc.
		}
	}
	
	private void fire() {
		if (System.currentTimeMillis() - lastShot > 1000 / sps) {
			Projectile p = new Projectile(UUID.randomUUID().toString(), parent, 1.0);
			parent.addGameObjectAtUpdate(p);
			
			p.setPosition(posx + width, posy + (height / 2));
			lastShot = System.currentTimeMillis();
		}
	}
	
	public void startShooting() {
		shooting = true;
	}
	
	public void stopShooting() {
		shooting = false;
	}
	
	public void startMovingUp() {
		movingUp = true;
	}
	
	public void stopMovingUp() {
		movingUp = false;
	}
	
	public void startMovingDown() {
		movingDown = true;
	}
	
	public void stopMovingDown() {
		movingDown = false;
	}
}

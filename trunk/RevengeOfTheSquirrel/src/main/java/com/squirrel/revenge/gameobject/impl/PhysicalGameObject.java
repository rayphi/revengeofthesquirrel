package com.squirrel.revenge.gameobject.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import com.squirrel.revenge.asset.Asset;
import com.squirrel.revenge.asset.impl.ImageAsset;
import com.squirrel.revenge.event.impl.CollisionEvent;
import com.squirrel.revenge.game.GameManager;
import com.squirrel.revenge.gameobject.Collidable;
import com.squirrel.revenge.gameobject.Drawable;
import com.squirrel.revenge.gameobject.GameObject;
import com.squirrel.revenge.gameobject.Mobile;
import com.squirrel.revenge.gameobject.Updateable;
import com.squirrel.revenge.gameobject.handler.MovementHandler;
import com.squirrel.revenge.utils.SquirrelRevengeUtils;

public class PhysicalGameObject extends GameObject implements Collidable,
		Drawable, Updateable, Mobile {

	protected MovementHandler movementHandler;
	protected Asset asset;
	protected Rectangle[] collisionBoxes;
	
	private GameManager gm; 
	
	public PhysicalGameObject(String identifier) {
		super(identifier);
		
		gm = (GameManager) SquirrelRevengeUtils.getInstance().getBean("gameManager");
	}

	@Override
	public void move() {
	}

	@Override
	public void setMovementHandler(MovementHandler handler) {
		this.movementHandler = handler;
	}

	@Override
	public void update() {
	}

	@Override
	public void draw(Graphics g) {
		if (asset != null && asset.getType().equals(ImageAsset.IMAGE_ASSET_TYPE))
			g.drawImage(((ImageAsset)asset).getImage(), (int) posx, (int) posy, null);
		
		// Wenn im DebugMode, dann Boundingboxen zeichnen
		if (gm.isDebug()) {
			g.setColor(Color.yellow);
			for (Rectangle rect : collisionBoxes) {
				g.drawRect((int) (rect.x + posx), (int) (rect.y + posy), rect.width, rect.height);
			}
		}
	}

	@Override
	public boolean collisionCheck(Collidable c) {
		if (c == null) {
			return false;
		}
		
		for (Rectangle target_rect : c.getCollisionBoxes()) {
			for (Rectangle own_rect : collisionBoxes) {
				Rectangle rect1 = new Rectangle(target_rect);
				Rectangle rect2 = new Rectangle(own_rect);
				rect1.translate((int) c.getPosx(), (int) c.getPosy());
				rect2.translate((int) posx, (int) posy);
				if (rect1.intersects(rect2)) {
					c.onCollision(new CollisionEvent(this));
					this.onCollision(new CollisionEvent(c));
				}
			}
		}
		return false;
	}

	@Override
	public Rectangle[] getCollisionBoxes() {
		return collisionBoxes;
	}

	@Override
	public void onCollision(CollisionEvent cevt) {
	}

	/**
	 * Setzt die Kollisionsboxen
	 * @param collisionBoxes
	 */
	public void setCollisionBoxes(Rectangle[] collisionBoxes) {
		this.collisionBoxes = collisionBoxes;
	}

	public void setPosition(double x, double y) {
		this.posx = x;
		this.posy = y;
	}
}

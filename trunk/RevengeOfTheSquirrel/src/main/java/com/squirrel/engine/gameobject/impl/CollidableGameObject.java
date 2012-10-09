package com.squirrel.engine.gameobject.impl;

import java.awt.Rectangle;

import com.squirrel.engine.event.impl.CollisionEvent;
import com.squirrel.engine.game.GameManager;
import com.squirrel.engine.gameobject.Collidable;
import com.squirrel.engine.gameobject.GameObject;
import com.squirrel.engine.scene.Layer;
import com.squirrel.engine.utils.ApplicationUtils;

/**
 * TODO Dokumentieren
 * 
 * @author mills
 *
 */
public class CollidableGameObject extends GameObject implements Collidable {

	/**
	 * Die Boundingboxes
	 */
	protected Rectangle[] collisionBoxes;
	
	/**
	 * Eine Referenz auf den {@link GameManager}
	 */
	protected GameManager gm;
	
	/**
	 * Der Konstruktor initialisiert das {@link CollidableGameObject}
	 * 
	 * @param identifier
	 * @param parent
	 */
	public CollidableGameObject(String identifier, Layer parent) {
		super(identifier, parent);
		
		// Den GameManager aus dem Kontext laden
		gm = (GameManager) ApplicationUtils.getInstance().getBean("gameManager");
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

	/**
	 * Die Boundingboxes des {@link DrawableCollidableGameObject} skalieren
	 * 
	 * @param scale
	 */
	protected void scaleBoundingBoxes(double scale) {
		for (Rectangle r : collisionBoxes) {
			r.setLocation((int)(r.x * scale), (int)(r.y * scale));
			r.setSize((int)(r.width * scale), (int)(r.height * scale));
		}
	}

	@Override
	public boolean collisionCheck(Rectangle rect) {
		for (Rectangle bb : collisionBoxes) {
			Rectangle bbrect = new Rectangle(bb);
			bbrect.translate((int) posx, (int) posy);
			if (bbrect.intersects(rect))
				return true;
		}
		return false;
	}
}

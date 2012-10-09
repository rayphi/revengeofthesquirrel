package com.squirrel.revenge.gameobject;

import java.awt.Rectangle;
import java.util.Map;

import org.apache.log4j.Logger;

import com.squirrel.engine.asset.impl.SpriteAsset;
import com.squirrel.engine.event.impl.CollisionEvent;
import com.squirrel.engine.gameobject.Collidable;
import com.squirrel.engine.gameobject.GameObject;
import com.squirrel.engine.gameobject.impl.UpdateableDrawableCollidableGameObject;
import com.squirrel.engine.scene.Layer;

/**
 * TODO document
 * 
 * @author mills
 *
 */
public class RevengeSimpleGameObject extends UpdateableDrawableCollidableGameObject {

	private static Logger logger = Logger.getLogger(RevengeSimpleGameObject.class);
	
	/**
	 * Eine gefakte Gravitation
	 * TODO in irgendwelche Constants ausgliedern
	 */
	public static final double g = 5.0;
	
	/**
	 * Enthält alle möglichen Animationen des {@link GameObject} welche über
	 * einen eindeutigen Bezeichner abrufbar sind
	 */
	protected Map<String, SpriteAsset[]> animationMap;
	
	/**
	 * true wenn die figur auf festem Grund steht
	 */
	protected boolean grounded = false;
	
	protected boolean jumping = false;
	
	protected double jumpAdded = 0.0;
	
	protected double maxJumpHeight = 50.0;
	
	/**
	 * Die aktuelle horizontale Geschwindigkeit.
	 */
	protected double movementHorizontal = 0.0;
	/**
	 * Die aktuelle vertikale Geschwindigkeit
	 */
	protected double movementVertical = 0.0;
	
	protected double maxSpeed = 20.0;
	
	/**
	 * Die Beschleunigung gibt an, wie schnell die maximale Geschwindigkeit erreicht wird
	 */
	protected double acceleration = 0.4;
		
	public RevengeSimpleGameObject(String identifier, Layer parent) {
		super(identifier, parent);
	}

	@Override
	public void onCollision(CollisionEvent cevt) {
		Collidable c = cevt.getTarget();
		
		if (c instanceof InvisibleWall) {
			// Es gab eine collision mit einer Bande
			
			// TODO Wo liegt das andere Objekt? (drüber drunter...)
			Rectangle bottom = new Rectangle((int)posx, (int)posy + height, width, 1);
			// TODO wenn drunter, dann grounded = true setzen
			if (c.collisionCheck(bottom)) {
				grounded = true;
				jumping = false;
				if (movementVertical < 0) {
					movementVertical = 0;
				}
			}
			// TODO Auswirkung auf Bewegung bestimmen
				// Wenn grounded, dann keine negative vertikale bewegung...
		} else {
			logger.info("collided with instance of " + c.getClass().getCanonicalName());
		}
		
		customCollision(cevt);
	}
	
	/**
	 * Diese Methode kann überschrieben werden, wenn zusätzlich
	 * zur standard onCollision weitere Aufgaben ausgeführt werden sollen.
	 */
	protected void customCollision(CollisionEvent cevt) {
	}

	@Override
	public void customUpdate() {
		calculateAnimation();
		
		calculateMovement();
		
		move();
	}
	
	/**
	 * Soll die aktuelle Animation festlegen
	 */
	private void calculateAnimation() {
	}

	/**
	 * Hier wird das Object bewegt.
	 */
	protected void move() {		
		posx += movementHorizontal;
		posy -= movementVertical;
	}

	/**
	 * Diese Methode berechnet die vertikale und horizontale Bewegung
	 * Ist das Objekt nicht grounded, dann wird es sich durch die Konstante {@link #g}
	 * bedingt abwärts bewegen
	 */
	protected void calculateMovement() {
		if (!grounded) {
			if (ps.getFPS() > 0)
				movementVertical -= g / ps.getFPS();
		}
		
		if (jumping) {
			if (jumpAdded < maxJumpHeight) {
				grounded = false;
				double jumpFragment = (maxJumpHeight * acceleration) / ps.getFPS(); 
				jumpAdded += jumpFragment;
				movementVertical += jumpFragment;
			}
		}
	}

	/**
	 * Wechselt die Animation auf die Animation mit dem übergebenen Namen
	 * (falls vorhanden)
	 * @param name
	 */
	protected void switchAnimation(String name) {
		if (animationMap.containsKey(name)) {
			spriteArr = animationMap.get(name);
			nextImage = 0;
		} else {
			logger.warn("there is no animation with name '" + name +"'");
		}
	}
	
	/**
	 * Fügt die übergebene Animation hinzu.
	 * Wenn es bereits eine Animation unter dem übergebenen Namen gibt, dann wird diese überschrieben.
	 * 
	 * @param name
	 * @param animation
	 */
	protected void addAnimation(String name, SpriteAsset[] animation) {
		if (animationMap.containsKey(name)) {
			logger.warn("animation with name '" + name + "' allready exists. it will be overwritten.");
		}
		
		animationMap.put(name, animation);
	}

	public void jump() {
		jumping = true;
	}
}

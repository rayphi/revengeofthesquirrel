package com.squirrel.engine.gameobject.impl;

import java.awt.Rectangle;
import java.util.Map;

import org.apache.log4j.Logger;

import com.squirrel.engine.asset.AssetManager;
import com.squirrel.engine.asset.impl.SpriteAsset;
import com.squirrel.engine.event.impl.CollisionEvent;
import com.squirrel.engine.game.Configuration;
import com.squirrel.engine.gameobject.Collidable;
import com.squirrel.engine.gameobject.GameObject;
import com.squirrel.engine.layer.Layer;
import com.squirrel.engine.statistics.PerformanceStatistics;
import com.squirrel.engine.utils.ApplicationUtils;
import com.squirrel.revenge.gameobject.InvisibleWall;

public class DemoSquirrel extends UpdateableDrawableCollidableGameObject {
	

	private static Logger logger = Logger.getLogger(DemoSquirrel.class);
	
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
	
	private double maxSpeed = 100;
	
	private double speed_x = 0;
	private double speed_y = 0;
	
	/**
	 * true wenn die figur auf festem Grund steht
	 */
	protected boolean grounded = false;
	
	protected boolean jumping = false;
	protected double jumpStartY = 0;
	protected double maxJumpHeight = 100.0;
	protected double jumpSpeed = 20.0;
	
	/**
	 * Die aktuelle horizontale Geschwindigkeit.
	 */
	protected double movementHorizontal = 0.0;
	/**
	 * Die aktuelle vertikale Geschwindigkeit
	 */
	protected double movementVertical = 0.0;
	
	/**
	 * Die Beschleunigung gibt an, wie schnell die maximale Geschwindigkeit erreicht wird
	 */
	protected double acceleration = 0.4;
	
	private Configuration config = (Configuration) ApplicationUtils.getInstance().getBean("configuration");
	
	public DemoSquirrel(Layer parent) {
		super("Squirrel", parent);
		
		AssetManager am = (AssetManager) ApplicationUtils.getInstance().getBean("assetManager");

		SpriteAsset[] animArr = {(SpriteAsset) am.load("squirrel", "assets/images/Squirrel-75x75.png")};
		setSpriteArr(animArr);
		if (animArr != null && animArr.length > 0) {
			Rectangle[] bboxes = {new Rectangle(0,0,animArr[0].getImage().getWidth(null), animArr[0].getImage().getHeight(null))};
			setCollisionBoxes(bboxes);
			setDimension(animArr[0].getImage().getWidth(null),animArr[0].getImage().getHeight(null));
		}
	}
	
	@Override
	public void onCollision(CollisionEvent cevt) {
		Collidable c = cevt.getTarget();
		
		// TODO Wenn das Objekt irgendwo gegen spring -> sprung abbrechen
		
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
	
	@Override
	public void onNoCollision() {
		grounded = false;
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
		long fps = ps.getFPS();
		if (fps != 0) {
			posx += movementHorizontal / fps;
			posy -= movementVertical;
		}
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
		
		// Wenn sich das Objekt gerade in einem Sprung befindet
		if (jumping) {
			// Wenn die Maximale Sprunghöhe erreicht wurde 
			if (jumpStartY - posy >= maxJumpHeight) {
				movementVertical = 0.0;
				jumping = false;
			} 
			// Wenn die maximale Sprunghöhe noch nicht erreicht wurde
			else {
				grounded = false;
				double jumpFragment = (maxJumpHeight * acceleration) / ps.getFPS(); 
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

	/**
	 * Initiiert eine Sprung des Objektes.
	 * Dies funktioniert nur, wenn das Objekt gerade auf etwas steht oder liegt, also grounded == true erfüllt ist
	 */
	public void jump() {
		if (grounded) {
			jumping = true;
			jumpStartY = posy;
		}
	}
	
	public void accelerateRight() {
		movementHorizontal = maxSpeed;
	}
	
	public void decelerateRight() {
		movementHorizontal = 0;
	}
	
	public void accelerateLeft() {
		movementHorizontal = -maxSpeed;
	}
	
	public void decelerateLeft() {
		movementHorizontal = 0;
	}
}

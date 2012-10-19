package com.squirrel.engine.layer.demo;

import java.awt.Point;
import java.awt.event.KeyEvent;

import com.squirrel.engine.gameobject.impl.DemoSquirrel;
import com.squirrel.engine.gameobject.impl.InteractingSimpleGameObject;
import com.squirrel.engine.gameobject.impl.TextualGameObject;
import com.squirrel.engine.io.InputManager;
import com.squirrel.engine.io.KeyHandler;
import com.squirrel.engine.layer.impl.LayerImpl;
import com.squirrel.engine.utils.ApplicationUtils;
import com.squirrel.revenge.gameobject.InvisibleWall;

public class SimpleKeyHandlerDemoLayer extends LayerImpl {

	private final TextualGameObject tgo;
	private final DemoSquirrel obj;
	
	private InputManager im;
	
	public SimpleKeyHandlerDemoLayer() {
		super("SimpleKeyHandlerDemoLayer");
		
		setPriority(101);
		im = (InputManager) ApplicationUtils.getInstance().getBean("inputManager");
		
		tgo = new TextualGameObject("KeyInteractionDisplay", new Point(100, 100), "no key interaction", this);
		addGameObject(tgo);
		
		obj = new DemoSquirrel(this);
		addGameObject(obj);
		
		initWalls();
		
		initKeyMapping();
	}
	
	private void initWalls() {
		InvisibleWall iw_ground = new InvisibleWall("wall", this);
		iw_ground.setPosition(0, 560);
		iw_ground.setDimension(1280, 10);
		addGameObject(iw_ground);
		
		InvisibleWall iw_wallLeft = new InvisibleWall("leftWall", this);
		iw_wallLeft.setPosition(0, 0);
		iw_wallLeft.setDimension(3, 559);
		addGameObject(iw_wallLeft);
	}

	private void initKeyMapping() {
		im.addKeyMapping(KeyEvent.VK_LEFT, new KeyHandler() {
			@Override
			public void pressed(Integer keyCode) {
				obj.accelerateLeft();
			}
			@Override
			public void typed(Integer keyCode) {
				// nothing
			}
			@Override
			public void released(Integer keyCode) {
				obj.decelerateLeft();
			}
		});
		
		im.addKeyMapping(KeyEvent.VK_RIGHT, new KeyHandler() {
			@Override
			public void pressed(Integer keyCode) {
				obj.accelerateRight();
			}
			@Override
			public void typed(Integer keyCode) {
				// nothing
			}
			@Override
			public void released(Integer keyCode) {
				obj.decelerateRight();
			}
		});
		
		im.addKeyMapping(KeyEvent.VK_SPACE, new KeyHandler() {
			@Override
			public void pressed(Integer keyCode) {
				obj.jump();
			}
			@Override
			public void typed(Integer keyCode) {
			}
			@Override
			public void released(Integer keyCode) {
			}
		});
			
		im.addKeyMapping(InputManager.DEFAULT, new KeyHandler() {

			@Override
			public void pressed(Integer keyCode) {
				tgo.setMsg(keyCode + " pressed");
			}

			@Override
			public void typed(Integer keyCode) {
				// nothing
			}

			@Override
			public void released(Integer keyCode) {
				tgo.setMsg(keyCode + " released");
			}
		});
	}

}

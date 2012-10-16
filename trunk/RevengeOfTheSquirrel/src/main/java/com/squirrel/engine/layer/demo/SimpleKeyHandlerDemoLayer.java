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
		
		InvisibleWall iw = new InvisibleWall("wall", this);
		iw.setPosition(0, 250);
		iw.setDimension(300, 20);
		addGameObject(iw);
		
		initKeyMapping();
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

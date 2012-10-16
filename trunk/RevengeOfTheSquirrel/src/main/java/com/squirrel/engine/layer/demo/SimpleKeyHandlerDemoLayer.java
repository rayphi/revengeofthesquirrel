package com.squirrel.engine.layer.demo;

import java.awt.Point;
import java.awt.event.KeyEvent;

import com.squirrel.engine.gameobject.impl.DemoAstroid;
import com.squirrel.engine.gameobject.impl.TextualGameObject;
import com.squirrel.engine.io.InputManager;
import com.squirrel.engine.io.KeyHandler;
import com.squirrel.engine.layer.impl.LayerImpl;
import com.squirrel.engine.utils.ApplicationUtils;

public class SimpleKeyHandlerDemoLayer extends LayerImpl {

	private final TextualGameObject tgo;
	private final DemoAstroid obj;
	
	private InputManager im;
	
	public SimpleKeyHandlerDemoLayer() {
		super("SimpleKeyHandlerDemoLayer");
		
		setPriority(1000);
		im = (InputManager) ApplicationUtils.getInstance().getBean("inputManager");
		
		tgo = new TextualGameObject("KeyInteractionDisplay", new Point(100, 100), "no key interaction", this);
		addGameObject(tgo);
		
		obj = new DemoAstroid(this);
		addGameObject(obj);
		
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

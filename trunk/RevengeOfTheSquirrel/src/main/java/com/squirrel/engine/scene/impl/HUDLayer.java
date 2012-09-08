package com.squirrel.engine.scene.impl;

import java.awt.Point;

import com.squirrel.engine.gameobject.impl.TextualGameObject;

public class HUDLayer extends LayerImpl {
	
	public HUDLayer() {
		super("HUD");
	
		
		if (gm.isDebug())
			initDebugHUD();
	}

	private void initDebugHUD() {
		// FPS ausgeben
		addGameObject(new TextualGameObject("fps", new Point(30, 100), null, this){
			@Override
			public void update() {
				super.update();
				msg = String.valueOf(ps.getFPS()) + " FPS";
			}
		});
	}

}

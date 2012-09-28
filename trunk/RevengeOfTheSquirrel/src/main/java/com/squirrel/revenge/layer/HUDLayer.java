package com.squirrel.revenge.layer;

import java.awt.Point;

import com.squirrel.engine.gameobject.impl.TextualGameObject;
import com.squirrel.engine.scene.impl.LayerImpl;

public class HUDLayer extends LayerImpl {
	
	public HUDLayer() {
		super("HUD");
	
		setPriority(10000);
		
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

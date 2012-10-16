package com.squirrel.revenge.layer;

import java.awt.Point;

import com.squirrel.engine.game.Configuration;
import com.squirrel.engine.gameobject.impl.TextualGameObject;
import com.squirrel.engine.scene.impl.LayerImpl;
import com.squirrel.engine.utils.ApplicationUtils;
import com.squirrel.revenge.score.Score;

public class HUDLayer extends LayerImpl {
	
	public HUDLayer() {
		super("HUD");
	
		setPriority(10000);
		
		if (gm.isDebug())
			initDebugHUD();
		
		InitScoreHUD();
		
		// Hinzufügen des Score
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
	
	private void InitScoreHUD(){
		Configuration conf = (Configuration) ApplicationUtils.getInstance().getBean("configuration");
				
		// Score wird im Bild 90% vom linken Rand angezeigt		
		int posX =	(conf.getScreenWidth() / 100) * 90;
		// Score wird im Bild 5% vom oberen Rand angezeigt
		int posY =  (conf.getScreenHeight() / 100) * 5;
		
		Score score = new Score();
		addGameObject(new TextualGameObject("score", new Point(posX,posY), String.valueOf(score.getScore()), this));
		
	}
	
	

}

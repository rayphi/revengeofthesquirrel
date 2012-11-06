package com.squirrel.invaders.layer;

import java.awt.Point;

import com.squirrel.engine.game.Configuration;
import com.squirrel.engine.gameobject.impl.TextualGameObject;
import com.squirrel.engine.layer.impl.LayerImpl;
import com.squirrel.engine.utils.ApplicationUtils;
import com.squirrel.revenge.score.ScoreManager;

public class HUDLayer extends LayerImpl {

	public HUDLayer() {
		super("HUD");
		
		setPriority(10000);
		
		init();
	}

	private void init() {
		Configuration conf = (Configuration) ApplicationUtils.getInstance().getBean("configuration");
		
		// Score wird im Bild 90% vom linken Rand angezeigt		
		int posX =	(conf.getScreenWidth() / 100) * 90;
		// Score wird im Bild 5% vom oberen Rand angezeigt
		int posY =  (conf.getScreenHeight() / 100) * 5;
		
		// Den Score anzeigen
		addGameObject(new TextualGameObject("score", new Point(posX,posY), null, this){
			ScoreManager sm = (ScoreManager) ApplicationUtils.getInstance().getBean("scoreManager");
			@Override
			public void update() {
				super.update();
				msg = String.valueOf(sm.getScore());
			}
		});
	}

}

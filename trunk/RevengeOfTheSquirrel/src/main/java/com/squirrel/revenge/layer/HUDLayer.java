package com.squirrel.revenge.layer;

import java.awt.Point;

import com.squirrel.engine.game.Configuration;
import com.squirrel.engine.gameobject.impl.TextualGameObject;
import com.squirrel.engine.layer.impl.LayerImpl;
import com.squirrel.engine.score.ScoreManager;
import com.squirrel.engine.utils.ApplicationUtils;

/**
 * Dieser Layer realisiert ein HUD
 * 
 * @author Shane, Andreas
 *
 */
public class HUDLayer extends LayerImpl {
	
	public HUDLayer() {
		super("HUD");
	
		// Die h�chste Priorit�t wird als letztes gezeichnet und liegt somit ganz oben,
		// deswegen bekommt dieser Layer eine extrem hohe Prio
		setPriority(10000);
		
		// Wenn der debug-Modus aktiviert ist, sollen die Dbug-Anzeigen initialisiert werden
		if (gm.isDebug())
			initDebugHUD();
		
		// Score-Anzeigen initialisieren
		InitScoreHUD();
	}

	/**
	 * Initialisiert ein paar Debug-Anzeigen
	 */
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
	
	/**
	 * Initialisiert die Score-related Anzeigen
	 */
	private void InitScoreHUD(){
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

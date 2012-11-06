package com.squirrel.invaders;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.squirrel.engine.asset.AssetManager;
import com.squirrel.engine.game.Configuration;
import com.squirrel.engine.game.GameManager;
import com.squirrel.engine.io.InputManager;
import com.squirrel.engine.scene.Scene;
import com.squirrel.engine.scene.SceneFactory;
import com.squirrel.engine.utils.ApplicationUtils;
import com.squirrel.invaders.layer.GameLayer;
import com.squirrel.invaders.layer.HUDLayer;

public class Main {

	/**
	 * Der Startpunkt für squirrel invaders
	 * @param args
	 */
	public static void main(String[] args) {
		// Den ApplicationContext initialisieren
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/com/squirrel/revenge/applicationContext.xml");
		// Die Singletoninstanz der Utils initialisieren
		new ApplicationUtils(ctx);
		
		// Den GameManager aus dem Context holen
		GameManager gm = (GameManager) ApplicationUtils.getInstance().getBean("gameManager");
		// Die SceneFactory aus dem Context holen
		SceneFactory sf = (SceneFactory) ApplicationUtils.getInstance().getBean("sceneFactory");
		// InputManager aus dem Context laden
		InputManager im = (InputManager) ApplicationUtils.getInstance().getBean("inputManager");
		AssetManager am = (AssetManager) ApplicationUtils.getInstance().getBean("assetManager");
		// Die Configuration aus dem Context laden
		Configuration config = (Configuration) ApplicationUtils.getInstance().getBean("configuration");
		
		config.setScreenHeight(600);
		config.setScreenWidth(800);
		
		Scene scene = sf.getCurrentScene();
		
		// HUD hinzufügen
		scene.addLayer(new HUDLayer());
		// GameLayer hinzufügen
		scene.addLayer(new GameLayer());
		
		gm.start();
	}

}

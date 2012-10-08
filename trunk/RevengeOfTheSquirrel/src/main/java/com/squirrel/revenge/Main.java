package com.squirrel.revenge;
import java.awt.event.KeyEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.squirrel.engine.game.Configuration;
import com.squirrel.engine.game.GameManager;
import com.squirrel.engine.io.InputManager;
import com.squirrel.engine.io.KeyHandler;
import com.squirrel.engine.scene.Scene;
import com.squirrel.engine.scene.SceneFactory;
import com.squirrel.engine.scene.impl.AnimatedCollisionDemoLayer;
import com.squirrel.engine.scene.impl.SimpleKeyHandlerDemoLayer;
import com.squirrel.engine.utils.ApplicationUtils;
import com.squirrel.revenge.layer.BackgroundLayer;
import com.squirrel.revenge.layer.HUDLayer;


public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
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
		// Die Configuration aus dem Context laden
		Configuration config = (Configuration) ApplicationUtils.getInstance().getBean("configuration");
		
		{ // Änderungen an der Konfiguration vornehmen
			
		}
		
		// Die aktuelle Szene holen
		Scene currentScene = sf.getCurrentScene();
				
		// TODO rausnehmen, nur zu demo zwecken
		currentScene.addLayer(new AnimatedCollisionDemoLayer(10));
		currentScene.addLayer(new SimpleKeyHandlerDemoLayer());
		
		{ // Layer des Spiels hinzufügen
			currentScene.addLayer(new HUDLayer());
			currentScene.addLayer(new BackgroundLayer());
		}
		
		{ // keyMappings
			// Pausen handler
			im.addKeyMapping(KeyEvent.VK_P, new KeyHandler() {

				@Override
				public void pressed(Integer keyCode) { }

				@Override
				public void typed(Integer keyCode) { }

				@Override
				public void released(Integer keyCode) {
					// Pause toggeln
					GameManager gm = (GameManager) ApplicationUtils.getInstance().getBean("gameManager");
					gm.triggerPause();
				}
			});
		}
		
		// Dem GameManager sagen, er soll das Spiel starten
		gm.start();
	}

}

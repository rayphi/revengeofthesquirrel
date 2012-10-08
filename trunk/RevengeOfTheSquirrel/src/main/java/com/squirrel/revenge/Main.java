package com.squirrel.revenge;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.squirrel.engine.game.Configuration;
import com.squirrel.engine.game.GameManager;
import com.squirrel.engine.scene.Scene;
import com.squirrel.engine.scene.SceneFactory;
import com.squirrel.engine.scene.impl.AnimatedCollisionDemoLayer;
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
		// Die Configuration aus dem Context laden
		Configuration config = (Configuration) ApplicationUtils.getInstance().getBean("configuration");
		
		// Die aktuelle Szene holen
		Scene currentScene = sf.getCurrentScene();
		
		{ // TODO Dieser Code soll durch die Engine selber durchgeführt werden
			// Ein Panel erzeugen, auf dem wir das Spiel zeihnen werden
			JPanel gamePanel = new JPanel(true);
			gamePanel.setSize(config.getScreenWidth(), config.getScreenHeight());
			
			// Einen Frame erzeugen, der das Panel enthalten soll
			JFrame frame = new JFrame();
			frame.setSize(config.getScreenWidth(), config.getScreenHeight() + frame.getInsets().top);
			frame.setTitle("Bitch, please..." + (gm.isDebug() ? "(DEBUG)" : ""));
			frame.add(gamePanel);
			frame.setResizable(false);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
			// Das Graphicsobjekt des Panel dem Gamemanager �bergeben
			gm.setGraphics(gamePanel.getGraphics());
		}
		
		// TODO rausnehmen, nur zu demo zwecken
		currentScene.addLayer(new HUDLayer());
		currentScene.addLayer(new AnimatedCollisionDemoLayer(10));
		currentScene.addLayer(new BackgroundLayer());
		
		// Dem GameManager sagen, er soll das Spiel starten
		gm.start();
	}

}

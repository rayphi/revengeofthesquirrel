package com.squirrel.engine;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.squirrel.engine.game.GameManager;
import com.squirrel.engine.scene.impl.CollisionDemoLayer;
import com.squirrel.engine.utils.SquirrelRevengeUtils;


public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		// Den ApplicationContext initialisieren
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/com/squirrel/revenge/applicationContext.xml");
		// Die Singletoninstanz der Utils initialisieren
		new SquirrelRevengeUtils(ctx);
		
		// Den GameManager aus dem Context holen
		GameManager gm = (GameManager) SquirrelRevengeUtils.getInstance().getBean("gameManager");
		
		// Ein Panel erzeugen, auf dem wir das Spiel zeihnen werden
		JPanel gamePanel = new JPanel(true);
		gamePanel.setSize(800,  600);
		
		// Einen Frame erzeugen, der das Panel enthalten soll
		JFrame frame = new JFrame();
		frame.setSize(800, 600 + frame.getInsets().top);
		frame.setTitle("Bitch, please..." + (gm.isDebug() ? "(DEBUG)" : ""));
		frame.add(gamePanel);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Das Graphicsobjekt des Panel dem Gamemanager Ÿbergeben
		gm.setGraphics(gamePanel.getGraphics());
		
		// TODO rausnehmen, nur zu demo zwecken
		gm.addLayer(new CollisionDemoLayer(1000));
		
		// Dem GameManager sagen, er soll das Spiel starten
		gm.start();
	}

}

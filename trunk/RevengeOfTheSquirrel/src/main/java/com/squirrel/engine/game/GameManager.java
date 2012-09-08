package com.squirrel.engine.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.springframework.beans.factory.annotation.Autowired;

import com.squirrel.engine.scene.Scene;
import com.squirrel.engine.scene.impl.CollisionDemoLayer;
import com.squirrel.engine.scene.impl.HUDLayer;
import com.squirrel.engine.scene.impl.SceneImpl;
import com.squirrel.engine.statistics.PerformanceStatistics;

/**
 * Der GameManager steuert den Ablauf des Spieles
 * @author Shane
 *
 */
public class GameManager {

	private boolean debug = false;
	private Graphics g;
	private BufferedImage buffer;
	private Scene scene;
	private boolean running;
	@Autowired PerformanceStatistics ps;
	
	public GameManager() {
		this.g = null;
		running = true;
		scene = new SceneImpl();
	}

	/**
	 * Durch Aufruf dieser Funktion nimmt der GameManager seine Funktion auf,
	 * wodurch das Spiel startet
	 * @param g
	 */
	public void start() {
		// TODO initGame
		initHUD();
		// TODO Menü
		
		// Die Spielschleife starten
		gameLoop();
	}

	private void gameLoop() {
		// Die PerformanceStatistics initialisieren
		ps.init();
		
		while (running) {
			// TODO process input
			
			// update all updateables
			scene.update();
			
			// Frame zeichnen (doublebuffered)
			buffer = new BufferedImage(800, 600, BufferedImage.TYPE_3BYTE_BGR);
			scene.draw(buffer.getGraphics());
			g.drawImage(buffer, 0, 0, null);			
			
			// Frame zählen
			ps.countFrame();
		}
	}
	
	private void initHUD() {
		scene.addLayer(0, new HUDLayer());
	}
	
	/**
	 * Den Graphics Kontext setzen, in dem das Spiel ablaufen soll
	 * @param g
	 */
	public void setGraphics(Graphics g) {
		this.g = g;
	}
	
	/**
	 * Den Debugmodus aktivieren/deaktivieren
	 * @param debug true = aktivieren; false = deaktivieren
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	/**
	 * Gibt zurück, ob sich das Spiel im Debug-Modus befindet
	 * @return
	 */
	public boolean isDebug() {
		return debug;
	}

	public void addLayer(CollisionDemoLayer collisionDemoLayer) {
		scene.addLayer(0, collisionDemoLayer);
	}
}

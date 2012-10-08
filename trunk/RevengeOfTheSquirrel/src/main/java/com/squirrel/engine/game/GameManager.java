package com.squirrel.engine.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;

import com.squirrel.engine.io.InputManager;
import com.squirrel.engine.scene.SceneFactory;
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
	private boolean running;
	private boolean paused;

	@Autowired PerformanceStatistics ps;
	@Autowired SceneFactory sf;
	@Autowired Configuration config;
	@Autowired InputManager im;

	private JFrame frame;
	private JPanel gamePanel;

	public GameManager() {
		this.g = null;
		running = true;
		paused = false;
	}

	/**
	 * Durch Aufruf dieser Funktion nimmt der GameManager seine Funktion auf,
	 * wodurch das Spiel startet
	 * @param g
	 */
	public void start() {
		// Spiel initialisieren
		init();

		// TODO Menü

		// Die Spielschleife starten
		gameLoop();
	}

	/**
	 * Initialisiert das Spiel.
	 * Dazu gehören folgende Punkte:
	 *   - Swingkomponenten initialisieren
	 */
	private void init() {
		// Die GUI initialisieren
		initGUI(); 
	}

	private void initGUI() {
		// Ein Panel erzeugen, auf dem wir das Spiel zeihnen werden
		gamePanel = new JPanel(true);
		gamePanel.setSize(config.getScreenWidth(), config.getScreenHeight());

		// Einen Frame erzeugen, der das Panel enthalten soll
		frame = new JFrame();
		frame.setSize(config.getScreenWidth(), config.getScreenHeight() + frame.getInsets().top);
		frame.setTitle(config.getTitle() + (debug ? "(DEBUG)" : ""));
		frame.add(gamePanel);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// KeyListener anmelden
		frame.addKeyListener(im.createKeyListener());

		// Das Graphicsobjekt des Panel merken
		g = gamePanel.getGraphics();
	}

	/**
	 * Erneuert die GUI. Muss aufgerufen werden, falls Änderungen an
	 * der {@link Configuration} gemacht wurden, um diese Änderungen
	 * für ein aktuell laufendes Spiel zu übernehmen.
	 */
	public void resetGUI() {
		// Wenn es bereits einen Frame gibt muss diese unsichtbar gemacht werden, bevor er
		// neu initialisiert wird.
		if (frame != null && frame.isVisible()) {
			frame.setVisible(false);
		}
		
		// Swing Komponenten überschreiben
		initGUI();
	}

	private void gameLoop() {
		// Die PerformanceStatistics initialisieren
		ps.init();

		while (running) {
			while (paused) {
				// TODO Pausenbildschirm etc...
			}

			while (!paused) {
				// TODO process input

				// update all updateables
				sf.getCurrentScene().update();

				// Frame zeichnen (doublebuffered)
				buffer = new BufferedImage(config.getScreenWidth(), config.getScreenHeight(), BufferedImage.TYPE_3BYTE_BGR);
				sf.getCurrentScene().draw(buffer.getGraphics());
				g.drawImage(buffer, 0, 0, null);			

				// Frame z�hlen
				ps.countFrame();
			}
		}
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
	 * Gibt zur�ck, ob sich das Spiel im Debug-Modus befindet
	 * @return
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * Pausiert/depausiert die Spielschleife.
	 * 
	 * Es kann nur von einem Modus in den anderen gewechselt werden.
	 */
	public void triggerPause() {
		this.paused = !this.paused;
	}
}

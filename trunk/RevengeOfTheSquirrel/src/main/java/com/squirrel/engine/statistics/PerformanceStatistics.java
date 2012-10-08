package com.squirrel.engine.statistics;

/**
 * Erfasst Daten zur Analyse der Performance
 * 
 * @author Shane
 *
 */
public class PerformanceStatistics {

	private long gameStartet;
	
	private long fps;
	private long timesCounted;
	
	private long overallframes;
	
	private long frameCount;
	private long frameCountStarted;
	
	public PerformanceStatistics() {
		gameStartet = System.currentTimeMillis();
	}

	/**
	 * Gibt die aktuelle Framerate aus
	 * @return
	 */
	public long getFPS() {
		return fps;
	}
	
	public void countFrame() {
		frameCount++;
		overallframes++;
		
		if (System.currentTimeMillis() - frameCountStarted >= 1000) {
			fps = frameCount;
			frameCount = 0;
			frameCountStarted = System.currentTimeMillis();
			timesCounted++;
		}
	}

	public long getTimeRunning() {
		return System.currentTimeMillis() - gameStartet;
	}

	public void init() {
		frameCountStarted = System.currentTimeMillis();
	}

	/**
	 * Diese Methode kann aufgerufen werden, um unterbrechungen während der
	 * Spielschleife zu kompensieren. Hierdurch werden massive Sprünge bei
	 * den FPS verhindert.
	 */
	public void compensateFPS() {
		fps = overallframes / timesCounted;
		frameCount = 0;
		frameCountStarted = System.currentTimeMillis();
	}
}

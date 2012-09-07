package com.squirrel.revenge.statistics;

/**
 * Erfasst Daten zur Analyse der Performance
 * @author Shane
 *
 */
public class PerformanceStatistics {

	private long gameStartet;
	
	private long fps;
	
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
		
		if (System.currentTimeMillis() - frameCountStarted >= 1000) {
			fps = frameCount;
			frameCount = 0;
			frameCountStarted = System.currentTimeMillis();
		}
	}

	public long getTimeRunning() {
		return System.currentTimeMillis() - gameStartet;
	}

	public void init() {
		frameCountStarted = System.currentTimeMillis();
	}
}

package com.squirrel.engine.game;

/**
 * Enthält einige Konfigurationen zum SPiel
 * 
 * @author Shane
 *
 */
public class Configuration {

	protected int screenWidth;
	protected int screenHeight;
	
	/**
	 * der default Konstruktor initialisiert alle default werte
	 */
	public Configuration() {
		screenWidth = 800;
		screenHeight = 600;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
}

package com.squirrel.engine.game;

/**
 * Enth�lt einige Konfigurationen zum SPiel
 * 
 * @author Shane
 *
 */
public class Configuration {

	protected int screenWidth;
	protected int screenHeight;
	protected String title;

	/**
	 * der default Konstruktor initialisiert alle default werte
	 */
	public Configuration() {
		screenWidth = 1280;
		screenHeight = 955;
		title = "Bitch, please...";
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

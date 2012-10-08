package com.squirrel.engine.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Diese Klasse kümmert sich um die Verarbeitung von Benutzereingaben
 * 
 * @author Shane
 *
 */
public class InputManager {
	
	private static Logger logger = Logger.getLogger(InputManager.class);
	
	private Map<Integer, KeyHandler> keyMap;
	
	/**
	 * Stellt eine Bridge für Key-Eingaben auf Basis eines {@link KeyListener} zur Verfügung
	 * @return
	 */
	public KeyListener createKeyListener() {
		return new InputManagerKeyBridge(this);
	}
	
	public void keyPressed(KeyEvent e) {
		Integer keyCode = e.getKeyCode();
		
		// Wenn ein handler für den keyCode definiert wurde...
		if (keyMap.containsKey(keyCode)) {
			KeyHandler handler = keyMap.get(keyCode);
			// ..dem handler sagen, dass er gepressed wurde
			handler.pressed();
		}
	}

	public void keyTyped(KeyEvent e) {
		Integer keyCode = e.getKeyCode();
		
		// Wenn ein handler für den keyCode definiert wurde...
		if (keyMap.containsKey(keyCode)) {
			KeyHandler handler = keyMap.get(keyCode);
			// ..dem handler sagen, dass er getyped wurde
			handler.typed();
		}
	}

	public void keyReleased(KeyEvent e) {
		Integer keyCode = e.getKeyCode();
		
		// Wenn ein handler für den keyCode definiert wurde...
		if (keyMap.containsKey(keyCode)) {
			KeyHandler handler = keyMap.get(keyCode);
			// ..dem handler sagen, dass er released wurde
			handler.released();
		}
	}
	
	/**
	 * Über diese Methode kann ein {@link KeyHandler} für einen bestimmten keyCode
	 * angemeldet werden.
	 * 
	 * @param keyCode
	 * @param keyHandler
	 */
	public void addKeyMapping(Integer keyCode, KeyHandler keyHandler) {
		if (keyMap.containsKey(keyCode)) {
			logger.warn("A keyHandler for keyCode " + keyCode + " allready exists. It will be overritten.");
		}
		
		keyMap.put(keyCode, keyHandler);
	}
}

package com.squirrel.engine.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Diese Bridge leitet einfach alle Benutzereingaben 
 * der Tastatur an den InputManager weiter.
 * @author mills
 *
 */
public class InputManagerKeyBridge implements KeyListener {

	private InputManager im;
	
	public InputManagerKeyBridge(InputManager im) {
		this.im = im;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		im.keyPressed(e);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		im.keyReleased(e);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		im.keyTyped(e);
	}
}
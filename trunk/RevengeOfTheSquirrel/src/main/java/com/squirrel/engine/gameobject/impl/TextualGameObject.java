package com.squirrel.engine.gameobject.impl;

import java.awt.Graphics;
import java.awt.geom.Point2D;

import com.squirrel.engine.gameobject.Drawable;
import com.squirrel.engine.gameobject.GameObject;
import com.squirrel.engine.gameobject.Updateable;
import com.squirrel.engine.scene.Layer;

/**
 * Ein {@link Drawable} und {@link Updateable} {@link GameObject}
 * 
 * Dies repräsentiert explizit ein textuelles Object
 * 
 * @author Shane
 *
 */
public class TextualGameObject extends GameObject implements Drawable, Updateable {

	/**
	 * Die Textnachricht welche dargestellt werden soll
	 */
	protected String msg = "";
	
	/**
	 * Initialisiert das textuelle {@link GameObject}
	 * 
	 * @param id
	 * @param pos
	 * @param msg
	 * @param parent
	 */
	public TextualGameObject(String id, Point2D pos, String msg, Layer parent) {
		super(id, parent);
		posx = pos.getX();
		posy = pos.getY();
		this.msg = msg;
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawString(msg, (int) posx, (int) posy);
	}

	@Override
	public void update() {
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}

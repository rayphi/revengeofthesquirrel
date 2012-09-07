package com.squirrel.revenge.gameobject.impl;

import java.awt.Graphics;
import java.awt.geom.Point2D;

import com.squirrel.revenge.gameobject.Drawable;
import com.squirrel.revenge.gameobject.GameObject;
import com.squirrel.revenge.gameobject.Updateable;

public class TextualGameObject extends GameObject implements Drawable, Updateable {

	protected String msg = "";
	
	public TextualGameObject(String id, Point2D pos, String msg) {
		super(id);
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

}

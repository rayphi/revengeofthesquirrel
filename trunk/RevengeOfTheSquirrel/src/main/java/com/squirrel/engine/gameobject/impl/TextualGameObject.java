package com.squirrel.engine.gameobject.impl;

import java.awt.Graphics;
import java.awt.geom.Point2D;

import com.squirrel.engine.gameobject.Drawable;
import com.squirrel.engine.gameobject.GameObject;
import com.squirrel.engine.gameobject.Updateable;
import com.squirrel.engine.scene.Layer;

public class TextualGameObject extends GameObject implements Drawable, Updateable {

	protected String msg = "";
	
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

}

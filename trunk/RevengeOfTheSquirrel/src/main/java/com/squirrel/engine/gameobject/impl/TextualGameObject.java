package com.squirrel.engine.gameobject.impl;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.Map;

import com.squirrel.engine.gameobject.Drawable;
import com.squirrel.engine.gameobject.GameObject;
import com.squirrel.engine.gameobject.Updateable;
import com.squirrel.engine.scene.impl.SceneArchiveConstants;

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

	@SuppressWarnings("unchecked")
	@Override
	public void load(Map<String, Object> goMap) throws Exception {
		super.load(goMap);
		
		Map<String, Object> map = (Map<String, Object>) goMap.get(SceneArchiveConstants.MAP);
		if (map != null) {
			
			// msg
			String msg = (String) map.get("msg");
			if (msg != null) {
				this.msg = msg;
			}
		}
	}
}

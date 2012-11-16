package com.squirrel.invaders.layer;

import java.util.Random;

import com.squirrel.engine.game.Configuration;
import com.squirrel.engine.gameobject.GameObject;
import com.squirrel.engine.layer.impl.LayerImpl;
import com.squirrel.engine.utils.ApplicationUtils;
import com.squirrel.invaders.gameobjects.Invader;
import com.squirrel.invaders.gameobjects.Squirrel;

public class GameLayer extends LayerImpl {

	private int numInvaders = 30;
	
	private Random r = new Random(System.currentTimeMillis());
	private Configuration config = (Configuration) ApplicationUtils.getInstance().getBean("configuration");
	
	public GameLayer() {
		super("GameLayer");
		
		Squirrel s = new Squirrel(this);
		
		addGameObject(s);
		
		addInvader(r.nextDouble());
	}

	private void addInvader(double id) {
		int ypos = r.nextInt(config.getScreenHeight() - 75);
		addGameObject(createInvader("invader_"+id, config.getScreenWidth(), ypos));
	}
	
	private GameObject createInvader(String name, int pos_x, int pos_y) {
		GameObject invader = new Invader(name, this);
		
		invader.setPosition(pos_x, pos_y);
		
		return invader;
	}

	@Override
	public void update() {
		super.update();
		
		if (content.size() < numInvaders) {
			addInvader(r.nextDouble());
		}
	}
}

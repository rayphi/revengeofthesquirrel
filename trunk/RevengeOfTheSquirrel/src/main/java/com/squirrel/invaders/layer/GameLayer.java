package com.squirrel.invaders.layer;

import com.squirrel.engine.layer.impl.LayerImpl;
import com.squirrel.invaders.gameobjects.Squirrel;

public class GameLayer extends LayerImpl {

	private int numInvaders = 3;
	
	public GameLayer() {
		super("GameLayer");
		
		Squirrel s = new Squirrel(this);
		
		addGameObject(s);
		
		addInvaders();
	}

	private void addInvaders() {
		// TODO add Invaders
	}

}

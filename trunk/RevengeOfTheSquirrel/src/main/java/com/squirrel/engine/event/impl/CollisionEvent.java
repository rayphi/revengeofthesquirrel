package com.squirrel.engine.event.impl;

import com.squirrel.engine.event.SquirrelEvent;
import com.squirrel.engine.gameobject.Collidable;

/**
 * 
 * @author Shane
 *
 */
public class CollisionEvent implements SquirrelEvent {

	private Collidable triggerer;
	
	public CollisionEvent(Collidable c) {
		this.triggerer = c;
	}
	
	@Override
	public Collidable getTarget() {
		return triggerer;
	}

	@Override
	public String getType() {
		return "CollisionEvent";
	}

}

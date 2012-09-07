package com.squirrel.revenge.event.impl;

import com.squirrel.revenge.event.SquirrelEvent;
import com.squirrel.revenge.gameobject.Collidable;

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

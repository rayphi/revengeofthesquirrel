package com.squirrel.revenge.gameobject;

import com.squirrel.revenge.gameobject.handler.MovementHandler;

/**
 * Alle beweglichen {@link GameObject} Entit�ten
 * @author Shane
 *
 */
public interface Mobile {

	void move();
	void setMovementHandler(MovementHandler handler);
}

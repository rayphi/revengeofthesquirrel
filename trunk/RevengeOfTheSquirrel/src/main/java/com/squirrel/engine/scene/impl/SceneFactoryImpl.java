package com.squirrel.engine.scene.impl;

import com.squirrel.engine.scene.Scene;
import com.squirrel.engine.scene.SceneFactory;

/**
 * Die SceneFactory h�lt eine Referenz auf die aktuelle {@link Scene} und 
 * erm�glicht gewisse Operationen auf dieser.
 * 
 * @author Shane
 *
 */
public class SceneFactoryImpl implements SceneFactory {

	protected Scene currentScene;
	
	@Override
	public Scene getCurrentScene() {
		if (currentScene == null)
			currentScene = new SceneImpl();
		return currentScene;
	}

	@Override
	public void clearScene() {
		currentScene = null;
	}

}

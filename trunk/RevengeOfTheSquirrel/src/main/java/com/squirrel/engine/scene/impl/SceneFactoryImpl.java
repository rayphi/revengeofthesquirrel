package com.squirrel.engine.scene.impl;

import com.squirrel.engine.scene.Scene;
import com.squirrel.engine.scene.SceneFactory;

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

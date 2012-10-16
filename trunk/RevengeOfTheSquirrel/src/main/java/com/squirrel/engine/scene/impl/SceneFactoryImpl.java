package com.squirrel.engine.scene.impl;

import com.squirrel.engine.scene.Scene;
import com.squirrel.engine.scene.SceneFactory;
import com.squirrel.engine.scene.SceneLoader;

/**
 * Die SceneFactory hält eine Referenz auf die aktuelle {@link Scene} und 
 * ermöglicht gewisse Operationen auf dieser.
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

	@Override
	public Scene loadScene(String pathToArchive, String sceneName) {
		SceneLoader sl = new SceneLoader(pathToArchive);
		return sl.loadScene(sceneName);
	}

}

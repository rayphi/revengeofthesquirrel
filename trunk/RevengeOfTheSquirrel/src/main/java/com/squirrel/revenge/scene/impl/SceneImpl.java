package com.squirrel.revenge.scene.impl;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import com.squirrel.revenge.gameobject.GameObject;
import com.squirrel.revenge.scene.Layer;
import com.squirrel.revenge.scene.Scene;

public class SceneImpl implements Scene {

	private List<Layer> layers;

	public SceneImpl() {
		layers = new ArrayList<Layer>();
	}
	
	@Override
	public void addLayer(String name, int priority) {
		addLayer(priority, new LayerImpl(name));
	}

	@Override
	public void addGameObject(String layername, GameObject obj) {
		Layer layer = getLayer(layername);
		if (layer != null)
			layer.addGameObject(obj);
	}

	@Override
	public Layer getLayer(String name) {
		for (Layer sl : layers) {
			if (sl.getName().equals(name))
				return sl;
		}
		return null;
	}

	@Override
	public void draw(Graphics g) {
		for (int i=0; i < layers.size(); i++) {
			layers.get(i).draw(g);
		}
	}

	@Override
	public void update() {
		for (int i=0; i < layers.size(); i++) {
			layers.get(i).update();
		}
	}

	@Override
	public void move() {
		for (int i=0; i < layers.size(); i++) {
			layers.get(i).move();
		}
	}

	@Override
	public void addLayer(int priority, Layer layer) {
		layers.add(priority, layer);
	}
	
}

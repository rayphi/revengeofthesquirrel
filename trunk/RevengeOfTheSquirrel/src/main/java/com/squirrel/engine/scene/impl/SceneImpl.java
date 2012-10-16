package com.squirrel.engine.scene.impl;

import java.awt.Graphics;
import java.util.Arrays;

import com.squirrel.engine.gameobject.GameObject;
import com.squirrel.engine.layer.Layer;
import com.squirrel.engine.layer.impl.LayerImpl;
import com.squirrel.engine.scene.Scene;

public class SceneImpl implements Scene {

	//private List<Layer> layers;
	private Layer[] layers;

	public SceneImpl() {
	}
	
	@Override
	public void addLayer(String name, int priority) {
		Layer layer = new LayerImpl(name);
		layer.setPriority(priority);
		addLayer(layer);
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
		for (int i=0; i < layers.length; i++) {
			layers[i].draw(g);
		}
	}

	@Override
	public void update() {
		for (Layer layer : layers) {
			layer.update();
		}
	}

	@Override
	public void addLayer(Layer layer) {
		
		if (layers == null || layers.length == 0) {
			layers = new Layer[1];
			layers[0] = layer;
			return;
		}
		
		Layer[] newLayers = new Layer[layers.length+1];
		int insertIndex = 0;
		boolean newInserted = false;
		for (int j=0; j<=layers.length; j++) {
			if (!newInserted && (j==layers.length || layers[j].getPriority() >= layer.getPriority())) {
				newLayers[insertIndex++] = layer;
				newInserted = true;
				j--;
			} else if (j<layers.length){
				newLayers[insertIndex++] = layers[j];			
			}
		}
		
		layers = Arrays.copyOf(newLayers, newLayers.length);
	}
	
}

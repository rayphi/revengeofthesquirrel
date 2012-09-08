package com.squirrel.engine.gameobject;

import com.squirrel.engine.scene.Layer;

/**
 * Repräsentiert ein beliebiges Objekt im Spiel, z.B die Spielfigur, oder ein Element
 * des HUD.
 * 
 * @author Shane
 *
 */
public abstract class GameObject {

	protected double posx;
	protected double posy;
	protected String identifier;
	protected Layer parent;
	
	public GameObject(String identifier, Layer parent) {
		this.identifier = identifier;
		this.parent = parent;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public double getPosx() {
		return posx;
	}
	public double getPosy() {
		return posy;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameObject)
			if (((GameObject)obj).getIdentifier().equals(identifier))
				return true;
		return false;
	}
	
	@Override
	public String toString() {
		return identifier;
	}
	
	@Override
	public int hashCode() {
		return identifier.hashCode();
	}
}

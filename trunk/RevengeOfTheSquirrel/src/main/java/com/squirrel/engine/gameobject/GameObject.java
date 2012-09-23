package com.squirrel.engine.gameobject;

import com.squirrel.engine.scene.Layer;

/**
 * Repräsentiert ein beliebiges Objekt im Spiel, z.B die Spielfigur, oder ein Element
 * des HUD. Auch unsichtbare Objekte sind denkbar, wie zum Beispiel unsichtbare Wände oder
 * Töne.
 * 
 * @author Shane
 *
 */
public abstract class GameObject {

	/**
	 * die x-Koordinate der Position dieses Objektes
	 */
	protected double posx;
	/**
	 * die y-Koordinate der Position dieses Objektes
	 */
	protected double posy;
	/**
	 * Über diesen Bezeichner lässt sich das Objekt identifizieren
	 */
	protected String identifier;
	/**
	 * Der Layer, auf dem sich dieses Objekt befindet
	 */
	protected Layer parent;
	
	/**
	 * Initialisiert ein GameObject
	 * 
	 * @param identifier
	 * @param parent
	 */
	public GameObject(String identifier, Layer parent) {
		this.identifier = identifier;
		this.parent = parent;
	}
	
	/**
	 * Gibt den Bezeichner zurück
	 * 
	 * @return
	 */
	public String getIdentifier() {
		return identifier;
	}
	
	/**
	 * Gibt die x-Koordinate der Position zurück
	 * 
	 * @return
	 */
	public double getPosx() {
		return posx;
	}
	
	/**
	 * Gibt die x-Koordinate der Position zurück
	 * 
	 * @return
	 */
	public double getPosy() {
		return posy;
	}
	
	/**
	 * GameObjects werden über ihren Identifier verglichen
	 */
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

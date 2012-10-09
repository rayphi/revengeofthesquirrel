package com.squirrel.engine.gameobject;

import com.squirrel.engine.scene.Layer;

/**
 * Repr�sentiert ein beliebiges Objekt im Spiel, z.B die Spielfigur, oder ein Element
 * des HUD. Auch unsichtbare Objekte sind denkbar, wie zum Beispiel unsichtbare W�nde oder
 * T�ne.
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
	
	protected int width;

	protected int height;
	/**
	 * �ber diesen Bezeichner l�sst sich das Objekt identifizieren
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
	 * Gibt den Bezeichner zur�ck
	 * 
	 * @return
	 */
	public String getIdentifier() {
		return identifier;
	}
	
	/**
	 * Gibt die x-Koordinate der Position zur�ck
	 * 
	 * @return
	 */
	public double getPosx() {
		return posx;
	}
	
	/**
	 * Gibt die x-Koordinate der Position zur�ck
	 * 
	 * @return
	 */
	public double getPosy() {
		return posy;
	}
	
	/**
	 * GameObjects werden �ber ihren Identifier verglichen
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
	
	public void setPosition(double x, double y) {
		this.posx = x;
		this.posy = y;
	}
	
	public void setDimension(int width, int height) {
		this.width = width;
		this.height = height;
	}
}

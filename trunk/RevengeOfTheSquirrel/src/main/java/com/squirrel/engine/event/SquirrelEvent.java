package com.squirrel.engine.event;

/**
 * Alle im Spiel erzeugten Events müssen von diesem interface erben
 * @author Shane
 *
 */
public interface SquirrelEvent {

	/**
	 * Gibt das Auslösende Object zurück
	 * @return
	 */
	Object getTarget();
	
	/**
	 * Gibt eine String Repräsentation des Events zurück
	 * @return
	 */
	String getType();
}

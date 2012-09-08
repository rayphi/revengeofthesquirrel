package com.squirrel.engine.event;

/**
 * Alle im Spiel erzeugten Events m�ssen von diesem interface erben
 * @author Shane
 *
 */
public interface SquirrelEvent {

	/**
	 * Gibt das Ausl�sende Object zur�ck
	 * @return
	 */
	Object getTarget();
	
	/**
	 * Gibt eine String Repr�sentation des Events zur�ck
	 * @return
	 */
	String getType();
}

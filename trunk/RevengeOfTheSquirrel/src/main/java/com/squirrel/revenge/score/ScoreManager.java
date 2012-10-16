package com.squirrel.revenge.score;

/**
 * Ein simpler ScoreManager der einfach nur einen Punktestand hält,
 * welcher über die zur Verfügung gestellte Methoden manipuliert werden kann
 * 
 * @author Andreas
 *
 */
public class ScoreManager {

	public long score;

	public ScoreManager()
	{
		this.score = 0;
	}
	
	/**
	 * Score holen
	 * 
	 * @return
	 */
	public long getScore() {
		return score;
	}

	/**
	 * Score dazu addieren, z.B: beim Einsammeln von Items	
	 * @param scoreToAdd
	 */
	public void addScore(long scoreToAdd)
	{
		this.score += scoreToAdd;
	}
	
	/**
	 *  Setzt den Score auf 0 zurück, in einem neuen Spiel
	 */
	public void resetScore()
	{
		this.score = 0;
	}
}

package com.squirrel.engine.score;

/**
 * Ein simpler ScoreManager der einfach nur einen Punktestand h�lt,
 * welcher �ber die zur Verf�gung gestellte Methoden manipuliert werden kann
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
	 *  Setzt den Score auf 0 zur�ck, in einem neuen Spiel
	 */
	public void resetScore()
	{
		this.score = 0;
	}
}

package com.squirrel.revenge.score;

public class Score {

	public Score()
	{
		this.score = 0;
	}
	
	public long score;
	
	// Score holen
	public long getScore() {
		return score;
	}

	// Score dazuaddieren, z.B: beim Einsammeln von Items	
	public void AddScore(long scoreToAdd)
	{
		this.score += scoreToAdd;
	}
	
	// Setzt den Score auf 0 zurück, in einem neuen Spiel
	public void ResetScore()
	{
		this.score = 0;
	}
}

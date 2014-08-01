package com.turpgames.ballgamepuzzle.effects;

public interface IBox2DEffect {
	void start();

	void stop();

	public void setDuration(float duration);

	public float getDuration();
}

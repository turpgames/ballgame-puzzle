package com.turpgames.ballgamepuzzle.effects;

import com.turpgames.ballgamepuzzle.objects.Ball;

public class ExistanceEffect extends Box2DEffect implements IBallGameEffect {

	private final Ball ball;
	private float[] durations;
	private boolean isHiddenAtStartup;
	private int currentStateIndex;
	private float nextStateChange;
	private boolean isLooping;

	public ExistanceEffect(Ball ball) {
		super(ball);
		this.ball = ball;
	}
	
	public void setHiddenAtStartup(boolean isHiddenAtStartup) {
		this.isHiddenAtStartup = isHiddenAtStartup;
	}
	
	public void setLooping(boolean isLooping) {
		this.isLooping = isLooping;
	}
	
	public void setDurations(float... durations) {
		this.durations = durations;
	}
	
	@Override
	protected void onStart() {
		currentStateIndex = 0;
		ball.setHidden(isHiddenAtStartup);
		nextStateChange = durations[0];
	}

	@Override
	protected void onUpdate() {
		if (isLooping && getElapsed() == 0) {
			onStart();
		}
		else if (getElapsed() > nextStateChange) {
			currentStateIndex++;
			nextStateChange += durations[currentStateIndex];
			ball.setHidden(currentStateIndex % 2 == (isHiddenAtStartup ? 0 : 1));
		}
	}
}

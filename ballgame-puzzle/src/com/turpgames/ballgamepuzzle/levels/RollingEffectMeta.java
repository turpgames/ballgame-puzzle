package com.turpgames.ballgamepuzzle.levels;

public class RollingEffectMeta implements IEffectMeta {
	private float totalDuration;
	private boolean clockWise;

	public float getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(float totalDuration) {
		this.totalDuration = totalDuration;
	}

	public boolean isClockWise() {
		return clockWise;
	}

	public void setClockWise(boolean clockWise) {
		this.clockWise = clockWise;
	}
}

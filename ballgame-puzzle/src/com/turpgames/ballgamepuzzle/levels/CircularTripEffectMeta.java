package com.turpgames.ballgamepuzzle.levels;

import com.turpgames.framework.v0.util.Vector;

public class CircularTripEffectMeta implements IEffectMeta {
	private final Vector center;
	private float totalDuration;
	private boolean clockWise;

	public CircularTripEffectMeta() {
		center = new Vector();
	}

	public Vector getCenter() {
		return center;
	}

	public void setCenter(float x, float y) {
		center.set(x, y);
	}

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

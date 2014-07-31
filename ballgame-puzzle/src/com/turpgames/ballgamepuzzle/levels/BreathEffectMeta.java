package com.turpgames.ballgamepuzzle.levels;

public class BreathEffectMeta implements IEffectMeta {
	private float totalDuration;
	private float minScale;
	private float maxScale;
	private boolean startFromMaxScale;

	public float getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(float totalDuration) {
		this.totalDuration = totalDuration;
	}

	public float getMinScale() {
		return minScale;
	}

	public void setMinScale(float minScale) {
		this.minScale = minScale;
	}

	public float getMaxScale() {
		return maxScale;
	}

	public void setMaxScale(float maxScale) {
		this.maxScale = maxScale;
	}

	public boolean isStartFromMaxScale() {
		return startFromMaxScale;
	}

	public void setStartFromMaxScale(boolean startFromMaxScale) {
		this.startFromMaxScale = startFromMaxScale;
	}
}

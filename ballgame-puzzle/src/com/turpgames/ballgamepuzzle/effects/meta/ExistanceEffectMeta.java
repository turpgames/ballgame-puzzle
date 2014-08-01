package com.turpgames.ballgamepuzzle.effects.meta;

public class ExistanceEffectMeta implements IEffectMeta {
	private float totalDuration;
	private boolean isHiddenAtStartup;
	private float[] durations;
	private boolean isLooping;

	public float getTotalDuration() {
		return totalDuration;
	}

	public boolean isHiddenAtStartup() {
		return isHiddenAtStartup;
	}

	public void setHiddenAtStartup(boolean isHiddenAtStartup) {
		this.isHiddenAtStartup = isHiddenAtStartup;
	}

	public float[] getDurations() {
		return durations;
	}

	public void setDurations(float... durations) {
		this.durations = durations;
		totalDuration = 0;
		for (float d : durations)
			totalDuration += d;
	}

	public boolean isLooping() {
		return isLooping;
	}

	public void setLooping(boolean isLooping) {
		this.isLooping = isLooping;
	}
}

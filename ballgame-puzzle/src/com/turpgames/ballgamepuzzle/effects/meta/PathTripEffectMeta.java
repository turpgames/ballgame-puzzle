package com.turpgames.ballgamepuzzle.effects.meta;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.util.Vector;

public class PathTripEffectMeta implements IEffectMeta {

	private float totalDuration;
	private boolean isRoundTrip;
	private final List<Vector> path;

	public PathTripEffectMeta() {
		path = new ArrayList<Vector>();
	}

	public float getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(float totalDuration) {
		this.totalDuration = totalDuration;
	}

	public boolean isRoundTrip() {
		return isRoundTrip;
	}

	public void setRoundTrip(boolean isRoundTrip) {
		this.isRoundTrip = isRoundTrip;
	}

	public List<Vector> getPath() {
		return path;
	}

	public PathTripEffectMeta addNode(float x, float y) {
		path.add(new Vector(x, y));
		return this;
	}
}

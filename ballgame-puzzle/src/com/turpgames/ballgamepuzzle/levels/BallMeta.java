package com.turpgames.ballgamepuzzle.levels;

import com.turpgames.ballgamepuzzle.effects.meta.IEffectMeta;

public class BallMeta {
	private final int type;
	private final float r;
	private final float cx;
	private final float cy;
	private boolean isHidden;

	private IEffectMeta[] effects;

	public BallMeta(int type, float r, float cx, float cy) {
		this.type = type;
		this.r = r;
		this.cx = cx;
		this.cy = cy;
	}

	public int getType() {
		return type;
	}

	public float getR() {
		return r;
	}

	public float getCx() {
		return cx;
	}

	public float getCy() {
		return cy;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public IEffectMeta[] getEffects() {
		return effects;
	}

	public void setEffect(IEffectMeta[] effects) {
		this.effects = effects;
	}

	public boolean hasEffect() {
		return effects != null && effects.length > 0;
	}
}

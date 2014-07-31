package com.turpgames.ballgamepuzzle.levels;

import java.util.ArrayList;
import java.util.List;


public class BallMeta {
	private final int type;
	private final float r;
	private final float cx;
	private final float cy;

	private final List<IEffectMeta> effects;

	public BallMeta(int type, float r, float cx, float cy) {
		this.type = type;
		this.r = r;
		this.cx = cx;
		this.cy = cy;
		
		this.effects = new ArrayList<IEffectMeta>();
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
	
	public boolean hasEffect() {
		return effects.size() > 0;
	}

	public IEffectMeta[] getEffects() {
		return effects.toArray(new IEffectMeta[0]);
	}

	public void addEffect(IEffectMeta effect) {
		effects.add(effect);
	}
}

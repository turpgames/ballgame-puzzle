package com.turpgames.ballgamepuzzle.levels;

public class BallMeta {
	private final int type;
	private final float r;
	private final float cx;
	private final float cy;

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
}

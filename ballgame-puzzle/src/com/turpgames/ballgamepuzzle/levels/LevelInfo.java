package com.turpgames.ballgamepuzzle.levels;

import java.io.Serializable;

public class LevelInfo implements Serializable {
	private static final long serialVersionUID = 7660178242719077983L;

	public final static int Locked = -1;
	public final static int Unlocked = 0;
	public final static int Star1 = 1;
	public final static int Star2 = 2;
	public final static int Star3 = 3;

	private int state;
	private int index;
	private String id;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

package com.turpgames.ballgamepuzzle.levels;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.physics.box2d.ContactListener;
import com.turpgames.framework.v0.impl.Settings;

public class LevelMeta {

	public final static int Locked = -1;
	public final static int Unlocked = 0;
	public final static int Star1 = 1;
	public final static int Star2 = 2;
	public final static int Star3 = 3;

	private final String id;
	private final int index;
	private final BallMeta[] balls;
	private final ContactListener contactListener;
	private final int star1;
	private final int star2;
	private final int star3;
	private final int maxHit;
	private int state;

	private LevelMeta(Builder builder) {
		this.id = builder.id;
		this.index = builder.index;
		this.maxHit = builder.maxHit;
		this.star1 = builder.star1;
		this.star2 = builder.star2;
		this.star3 = builder.star3;
		this.balls = builder.balls.toArray(new BallMeta[0]);
		this.contactListener = builder.contactListener;
		this.state = Settings.getInteger(id, Locked);
	}

	public void updateState(int state) {
		this.state = state;
		Settings.putInteger(id, state);
	}

	public int getState() {
		return state;
	}

	public int getIndex() {
		return index;
	}

	public BallMeta[] getBalls() {
		return balls;
	}

	public ContactListener getContactListener() {
		return contactListener;
	}

	public int getStar1() {
		return star1;
	}

	public int getStar2() {
		return star2;
	}

	public int getStar3() {
		return star3;
	}

	public int getMaxHit() {
		return maxHit;
	}

	public static Builder newBuilder(String id) {
		return new Builder(id);
	}

	public static class Builder {
		private final List<BallMeta> balls;
		private final String id;
		private int index;
		private int star1;
		private int star2;
		private int star3;
		private int maxHit;
		private ContactListener contactListener;

		private Builder(String id) {
			this.id = id;
			this.balls = new ArrayList<BallMeta>();
		}

		public Builder setIndex(int index) {
			this.index = index;
			return this;
		}

		public Builder setContactListener(ContactListener listener) {
			this.contactListener = listener;
			return this;
		}

		public Builder setScoreMeta(int maxHits, int star3, int star2, int star1) {
			this.maxHit = maxHits;
			this.star3 = star3;
			this.star2 = star2;
			this.star1 = star1;
			return this;
		}

		public Builder addBall(int type, float r, float cx, float cy) {
			balls.add(new BallMeta(type, r, cx, cy));
			return this;
		}

		public LevelMeta build() {
			return new LevelMeta(this);
		}
	}
}

package com.turpgames.ballgamepuzzle.levels;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.box2d.IContactListener;
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
	private final IContactListener contactListener;
	private final int star2;
	private final int star3;
	private final String description;

	private LevelPack pack;

	private LevelMeta(Builder builder) {
		this.id = builder.id;
		this.index = builder.index;
		this.star2 = builder.star2;
		this.star3 = builder.star3;
		this.balls = builder.balls.toArray(new BallMeta[0]);
		this.contactListener = builder.contactListener;
		this.description = builder.description;
	}

	public void setState(int state) {
		Settings.putInteger(id, state);
	}

	public int getState() {
		return Settings.getInteger(id, Locked);
	}

	public String getId() {
		return id;
	}
	
	public int getIndex() {
		return index;
	}

	public BallMeta[] getBalls() {
		return balls;
	}

	public IContactListener getContactListener() {
		return contactListener;
	}

	public int getStar2() {
		return star2;
	}

	public int getStar3() {
		return star3;
	}

	public LevelPack getPack() {
		return pack;
	}
	
	public String getDescription() {
		return description;
	}
	
	public boolean hasDescription() {
		return description != null;
	}
	
	public boolean isDescriptionRead() {
		return Settings.getBoolean(id + "-read", false);
	}
	
	public void setDescriptionAsRead() {
		Settings.putBoolean(id + "-read", true);
	}
	
	void setPack(LevelPack pack) {
		this.pack = pack;
	}

	public static Builder newBuilder(String id) {
		return new Builder(id);
	}

	public static class Builder {
		private final List<BallMeta> balls;
		private final String id;
		private int index;
		private int star2;
		private int star3;
		private IContactListener contactListener;
		private String description;

		private Builder(String id) {
			this.id = id;
			this.balls = new ArrayList<BallMeta>();
		}

		public Builder setIndex(int index) {
			this.index = index;
			return this;
		}

		public Builder setContactListener(IContactListener listener) {
			this.contactListener = listener;
			return this;
		}

		public Builder setDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder setScoreMeta(int star2, int star3) {
			this.star2 = star2;
			this.star3 = star3;
			return this;
		}

		public Builder addBall(int type, float r, float cx, float cy) {
			return addBall(new BallMeta(type, r, cx, cy));
		}

		public Builder addBall(BallMeta ballMeta) {
			balls.add(ballMeta);
			return this;
		}

		public LevelMeta build() {
			return new LevelMeta(this);
		}
	}
}

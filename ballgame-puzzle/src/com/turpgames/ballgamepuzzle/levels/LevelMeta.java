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
	private final BlockMeta[] blocks;
	private final IContactListener contactListener;

	private LevelPack pack;

	private LevelMeta(Builder builder) {
		this.id = builder.id;
		this.index = builder.index;
		this.balls = builder.balls.toArray(new BallMeta[0]);
		this.blocks = builder.blocks.toArray(new BlockMeta[0]);
		this.contactListener = builder.contactListener;
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

	public BlockMeta[] getBlocks() {
		return blocks;
	}

	public IContactListener getContactListener() {
		return contactListener;
	}

	public LevelPack getPack() {
		return pack;
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
		private final List<BlockMeta> blocks;
		private final String id;
		private int index;
		private IContactListener contactListener;

		private Builder(String id) {
			this.id = id;
			this.balls = new ArrayList<BallMeta>();
			this.blocks = new ArrayList<BlockMeta>();
		}

		public Builder setIndex(int index) {
			this.index = index;
			return this;
		}

		public Builder setContactListener(IContactListener listener) {
			this.contactListener = listener;
			return this;
		}

		public Builder addBall(int type, float r, float cx, float cy) {
			return addBall(new BallMeta(type, r, cx, cy));
		}

		public Builder addBlock(float x, float y, float width, float height) {
			return addBlock(x, y, width, height, 0);
		}

		public Builder addBlock(float x, float y, float width, float height, float rotation) {
			blocks.add(new BlockMeta(x, y, width, height, rotation));
			return this;
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

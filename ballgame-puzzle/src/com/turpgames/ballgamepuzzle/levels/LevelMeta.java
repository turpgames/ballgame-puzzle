package com.turpgames.ballgamepuzzle.levels;

import com.turpgames.box2d.IContactListener;
import com.turpgames.framework.v0.impl.Settings;

public class LevelMeta {

	public final static int Locked = -1;
	public final static int Unlocked = 0;
	public final static int Star1 = 1;
	public final static int Star2 = 2;
	public final static int Star3 = 3;

	private String id;
	private int index;
	private boolean designerLevel;
	private BallMeta[] balls;
	private BlockMeta[] blocks;
	private IContactListener contactListener;
	private LevelPack pack;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isDesignerLevel() {
		return designerLevel;
	}

	public void setDesignerLevel(boolean designerLevel) {
		this.designerLevel = designerLevel;
	}

	public BallMeta[] getBalls() {
		return balls;
	}

	public void setBalls(BallMeta[] balls) {
		this.balls = balls;
	}

	public BlockMeta[] getBlocks() {
		return blocks;
	}

	public void setBlocks(BlockMeta[] blocks) {
		this.blocks = blocks;
	}

	public IContactListener getContactListener() {
		return contactListener;
	}

	public void setContactListener(IContactListener contactListener) {
		this.contactListener = contactListener;
	}

	public LevelPack getPack() {
		return pack;
	}

	public void setPack(LevelPack pack) {
		this.pack = pack;
	}

	public void setState(int state) {
		Settings.putInteger(id, state);
	}

	public int getState() {
		return Settings.getInteger(id, Locked);
	}
}

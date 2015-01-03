package com.turpgames.ballgamepuzzle.levels;

public class BlockMeta {
	private final float x;
	private final float y;
	private final float width;
	private final float height;
	private final float rotation;

	public BlockMeta(float x, float y, float width, float height, float rotation) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.rotation = rotation;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getRotation() {
		return rotation;
	}
}

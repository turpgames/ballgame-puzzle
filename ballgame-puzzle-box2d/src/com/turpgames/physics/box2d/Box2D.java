package com.turpgames.physics.box2d;

import com.turpgames.framework.v0.util.Game;

public final class Box2D {
	private Box2D() {

	}

	/**
	 * ie: if viewportToWorldScale = 0.01f and viewport = 500x800 then world = 5 meters x 8 meters
	 */
	public static float viewportToWorldScale = 0.01f;
	
	private final static float worldOffsetX = Game.descale(Game.getViewportOffsetX());
	private final static float worldOffsetY = Game.descale(Game.getViewportOffsetY());

	public static float viewportToWorld(float f) {
		return f * viewportToWorldScale;
	}

	public static float worldToViewport(float f) {
		return f / viewportToWorldScale;
	}

	public static float viewportToWorldX(float x) {
		return viewportToWorld(x + worldOffsetX);
	}

	public static float worldToViewportX(float x) {
		return worldToViewport(x) - worldOffsetX;
	}

	public static float viewportToWorldY(float y) {
		return viewportToWorld(y + worldOffsetY);
	}

	public static float worldToViewportY(float y) {
		return worldToViewport(y) - worldOffsetY;
	}
}

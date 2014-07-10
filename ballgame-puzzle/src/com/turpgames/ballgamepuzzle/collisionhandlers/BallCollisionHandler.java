package com.turpgames.ballgamepuzzle.collisionhandlers;

import com.turpgames.ballgamepuzzle.objects.Ball;

public abstract class BallCollisionHandler implements IBallCollisionHandler {

	private final int ballType;

	protected BallCollisionHandler(int ballType) {
		this.ballType = ballType;
	}

	@Override
	public boolean onBeginCollide(Ball b1, Ball b2) {
		if (!canHandle(b1, b2))
			return false;

		return b1.getType() == ballType
				? handleBeginCollide(b1, b2)
				: handleBeginCollide(b2, b1);
	}

	@Override
	public boolean onEndCollide(Ball b1, Ball b2) {
		if (!canHandle(b1, b2))
			return false;

		return b1.getType() == ballType
				? handleEndCollide(b1, b2)
				: handleEndCollide(b2, b1);
	}

	private boolean canHandle(Ball b1, Ball b2) {
		return b1.getType() == ballType || b2.getType() == ballType;
	}

	protected boolean handleBeginCollide(Ball b1, Ball b2) {
		return true;
	}

	protected boolean handleEndCollide(Ball b1, Ball b2) {
		return true;
	}
}

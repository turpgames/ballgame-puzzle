package com.turpgames.ballgamepuzzle.collisionhandlers;

import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.box2d.IContact;
import com.turpgames.box2d.IFixture;

public abstract class BallCollisionHandler implements IBallCollisionHandler {
	private final int ballType;
	protected IContact currentContact;
	protected IFixture fixtureB;

	protected BallCollisionHandler(int ballType) {
		this.ballType = ballType;
	}

	@Override
	public boolean onBeginCollide(Ball b1, Ball b2, IContact contact) {
		if (!canHandle(b1, b2))
			return false;
		
		currentContact = contact;

		return b1.getBallType() == ballType
				? handleBeginCollide(b1, b2)
				: handleBeginCollide(b2, b1);
	}

	@Override
	public boolean onEndCollide(Ball b1, Ball b2, IContact contact) {
		if (!canHandle(b1, b2))
			return false;

		currentContact = contact;
		
		return b1.getBallType() == ballType
				? handleEndCollide(b1, b2)
				: handleEndCollide(b2, b1);
	}

	private boolean canHandle(Ball b1, Ball b2) {
		return b1.getBallType() == ballType || b2.getBallType() == ballType;
	}

	protected boolean handleBeginCollide(Ball b1, Ball b2) {
		return true;
	}

	protected boolean handleEndCollide(Ball b1, Ball b2) {
		return true;
	}
}

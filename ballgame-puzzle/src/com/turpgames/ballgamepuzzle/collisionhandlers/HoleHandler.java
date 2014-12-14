package com.turpgames.ballgamepuzzle.collisionhandlers;

import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.objects.balls.HoleBall;
import com.turpgames.ballgamepuzzle.objects.balls.SubjectBall;
import com.turpgames.ballgamepuzzle.utils.Global;
import com.turpgames.ballgamepuzzle.utils.Sounds;
import com.turpgames.box2d.IBody;

public class HoleHandler extends BallCollisionHandler {
	public HoleHandler(int type) {
		super(type);
	}

	private boolean isCollidingHoleSensor(IBody sensorBody) {
		return sensorBody == currentContact.getFixtureA().getBody() ||
				sensorBody == currentContact.getFixtureB().getBody();
	}

	@Override
	protected boolean handleBeginCollide(Ball b1, Ball b2) {
		if (b2.getBallType() != Ball.Subject)
			return false;

		HoleBall hole = (HoleBall) b1;

		if (isCollidingHoleSensor(hole.getSensorBody())) {
			hole.enter(b2.getBody());
			System.out.println("Play hole enter sound!!!");
		} else if (hole.getBallType() == Ball.BlackHole) {
			if (!((SubjectBall) b2).isGhost()) {
				Global.currentController.onHitEnemy();
				Sounds.enemy.play();
			}
		}

		return true;
	}

	@Override
	protected boolean handleEndCollide(Ball b1, Ball b2) {
		if (b2.getBallType() != Ball.Subject)
			return false;

		HoleBall hole = (HoleBall) b1;

		if (isCollidingHoleSensor(hole.getSensorBody())) {
			hole.leave(b2.getBody());
		}

		return true;
	}
}

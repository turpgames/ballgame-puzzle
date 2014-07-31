package com.turpgames.ballgamepuzzle.collisionhandlers;

import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.utils.Sounds;

public class StoneHandler extends BallCollisionHandler {
	public StoneHandler() {
		super(Ball.Stone);
	}

	@Override
	protected boolean handleBeginCollide(Ball b1, Ball b2) {
		if (b2.getBallType() != Ball.Subject)
			return false;
		Sounds.stone.play();
		return true;
	}
}

package com.turpgames.ballgamepuzzle.collisionhandlers;

import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.objects.balls.SubjectBall;
import com.turpgames.ballgamepuzzle.objects.balls.BounceBall;
import com.turpgames.ballgamepuzzle.utils.Sounds;

public class BounceHandler extends BallCollisionHandler {
	public BounceHandler() {
		super(Ball.Bounce);
	}

	@Override
	protected boolean handleBeginCollide(Ball b1, Ball b2) {
		if (b2.getBallType() != Ball.Subject)
			return false;
		BounceBall yellowBall = (BounceBall) b1;
		SubjectBall azureBall = (SubjectBall) b2;
		yellowBall.bounce(azureBall);
		Sounds.bounce.play();
		return true;
	}
}

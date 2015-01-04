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
	protected boolean handleEndCollide(Ball b1, Ball b2) {
		if (b2.getBallType() != Ball.Subject)
			return false;
		if (((SubjectBall) b2).isGhost())
			return false;

		BounceBall bounceBall = (BounceBall) b1;
		SubjectBall subject = (SubjectBall) b2;
		
		bounceBall.bounce(subject);
		
		Sounds.bounce.play();
		return true;
	}
}

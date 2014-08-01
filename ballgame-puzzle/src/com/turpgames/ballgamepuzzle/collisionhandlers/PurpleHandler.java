package com.turpgames.ballgamepuzzle.collisionhandlers;

import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.objects.balls.SubjectBall;

public class PurpleHandler extends BallCollisionHandler {
	public PurpleHandler() {
		super(Ball.Purple);
	}
	
	@Override
	protected boolean handleBeginCollide(Ball b1, Ball b2) {
		if (b2.getBallType() != Ball.Subject)
			return false;
		
		b1.destroy();
		
		SubjectBall subject = (SubjectBall)b2;
		subject.setAsGhost();
		
		System.out.println("Play purple sound...");
		
		return true;
	}
}

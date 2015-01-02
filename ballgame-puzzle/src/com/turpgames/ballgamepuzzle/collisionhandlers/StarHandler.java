package com.turpgames.ballgamepuzzle.collisionhandlers;

import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.utils.Global;

public class StarHandler extends BallCollisionHandler {
	public StarHandler() {
		super(Ball.Star);
	}
	
	@Override
	protected boolean handleBeginCollide(Ball b1, Ball b2) {
		if (b2.getBallType() != Ball.Subject)
			return false;
		
		b1.destroy();
		
		Global.currentController.onHitStar();
		
		return true;
	}
}

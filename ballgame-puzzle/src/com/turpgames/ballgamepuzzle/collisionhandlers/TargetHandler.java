package com.turpgames.ballgamepuzzle.collisionhandlers;

import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.utils.Global;
import com.turpgames.ballgamepuzzle.utils.Sounds;

public class TargetHandler extends BallCollisionHandler {
	public TargetHandler() {
		super(Ball.Target);
	}

	@Override
	protected boolean handleBeginCollide(Ball b1, Ball b2) {
		if (b2.getType() != Ball.Subject)
			return false;
		Global.currentGame.onHitTarget();
		Sounds.target.play();
		return true;
	}
}

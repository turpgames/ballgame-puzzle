package com.turpgames.ballgamepuzzle.collisionhandlers;

import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.utils.Global;
import com.turpgames.ballgamepuzzle.utils.Sounds;

public class EnemyHandler extends BallCollisionHandler {
	public EnemyHandler() {
		super(Ball.Enemy);
	}

	@Override
	protected boolean handleBeginCollide(Ball b1, Ball b2) {
		if (b2.getType() != Ball.Subject)
			return false;
		Global.currentController.onHitEnemy();
		Sounds.enemy.play();
		return true;
	}
}

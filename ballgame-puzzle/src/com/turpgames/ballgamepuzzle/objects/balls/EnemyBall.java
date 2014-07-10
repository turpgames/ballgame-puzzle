package com.turpgames.ballgamepuzzle.objects.balls;

import com.turpgames.ballgamepuzzle.levels.BallMeta;
import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.objects.BallBodyBuilder;
import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.box2d.Box2DWorld;

public class EnemyBall extends Ball {
	public EnemyBall(BallMeta meta, Box2DWorld world) {
		super(meta, world, Textures.ball_red);
	}

	@Override
	public int getType() {
		return Ball.Enemy;
	}
	
	@Override
	protected BallBodyBuilder createBodyBuilder() {
		return super.createBodyBuilder().setAsSensor();
	}
}

package com.turpgames.ballgamepuzzle.objects.balls;

import com.turpgames.ballgamepuzzle.levels.BallMeta;
import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.box2d.IWorld;

public class StoneBall extends Ball {
	public StoneBall(BallMeta meta, IWorld world) {
		super(meta, world, Textures.ball_gray);
	}
}

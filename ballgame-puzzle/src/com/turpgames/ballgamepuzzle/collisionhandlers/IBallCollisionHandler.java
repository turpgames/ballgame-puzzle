package com.turpgames.ballgamepuzzle.collisionhandlers;

import com.turpgames.ballgamepuzzle.objects.Ball;

public interface IBallCollisionHandler {
	boolean onBeginCollide(Ball b1, Ball b2);

	boolean onEndCollide(Ball b1, Ball b2);
}

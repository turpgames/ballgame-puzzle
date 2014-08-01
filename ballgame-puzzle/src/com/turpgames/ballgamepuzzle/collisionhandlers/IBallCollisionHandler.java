package com.turpgames.ballgamepuzzle.collisionhandlers;

import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.box2d.IContact;

public interface IBallCollisionHandler {
	boolean onBeginCollide(Ball b1, Ball b2, IContact contact);

	boolean onEndCollide(Ball b1, Ball b2, IContact contact);
}

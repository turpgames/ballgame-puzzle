package com.turpgames.ballgamepuzzle.collisionhandlers;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.ballgamepuzzle.objects.Ball;

public class BallCollisionHandlerChain implements IBallCollisionHandler {
	private final List<IBallCollisionHandler> handlerChain;

	public BallCollisionHandlerChain(IBallCollisionHandler... handlers) {
		this.handlerChain = new ArrayList<IBallCollisionHandler>();
		for (IBallCollisionHandler handler : handlers)
			add(handler);
	}

	public void add(IBallCollisionHandler handler) {
		handlerChain.add(handler);
	}

	public void remove(IBallCollisionHandler handler) {
		handlerChain.remove(handler);
	}

	@Override
	public boolean onBeginCollide(Ball b1, Ball b2) {
		for (IBallCollisionHandler handler : handlerChain) {
			if (handler.onBeginCollide(b1, b2)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onEndCollide(Ball b1, Ball b2) {
		for (IBallCollisionHandler handler : handlerChain) {
			if (handler.onEndCollide(b1, b2)) {
				return true;
			}
		}
		return false;
	}
}

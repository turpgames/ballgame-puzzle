package com.turpgames.ballgamepuzzle.levels;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.turpgames.ballgamepuzzle.collisionhandlers.IBallCollisionHandler;
import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.utils.Sounds;

public class DefaultContactListener implements ContactListener {
	private final IBallCollisionHandler collisionHandler;

	public DefaultContactListener(IBallCollisionHandler collisionHandler) {
		this.collisionHandler = collisionHandler;
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

	@Override
	public void endContact(Contact contact) {
		Object o1 = contact.getFixtureA().getBody().getUserData();
		Object o2 = contact.getFixtureB().getBody().getUserData();

		if (o1 == null || o2 == null) {
			return;
		}

		collisionHandler.onEndCollide((Ball) o1, (Ball) o2);
	}

	@Override
	public void beginContact(Contact contact) {
		Object o1 = contact.getFixtureA().getBody().getUserData();
		Object o2 = contact.getFixtureB().getBody().getUserData();

		if (o1 == null || o2 == null) {
			Sounds.hit.play();
			return;
		}

		collisionHandler.onBeginCollide((Ball) o1, (Ball) o2);
	}
}

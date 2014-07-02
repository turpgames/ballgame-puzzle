package com.turpgames.ballgamepuzzle.levels;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.utils.Sounds;

public class DefaultContactListener implements ContactListener {
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void beginContact(Contact contact) {
		Object o1 = contact.getFixtureA().getBody().getUserData();
		Object o2 = contact.getFixtureB().getBody().getUserData();

		Sounds.hit.play();

		if (o1 == null || o2 == null) {
			return;
		}

		Ball b1 = (Ball) o1;
		Ball b2 = (Ball) o2;
		
		if (bounce(b1, b2) || bounce(b2, b1))
			return;

		
	}

	private boolean bounce(Ball b1, Ball b2) {
		if (b1.getType() == Ball.Yellow) {
			b1.bounce(b2);
			return true;
		}
		return false;
	}
}

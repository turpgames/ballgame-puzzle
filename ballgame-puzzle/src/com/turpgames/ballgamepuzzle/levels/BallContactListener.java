package com.turpgames.ballgamepuzzle.levels;

import com.turpgames.ballgamepuzzle.collisionhandlers.IBallCollisionHandler;
import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.utils.Sounds;
import com.turpgames.box2d.IBox2DObject;
import com.turpgames.box2d.IContact;
import com.turpgames.box2d.IContactListener;

public class BallContactListener implements IContactListener {
	private final IBallCollisionHandler collisionHandler;

	public BallContactListener(IBallCollisionHandler collisionHandler) {
		this.collisionHandler = collisionHandler;
	}

	@Override
	public void beginContact(IContact contact) {
		IBox2DObject o1 = contact.getFixtureA().getBody().getData();
		IBox2DObject o2 = contact.getFixtureB().getBody().getData();

		if (o1 instanceof Ball && o2 instanceof Ball) {
			collisionHandler.onBeginCollide((Ball) o1, (Ball) o2, contact);
		}
		else {
			Sounds.hit.play();
		}
	}

	@Override
	public void endContact(IContact contact) {
		IBox2DObject o1 = contact.getFixtureA().getBody().getData();
		IBox2DObject o2 = contact.getFixtureB().getBody().getData();

		if (o1 instanceof Ball && o2 instanceof Ball) {
			collisionHandler.onEndCollide((Ball) o1, (Ball) o2, contact);
		}
	}
}

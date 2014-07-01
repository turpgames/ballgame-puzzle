package com.turpgames.ballgamepuzzle.controller;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.objects.Walls;
import com.turpgames.ballgamepuzzle.utils.Sounds;
import com.turpgames.ballgamepuzzle.view.IScreenView;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.impl.InputListener;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.box2d.Box2DWorld;

public class GameController {
	private final IScreenView view;
	private final Box2DWorld world;
	private Ball ball;

	public GameController(IScreenView view) {
		this.view = view;
		this.world = new Box2DWorld();
	}

	public void activate() {
		view.registerInputListener(listener);
		registerGameDrawable(new Walls(world));
		registerGameDrawable(ball = newBall(150, 700, Ball.Azure));
		registerGameDrawable(newBall(325, 550, Ball.Green, Ball.Small));
		registerGameDrawable(newBall(180, 170, Ball.Yellow, Ball.Large));
		registerGameDrawable(newBall(55, 100, Ball.Gray, Ball.Medium));
		registerGameDrawable(newBall(320, 350, Ball.Purple, Ball.Small));
		registerGameDrawable(newBall(430, 270, Ball.Red, Ball.Large));
		registerGameDrawable(newBall(240, 620, Ball.Blue, Ball.Small));
		registerGameDrawable(newBall(120, 500, Ball.Orange, Ball.Medium));
		// registerGameDrawable(world);
		world.getWorld().setContactListener(new ContactListener() {
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
				
				Ball b1 = (Ball)o1;
				Ball b2 = (Ball)o2;
				
				if (b1.isElastic()) {
					b1.bounce(b2);
				}
				else if (b2.isElastic()) {
					b2.bounce(b1);
				}
			}
		});
	}

	public void deactivate() {
	}

	public void update() {
		world.update();
	}

	private boolean onTap() {
		return false;
	}

	private boolean onTouchDown(float x, float y) {
		ball.hit2(Game.screenToViewportX(x), Game.screenToViewportY(y));
		Sounds.hit.play();
		return true;
	}

	private void registerGameDrawable(IDrawable drawable) {
		view.registerDrawable(drawable, Game.LAYER_GAME);
	}

	private Ball newBall(float cx, float cy, int type) {
		return newBall(cx, cy, type, Ball.Medium);
	}

	private Ball newBall(float cx, float cy, int type, float r) {
		return Ball.newBuilder(type).setCenter(cx, cy).setRadius(r).build(world);
	}

	private final InputListener listener = new InputListener() {
		@Override
		public boolean touchDown(float x, float y, int pointer, int button) {
			return onTouchDown(x, y);
		}

		@Override
		public boolean tap(float x, float y, int count, int button) {
			return onTap();
		}
	};
}

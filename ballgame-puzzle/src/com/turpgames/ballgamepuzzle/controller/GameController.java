package com.turpgames.ballgamepuzzle.controller;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.ballgamepuzzle.levels.BallMeta;
import com.turpgames.ballgamepuzzle.levels.LevelMeta;
import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.objects.Walls;
import com.turpgames.ballgamepuzzle.utils.Global;
import com.turpgames.ballgamepuzzle.utils.Sounds;
import com.turpgames.ballgamepuzzle.view.IScreenView;
import com.turpgames.box2d.Box2DWorld;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.impl.InputListener;
import com.turpgames.framework.v0.util.Game;

public class GameController {
	private final IScreenView view;
	private Box2DWorld world;
	private Ball ball;

	private final List<IDrawable> drawables = new ArrayList<IDrawable>();

	public GameController(IScreenView view) {
		this.view = view;
	}

	public void activate() {
		this.world = new Box2DWorld();

		view.registerInputListener(listener);
		registerGameDrawable(new Walls(world));

		LevelMeta meta = Global.levelMeta;

		for (BallMeta ballMeta : meta.getBalls()) {
			Ball ball = createBall(ballMeta);
			if (ball.getType() == Ball.Azure)
				this.ball = ball;
			registerGameDrawable(ball);
		}

		world.getWorld().setContactListener(meta.getContactListener());
	}

	public void deactivate() {
		world.getWorld().dispose();
		world = null;

		for (IDrawable d : drawables)
			view.unregisterDrawable(d);
		drawables.clear();
	}

	public void update() {
		if (world != null)
			world.update();
	}

	private boolean onTap() {
		return false;
	}

	private boolean onTouchDown(float x, float y) {
		y = Game.screenToViewportY(y);
		if (Game.descale(Game.getScreenHeight()) - y < 100)
			return false;
		
		ball.hit2(Game.viewportToScreenX(x), y);
		Sounds.hit.play();
		return false;
	}

	private void registerGameDrawable(IDrawable drawable) {
		drawables.add(drawable);
		view.registerDrawable(drawable, Game.LAYER_GAME);
	}

	private Ball createBall(BallMeta ballMeta) {
		return Ball.newBuilder(ballMeta.getType())
				.setCenter(ballMeta.getCx(), ballMeta.getCy())
				.setRadius(ballMeta.getR())
				.build(world);
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

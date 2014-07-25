package com.turpgames.ballgamepuzzle.controller;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.ballgamepuzzle.components.LevelResetEffect;
import com.turpgames.ballgamepuzzle.levels.BallMeta;
import com.turpgames.ballgamepuzzle.levels.LevelMeta;
import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.objects.Walls;
import com.turpgames.ballgamepuzzle.objects.balls.PortalBall;
import com.turpgames.ballgamepuzzle.objects.balls.SubjectBall;
import com.turpgames.ballgamepuzzle.utils.Global;
import com.turpgames.ballgamepuzzle.utils.R;
import com.turpgames.ballgamepuzzle.utils.Sounds;
import com.turpgames.ballgamepuzzle.view.IScreenView;
import com.turpgames.box2d.Box2DWorld;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.impl.InputListener;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;

public class GameController implements IGameController {
	private final IScreenView view;
	private Box2DWorld world;
	private SubjectBall ball;
	private final LevelResetEffect resetEffect;

	private boolean isPlaying;
	private boolean isGameOver;
	private int hits;

	private final List<IDrawable> drawables = new ArrayList<IDrawable>();

	public GameController(IScreenView view) {
		this.view = view;
		this.resetEffect = new LevelResetEffect(new LevelResetEffect.IListener() {
			@Override
			public void onHalfCompleted() {
				endGame();
				initGame();
			}
			
			@Override
			public void onEnd() {
				
			}
		});
	}

	private void initBalls() {
		LevelMeta meta = Global.currentLevel;

		PortalBall portalPair = null;

		for (BallMeta ballMeta : meta.getBalls()) {
			Ball ball = Ball.create(ballMeta, world);

			if (ball.getType() == Ball.Subject) {
				this.ball = (SubjectBall) ball;
			}
			else if (ball.getType() == Ball.Portal) {
				if (portalPair == null) {
					portalPair = (PortalBall) ball;
				}
				else {
					PortalBall.makePair(portalPair, (PortalBall) ball);
					portalPair = null;
				}
			}

			registerGameDrawable(ball);
		}

		if (portalPair != null) {
			throw new UnsupportedOperationException("Unpaired portal exists!");
		}
	}

	@Override
	public void onHitTarget() {
		isPlaying = false;
		isGameOver = true;
		
		Global.hitCount = hits;
		ScreenManager.instance.switchTo(R.screens.result, false);
	}

	@Override
	public void onHitEnemy() {
		isPlaying = false;
		isGameOver = true;
	}

	public void activate() {
		Global.currentController = this;

		initGame();

		view.registerInputListener(listener);
	}

	public void deactivate() {
		Global.currentController = null;

		endGame();

		view.unregisterInputListener(listener);
	}

	private void startPlaying() {
		isPlaying = true;
		hits = 0;
	}

	private void initGame() {
		isPlaying = false;
		isGameOver = false;
		world = new Box2DWorld();

		registerGameDrawable(new Walls(world));

		initBalls();
		world.getWorld().setContactListener(Global.currentLevel.getContactListener());
	}

	private void endGame() {
		isPlaying = false;

		world.getWorld().dispose();
		world = null;
		for (IDrawable d : drawables)
			view.unregisterDrawable(d);
		drawables.clear();
	}

	public void resetGame() {
		resetEffect.start();
	}

	public void update() {
		if (isPlaying) {
			world.update();
		}
	}

	private boolean onTouchDown(float x, float y) {
		if (isPlaying) {
			hit(x, y);
			hits++;
		} else if (isGameOver) {
			resetGame();
		} else {
			startPlaying();
		}
		return false;
	}

	private void hit(float x, float y) {
		ball.hit(x, y);
		Sounds.hit.play();
	}

	private void registerGameDrawable(IDrawable drawable) {
		drawables.add(drawable);
		view.registerDrawable(drawable, Game.LAYER_GAME);
	}

	private final InputListener listener = new InputListener() {
		@Override
		public boolean touchDown(float x, float y, int pointer, int button) {
			y = Game.screenToViewportY(y);
			if (y > Game.getVirtualHeight() - Walls.marginY)
				return false;
			return onTouchDown(Game.screenToViewportX(x), y);
		}
	};
}

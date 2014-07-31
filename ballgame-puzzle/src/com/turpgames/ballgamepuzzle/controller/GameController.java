package com.turpgames.ballgamepuzzle.controller;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.ballgamepuzzle.components.LevelResetEffect;
import com.turpgames.ballgamepuzzle.components.Toolbar;
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
import com.turpgames.box2d.Box2D;
import com.turpgames.box2d.IWorld;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.impl.InputListener;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;

public class GameController implements IGameController {
	private final static int StateWaitingStart = 0;
	private final static int StatePlaying = 1;
	private final static int StateGameOver = 2;
	private final static int StateReseting = 3;
	private final static int StateGameEnd = 4;
	private final static int StateReadingDescription = 5;

	private final IScreenView view;
	private final IWorld world;
	private final LevelResetEffect resetEffect;
	private final Timer restartTimer;
	private SubjectBall ball;
	private List<Ball> balls;

	private int state;
	private int hits;

	private final List<IDrawable> drawables = new ArrayList<IDrawable>();

	public GameController(IScreenView view) {
		this.view = view;
		this.world = Box2D.createWorld();
		this.resetEffect = new LevelResetEffect(new LevelResetEffect.IListener() {
			@Override
			public void onHalfCompleted() {
				endGame();
				initGame();
			}
		});

		this.restartTimer = new Timer();
		this.restartTimer.setInterval(1f);
		this.restartTimer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer timer) {
				resetGame();
			}
		});

//		view.registerDrawable(new IDrawable() {
//			@Override
//			public void draw() {
//				world.drawDebug();
//			}
//		}, Game.LAYER_BACKGROUND);
	}

	@Override
	public void onHitTarget() {
		state = StateGameOver;

		Global.hitCount = hits;
		ScreenManager.instance.switchTo(R.screens.result, false);
	}

	@Override
	public void onHitEnemy() {
		state = StateGameOver;
		restartTimer.start();
	}

	public void activate() {
		Global.currentController = this;

		initGame();

		view.registerInputListener(listener);
	}

	public void deactivate() {
		Global.currentController = null;

		endGame();
		resetEffect.stop();
		restartTimer.stop();

		view.unregisterInputListener(listener);
	}

	private void initGame() {
		LevelMeta level = Global.currentLevel;

		if (level.hasDescription()) {
			Toolbar.getInstance().activateInfoButton();
			if (level.isDescriptionRead()) {
				state = StateWaitingStart;
			} else {
				openDescriptionDialog();
			}
		}
		else {
			state = StateWaitingStart;
		}

		world.reset();

		registerGameDrawable(new Walls(world));

		initBalls();
		world.setContactListener(Global.currentLevel.getContactListener());
		Global.levelPackViewId = Global.currentLevel.getPack().getTitle();
	}

	public void openDescriptionDialog() {
		if (state == StateReadingDescription)
			return;
		state = StateReadingDescription;

		String description = Global.currentLevel.getDescription();

		Dialog dialog = new Dialog();
		dialog.addButton("ok", "Ok");
		dialog.setFontScale(0.6f);
		dialog.setListener(new Dialog.IDialogListener() {
			@Override
			public void onDialogClosed() {
				state = StateWaitingStart;
			}

			@Override
			public void onDialogButtonClicked(String id) {
				state = StateWaitingStart;
				Global.currentLevel.setDescriptionAsRead();
			}
		});
		dialog.open(description);
	}

	private void initBalls() {
		LevelMeta level = Global.currentLevel;

		PortalBall portalPair = null;

		balls = new ArrayList<Ball>();

		for (BallMeta ballMeta : level.getBalls()) {
			Ball ball = Ball.create(ballMeta, world);

			if (ball.getBallType() == Ball.Subject) {
				this.ball = (SubjectBall) ball;
			}
			else if (ball.getBallType() == Ball.Portal) {
				if (portalPair == null) {
					portalPair = (PortalBall) ball;
				}
				else {
					PortalBall.makePair(portalPair, (PortalBall) ball);
					portalPair = null;
				}
			}

			balls.add(ball);
			registerGameDrawable(ball);
		}

		if (portalPair != null) {
			throw new UnsupportedOperationException("Unpaired portal exists!");
		}
	}

	private void startPlaying() {
		state = StatePlaying;
		hits = 0;
		for (Ball ball : balls)
			ball.startEffect();
	}

	private void endGame() {
		state = StateGameEnd;

		for (Ball ball : balls)
			ball.stopEffect();

		for (IDrawable d : drawables)
			view.unregisterDrawable(d);
		drawables.clear();
	}

	public void resetGame() {
		if (state == StateReseting)
			return;
		state = StateReseting;
		restartTimer.stop();
		resetEffect.start();
	}

	public void update() {
		if (state == StatePlaying) {
			world.update();
		}
	}

	private boolean onTouchDown(float x, float y) {
		if (state == StatePlaying) {
			hit(x, y);
			hits++;
		} else if (state == StateGameOver) {
			resetGame();
		} else if (state == StateWaitingStart) {
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

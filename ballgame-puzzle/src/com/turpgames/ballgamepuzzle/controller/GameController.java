package com.turpgames.ballgamepuzzle.controller;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.ballgamepuzzle.components.GameMenu;
import com.turpgames.ballgamepuzzle.components.IGameMenuListener;
import com.turpgames.ballgamepuzzle.components.LevelResetEffect;
import com.turpgames.ballgamepuzzle.levels.BallContactFilter;
import com.turpgames.ballgamepuzzle.levels.BallMeta;
import com.turpgames.ballgamepuzzle.levels.BlockMeta;
import com.turpgames.ballgamepuzzle.levels.LevelMeta;
import com.turpgames.ballgamepuzzle.levels.PackFactory;
import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.objects.Block;
import com.turpgames.ballgamepuzzle.objects.Spanner;
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
import com.turpgames.framework.v0.impl.InputListener;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Timer;

public class GameController implements IGameMenuListener, IGameController {
	private final static int StateWaitingTouchDown = 0;
	private final static int StateWaitingTouchUp = 1;
	private final static int StatePlaying = 2;
	private final static int StateGameOver = 3;
	private final static int StateReseting = 4;
	private final static int StateGameEnd = 5;
	private final static int StatePaused = 6;

	private final IScreenView view;
	private final IWorld world;
	private final GameMenu menu;
	private final LevelResetEffect resetEffect;
	private final Timer restartTimer;
	private final Text pointText;
	private final Text hitPointText;
	private SubjectBall ball;
	private List<Ball> balls;
	private Spanner spanner;

	private int state;
	private int prevState;
	private int stars;

	private final List<IDrawable> drawables = new ArrayList<IDrawable>();

	public GameController(IScreenView view) {
		Box2D.gravity = -7.5f;

		this.view = view;
		this.menu = new GameMenu(this);
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

		this.pointText = new Text();
		this.pointText.setLocation(400f, 10f);
		this.pointText.setFontScale(0.33f);
		this.pointText.setWidth(100f);
		this.pointText.setHeight(30f);
		this.pointText.setAlignment(Text.HAlignCenter, Text.VAlignCenter);
		
		this.hitPointText = new Text();
		this.hitPointText.setLocation(200f, 10f);
		this.hitPointText.setFontScale(0.33f);
		this.hitPointText.setWidth(100f);
		this.hitPointText.setHeight(30f);
		this.hitPointText.setAlignment(Text.HAlignCenter, Text.VAlignCenter);

		// view.registerDrawable(new IDrawable() {
		// @Override
		// public void draw() {
		// world.renderLights();
		// }
		// }, Game.LAYER_DIALOG);

//		 view.registerDrawable(new IDrawable() {
//		 @Override
//		 public void draw() {
//		 world.drawDebug();
//		 }
//		 }, Game.LAYER_DIALOG);
	}

	@Override
	public void onHitTarget() {
		if (Global.designerMode) {
			onHitEnemy();
		} else {
			state = StateGameOver;

			Global.stars = stars;
			ScreenManager.instance.switchTo(R.screens.result, false);
		}
	}

	@Override
	public void onHitEnemy() {
		state = StateGameOver;

		stopBallEffects();

		restartTimer.start();
	}

	@Override
	public void onHitStar() {
		stars++;
	}

	public void showMenu() {
		menu.show();
		prevState = state;
		state = StatePaused;
	}

	@Override
	public void onMenuHide() {
		state = prevState;
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
		menu.deactivate();

		view.unregisterInputListener(listener);
	}

	private void initGame() {
		if (Global.designerMode) {
			Global.currentLevel = PackFactory.getDesignerLevel();
		}

		LevelMeta level = Global.currentLevel;

		world.reset();
		// world.enableLights();

		registerGameDrawable(new Walls(world));
		// registerGameDrawable(new Cup(world, 200, 40));
		// registerGameDrawable(new Block(world, 100, 150, 300, 10));
		initBalls();

		for (BlockMeta meta : level.getBlocks()) {
			registerGameDrawable(new Block(world, meta));
		}

		world.setContactListener(level.getContactListener());
		world.setContactFilter(BallContactFilter.instance);
		Global.levelPackViewId = level.getPack().getTitle();
		
		if (Global.designerMode) {
			registerGameDrawable(pointText);
			registerGameDrawable(hitPointText);
		}

		state = StateWaitingTouchDown;
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
		stars = 0;
		
		startBallEffects();
	}

	private void endGame() {
		state = StateGameEnd;

		stopBallEffects();

		for (IDrawable d : drawables)
			view.unregisterDrawable(d);
		drawables.clear();
	}

	public void resetGame() {
		if (state == StateReseting)
			return;
		state = StateReseting;

		stopBallEffects();

		restartTimer.stop();
		resetEffect.start();
	}

	public void update() {
		if (state == StatePlaying) {
			world.update();
		}
	}

	private void stopBallEffects() {
		for (Ball ball : balls)
			ball.stopEffect();
	}

	private void startBallEffects() {
		for (Ball ball : balls)
			ball.startEffect();
	}

	private void beginSpanning(float x, float y) {
		state = StateWaitingTouchUp;
		spanner = new Spanner(ball.getCenterX(), ball.getCenterY(), x, y);
		view.registerDrawable(spanner, Game.LAYER_GAME);
	}

	private boolean onTouchUp(float x, float y) {
		if (state == StateWaitingTouchUp) {
			startPlaying();
			hit(spanner.getHitPoint().x, spanner.getHitPoint().y);
			
			hitPointText.setText("(" + spanner.getHitPoint().x + ", " + spanner.getHitPoint().y + ")");
			
			view.unregisterDrawable(spanner);
			spanner = null;
		}
		return false;
	}

	private boolean onTouchDragged(float x, float y) {
		if (state != StateWaitingTouchUp) {
			return false;
		}

		spanner.update(x, y);
		hitPointText.setText("(" + spanner.getHitPoint().x + ", " + spanner.getHitPoint().y + ")");

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
			if (state == StateGameOver) {
				resetGame();
				return true;
			} else if (state == StateWaitingTouchDown) {
				beginSpanning(Game.screenToViewportX(x), Game.screenToViewportY(y));
				return true;
			}
			return false;
		}

		@Override
		public boolean touchUp(float x, float y, int pointer, int button) {
			return onTouchUp(Game.screenToViewportX(x), Game.screenToViewportY(y));
		}

		@Override
		public boolean touchDragged(float x, float y, int pointer) {
			return onTouchDragged(Game.screenToViewportX(x), Game.screenToViewportY(y));
		}

		public boolean tap(float x, float y, int count, int button) {
			if (Global.designerMode) {
				pointText.setText("(" + Game.screenToViewportX(x) + ", " + Game.screenToViewportY(y) + ")");
			}
			return false;
		}
	};
}

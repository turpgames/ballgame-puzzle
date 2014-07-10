package com.turpgames.ballgamepuzzle.controller;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.ballgamepuzzle.levels.BallMeta;
import com.turpgames.ballgamepuzzle.levels.LevelMeta;
import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.objects.Walls;
import com.turpgames.ballgamepuzzle.objects.balls.PortalBall;
import com.turpgames.ballgamepuzzle.objects.balls.SubjectBall;
import com.turpgames.ballgamepuzzle.utils.Global;
import com.turpgames.ballgamepuzzle.utils.Sounds;
import com.turpgames.ballgamepuzzle.view.IScreenView;
import com.turpgames.box2d.Box2DWorld;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.impl.InputListener;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;

public class GameController implements IGameController {
	private final IScreenView view;
	private Box2DWorld world;
	private SubjectBall ball;

	private boolean isPlaying;
	private boolean isGameOver;
	private int hits;
	private Text text;

	private final List<IDrawable> drawables = new ArrayList<IDrawable>();

	public GameController(IScreenView view) {
		this.view = view;
	}

	private void initBalls() {
		LevelMeta meta = Global.levelMeta;

		PortalBall orangePair = null;

		for (BallMeta ballMeta : meta.getBalls()) {
			Ball ball = Ball.create(ballMeta, world);

			if (ball.getType() == Ball.Subject) {
				this.ball = (SubjectBall) ball;
			}
			else if (ball.getType() == Ball.Portal) {
				if (orangePair == null) {
					orangePair = (PortalBall) ball;
				}
				else {
					PortalBall.makePair(orangePair, (PortalBall) ball);
					orangePair = null;
				}
			}

			registerGameDrawable(ball);
		}

		if (orangePair != null) {
			throw new UnsupportedOperationException("Unpaired orange ball exists!");
		}
	}

	@Override
	public void onHitGreen() {
		isPlaying = false;
		isGameOver = true;
		int hitCount = Global.levelMeta.getMaxHit() - hits;
		if (hitCount <= Global.levelMeta.getStar3())
			updateText("you win: full star!");
		else if (hitCount <= Global.levelMeta.getStar2())
			updateText("you win: half star!");
		else if (hitCount <= Global.levelMeta.getStar1())
			updateText("you win: empty star!");
	}

	@Override
	public void onHitRed() {
		isPlaying = false;
		isGameOver = true;
		updateText("touched red ball");
	}

	private void onHitCountEnd() {
		isPlaying = false;
		isGameOver = true;
		Sounds.enemy.play();
		updateText("no more hits");
	}
	
	private void updateText(String s) {
		text.setText(s);
	}

	public void activate() {
		Global.currentGame = this;
		
		text = new Text();
		text.setLocation(Game.screenToViewportX(10f), Game.screenToViewportY(10f));
		text.setFontScale(0.5f);

		initGame();

		view.registerDrawable(text, Game.LAYER_INFO);
		view.registerInputListener(listener);
	}

	public void deactivate() {
		Global.currentGame = null;

		endGame();

		view.unregisterDrawable(text);
		view.unregisterInputListener(listener);
	}

	private void startPlaying() {
		isPlaying = true;
		hits = Global.levelMeta.getMaxHit();
		updateText(hits + "");
	}

	private void initGame() {
		isPlaying = false;
		isGameOver = false;
		world = new Box2DWorld();

		registerGameDrawable(new Walls(world));

		initBalls();
		world.getWorld().setContactListener(Global.levelMeta.getContactListener());
		
		updateText("touch to begin");
	}

	private void endGame() {
		isPlaying = false;

		world.getWorld().dispose();
		world = null;
		for (IDrawable d : drawables)
			view.unregisterDrawable(d);
		drawables.clear();
	}

	public void update() {
		if (isPlaying) {
			world.update();
			if (hits == 0 && !ball.isMoving()) {
				onHitCountEnd();
			}
		}
	}

	private boolean onTouchDown(float x, float y) {
		if (isPlaying) {
			if (hits > 0) {
				hit(x, y);
				hits--;
				updateText(hits + "");
			}
		} else if (isGameOver) {
			endGame();
			initGame();
		} else {
			startPlaying();
		}
		return false;
	}

	private void hit(float x, float y) {
		if (Game.descale(Game.getScreenHeight()) - y < 100)
			return;

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
			if (y > Game.getVirtualHeight() - 100f)
				return false;
			return onTouchDown(Game.screenToViewportX(x), y);
		}
	};
}

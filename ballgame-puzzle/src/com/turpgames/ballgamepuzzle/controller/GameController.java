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
		
		LevelMeta level = Global.levelMeta;
		
		int hitCount = level.getMaxHit() - hits;
		if (hitCount <= level.getStar3()) {
			level.updateState(LevelMeta.Star3);
			updateText("you win: full star!");
		}
		else if (hitCount <= level.getStar2()) {
			if (level.getState() < LevelMeta.Star2)
				level.updateState(LevelMeta.Star2);
			updateText("you win: half star!");
		}
		else if (hitCount <= level.getStar1()) {
			if (level.getState() < LevelMeta.Star1)
				level.updateState(LevelMeta.Star1);
			updateText("you win: empty star!");
		}
	}

	@Override
	public void onHitEnemy() {
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
		text.setLocation(10f, 10f);
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

	public void resetGame() {
		endGame();
		initGame();
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

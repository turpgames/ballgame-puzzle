package com.turpgames.ballgamepuzzle.levels;

import com.turpgames.ballgamepuzzle.collisionhandlers.BallCollisionHandlerChain;
import com.turpgames.ballgamepuzzle.collisionhandlers.BounceHandler;
import com.turpgames.ballgamepuzzle.collisionhandlers.EnemyHandler;
import com.turpgames.ballgamepuzzle.collisionhandlers.HoleHandler;
import com.turpgames.ballgamepuzzle.collisionhandlers.PortalHandler;
import com.turpgames.ballgamepuzzle.collisionhandlers.RedGrayHandler;
import com.turpgames.ballgamepuzzle.collisionhandlers.StoneHandler;
import com.turpgames.ballgamepuzzle.collisionhandlers.TargetHandler;
import com.turpgames.ballgamepuzzle.effects.meta.BreathEffectMeta;
import com.turpgames.ballgamepuzzle.effects.meta.CircularTripEffectMeta;
import com.turpgames.ballgamepuzzle.effects.meta.ExistanceEffectMeta;
import com.turpgames.ballgamepuzzle.effects.meta.HoleEffectMeta;
import com.turpgames.ballgamepuzzle.effects.meta.PathTripEffectMeta;
import com.turpgames.ballgamepuzzle.effects.meta.RollingEffectMeta;
import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.objects.Walls;
import com.turpgames.ballgamepuzzle.utils.R;
import com.turpgames.framework.v0.util.Game;

class GreenPack {
	private final static String packTitle = "Green Pack";

	private static LevelPack pack;

	public static LevelPack getPack() {
		if (pack == null)
			createPack();
		return pack;
	}

	private static void createPack() {
		LevelMeta level1 = level1();

		if (level1.getState() == LevelMeta.Locked)
			level1.setState(LevelMeta.Unlocked);

		pack = LevelPack.newBuilder()
				.setTitle(packTitle)
				.setThemeColor(R.colors.green)
				.addLevel(level1)
				.addLevel(level2())
				.addLevel(level3())
				.addLevel(level4())
				.addLevel(level5())
				.addLevel(level6())
				.addLevel(level7())
				.addLevel(level8())
				.addLevel(level9())
				.addLevel(level10())
				.addLevel(level11())
				.addLevel(level12())
				.addLevel(level13())
				.addLevel(level14())
				.addLevel(level15())
				.addLevel(level16())
				.addLevel(level17())
				.addLevel(level18())
				.addLevel(level18())
				.addLevel(level18())
				.build();

		for (LevelMeta level : pack.getLevels())
			level.setPack(pack);
	}

	private static LevelMeta level1() {
		return newBuilder(1)
				.setScoreMeta(6, 3)
				.addBall(Ball.Subject, Ball.Medium, Ball.ViewportCenterX, Ball.ViewportCenterY * 1.5f)
				.addBall(Ball.Target, Ball.Medium, Ball.ViewportCenterX - 5f, Ball.ViewportCenterY * 0.5f)
				.setDescription("Goal of the game is making blue ball to touch green ball. To start playing touch screen after clicking Ok button.")
				.build();
	}

	private static LevelMeta level2() {
		return newBuilder(2)
				.setScoreMeta(6, 3)
				.addBall(Ball.Subject, Ball.Medium, 100f, Ball.ViewportCenterY * 1.20f)
				.addBall(Ball.Target, Ball.Medium, 350f, Ball.ViewportCenterY * 1.60f)
				.setDescription("You can control blue ball by tapping around. To start playing touch screen after clicking Ok button.")
				.build();
	}

	private static LevelMeta level3() {
		return newBuilder(3)
				.setScoreMeta(6, 3)
				.addBall(Ball.Subject, Ball.Medium, 100, 650)
				.addBall(Ball.Target, Ball.Medium, 390, 100)
				.addBall(Ball.Stone, Ball.Medium, 80, 400)
				.addBall(Ball.Stone, Ball.Medium, 350, 350)
				.addBall(Ball.Stone, Ball.Medium, 95, 200)
				.setDescription("Gray balls reflects you in physics rules. To start playing touch screen after clicking Ok button.")
				.build();
	}

	private static LevelMeta level4() {
		return newBuilder(4)
				.setScoreMeta(6, 3)
				.addBall(Ball.Subject, Ball.Medium, 100, 650)
				.addBall(Ball.Target, Ball.Medium, 350, 440)
				.addBall(Ball.Stone, Ball.Medium, 95, 100)
				.addBall(Ball.Bounce, Ball.Medium, 200, 220)
				.setDescription("Yellow balls makes you bounce. To start playing touch screen after clicking Ok button.")
				.build();
	}

	private static LevelMeta level5() {
		return newBuilder(5)
				.setScoreMeta(6, 3)
				.addBall(Ball.Subject, Ball.Medium, 100, 650)
				.addBall(Ball.Target, Ball.Medium, 350, 650)
				.addBall(Ball.Bounce, Ball.Medium, 80f, 100)
				.addBall(Ball.Bounce, Ball.Large, 300, 100)
				.setDescription("The greater is yellow ball the more you bounce. To start playing touch screen after clicking Ok button.")
				.build();
	}

	private static LevelMeta level6() {
		return newBuilder(6)
				.setScoreMeta(6, 3)
				.addBall(Ball.Portal, Ball.Medium, 100, 150)
				.addBall(Ball.Portal, Ball.Medium, 350, 600)
				.addBall(Ball.Subject, Ball.Medium, 100, 600)
				.addBall(Ball.Target, Ball.Medium, 350, 150)
				.setDescription("Orange balls are portals. To start playing touch screen after clicking Ok button.")
				.build();
	}

	private static LevelMeta level7() {
		return newBuilder(7)
				.setScoreMeta(6, 3)
				.addBall(Ball.Portal, Ball.Medium, 350, 525)
				.addBall(Ball.Portal, Ball.Medium, 250, 700)
				.addBall(Ball.Subject, Ball.Medium, 150, 700)
				.addBall(Ball.Stone, Ball.Medium, 140, 100)
				.addBall(Ball.Enemy, Ball.Large, 280, 220)
				.addBall(Ball.Bounce, Ball.Medium, 400, 200)
				.addBall(Ball.Stone, Ball.Medium, 200, 450)
				.addBall(Ball.Enemy, Ball.Large, 300, 600)
				.addBall(Ball.Target, Ball.Medium, 80, 350)
				.setDescription("Blue ball should not touch to red balls. To start playing touch screen after clicking Ok button.")
				.build();
	}

	private static LevelMeta level8() {
		LevelMeta.Builder builder = newBuilder(8)
				.setScoreMeta(5, 4)
				.addBall(Ball.Portal, Ball.Medium, 150, 150)
				.addBall(Ball.Portal, Ball.Medium, 350, 650)
				.addBall(Ball.Subject, Ball.Medium, 100, 650)
				.addBall(Ball.Target, Ball.Medium, 400, 150)
				.addBall(Ball.Enemy, Ball.Medium, 300, 50);

		float w = Game.getVirtualWidth();
		int i = 0;
		while (w > Ball.Small) {
			float dx = (Ball.Small * 2 + 10f);
			builder.addBall(Ball.Stone, Ball.Small, 10f + (i++ * dx) + Ball.Small, 300);
			w -= dx;
		}

		for (float y = 300 + 6 * Ball.Small; y < Game.getVirtualHeight() - 80f; y += 2 * Ball.Small + 10f) {
			builder.addBall(Ball.Stone, Ball.Small, Game.getVirtualWidth() / 2, y);
		}

		return builder.build();
	}

	private static LevelMeta level9() {
		LevelMeta.Builder builder = newBuilder(9)
				.setScoreMeta(6, 3)
				.addBall(Ball.Subject, Ball.Medium, 100, 650)
				.addBall(Ball.Target, Ball.Medium, 400, 150);

		PathTripEffectMeta effect = new PathTripEffectMeta();
		effect.setTotalDuration(4f);
		effect.setRoundTrip(true);
		effect.addNode(150, 100)
				.addNode(300, 100)
				.addNode(300, 300)
				.addNode(150, 300);

		BallMeta ball = new BallMeta(Ball.Stone, Ball.Medium, 150, 100);
		ball.addEffect(effect);
		builder.addBall(ball);

		return builder.build();
	}

	private static LevelMeta level10() {
		LevelMeta.Builder builder = newBuilder(10)
				.setScoreMeta(6, 3)
				.addBall(Ball.Subject, Ball.Medium, 100, 150)
				.addBall(Ball.Target, Ball.Medium, 400, 500);

		PathTripEffectMeta effect = new PathTripEffectMeta();
		effect.setTotalDuration(4f);
		effect.setRoundTrip(true);
		effect.addNode(200, 350).addNode(400, 350);

		BallMeta ball = new BallMeta(Ball.Enemy, Ball.Medium, 200, 350);
		ball.addEffect(effect);
		builder.addBall(ball);

		effect = new PathTripEffectMeta();
		effect.setTotalDuration(4f);
		effect.setRoundTrip(true);
		effect.addNode(200, 650).addNode(200, 350);

		ball = new BallMeta(Ball.Enemy, Ball.Medium, 200, 650);
		ball.addEffect(effect);
		builder.addBall(ball);

		return builder.build();
	}

	private static LevelMeta level11() {
		LevelMeta.Builder builder = newBuilder(11)
				.setScoreMeta(6, 3)
				.addBall(Ball.Subject, Ball.Medium, 100, 600)
				.addBall(Ball.Target, Ball.Medium, 300, 200);

		CircularTripEffectMeta effect = new CircularTripEffectMeta();
		effect.setCenter(300, 200);
		effect.setClockWise(false);
		effect.setTotalDuration(2f);

		BallMeta ball = new BallMeta(Ball.Enemy, Ball.Medium, 300, 300);
		ball.addEffect(effect);
		builder.addBall(ball);

		ball = new BallMeta(Ball.Enemy, Ball.Medium, 200, 200);
		ball.addEffect(effect);
		builder.addBall(ball);

		ball = new BallMeta(Ball.Enemy, Ball.Medium, 300, 100);
		ball.addEffect(effect);
		builder.addBall(ball);

		ball = new BallMeta(Ball.Enemy, Ball.Medium, 400, 200);
		ball.addEffect(effect);
		builder.addBall(ball);

		return builder.build();
	}

	private static LevelMeta level12() {
		LevelMeta.Builder builder = newBuilder(12)
				.setScoreMeta(6, 3)
				.addBall(Ball.Subject, Ball.Medium, 125, 650)
				.addBall(Ball.Target, Ball.Medium, 400, 650);

		RollingEffectMeta effect = new RollingEffectMeta();
		effect.setTotalDuration(2);
		effect.setClockWise(false);

		BallMeta ball = new BallMeta(Ball.RedGray, Ball.Medium, 200, 650);
		ball.addEffect(effect);
		builder.addBall(ball);

		ball = new BallMeta(Ball.RedGray, Ball.Medium, 400, 450);
		ball.addEffect(effect);
		builder.addBall(ball);

		effect = new RollingEffectMeta();
		effect.setTotalDuration(1);
		effect.setClockWise(true);

		ball = new BallMeta(Ball.RedGray, Ball.Medium, 300, 550);
		ball.addEffect(effect);
		builder.addBall(ball);

		return builder.build();
	}

	private static LevelMeta level13() {
		LevelMeta.Builder builder = newBuilder(13)
				.setScoreMeta(6, 3)
				.addBall(Ball.Subject, Ball.Medium, 225, 325)
				.addBall(Ball.Target, Ball.Medium, 225, 650);

		BreathEffectMeta effect = new BreathEffectMeta();
		effect.setMinScale(0.5f);
		effect.setMaxScale(1f);
		effect.setStartFromMaxScale(true);
		effect.setTotalDuration(3f);

		float dx = 87.5f;
		for (int i = 0; i < 6; i++) {
			BallMeta ball = new BallMeta(Ball.Enemy, Ball.Medium, 50f + (dx * i), 400);
			ball.addEffect(effect);
			builder.addBall(ball);
		}

		return builder.build();
	}

	private static LevelMeta level14() {
		LevelMeta.Builder builder = newBuilder(14)
				.setScoreMeta(6, 3)
				.addBall(Ball.Subject, Ball.Medium, 75, 650)
				.addBall(Ball.Target, Ball.Medium, 375, 650);

		float yTop = Game.getVirtualHeight() - Walls.marginY;

		for (int i = 0; i < 36; i++) {
			ExistanceEffectMeta effect = new ExistanceEffectMeta();
			effect.setDurations((i + 1) * 0.1f, 1f);
			effect.setHiddenAtStartup(true);

			BallMeta ball = new BallMeta(Ball.Stone, Ball.Small * 0.5f, 300f, yTop - (20f * (i + 1)));
			ball.setHidden(true);
			ball.addEffect(effect);
			builder.addBall(ball);

			ball = new BallMeta(Ball.Enemy, Ball.Small * 0.5f, 150f, 20f * (i + 1));
			ball.setHidden(true);
			ball.addEffect(effect);
			builder.addBall(ball);
		}

		return builder.build();
	}

	private static LevelMeta level15() {
		LevelMeta.Builder builder = newBuilder(15)
				.setScoreMeta(6, 3)
				.addBall(Ball.Subject, Ball.Medium, 100, 550)
				.addBall(Ball.Target, Ball.Medium, 300, 550);

		RollingEffectMeta effect = new RollingEffectMeta();
		effect.setTotalDuration(1f);
		effect.setClockWise(false);

		CircularTripEffectMeta effect2 = new CircularTripEffectMeta();
		effect2.setCenter(300, 550);
		effect2.setClockWise(true);
		effect2.setTotalDuration(3f);

		BreathEffectMeta effect3 = new BreathEffectMeta();
		effect3.setMinScale(0.5f);
		effect3.setMaxScale(1f);
		effect3.setStartFromMaxScale(true);
		effect3.setTotalDuration(3f);

		ExistanceEffectMeta effect4 = new ExistanceEffectMeta();
		effect4.setDurations(2f, 2f);
		effect4.setLooping(true);
		effect4.setHiddenAtStartup(false);

		BallMeta ball = new BallMeta(Ball.RedGray, Ball.Large, 400, 550);
		ball.addEffect(effect);
		ball.addEffect(effect2);
		ball.addEffect(effect3);
		ball.addEffect(effect4);
		builder.addBall(ball);

		ball = new BallMeta(Ball.RedGray, Ball.Large, 200, 550);
		ball.addEffect(effect);
		ball.addEffect(effect2);
		ball.addEffect(effect3);
		ball.addEffect(effect4);
		builder.addBall(ball);

		effect4 = new ExistanceEffectMeta();
		effect4.setDurations(2f, 2f);
		effect4.setLooping(true);
		effect4.setHiddenAtStartup(true);

		ball = new BallMeta(Ball.RedGray, Ball.Large, 300, 650);
		ball.addEffect(effect);
		ball.addEffect(effect2);
		ball.addEffect(effect3);
		ball.addEffect(effect4);
		ball.setHidden(true);
		builder.addBall(ball);

		ball = new BallMeta(Ball.RedGray, Ball.Large, 300, 450);
		ball.addEffect(effect);
		ball.addEffect(effect2);
		ball.addEffect(effect3);
		ball.addEffect(effect4);
		ball.setHidden(true);
		builder.addBall(ball);

		return builder.build();
	}

	private static LevelMeta level16() {
		LevelMeta.Builder builder = newBuilder(16)
				.setScoreMeta(6, 3);

		HoleEffectMeta effect = new HoleEffectMeta();

		BallMeta ball = new BallMeta(Ball.BlackHole, Ball.Small, 225, 400);
		ball.addEffect(effect);

		return builder.addBall(ball)
				.addBall(Ball.Subject, Ball.Medium, 100, 600)
				.addBall(Ball.Target, Ball.Medium, 400, 650)
				.build();
	}

	private static LevelMeta level17() {
		LevelMeta.Builder builder = newBuilder(17)
				.setScoreMeta(6, 3);

		HoleEffectMeta effect = new HoleEffectMeta();
		effect.setWhiteHole(true);

		BallMeta ball = new BallMeta(Ball.WhiteHole, Ball.Small, 225, 400);
		ball.addEffect(effect);

		return builder.addBall(ball)
				.addBall(Ball.Subject, Ball.Medium, 100, 600)
				.addBall(Ball.Target, Ball.Medium, 400, 650)
				.build();
	}

	static int i = 18;

	private static LevelMeta level18() {
		return newBuilder(i++)
				.addBall(Ball.Subject, Ball.Medium, 150, 650)
				.addBall(Ball.Target, Ball.Medium, 400, 550)
				.addBall(Ball.Bounce, Ball.Medium, 125, 100)
				.addBall(Ball.Bounce, Ball.Large, 400, 100)
				.build();
	}

	private static LevelMeta.Builder newBuilder(int index) {
		return LevelMeta.newBuilder(packTitle + index)
				.setIndex(index)
				.setContactListener(new BallContactListener(
						new BallCollisionHandlerChain(
								new StoneHandler(),
								new TargetHandler(),
								new BounceHandler(),
								new PortalHandler(),
								new EnemyHandler(),
								new RedGrayHandler(),
								new HoleHandler(Ball.BlackHole),
								new HoleHandler(Ball.WhiteHole))));
	}
}

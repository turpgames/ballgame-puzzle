package com.turpgames.ballgamepuzzle.levels;

import com.turpgames.ballgamepuzzle.collisionhandlers.BallCollisionHandlerChain;
import com.turpgames.ballgamepuzzle.collisionhandlers.StoneHandler;
import com.turpgames.ballgamepuzzle.collisionhandlers.TargetHandler;
import com.turpgames.ballgamepuzzle.collisionhandlers.PortalHandler;
import com.turpgames.ballgamepuzzle.collisionhandlers.EnemyHandler;
import com.turpgames.ballgamepuzzle.collisionhandlers.BounceHandler;
import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.utils.R;
import com.turpgames.framework.v0.util.Game;

class OrangePack {
	private final static String packTitle = "Orange Pack";
	
	private static LevelPack pack;
	
	public static LevelPack getPack() {
		if (pack == null)
			createPack();
		return pack;
	}

	private static void createPack() {
		LevelMeta level1 = level1();

		pack = LevelPack.newBuilder()
				.setTitle(packTitle)
				.setThemeColor(R.colors.orange)
				.addLevel(level1)
				.addLevel(level2())
				.addLevel(level3())
				.addLevel(level4())
				.addLevel(level5())
				.addLevel(level6())
				.addLevel(level7())
				.addLevel(level8())
				.addLevel(level9())
				.addLevel(level9())
				.addLevel(level9())
				.addLevel(level9())
				.addLevel(level9())
				.addLevel(level9())
				.addLevel(level9())
				.addLevel(level9())
				.addLevel(level9())
				.addLevel(level9())
				.addLevel(level9())
				.addLevel(level9())
				.build();
		
		for (LevelMeta level : pack.getLevels())
			level.setPack(pack);
	}

	private static LevelMeta level1() {
		return newBuilder(1)
				.setScoreMeta(6, 3)
				.addBall(Ball.Subject, Ball.Medium, Ball.ViewportCenterX, Ball.ViewportCenterY * 1.5f)
				.addBall(Ball.Target, Ball.Medium, Ball.ViewportCenterX - 5f, Ball.ViewportCenterY * 0.5f)
				.build();
	}

	private static LevelMeta level2() {
		return newBuilder(2)
				.setScoreMeta(6, 3)
				.addBall(Ball.Subject, Ball.Medium, 100f, Ball.ViewportCenterY * 1.20f)
				.addBall(Ball.Target, Ball.Medium, 350f, Ball.ViewportCenterY * 1.60f)
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
				.build();
	}

	private static LevelMeta level4() {
		return newBuilder(4)
				.setScoreMeta(6, 3)
				.addBall(Ball.Subject, Ball.Medium, 100, 650)
				.addBall(Ball.Target, Ball.Medium, 350, 440)
				.addBall(Ball.Stone, Ball.Medium, 95, 100)
				.addBall(Ball.Bounce, Ball.Medium, 200, 220)
				.build();
	}

	private static LevelMeta level5() {
		return newBuilder(5)
				.setScoreMeta(6, 3)
				.addBall(Ball.Subject, Ball.Medium, 100, 650)
				.addBall(Ball.Target, Ball.Medium, 350, 650)
				.addBall(Ball.Bounce, Ball.Medium, 80f, 100)
				.addBall(Ball.Bounce, Ball.Large, 300, 100)
				.build();
	}

	private static LevelMeta level6() {
		return newBuilder(6)
				.setScoreMeta(6, 3)
				.addBall(Ball.Portal, Ball.Medium, 100, 150)
				.addBall(Ball.Portal, Ball.Medium, 350, 600)
				.addBall(Ball.Subject, Ball.Medium, 100, 600)
				.addBall(Ball.Target, Ball.Medium, 350, 150)
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

	static int i = 9;

	private static LevelMeta level9() {
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
				.setContactListener(new DefaultContactListener(
						new BallCollisionHandlerChain(
								new StoneHandler(),
								new TargetHandler(),
								new BounceHandler(),
								new PortalHandler(),
								new EnemyHandler())));
	}
}

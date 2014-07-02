package com.turpgames.ballgamepuzzle.levels;

import com.turpgames.ballgamepuzzle.objects.Ball;

class StarterPack {
	private final static String packTitle = "Starter Pack";

	public static LevelPack createPack() {
		LevelMeta level1 = level1();
		
		if (level1.getState() == LevelMeta.Locked)
			level1.updateState(LevelMeta.Unlocked);
		
		return LevelPack.newBuilder()
				.setTitle(packTitle)
				.addLevel(level1)
				.addLevel(level2())
				.addLevel(level3())
				.addLevel(level4())
				.addLevel(level5())
				.build();
	}

	private static LevelMeta level1() {
		return newBuilder(1)
				.addBall(Ball.Azure, Ball.Medium, Ball.ViewportCenterX, Ball.ViewportCenterY * 1.5f)
				.addBall(Ball.Green, Ball.Medium, Ball.ViewportCenterX - 5f, Ball.ViewportCenterY * 0.5f)
				.build();
	}

	private static LevelMeta level2() {
		return newBuilder(2)
				.addBall(Ball.Azure, Ball.Medium, Ball.ViewportCenterX, Ball.ViewportCenterY * 1.33f)
				.addBall(Ball.Green, Ball.Medium, Ball.ViewportCenterX, Ball.ViewportCenterY * 1.75f)
				.build();
	}

	private static LevelMeta level3() {
		return newBuilder(3)
				.addBall(Ball.Azure, Ball.Medium, 150, 750)
				.addBall(Ball.Green, Ball.Medium, 440, 150)
				.addBall(Ball.Gray, Ball.Medium, 145, 100)
				.addBall(Ball.Gray, Ball.Medium, 240, 250)
				.build();
	}

	private static LevelMeta level4() {
		return newBuilder(4)
				.addBall(Ball.Azure, Ball.Medium, 150, 750)
				.addBall(Ball.Green, Ball.Medium, 440, 440)
				.addBall(Ball.Gray, Ball.Medium, 145, 100)
				.addBall(Ball.Yellow, Ball.Medium, 240, 250)
				.build();
	}

	private static LevelMeta level5() {
		return newBuilder(5)
				.addBall(Ball.Azure, Ball.Medium, 150, 750)
				.addBall(Ball.Green, Ball.Medium, 450, 650)
				.addBall(Ball.Yellow, Ball.Medium, 125, 100)
				.addBall(Ball.Yellow, Ball.Large, 400, 100)
				.build();
	}

	private static LevelMeta.Builder newBuilder(int index) {
		return LevelMeta.newBuilder(packTitle + index)
				.setIndex(index)
				.setContactListener(new DefaultContactListener());
	}
}

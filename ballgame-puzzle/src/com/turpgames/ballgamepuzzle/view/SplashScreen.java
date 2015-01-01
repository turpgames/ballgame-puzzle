package com.turpgames.ballgamepuzzle.view;

import com.turpgames.ballgamepuzzle.components.TurpLogo;
import com.turpgames.ballgamepuzzle.updates.BallGameUpdateManager;
import com.turpgames.ballgamepuzzle.utils.R;
import com.turpgames.framework.v0.IResourceManager;
import com.turpgames.framework.v0.client.ConnectionManager;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Debug;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;

public class SplashScreen extends Screen {
	private Color progressColor;
	private IResourceManager resourceManager;

	@Override
	public void draw() {
		super.draw();
		float f = (Game.getVirtualWidth() - 50f) * resourceManager.getProgress();
		ShapeDrawer.drawRect((Game.getVirtualWidth() - f) / 2.0f, 40f, f, 10f, progressColor, true, false);
	}

	@Override
	public void init() {
		ConnectionManager.init();
		super.init();
		registerDrawable(new TurpLogo(), Game.LAYER_GAME);
		progressColor = Color.fromHex("#f9b000ff");
		resourceManager = Game.getResourceManager();

		Debug.println(Game.getPhysicalScreenSize());
	}

	@Override
	public void update() {
		if (!resourceManager.isLoading()) {
			BallGameUpdateManager.runUpdates();
			switchToGame();
		}
	}

	private void switchToGame() {
		ScreenManager.instance.switchTo(R.screens.menu, false);
//		ScreenManager.instance.switchTo("test", false);
	}
}

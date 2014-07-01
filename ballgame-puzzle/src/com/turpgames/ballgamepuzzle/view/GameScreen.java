package com.turpgames.ballgamepuzzle.view;

import com.turpgames.ballgamepuzzle.controller.GameController;
import com.turpgames.ballgamepuzzle.utils.R;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;

public class GameScreen extends Screen implements IScreenView {

	private GameController controller;

	public void init() {
		super.init();
		controller = new GameController(this);
	}

	@Override
	protected void onAfterActivate() {
		super.onAfterActivate();
		controller.activate();
	}

	@Override
	protected boolean onBeforeDeactivate() {
		controller.deactivate();
		return super.onBeforeDeactivate();
	}

	@Override
	public void update() {
		super.update();
		controller.update();
	}

	@Override
	protected boolean onBack() {
		ScreenManager.instance.switchTo(R.screens.menu, true);
		return true;
	}
}

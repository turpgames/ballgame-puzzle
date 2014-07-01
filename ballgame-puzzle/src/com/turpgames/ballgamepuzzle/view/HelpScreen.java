package com.turpgames.ballgamepuzzle.view;

import com.turpgames.ballgamepuzzle.controller.AutoPlayGameController;
import com.turpgames.ballgamepuzzle.utils.R;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;

public class HelpScreen extends Screen implements IScreenView {
	private AutoPlayGameController controller;

	public void init() {
		super.init();
		controller = new AutoPlayGameController(this);
	}

	@Override
	protected void onAfterActivate() {
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

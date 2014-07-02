package com.turpgames.ballgamepuzzle.view;

import com.turpgames.ballgamepuzzle.components.Toolbar;
import com.turpgames.ballgamepuzzle.controller.GameController;
import com.turpgames.ballgamepuzzle.utils.R;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;

public class GameScreen extends Screen implements IScreenView {

	private GameController controller;

	public void init() {
		super.init();
		controller = new GameController(this);
		registerDrawable(Toolbar.getInstance(), Game.LAYER_INFO);
	}

	@Override
	protected void onAfterActivate() {
		super.onAfterActivate();
		controller.activate();
		Toolbar.getInstance().enable();
		Toolbar.getInstance().setListener(new com.turpgames.framework.v0.component.Toolbar.IToolbarListener() {
			@Override
			public void onToolbarBack() {
				onBack();
			}
		});
	}

	@Override
	protected boolean onBeforeDeactivate() {
		controller.deactivate();
		Toolbar.getInstance().disable();
		return super.onBeforeDeactivate();
	}

	@Override
	public void update() {
		super.update();
		controller.update();
	}

	@Override
	protected boolean onBack() {
		ScreenManager.instance.switchTo(R.screens.levels, true);
		return true;
	}
}
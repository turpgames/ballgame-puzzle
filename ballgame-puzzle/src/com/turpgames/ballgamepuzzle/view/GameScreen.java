package com.turpgames.ballgamepuzzle.view;

import com.turpgames.ballgamepuzzle.components.Toolbar;
import com.turpgames.ballgamepuzzle.components.ToolbarListenerAdapter;
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

		Toolbar.getInstance().activateResetButton();
		Toolbar.getInstance().activateMenuButton();
		
		Toolbar.getInstance().setListener(new ToolbarListenerAdapter() {
			@Override
			public void onToolbarBack() {
				onBack();
			}
			
			@Override
			public void onResetGame() {
				controller.resetGame();
			}
			
			@Override
			public void onShowMenu() {
				controller.showMenu();
			}
		});
	}

	@Override
	protected boolean onBeforeDeactivate() {
		controller.deactivate();
		Toolbar.getInstance().deactivateResetButton();
		Toolbar.getInstance().deactivate();
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

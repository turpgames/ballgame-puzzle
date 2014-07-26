package com.turpgames.ballgamepuzzle.view;

import com.turpgames.ballgamepuzzle.components.Toolbar;
import com.turpgames.ballgamepuzzle.components.ToolbarListenerAdapter;
import com.turpgames.ballgamepuzzle.controller.LevelSelectionController;
import com.turpgames.ballgamepuzzle.utils.R;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;

public class LevelSelectionScreen extends Screen implements IScreenView {
	private LevelSelectionController controller;

	public void init() {
		super.init();
		controller = new LevelSelectionController(this);
	}
	
	@Override
	protected void onAfterActivate() {
		super.onAfterActivate();
		controller.activate();
		
		Toolbar.getInstance().activate();
		Toolbar.getInstance().setListener(new ToolbarListenerAdapter() {
			@Override
			public void onToolbarBack() {
				onBack();
			}
		});
	}

	@Override
	protected boolean onBeforeDeactivate() {
		controller.deactivate();
		Toolbar.getInstance().deactivate();
		return super.onBeforeDeactivate();
	}

	@Override
	protected boolean onBack() {
		ScreenManager.instance.switchTo(R.screens.menu, true);
		return true;
	}
}

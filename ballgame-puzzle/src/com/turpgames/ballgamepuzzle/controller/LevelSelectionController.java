package com.turpgames.ballgamepuzzle.controller;

import com.turpgames.ballgamepuzzle.levels.StarterLevelsView;
import com.turpgames.ballgamepuzzle.view.IScreenView;
import com.turpgames.framework.v0.impl.TouchSlidingViewSwitcher;
import com.turpgames.framework.v0.util.Game;

public class LevelSelectionController {
	
	private final TouchSlidingViewSwitcher viewSwitcher;
	
	public LevelSelectionController(IScreenView view) {
		viewSwitcher = new TouchSlidingViewSwitcher(false);
		for (int i = 0; i < 5; i++)
			viewSwitcher.addView(new StarterLevelsView(view));
		
		view.registerDrawable(viewSwitcher, Game.LAYER_GAME);
		viewSwitcher.setArea(0, 0, Game.getVirtualWidth(), Game.getVirtualHeight() - 200f);
	}

	public void activate() {
		viewSwitcher.activate();
	}

	public void deactivate() {
		viewSwitcher.deactivate();
	}
}

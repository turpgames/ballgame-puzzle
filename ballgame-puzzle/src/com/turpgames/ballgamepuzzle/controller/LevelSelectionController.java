package com.turpgames.ballgamepuzzle.controller;

import com.turpgames.ballgamepuzzle.levels.LevelPack;
import com.turpgames.ballgamepuzzle.levels.LevelPackView;
import com.turpgames.ballgamepuzzle.levels.PackBuilder;
import com.turpgames.ballgamepuzzle.view.IScreenView;
import com.turpgames.framework.v0.impl.TouchSlidingViewSwitcher;
import com.turpgames.framework.v0.util.Game;

public class LevelSelectionController {

	private final TouchSlidingViewSwitcher viewSwitcher;
	private final LevelPack[] packs;

	public LevelSelectionController(IScreenView view) {
		viewSwitcher = new TouchSlidingViewSwitcher(false);

		packs = PackBuilder.buildPacks();

		view.registerDrawable(viewSwitcher, Game.LAYER_GAME);
		viewSwitcher.setArea(0, 0, Game.getVirtualWidth(), Game.getVirtualHeight() - 200f);
	}

	public void activate() {
		initViews();
		viewSwitcher.activate();
	}

	public void deactivate() {
		viewSwitcher.deactivate();
	}
	
	private void initViews() {
		viewSwitcher.clearViews();
		for (LevelPack pack : packs) {
			viewSwitcher.addView(new LevelPackView(pack));
		}
	}
}

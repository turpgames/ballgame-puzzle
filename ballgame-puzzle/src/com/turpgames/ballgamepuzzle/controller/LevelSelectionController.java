package com.turpgames.ballgamepuzzle.controller;

import com.turpgames.ballgamepuzzle.levels.LevelManager;
import com.turpgames.ballgamepuzzle.levels.LevelPack;
import com.turpgames.ballgamepuzzle.levels.LevelPackView;
import com.turpgames.ballgamepuzzle.utils.Global;
import com.turpgames.ballgamepuzzle.view.IScreenView;
import com.turpgames.framework.v0.IView;
import com.turpgames.framework.v0.impl.TouchSlidingViewSwitcher;
import com.turpgames.framework.v0.util.Game;

public class LevelSelectionController {

	private final TouchSlidingViewSwitcher viewSwitcher;
	private final LevelPack[] packs;

	public LevelSelectionController(IScreenView view) {
		viewSwitcher = new TouchSlidingViewSwitcher(false);

		packs = LevelManager.getLevelPacks();

		initViews();

		view.registerDrawable(viewSwitcher, Game.LAYER_GAME);
		viewSwitcher.setArea(0, 0, Game.getVirtualWidth(), Game.getVirtualHeight() - 200f);
		viewSwitcher.setListener(new TouchSlidingViewSwitcher.IListener() {
			@Override
			public void onViewSwitched(IView newView, IView oldView) {
				Global.levelPackViewId = newView.getId();
			}
		});
	}

	public void activate() {
		for (IView view : viewSwitcher.getViews()) {
			if (view instanceof LevelPackView)
				((LevelPackView) view).updateView();
		}
		
		if (Global.levelPackViewId != null)
			viewSwitcher.activate(Global.levelPackViewId);
		else
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

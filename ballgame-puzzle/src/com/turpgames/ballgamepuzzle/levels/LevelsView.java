package com.turpgames.ballgamepuzzle.levels;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.ballgamepuzzle.view.IScreenView;
import com.turpgames.framework.v0.IView;
import com.turpgames.utils.Util;

public abstract class LevelsView implements IView {
	private List<LevelInfoView> views = new ArrayList<LevelInfoView>();
	private final IScreenView screenView;

	public LevelsView(IScreenView screenView) {
		this.screenView = screenView;

		int state = LevelInfo.Star3;
		for (int i = 0; i < 20; i++) {
			LevelInfo info = new LevelInfo();
			info.setIndex(i + 1);
			info.setState(state);

			views.add(new LevelInfoView(info));

			if (state == LevelInfo.Unlocked || Util.Random.randInt(5) == 0)
				state--;
			if (state < LevelInfo.Unlocked)
				state = LevelInfo.Locked;
		}
	}

	@Override
	public void draw() {
		for (LevelInfoView view : views)
			view.draw();
	}

	@Override
	public String getId() {
		return "StarterLevels";
	}

	@Override
	public void activate() {
		for (LevelInfoView view : views)
			screenView.registerInputListener(view);
	}

	@Override
	public boolean deactivate() {
		for (LevelInfoView view : views)
			screenView.unregisterInputListener(view);
		return true;
	}
}

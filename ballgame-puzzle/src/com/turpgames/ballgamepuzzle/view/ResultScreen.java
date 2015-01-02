package com.turpgames.ballgamepuzzle.view;

import com.turpgames.ballgamepuzzle.components.Stars;
import com.turpgames.ballgamepuzzle.levels.LevelManager;
import com.turpgames.ballgamepuzzle.levels.LevelMeta;
import com.turpgames.ballgamepuzzle.utils.Global;
import com.turpgames.ballgamepuzzle.utils.R;
import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.framework.v0.component.Button2;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;

public class ResultScreen extends Screen {
	private final static float buttonWidth = 150f;
	private final static float buttonHeight = 50f;

	private Button2 levelSelectionButton;
	private Button2 retryButton;
	private Button2 nextButton;
	private Stars stars;

	private LevelMeta nextLevel;

	@Override
	public void init() {
		super.init();

		levelSelectionButton = initButton("Levels", (Game.getVirtualWidth() - buttonWidth) * 0.5f, 75f, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				ScreenManager.instance.switchTo(R.screens.levels, true);
			}
		});

		retryButton = initButton("Retry", 225f, 150f, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				ScreenManager.instance.switchTo(R.screens.game, true);
			}
		});

		nextButton = initButton("Next", Game.getVirtualWidth() - buttonWidth - 225f, 150f, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				LevelManager.startLevel(nextLevel);
			}
		});

		stars = new Stars();
	}

	@Override
	protected void onAfterActivate() {
		int starCount = LevelManager.updateLevelState();

		if (starCount == 0) {
			levelSelectionButton.activate();
			retryButton.activate();
			if (Global.currentLevel.getState() == 0) {
				nextButton.deactivate();
				return;
			} else {
				nextButton.activate();
			}
		}
		else {
			stars.setupForResultScreen(starCount);
			registerDrawable(stars, Game.LAYER_GAME);
			stars.animateResult();
		}
		
		nextLevel = LevelManager.unlockNextLevel();

		if (nextLevel == null) {
			nextButton.deactivate();
		}
		else if (nextLevel.getPack() == Global.currentLevel.getPack()) {
			nextButton.setText("Next");
			nextButton.activate();
		}
		else {
			nextButton.setText("Next Pack");
			nextButton.activate();
		}

		levelSelectionButton.activate();
		retryButton.activate();
	}

	@Override
	protected boolean onBeforeDeactivate() {
		levelSelectionButton.deactivate();
		retryButton.deactivate();
		nextButton.deactivate();

		unregisterDrawable(stars);

		return super.onBeforeDeactivate();
	}

	private Button2 initButton(String text, float x, float y, IButtonListener listener) {
		Button2 btn = new Button2();

		btn.setText(text);
		btn.setSize(buttonWidth, buttonHeight);
		btn.setFontScale(0.6f);
		btn.setListener(listener);
		btn.setColor(R.colors.azure, R.colors.green);
		btn.setTexture(Textures.button);
		btn.setLocation(x, y);

		return btn;
	}

	@Override
	protected boolean onBack() {
		ScreenManager.instance.switchTo(R.screens.levels, true);
		return true;
	}
}

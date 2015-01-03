package com.turpgames.ballgamepuzzle.view;

import com.turpgames.ballgamepuzzle.components.BallGameLogo;
import com.turpgames.ballgamepuzzle.components.Toolbar;
import com.turpgames.ballgamepuzzle.utils.R;
import com.turpgames.ballgamepuzzle.utils.StatActions;
import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.framework.v0.component.Button2;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;

public class MenuScreen extends Screen {
	private final static float buttonWidth = 150f;
	private final static float buttonHeight = 50f;

	private Button2 playButton;
	private Button2 aboutButton;

	@Override
	public void init() {
		super.init();

		playButton = initButton("Play", (Game.getVirtualWidth() - buttonWidth) * 0.5f, 180f, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				ScreenManager.instance.switchTo(R.screens.levels, false);
			}
		});

		aboutButton = initButton("About", (Game.getVirtualWidth() - buttonWidth) * 0.5f, 90f, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				ScreenManager.instance.switchTo(R.screens.about, false);
			}
		});
		
		registerDrawable(new BallGameLogo(), Game.LAYER_GAME);
	}

	@Override
	protected void onAfterActivate() {
		playButton.activate();
		aboutButton.activate();
	}

	@Override
	protected boolean onBeforeDeactivate() {
		playButton.deactivate();
		aboutButton.deactivate();
		Toolbar.getInstance().deactivate();
		return super.onBeforeDeactivate();
	}

	@Override
	protected boolean onBack() {
		TurpClient.sendStat(StatActions.ExitGame);
		Game.exit();
		return true;
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
}

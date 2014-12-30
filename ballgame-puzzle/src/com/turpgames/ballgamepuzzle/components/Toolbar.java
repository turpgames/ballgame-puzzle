package com.turpgames.ballgamepuzzle.components;

import com.turpgames.ballgamepuzzle.utils.StatActions;
import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.framework.v0.component.Button2;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.impl.Settings;

public class Toolbar {
	public final static float toolbarMargin = 5f;
	public final static float menuButtonSize = 40f;

	protected Button2 backButton;
	protected Button2 resetButton;
	protected Button2 soundButton;
	protected Button2 infoButton;

	private IToolbarListener listener;

	private static Toolbar instance;

	public static Toolbar getInstance() {
		if (instance == null)
			instance = new Toolbar();
		return instance;
	}

	private Toolbar() {
		addBackButton();
		addSoundButton();
		addResetButton();
		addInfoButton();
	}

	public void setListener(IToolbarListener listener) {
		this.listener = listener;
	}

	public void activateBackButton() {
		backButton.activate();
	}

	public void deactivateBackButton() {
		backButton.deactivate();
	}

	public void activateResetButton() {
		resetButton.activate();
	}

	public void deactivateResetButton() {
		resetButton.deactivate();
	}

	public void activateInfoButton() {
		infoButton.activate();
	}

	public void deactivateInfoButton() {
		infoButton.deactivate();
	}

	public void deactivate() {
		soundButton.deactivate();
		backButton.deactivate();
		resetButton.deactivate();
		infoButton.deactivate();
	}

	public void activate() {
		soundButton.activate();
		backButton.activate();
	}

	public Button2 getBackButton() {
		return backButton;
	}

	protected void addBackButton() {
		backButton = createButton(Textures.tb_back, Button2.sw, 1, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				if (listener != null)
					listener.onToolbarBack();
			}
		});
	}

	protected void addResetButton() {
		resetButton = createButton(Textures.tb_reset, Button2.se, 2, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				if (listener != null)
					listener.onResetGame();
			}
		});
	}

	protected void addInfoButton() {
		infoButton = createButton(Textures.tb_info, Button2.se, 3, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				if (listener != null)
					listener.onShowDescription();
			}
		});
	}

	protected void addSoundButton() {
		ITexture texture = Settings.isSoundOn() ? Textures.tb_sound_on : Textures.tb_sound_off;
		soundButton = createButton(texture, Button2.se, 1, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				boolean isOn = !Settings.isSoundOn();
				Settings.putBoolean(Settings.sound, isOn);
				TurpClient.sendStat(isOn ? StatActions.SoundOn : StatActions.SoundOff);
				soundButton.setTexture(isOn ? Textures.tb_sound_on : Textures.tb_sound_off);
			}
		});
	}

	private Button2 createButton(ITexture texture, int alignment, int order, IButtonListener listener) {
		Button2 btn = new Button2();
		btn.setSize(menuButtonSize, menuButtonSize);
		btn.setTexture(texture);
		btn.setListener(listener);

		float dx = order * toolbarMargin + (order - 1) * menuButtonSize;
		float dy = toolbarMargin;
		
		btn.setLocation(alignment, dx, dy);

		return btn;
	}
}

package com.turpgames.ballgamepuzzle.components;

import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.component.Button2;
import com.turpgames.framework.v0.component.IButtonListener;

public class Toolbar {
	public final static float toolbarMargin = 5f;
	public final static float menuButtonSize = 40f;

	protected Button2 backButton;
	protected Button2 resetButton;
	protected Button2 menuButton;

	private IToolbarListener listener;

	private static Toolbar instance;

	public static Toolbar getInstance() {
		if (instance == null)
			instance = new Toolbar();
		return instance;
	}

	private Toolbar() {
		addBackButton();
		addResetButton();
		addMenuButton();
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

	public void activateMenuButton() {
		menuButton.activate();
	}

	public void deactivateMenuButton() {
		menuButton.deactivate();
	}

	public void deactivate() {
		menuButton.deactivate();
		backButton.deactivate();
		resetButton.deactivate();
	}
	
	public void triggerBack() {
		if (listener != null)
			listener.onToolbarBack();
	}

	protected void addBackButton() {
		backButton = createButton(Textures.tb_back, Button2.sw, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				triggerBack();
			}
		});
	}

	protected void addMenuButton() {
		menuButton = createButton(Textures.tb_info, Button2.sw, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				if (listener != null)
					listener.onShowMenu();
			}
		});
	}

	protected void addResetButton() {
		resetButton = createButton(Textures.tb_reset, Button2.se, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				if (listener != null)
					listener.onResetGame();
			}
		});
	}
	
	private Button2 createButton(ITexture texture, int alignment, IButtonListener listener) {
		Button2 btn = new Button2();
		
		btn.setSize(menuButtonSize, menuButtonSize);
		btn.setTexture(texture);
		btn.setListener(listener);
		btn.setLocation(alignment, toolbarMargin, toolbarMargin);

		return btn;
	}
}

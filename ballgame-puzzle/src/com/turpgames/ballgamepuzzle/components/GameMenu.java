package com.turpgames.ballgamepuzzle.components;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.IInputListener;
import com.turpgames.framework.v0.component.Button2;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.impl.InputListener;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Drawer;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;

public class GameMenu implements IDrawable {
	private final Color overlayColor = Color.fromHex("#00000080");

	private final IGameMenuListener listener;
	private final IInputListener inputListener;

	private final List<Button2> buttons;

	public GameMenu(IGameMenuListener listener) {
		this.listener = listener;
		this.inputListener = new GameMenuInputListener();

		buttons = new ArrayList<Button2>();
		
		buttons.add(createButton("Back To Menu", 200, 200, new IButtonListener() {
			@Override
			public void onButtonTapped() {
				Toolbar.getInstance().triggerBack();
			}
		}));
	}

	@Override
	public void draw() {
		ShapeDrawer.drawRect(0, 0, Game.getScreenWidth(), Game.getScreenHeight(), overlayColor, true, true);
	}

	public void show() {
		Toolbar.getInstance().deactivate();

		Drawer.getCurrent().register(this, Game.LAYER_INFO);
		Game.getInputManager().register(inputListener, Game.LAYER_INFO);

		for (Button2 btn : buttons) {
			btn.activate();
		}
	}
	
	public void deactivate() {
		Drawer.getCurrent().unregister(this);
		Game.getInputManager().unregister(inputListener);

		for (Button2 btn : buttons) {
			btn.deactivate();
		}
	}

	private void hide() {
		Toolbar.getInstance().activateMenuButton();
		Toolbar.getInstance().activateResetButton();

		deactivate();

		listener.onMenuHide();
	}

	private Button2 createButton(String text, float x, float y, IButtonListener listener) {
		Button2 btn = new Button2();

		btn.setSize(Toolbar.menuButtonSize * 4, Toolbar.menuButtonSize);
		btn.setFontScale(0.5f);
		btn.setTexture(Textures.button_blue, Textures.button_green);
		btn.setText(text);
		btn.setListener(listener);
		btn.setLocation(x, y);

		return btn;
	}

	class GameMenuInputListener extends InputListener {
		@Override
		public boolean tap(float x, float y, int count, int button) {
			hide();
			return true;
		}
	}
}

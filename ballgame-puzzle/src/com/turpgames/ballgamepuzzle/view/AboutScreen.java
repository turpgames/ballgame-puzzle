package com.turpgames.ballgamepuzzle.view;

import com.turpgames.ballgamepuzzle.components.Toolbar;
import com.turpgames.ballgamepuzzle.components.ToolbarListenerAdapter;
import com.turpgames.ballgamepuzzle.utils.R;
import com.turpgames.ballgamepuzzle.utils.StatActions;
import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.framework.v0.component.Button2;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Game;

public class AboutScreen extends Screen {
	private final static float buttonSize = 100f;

	private Button2 facebookButton;
	private Button2 twitterButton;
	private Button2 webSiteButton;

	private Button2 storeButton;
	private Button2 doubleupButton;
	private Button2 ballgameButton;
	private Button2 ichiguButton;

	@Override
	public void init() {
		super.init();
		initTitleText();
		initVersionText();

		float x = (Game.getVirtualWidth() - 3 * buttonSize) / 4f;
		float y = 100f;

		initFacebookButton(x, y);
		initTwitterButton(2 * x + buttonSize, y);
		initWebSiteButton(3 * x + 2 * buttonSize, y);

		x = (Game.getVirtualWidth() - 2 * buttonSize) / 3f;
		y = 450f;

		initStoreButton(x, y);
		initDoubleUpButton(2 * x + buttonSize, y);

		y = 300f;

		initBallgameButton(x, y);
		initIchiguButton(2 * x + buttonSize, y);
	}

	private void initFacebookButton(float x, float y) {
		facebookButton = createButton(Textures.facebook, Game.getParam("facebook-address"), StatActions.ClickedFacebookInAbout, x, y);
		registerDrawable(facebookButton, Game.LAYER_GAME);
	}

	private void initTwitterButton(float x, float y) {
		twitterButton = createButton(Textures.twitter, Game.getParam("twitter-address"), StatActions.ClickedTwitterInAbout, x, y);
		registerDrawable(twitterButton, Game.LAYER_GAME);
	}

	private void initWebSiteButton(float x, float y) {
		webSiteButton = createButton(Textures.turplogo, Game.getParam("turp-address"), StatActions.ClickedWebSiteInAbout, x, y);
		registerDrawable(webSiteButton, Game.LAYER_GAME);
	}

	private void initStoreButton(float x, float y) {
		storeButton = createButton(Textures.icon, getStoreUrl(), StatActions.ClickedDidYouLikeInAbout, x, y);
		registerDrawable(storeButton, Game.LAYER_GAME);
	}

	private void initDoubleUpButton(float x, float y) {
		doubleupButton = createButton(Textures.doubleup, getDoubleUpStoreUrl(), StatActions.ClickedDoubleUpInAbout, x, y);
		registerDrawable(doubleupButton, Game.LAYER_GAME);
	}

	private void initBallgameButton(float x, float y) {
		ballgameButton = createButton(Textures.ballgame, getBallGameStoreUrl(), StatActions.ClickedBallGameInAbout, x, y);
		registerDrawable(ballgameButton, Game.LAYER_GAME);
	}

	private void initIchiguButton(float x, float y) {
		ichiguButton = createButton(Textures.ichigu, getIchiguStoreUrl(), StatActions.ClickedIchiguInAbout, x, y);
		registerDrawable(ichiguButton, Game.LAYER_GAME);
	}

	private void initTitleText() {
		Text text = new Text();
		text.setText("Ball Game Puzzle");
		text.getColor().set(R.colors.azure);
		text.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		text.setPadY(125f);
		registerDrawable(text, Game.LAYER_GAME);
	}

	private void initVersionText() {
		Text text = new Text();
		text.setText("v" + Game.getVersion());
		text.setFontScale(0.66f);
		text.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		text.setPadY(175f);
		text.getColor().set(R.colors.green);
		registerDrawable(text, Game.LAYER_GAME);
	}

	private static Button2 createButton(ITexture texture, final String url, final int statAction, float x, float y) {
		Button2 btn = new Button2();
		btn.setSize(buttonSize, buttonSize);
		btn.setTexture(texture);
		btn.setLocation(x, y);
		btn.setListener(new IButtonListener() {
			public void onButtonTapped() {
				Game.openUrl(url);
				TurpClient.sendStat(statAction);
			}
		});
		return btn;
	}

	private String getStoreUrl() {
		if (Game.isIOS()) {
			if (Game.getOSVersion().getMajor() < 7) {
				return Game.getParam("app-store-address-old");
			} else {
				return Game.getParam("app-store-address-ios7");
			}
		} else {
			return Game.getParam("play-store-address");
		}
	}

	private String getDoubleUpStoreUrl() {
		if (Game.isIOS()) {
			if (Game.getOSVersion().getMajor() < 7) {
				return Game.getParam("doubleup-app-store-address-old");
			} else {
				return Game.getParam("doubleup-app-store-address-ios7");
			}
		} else {
			return Game.getParam("doubleup-play-store-address");
		}
	}

	private String getBallGameStoreUrl() {
		if (Game.isIOS()) {
			if (Game.getOSVersion().getMajor() < 7) {
				return Game.getParam("ballgame-app-store-address-old");
			} else {
				return Game.getParam("ballgame-app-store-address-ios7");
			}
		} else {
			return Game.getParam("ballgame-play-store-address");
		}
	}

	private String getIchiguStoreUrl() {
		if (Game.isIOS()) {
			if (Game.getOSVersion().getMajor() < 7) {
				return Game.getParam("ichigu-app-store-address-old");
			} else {
				return Game.getParam("ichigu-app-store-address-ios7");
			}
		} else {
			return Game.getParam("ichigu-play-store-address");
		}
	}

	@Override
	protected void onAfterActivate() {
		facebookButton.activate();
		twitterButton.activate();
		webSiteButton.activate();
		storeButton.activate();
		doubleupButton.activate();
		ballgameButton.activate();
		ichiguButton.activate();

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
		facebookButton.deactivate();
		twitterButton.deactivate();
		webSiteButton.deactivate();
		storeButton.deactivate();
		doubleupButton.deactivate();
		ballgameButton.deactivate();
		ichiguButton.deactivate();
		Toolbar.getInstance().deactivate();
		return super.onBeforeDeactivate();
	}

	@Override
	protected boolean onBack() {
		ScreenManager.instance.switchTo(R.screens.menu, true);
		return true;
	}
}

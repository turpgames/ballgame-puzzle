package com.turpgames.ballgamepuzzle.view;

import com.turpgames.ballgamepuzzle.components.BallGameLogo;
import com.turpgames.ballgamepuzzle.components.Toolbar;
import com.turpgames.ballgamepuzzle.components.hiscore.HiScoreController;
import com.turpgames.ballgamepuzzle.utils.R;
import com.turpgames.ballgamepuzzle.utils.StatActions;
import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;

public class HiScoresScreen extends Screen {
	private HiScoreController controller;

	public void init() {
		super.init();
		controller = new HiScoreController();
		registerDrawable(new BallGameLogo(), Game.LAYER_GAME);
		registerDrawable(controller, Game.LAYER_GAME);
		registerDrawable(Toolbar.getInstance(), Game.LAYER_INFO);
	}

	@Override
	protected void onAfterActivate() {
		super.onAfterActivate();

		TurpClient.sendStat(StatActions.EnterLeadersBoard);
		
		if (TurpClient.isRegistered()) {
			controller.activate();
			
			Toolbar.getInstance().enable();
			Toolbar.getInstance().setListener(new com.turpgames.framework.v0.component.Toolbar.IToolbarListener() {
				@Override
				public void onToolbarBack() {
					onBack();
				}
			});
		}
		else {
			final Dialog dialog = new Dialog();
			dialog.setFontScale(0.66f);
			dialog.addButton("ok", "Ok");
			dialog.setListener(new Dialog.IDialogListener() {
				@Override
				public void onDialogClosed() {
					onBack();
				}
				
				@Override
				public void onDialogButtonClicked(String id) {
					onBack();
					dialog.close();
				}
			});
			dialog.open("In order to see hi scores, you must be registered! You may not be registered if you do not have internet connection.");
		}
	}

	@Override
	protected boolean onBeforeDeactivate() {
		controller.deactivate();
		Toolbar.getInstance().disable();
		return super.onBeforeDeactivate();
	}

	protected boolean onBack() {
		ScreenManager.instance.switchTo(R.screens.menu, true);
		return true;
	}
}

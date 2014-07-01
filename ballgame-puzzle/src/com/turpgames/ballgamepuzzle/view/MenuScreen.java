package com.turpgames.ballgamepuzzle.view;

import com.turpgames.ballgamepuzzle.components.Toolbar;
import com.turpgames.ballgamepuzzle.utils.StatActions;
import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.framework.v0.impl.FormScreen;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.util.Game;

public class MenuScreen extends FormScreen {
	private boolean isFirstActivate;

	@Override
	public void init() {
		super.init();
		isFirstActivate = true;
		setForm("mainForm", false);
		registerDrawable(Toolbar.getInstance(), Game.LAYER_INFO);
	}

	@Override
	protected void onAfterActivate() {
		super.onAfterActivate();
		
		if (isFirstActivate) {
			isFirstActivate = false;
			TurpClient.init();

			if (Settings.getInteger("game-installed", 0) == 0) {
				TurpClient.sendStat(StatActions.GameInstalled, Game.getPhysicalScreenSize().toString());
				Settings.putInteger("game-installed", 1);
			}

			TurpClient.sendStat(StatActions.StartGame);
		}
		Toolbar.getInstance().enable();
		Toolbar.getInstance().deactivateBackButton();
		Toolbar.getInstance().setListener(new com.turpgames.framework.v0.component.Toolbar.IToolbarListener() {
			@Override
			public void onToolbarBack() {
				onBack();
			}
		});
	}
	
	@Override
	protected boolean onBeforeDeactivate() {
		Toolbar.getInstance().disable();
		return super.onBeforeDeactivate();
	}

	@Override
	protected boolean onBack() {
		TurpClient.sendStat(StatActions.ExitGame);
		Game.exit();
		return true;
	}
}

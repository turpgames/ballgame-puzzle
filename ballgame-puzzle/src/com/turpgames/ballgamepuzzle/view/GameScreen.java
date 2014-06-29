package com.turpgames.ballgamepuzzle.view;

import com.turpgames.ballgamepuzzle.controller.GameController;
import com.turpgames.ballgamepuzzle.utils.StatActions;
import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.util.Game;

public class GameScreen extends Screen implements IScreenView {

	private GameController controller;
	private boolean isFirstActivate;

	public void init() {
		super.init();
		isFirstActivate = true;
		controller = new GameController(this);
	}

	@Override
	protected void onAfterActivate() {
		controller.activate(isFirstActivate);
		
		if (isFirstActivate) {
			isFirstActivate = false;
			TurpClient.init();
			
			if (Settings.getInteger("game-installed", 0) == 0) {
				TurpClient.sendStat(StatActions.GameInstalled, Game.getPhysicalScreenSize().toString());
				Settings.putInteger("game-installed", 1);
			}
			
			TurpClient.sendStat(StatActions.StartGame);
		}
	}

	@Override
	protected boolean onBeforeDeactivate() {
		controller.deactivate();
		return super.onBeforeDeactivate();
	}

	@Override
	protected boolean onBack() {
		TurpClient.sendStat(StatActions.ExitGame);
		Game.exit();
		return true;
	}

	@Override
	public void update() {
		super.update();
		controller.update();
	}
}

package com.turpgames.ballgamepuzzle.components;

import com.turpgames.ballgamepuzzle.utils.StatActions;
import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.framework.v0.component.Button2;
import com.turpgames.framework.v0.impl.Settings;
import com.turpgames.framework.v0.util.Game;

public class AudioButton extends Button2 {
	public AudioButton() {
		setSize(Toolbar.menuButtonSize, Toolbar.menuButtonSize);
		setTexture(Game.getResourceManager().getTexture(isOn() ? "tb_sound_on" : "tb_sound_off"));
		setLocation(
				Game.descale(Game.getScreenWidth()) - 5f - Toolbar.menuButtonSize,
				Game.descale(Game.getScreenHeight()) - 5f - Toolbar.menuButtonSize);
	}
	
	private boolean isOn() {
		return Settings.getBoolean(Settings.sound, true);
	}

	@Override
	protected void onClick() {
		Settings.putBoolean(Settings.sound, !isOn());
		TurpClient.sendStat(isOn() ? StatActions.SoundOn : StatActions.SoundOff);
		setTexture(Game.getResourceManager().getTexture(isOn() ? "tb_sound_on" : "tb_sound_off"));
	}
}

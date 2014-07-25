package com.turpgames.ballgamepuzzle.levels;

import com.turpgames.ballgamepuzzle.utils.Global;
import com.turpgames.ballgamepuzzle.utils.R;
import com.turpgames.framework.v0.impl.ScreenManager;

public class LevelManager {
	public static int updateLevelState() {
		int hits = Global.hitCount;
		LevelMeta level = Global.currentLevel;

		int newState;

		if (hits <= level.getStar3()) {
			newState = LevelMeta.Star3;
		}
		else if (hits <= level.getStar2()) {
			newState = LevelMeta.Star2;
		}
		else {
			newState = LevelMeta.Star1;
		}

		if (level.getState() < newState)
			level.setState(newState);

		return newState;
	}

	public static LevelMeta unlockNextLevel() {
		LevelMeta level = Global.currentLevel;
		LevelMeta[] levels = level.getPack().getLevels();

		int index = level.getIndex() - 1; // level index starts from 1

		if (index == levels.length - 1)
			return null;
		else if (index > levels.length - 1)
			throw new UnsupportedOperationException(String.format("%s does not exists in %s", level.getId(), level.getPack().getTitle()));		
			
		LevelMeta nextLevel = levels[index + 1];
		
		if (nextLevel.getState() == LevelMeta.Locked)
			nextLevel.setState(LevelMeta.Unlocked);

		return nextLevel;
	}
	
	public static void startLevel(LevelMeta level) {
		Global.currentLevel = level;
		ScreenManager.instance.switchTo(R.screens.game, false);
	}
}

package com.turpgames.ballgamepuzzle.levels;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.ballgamepuzzle.utils.Global;
import com.turpgames.ballgamepuzzle.utils.R;
import com.turpgames.framework.v0.impl.ScreenManager;

public class LevelManager {
	private static LevelPack[] packs;
	
	static {
		initPacks();
	}
	
	private static void initPacks() {
		List<LevelPack> packList = new ArrayList<LevelPack>();

		packList.add(PackFactory.greenPack());

		packs = packList.toArray(new LevelPack[0]);
	}
	
	public static LevelPack[] getLevelPacks() {
		return packs;
	} 
	
	public static int updateLevelState() {
		int stars = Global.stars;
		LevelMeta level = Global.currentLevel;

		if (level.getState() < stars)
			level.setState(stars);

		return stars;
	}

	public static LevelMeta unlockNextLevel() {
		LevelMeta level = Global.currentLevel;
		LevelMeta[] levels = level.getPack().getLevels();

		int index = level.getIndex() - 1; // level index starts from 1

		LevelMeta nextLevel;
		
		if (index == levels.length - 1)
			nextLevel = getFirstLevelOfNextPack();
		else if (index < levels.length - 1)
			nextLevel = levels[index + 1];
		else
			throw new UnsupportedOperationException(String.format("%s does not exists in %s", level.getId(), level.getPack().getTitle()));

		if (nextLevel == null)
			return null;
		
		if (nextLevel.getState() == LevelMeta.Locked)
			nextLevel.setState(LevelMeta.Unlocked);

		return nextLevel;
	}

	private static LevelMeta getFirstLevelOfNextPack() {
		LevelPack nextPack = getNextPack();
		if (nextPack == null)
			return null;
		return nextPack.getLevels()[0];
	}
	
	private static LevelPack getNextPack() {
		LevelPack currentPack = Global.currentLevel.getPack();
		
		for (int i = 0; i < packs.length; i++) {
			if (packs[i] == currentPack && i < packs.length - 1) {
				return packs[i + 1];
			}
		}
		
		return null;
	}

	public static void startLevel(LevelMeta level) {
		Global.currentLevel = level;
		ScreenManager.instance.switchTo(R.screens.game, false);
	}
}

package com.turpgames.ballgamepuzzle.levels;

import java.util.ArrayList;
import java.util.List;

public abstract class PackBuilder {
	public static LevelPack[] buildPacks() {
		List<LevelPack> packs = new ArrayList<LevelPack>();

		packs.add(StarterPack.getPack());

		return packs.toArray(new LevelPack[0]);
	}
}

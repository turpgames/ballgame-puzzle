package com.turpgames.ballgamepuzzle.levels;

import java.util.ArrayList;
import java.util.List;

public class LevelPack {
	private final List<LevelInfo> levels;
	private String id;

	public LevelPack(String id) {
		levels = new ArrayList<LevelInfo>();
	}
	
	public String getId() {
		return id;
	}
	
	public List<LevelInfo> getLevels() {
		return levels;
	}
}

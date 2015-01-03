package com.turpgames.ballgamepuzzle.levels;

import com.turpgames.framework.v0.util.Color;

public class LevelPack {
	private String title;
	private Color themeColor;
	private LevelMeta[] levels;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Color getThemeColor() {
		return themeColor;
	}

	public void setThemeColor(Color themeColor) {
		this.themeColor = themeColor;
	}

	public LevelMeta[] getLevels() {
		return levels;
	}

	public void setLevels(LevelMeta[] levels) {
		this.levels = levels;
	}
}

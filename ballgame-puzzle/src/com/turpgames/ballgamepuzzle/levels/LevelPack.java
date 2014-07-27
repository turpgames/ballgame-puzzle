package com.turpgames.ballgamepuzzle.levels;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.util.Color;

public class LevelPack {
	private final LevelMeta[] levels;
	private final String title;
	private final Color themeColor;

	private LevelPack(Builder builder) {
		this.title = builder.title;
		this.levels = builder.levels.toArray(new LevelMeta[0]);
		this.themeColor = builder.themeColor;
	}

	public String getTitle() {
		return title;
	}

	public Color getThemeColor() {
		return themeColor;
	}

	public LevelMeta[] getLevels() {
		return levels;
	}
	
	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {
		private String title;
		private final List<LevelMeta> levels;
		private Color themeColor;

		private Builder() {
			levels = new ArrayList<LevelMeta>();
		}

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setThemeColor(Color color) {
			this.themeColor = new Color(color);
			return this;
		}

		public Builder addLevel(LevelMeta level) {
			this.levels.add(level);
			return this;
		}

		public LevelPack build() {
			return new LevelPack(this);
		}
	}
}

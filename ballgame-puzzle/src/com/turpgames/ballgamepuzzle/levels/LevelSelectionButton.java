package com.turpgames.ballgamepuzzle.levels;

import com.turpgames.ballgamepuzzle.utils.Global;
import com.turpgames.ballgamepuzzle.utils.R;
import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;

public class LevelSelectionButton extends ImageButton {
	private final static int cols = 4;
	private final static int rows = 5;
	private final static float itemMargin = 20f;
	private final static float itemSize = (Game.getVirtualWidth() - (cols + 1) * itemMargin) / cols;
	private final static float yOffset = (Game.getVirtualHeight() - rows * itemSize - (rows + 1) * itemMargin) / 2f - 40f;

	private final LevelMeta meta;

	public LevelSelectionButton(LevelMeta info) {
		this.meta = info;

		int levelIndex = info.getIndex();

		setWidth(itemSize);
		setHeight(itemSize);

		int col = (levelIndex - 1) % cols;
		int row = (levelIndex - 1) / cols;
		row = rows - row - 1;

		float x = (col + 1) * itemMargin + col * itemSize;
		float y = yOffset + (row + 1) * itemMargin + row * itemSize;

		getLocation().set(x, y);

		if (info.getState() == LevelMeta.Locked)
			setTexture(Textures.locked);
		if (info.getState() == LevelMeta.Unlocked)
			setTexture(Textures.unlocked);
		if (info.getState() == LevelMeta.Star1)
			setTexture(Textures.star_empty);
		if (info.getState() == LevelMeta.Star2)
			setTexture(Textures.star_half);
		if (info.getState() == LevelMeta.Star3)
			setTexture(Textures.star_full);

		listenInput(false);
	}

	@Override
	public boolean ignoreViewport() {
		return false;
	}

	@Override
	protected boolean onTap() {
		if (meta.getState() != LevelMeta.Locked) {
			Global.levelMeta = meta;
			ScreenManager.instance.switchTo(R.screens.game, false);
		}
		return true;
	}
}

package com.turpgames.ballgamepuzzle.levels;

import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.util.Game;

public class LevelInfoView extends ImageButton {
	private final static int cols = 4;
	private final static int rows = 5;
	private final static float itemMargin = 20f;
	private final static float itemSize = (Game.getVirtualWidth() - (cols + 1) * itemMargin) / cols;
	private final static float yOffset = (Game.getVirtualHeight() - rows * itemSize - (rows + 1) * itemMargin) / 2f - 40f;

	private final LevelInfo info;

	public LevelInfoView(LevelInfo info) {
		this.info = info;

		int levelIndex = info.getIndex();

		setWidth(itemSize);
		setHeight(itemSize);

		int col = (levelIndex - 1) % cols;
		int row = (levelIndex - 1) / cols;
		row = rows - row - 1;

		float x = (col + 1) * itemMargin + col * itemSize;
		float y = yOffset + (row + 1) * itemMargin + row * itemSize;

		getLocation().set(x, y);

		if (info.getState() == LevelInfo.Locked)
			setTexture(Textures.locked);
		if (info.getState() == LevelInfo.Unlocked)
			setTexture(Textures.unlocked);
		if (info.getState() == LevelInfo.Star1)
			setTexture(Textures.star_empty);
		if (info.getState() == LevelInfo.Star2)
			setTexture(Textures.star_half);
		if (info.getState() == LevelInfo.Star3)
			setTexture(Textures.star_full);
	}
	
	@Override
	public boolean ignoreViewport() {
		return false;
	}

	@Override
	protected boolean onTap() {
		if (info.getState() == LevelInfo.Locked)
			System.out.println("Locked");
		else
			System.out.println("Play");
		return super.onTap();
	}
}

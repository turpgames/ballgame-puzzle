package com.turpgames.ballgamepuzzle.levels;

import com.turpgames.ballgamepuzzle.components.Stars;
import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.impl.TexturedGameObject;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TextureDrawer;

public class LevelSelectionButton extends ImageButton {
	private final static int cols = 4;
	private final static int rows = 5;
	private final static float itemMargin = 20f;
	private final static float itemSize = (Game.getVirtualWidth() - (cols + 1) * itemMargin) / cols;
	private final static float yOffset = (Game.getVirtualHeight() - rows * itemSize - (rows + 1) * itemMargin) / 2f - 40f;

	private final LevelMeta level;
	private final Stars stars;
	private final LockObj lockObj;

	public LevelSelectionButton(LevelMeta level) {
		this.level = level;
		this.stars = new Stars();
		this.lockObj = new LockObj();

		int levelIndex = level.getIndex();

		setWidth(itemSize);
		setHeight(itemSize);

		int col = (levelIndex - 1) % cols;
		int row = (levelIndex - 1) / cols;
		row = rows - row - 1;

		float x = (col + 1) * itemMargin + col * itemSize;
		float y = yOffset + (row + 1) * itemMargin + row * itemSize;

		getLocation().set(x, y);
		lockObj.getLocation().set(x + 10f, y + 10f);
		
		getColor().set(level.getPack().getThemeColor());
		getColor().a = 0.5f;

		updateView();

		listenInput(false);

		super.setListener(new IButtonListener() {
			@Override
			public void onButtonTapped() {
				startLevel();
			}
		});
	}

	public void updateView() {
		if (level.getState() == LevelMeta.Locked)
			lockObj.setTexture(Textures.locked);
		else if (level.getState() == LevelMeta.Unlocked)
			lockObj.setTexture(Textures.unlocked);
		else
			stars.setupForLevelSelectionMenu(level.getState(), getLocation().x, getLocation().y, getWidth());
	}

	@Override
	public void draw() {
		if (isTouched() && level.getState() != LevelMeta.Locked)
			getColor().a = 0.75f;
		else
			getColor().a = 0.50f;
		TextureDrawer.draw(Textures.level_item, this);
		if (level.getState() <= LevelMeta.Unlocked)
			lockObj.draw();
		else
			stars.draw();
	}

	@Override
	public boolean ignoreViewport() {
		return false;
	}

	private void startLevel() {
		if (level.getState() != LevelMeta.Locked) {
			LevelManager.startLevel(level);
		}
	}

	private class LockObj extends TexturedGameObject {
		LockObj() {
			setWidth(itemSize - 20f);
			setHeight(itemSize - 20f);
		}
	}
}

package com.turpgames.ballgamepuzzle.levels;

import com.turpgames.ballgamepuzzle.components.Stars;
import com.turpgames.ballgamepuzzle.components.Toolbar;
import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.impl.TexturedGameObject;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TextureDrawer;

public class LevelSelectionButton extends ImageButton {
	private final static int cols = 7;
	private final static int rows = 4;
	private final static float yOffset = Toolbar.menuButtonSize;
	private final static float itemMargin = 20f;
	private final static float itemSize = (Game.getVirtualHeight() - (rows + 1) * itemMargin - yOffset) / rows;
	private final static float xOffset = (Game.getVirtualWidth() - (cols * itemSize) - (cols - 1) * itemMargin) / 2f;

	private final LevelMeta level;
	private final Stars stars;
	private final LockObj lockObj;
	private final Text text;

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

		float x = xOffset + col * (itemMargin + itemSize);
		float y = (row + 1) * itemMargin + row * itemSize;

		getLocation().set(x, y);
		lockObj.getLocation().set(x + 10f, y + 10f);
		
		text = new Text();
		text.setText(level.getIndex() + "");
		text.setAlignment(Text.HAlignLeft, Text.VAlignTop);
		text.setPadding(6f, 6f);
		text.setSize(itemSize, itemSize);
		text.setFontScale(0.5f);
		text.setLocation(x, y);
		
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
		text.draw();
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

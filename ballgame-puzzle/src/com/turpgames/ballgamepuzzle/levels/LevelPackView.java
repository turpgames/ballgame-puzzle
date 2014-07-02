package com.turpgames.ballgamepuzzle.levels;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.IView;
import com.turpgames.framework.v0.impl.Text;

public class LevelPackView implements IView {
	private List<LevelSelectionButton> buttons = new ArrayList<LevelSelectionButton>();

	private final LevelPack pack;
	private final Text text;

	public LevelPackView(LevelPack pack) {
		this.pack = pack;

		this.text = new Text();
		this.text.setText(pack.getTitle());
		this.text.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		this.text.setPadY(80f);

		for (LevelMeta level : pack.getLevels())
			buttons.add(new LevelSelectionButton(level));
	}

	@Override
	public void draw() {
		for (LevelSelectionButton button : buttons)
			button.draw();
		text.draw();
	}

	@Override
	public void activate() {
		for (LevelSelectionButton button : buttons)
			button.listenInput(true);
	}

	@Override
	public String getId() {
		return pack.getTitle();
	}

	@Override
	public boolean deactivate() {
		for (LevelSelectionButton button : buttons)
			button.listenInput(false);
		return true;
	}
}

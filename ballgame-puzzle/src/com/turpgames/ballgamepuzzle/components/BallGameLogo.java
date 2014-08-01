package com.turpgames.ballgamepuzzle.components;

import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.impl.TexturedGameObject;
import com.turpgames.framework.v0.util.Game;

public class BallGameLogo implements IDrawable {
	private final LogoObj logo;

	public BallGameLogo() {
		logo = new LogoObj();
	}

	@Override
	public void draw() {
		logo.draw();
	}

	private static class LogoObj extends TexturedGameObject {
		public LogoObj() {
			setTexture(Textures.icon);
			float size = Game.getVirtualWidth() * 0.66f;
			float d = (Game.getVirtualWidth() - size) * 0.5f;
			setWidth(size);
			setHeight(size);
			getLocation().set(d, Game.getVirtualHeight() - size - d);
		}
	}
}

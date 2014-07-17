package com.turpgames.ballgamepuzzle.components;

import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TextureDrawer;

public class TurpLogo extends GameObject {
	private float logoSize = Game.getVirtualWidth();
	private ITexture logo;

	public TurpLogo() {
		logo = Game.getResourceManager().getTexture("turp_logo");
		setLogoSize(logoSize);
		getLocation().set(0f, 50f + (Game.getVirtualHeight() - logoSize) / 2f);
	}

	public void draw() {
		TextureDrawer.draw(logo, this);
	}

	public void setLogoSize(float f) {
		logoSize = f;
		setWidth(logoSize);
		setHeight(logoSize);
	}
}

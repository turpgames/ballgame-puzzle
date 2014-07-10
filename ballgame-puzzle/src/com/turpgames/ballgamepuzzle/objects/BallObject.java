package com.turpgames.ballgamepuzzle.objects;

import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.TextureDrawer;

class BallObject extends GameObject {
	private final ITexture ballTexture;

	BallObject(ITexture texture) {
		ballTexture = texture;
	}

	@Override
	public void draw() {
		TextureDrawer.draw(ballTexture, this);
	}
}

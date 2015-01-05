package com.turpgames.ballgamepuzzle.objects.balls;

import com.turpgames.ballgamepuzzle.levels.BallMeta;
import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.objects.BallBodyBuilder;
import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.box2d.IWorld;
import com.turpgames.framework.v0.metadata.GameMetadata;
import com.turpgames.framework.v0.util.Animation;
import com.turpgames.framework.v0.util.TextureDrawer;

public class Star extends Ball {
	private final Animation animation;
	
	public Star(BallMeta meta, IWorld world) {
		super(meta, world, Textures.star);
		ball.getScale().set(1.8f);
		animation = Animation.fromMetadata(GameMetadata.getAnimation("coin"));
	}
	
	@Override
	protected BallBodyBuilder createBodyBuilder() {
		return super.createBodyBuilder().setAsSensor();
	}
	
	@Override
	public void startEffect() {
		animation.start(true);
		super.startEffect();
	}
	
	@Override
	public void stopEffect() {
		animation.stop();
		super.stopEffect();
	}
	
	@Override
	public void draw() {
		if (!isHidden)
			TextureDrawer.draw(animation.getFrame(), ball);
	}
}

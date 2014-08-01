package com.turpgames.ballgamepuzzle.objects.balls;

import com.turpgames.ballgamepuzzle.levels.BallMeta;
import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.objects.BallBodyBuilder;
import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.box2d.IWorld;
import com.turpgames.framework.v0.effects.BreathEffect;

public class PortalBall extends Ball {
	private PortalBall pair;
	private final BreathEffect inEffect;
	private final BreathEffect outEffect;

	public PortalBall(BallMeta meta, IWorld world) {
		super(meta, world, Textures.ball_orange);
		
		inEffect = new BreathEffect(ball);
		inEffect.setDuration(0.3f);
		inEffect.setFinalScale(1.0f);
		inEffect.setMaxFactor(0.5f);
		inEffect.setMinFactor(1.0f);
		
		outEffect = new BreathEffect(ball);
		outEffect.setDuration(0.3f);
		outEffect.setFinalScale(1.0f);
		outEffect.setMaxFactor(1.0f);
		outEffect.setMinFactor(0.5f);
	}

	@Override
	protected BallBodyBuilder createBodyBuilder() {
		return super.createBodyBuilder().setAsSensor();
	}

	public PortalBall getPair() {
		return pair;
	}
	
	public void playInEfect() {
		inEffect.start();
	}
	
	public void playOutEfect() {
		outEffect.start();
	}

	public static void makePair(PortalBall b1, PortalBall b2) {
		b1.pair = b2;
		b2.pair = b1;
	} 
}

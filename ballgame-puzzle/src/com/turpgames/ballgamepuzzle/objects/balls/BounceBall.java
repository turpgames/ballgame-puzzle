package com.turpgames.ballgamepuzzle.objects.balls;

import com.turpgames.ballgamepuzzle.levels.BallMeta;
import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.objects.BallBodyBuilder;
import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.box2d.IWorld;
import com.turpgames.framework.v0.effects.BreathEffect;

public class BounceBall extends Ball {
	private final BreathEffect effect;

	public BounceBall(BallMeta meta, IWorld world) {
		super(meta, world, Textures.ball_yellow);

		effect = new BreathEffect(ball);
		effect.setDuration(0.1f);
		effect.setFinalScale(1.0f);
		effect.setMaxFactor(1.2f);
		effect.setMinFactor(1.0f);
	}

	@Override
	protected BallBodyBuilder createBodyBuilder() {
		return super.createBodyBuilder();
	}

	public void bounce(SubjectBall subject) {
		float dx = subject.getCenterX() - this.getCenterX();
		float dy = subject.getCenterY() - this.getCenterY();
		float d = (float) Math.sqrt(dx * dx + dy * dy);

		float f = 200f;

		subject.bounce((dx / d) * f, (dy / d) * f);

		effect.start();
	}
}

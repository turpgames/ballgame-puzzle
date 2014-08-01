package com.turpgames.ballgamepuzzle.effects;

import com.turpgames.box2d.IBody;
import com.turpgames.box2d.IBox2DObject;

public class RollingEffect extends Box2DEffect {
	private final IBody body;
	private boolean clockWise;
	
	public RollingEffect(IBox2DObject obj) {
		super(obj);
		this.body = obj.getBody();
	}
	
	public void setClockWise(boolean clockWise) {
		this.clockWise = clockWise;
	}

	@Override
	protected void onUpdate() {

	}

	@Override
	protected void onStart() {
		float angularVelocity = 360f / getDuration();
		if (clockWise)
			angularVelocity = -angularVelocity;
		body.setAngularVelocity(angularVelocity);
	}

	@Override
	protected void onStop() {
		body.setAngularVelocity(0);
	}
}

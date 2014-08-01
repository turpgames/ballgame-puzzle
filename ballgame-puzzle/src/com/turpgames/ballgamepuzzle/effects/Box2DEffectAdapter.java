package com.turpgames.ballgamepuzzle.effects;

import com.turpgames.framework.v0.effects.Effect;

class Box2DEffectAdapter extends Effect {
	private final Box2DEffect box2dEffect;

	Box2DEffectAdapter(Box2DEffect box2dEffect) {
		this.box2dEffect = box2dEffect;
		setLooping(true);
		setDuration(1f);
	}

	@Override
	protected Object getObject() {
		return box2dEffect.getObject();
	}

	@Override
	protected void onStart() {
		box2dEffect.onStart();
	}

	@Override
	protected void onStop() {
		box2dEffect.onStop();
	}

	@Override
	protected void onUpdate() {
		box2dEffect.onUpdate();
	}
}

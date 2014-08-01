package com.turpgames.ballgamepuzzle.effects;

import com.turpgames.box2d.IBox2DObject;

public abstract class Box2DEffect implements IBox2DEffect {
	protected final Box2DEffectAdapter effectAdapter;
	private final IBox2DObject obj;
	
	protected Box2DEffect(IBox2DObject obj) {
		this.obj = obj;
		this.effectAdapter = new Box2DEffectAdapter(this);
	}
	
	public void start() {
		effectAdapter.start();
	}
	
	public void stop() {
		effectAdapter.stop();
	}
	
	public void setDuration(float duration) {
		effectAdapter.setDuration(duration);
	}
	
	public float getDuration() {
		return effectAdapter.getDuration();
	}
	
	protected void onStart() {
		
	}

	protected void onStop() {
		
	}

	protected abstract void onUpdate();
	
	protected IBox2DObject getObject() {
		return obj;
	}
	
	protected float getElapsed() {
		return effectAdapter.getElapsed();
	}
	
	protected float getProgress() {
		return effectAdapter.getProgress();
	}
}

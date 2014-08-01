package com.turpgames.ballgamepuzzle.effects;

import com.badlogic.gdx.math.MathUtils;
import com.turpgames.box2d.IBox2DObject;
import com.turpgames.framework.v0.util.Vector;

public class CircularTripEffect implements IBox2DEffect {

	private final IBox2DObject obj;
	private final Vector center;
	private boolean clockWise;

	private PathTripEffect pathTripEffect;

	public CircularTripEffect(IBox2DObject obj) {
		this.obj = obj;
		this.center = new Vector();
		this.pathTripEffect = new PathTripEffect(obj);
		this.pathTripEffect.setRoundTrip(true);
	}

	public void setCenter(float x, float y) {
		center.set(x, y);
	}

	public void setClockWise(boolean clockWise) {
		this.clockWise = clockWise;
	}

	@Override
	public void start() {
		buildPath();
		pathTripEffect.start();
	}

	@Override
	public void stop() {
		if (pathTripEffect != null)
			pathTripEffect.stop();
	}

	@Override
	public void setDuration(float duration) {
		pathTripEffect.setDuration(duration);		
	}

	@Override
	public float getDuration() {
		return pathTripEffect.getDuration();
	}

	private void buildPath() {
		Vector bodyCenter = obj.getBody().getCenter();
		float dist = center.dist(bodyCenter);

		Vector t = bodyCenter.tmp();
		t.sub(center);

		float initialAlpha = (float) Math.atan2(t.y, t.x);

		int fps = 60;
		float _360 = (float) Math.PI * 2;
		float _10 = (float) Math.PI / 18;

		float totalFrames = fps * getDuration();

		float alphaPerFrame = _360 / totalFrames;

		if (alphaPerFrame < _10)
			alphaPerFrame = _10;
		
		pathTripEffect.clearNodes();

		if (clockWise) {
			for (float alpha = initialAlpha; alpha > initialAlpha - _360; alpha -= alphaPerFrame) {
				addNode(dist, alpha);
			}
		}
		else {
			for (float alpha = initialAlpha; alpha < initialAlpha + _360; alpha += alphaPerFrame) {
				addNode(dist, alpha);
			}
		}
	}

	private void addNode(float dist, float alpha) {
		pathTripEffect.addNode(
				center.x + dist * MathUtils.cos(alpha),
				center.y + dist * MathUtils.sin(alpha));
	}
}

package com.turpgames.ballgamepuzzle.effects;

import com.turpgames.box2d.Box2D;
import com.turpgames.box2d.IBody;
import com.turpgames.box2d.IBox2DObject;
import com.turpgames.box2d.ICircleShape;
import com.turpgames.box2d.IFixture;
import com.turpgames.box2d.IFixtureDef;
import com.turpgames.box2d.builders.Box2DBuilders;
import com.turpgames.framework.v0.util.Vector;

public class BreathEffect extends Box2DEffect {
	private final IBody body;
	private final Vector objScale;

	private float minScale;
	private float maxScale;
	private boolean startFromMaxScale;

	private float lastScale;

	public BreathEffect(IBox2DObject obj) {
		super(obj);
		this.body = obj.getBody();
		this.objScale = obj.getObject().getScale();
	}

	public void setMinScale(float minScale) {
		this.minScale = minScale;
	}

	public void setMaxScale(float maxScale) {
		this.maxScale = maxScale;
	}

	public void setStartFromMaxScale(boolean startFromMaxScale) {
		this.startFromMaxScale = startFromMaxScale;
	}

	@Override
	protected void onStart() {
		lastScale = objScale.x;

		float intialScale = startFromMaxScale ? maxScale : minScale;
		setScale(intialScale);
	}

	@Override
	protected void onStop() {

	}

	@Override
	protected void onUpdate() {
		float prg = getProgress();

		if (prg >= 0.5f)
			prg = 1 - prg;

		if (startFromMaxScale)
			prg = 0.5f - prg;

		float scale = 2 * prg * (maxScale - minScale) + minScale;
		setScale(scale);
	}

	private void setScale(float scale) {
		objScale.set(scale);
		scaleBody(scale);
		lastScale = scale;
	}

	private void scaleBody(float scale) {
		IFixture fixture = body.getFixtures().get(0);

		ICircleShape oldShape = (ICircleShape) fixture.getShape();
		float newRadius = (scale / lastScale) * oldShape.getRadius();
		Vector oldCenter = oldShape.getCenter();
		
		ICircleShape shape = Box2DBuilders.Shape.buildCircle(newRadius, oldCenter.x, oldCenter.y);

		IFixtureDef fixtureDef = Box2D.createFixtureDef();
		fixtureDef.setDensity(fixture.getDensity());
		fixtureDef.setFriction(fixture.getFriction());
		fixtureDef.setSensor(fixture.isSensor());
		fixtureDef.setElasticity(fixture.getElasticity());
		fixtureDef.setShape(shape);

		body.createFixture(fixtureDef);

		shape.dispose();
		body.destroyFixture(fixture);
	}
}

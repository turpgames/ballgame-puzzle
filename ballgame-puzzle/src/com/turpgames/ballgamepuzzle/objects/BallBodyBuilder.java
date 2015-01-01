package com.turpgames.ballgamepuzzle.objects;

import com.turpgames.box2d.IBody;
import com.turpgames.box2d.IBodyDef;
import com.turpgames.box2d.IShape;
import com.turpgames.box2d.IWorld;
import com.turpgames.box2d.builders.BodyBuilder;
import com.turpgames.box2d.builders.Box2DBuilders;
import com.turpgames.box2d.builders.FixtureBuilder;

public class BallBodyBuilder {
	private final IShape circle;
	private final BodyBuilder bodyBuilder;
	private final FixtureBuilder fixtureBuilder;

	private BallBodyBuilder(float radius, float cx, float cy, int bodyType) {
		this.circle = Box2DBuilders.Shape.buildCircle(radius);

		if (bodyType == IBodyDef.Static) {
			this.bodyBuilder = Box2DBuilders.Body.staticBodyBuilder();
		} else if (bodyType == IBodyDef.Kinematic) {
			this.bodyBuilder = Box2DBuilders.Body.kinematicBodyBuilder();
		} else {
			this.bodyBuilder = Box2DBuilders.Body.dynamicBodyBuilder();
		}

		this.bodyBuilder.setCenter(cx, cy)
				.setAngularDamping(0.5f);

		this.fixtureBuilder = Box2DBuilders.Fixture.fixtureBuilder()
				.setElasticity(0.8f)
				.setDensity(0.5f)
				.setFriction(0.2f)
				.setShape(circle);
	}

	public BallBodyBuilder setAsSensor() {
		fixtureBuilder.setSensor(true);
		return this;
	}

	public BallBodyBuilder setAsBullet() {
		bodyBuilder.setBullet(true);
		return this;
	}

	public BallBodyBuilder setNonElastic() {
		fixtureBuilder.setElasticity(0);
		return this;
	}

	public BallBodyBuilder setDensity(float density) {
		fixtureBuilder.setDensity(density);
		return this;
	}

	public IBody build(IWorld world) {
		IBody body = bodyBuilder.build(world, fixtureBuilder);
		circle.dispose();
		return body;
	}

	public static BallBodyBuilder newBuilder(float r, float cx, float cy, int bodyType) {
		return new BallBodyBuilder(r, cx, cy, bodyType);
	}
}

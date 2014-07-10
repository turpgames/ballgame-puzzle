package com.turpgames.ballgamepuzzle.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Shape;
import com.turpgames.box2d.Box2D;
import com.turpgames.box2d.Box2DWorld;
import com.turpgames.box2d.builders.BodyBuilder;
import com.turpgames.box2d.builders.Box2DBuilders;
import com.turpgames.box2d.builders.FixtureBuilder;

public class BallBodyBuilder {
	private final Shape circle;
	private final BodyBuilder bodyBuilder;
	private final FixtureBuilder fixtureBuilder;

	private BallBodyBuilder(float radius, float x, float y, boolean isDynamic) {
		this.circle = Box2DBuilders.Shape.buildCircle(Box2D.viewportToWorld(radius));

		this.bodyBuilder = isDynamic
				? Box2DBuilders.Body.dynamicBodyBuilder()
				: Box2DBuilders.Body.staticBodyBuilder();

		this.bodyBuilder.setCenter(
				Box2D.viewportToWorldX(x),
				Box2D.viewportToWorldY(y));

		this.fixtureBuilder = Box2DBuilders.Fixture.fixtureBuilder()
				.setElasticity(0.6f)
				.setDensity(1.2f)
				.setFriction(0.2f)
				.setShape(circle);
	}

	public BallBodyBuilder setAsSensor() {
		fixtureBuilder.setSensor(true);
		return this;
	}

	public BallBodyBuilder setNonElastic() {
		fixtureBuilder.setElasticity(0);
		return this;
	}

	public Body build(Box2DWorld world) {
		Body body = bodyBuilder.build(world.getWorld(), fixtureBuilder);
		circle.dispose();
		return body;
	}

	public static BallBodyBuilder newBuilder(float r, float cx, float cy, boolean isDynamic) {
		return new BallBodyBuilder(r, cx, cy, isDynamic);
	}
}

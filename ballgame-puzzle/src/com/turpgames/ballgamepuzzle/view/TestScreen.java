package com.turpgames.ballgamepuzzle.view;

import box2dLight.ConeLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.turpgames.framework.v0.impl.Screen;

public class TestScreen extends Screen {
	private final static float step = 1 / 60f;
	private final static int velocityIterations = 3;
	private final static int positionIterations = 1;

	Camera camera;
	SpriteBatch batch;
	World world;
	Box2DDebugRenderer debugRenderer;
	RayHandler rayHandler;

	@Override
	public void init() {
		super.init();

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(w, h);
		camera.position.set(camera.viewportWidth * .5f, camera.viewportHeight * .5f, 0f);
		camera.update();
		batch = new SpriteBatch();

		world = new World(new Vector2(0, -10), true);
		debugRenderer = new Box2DDebugRenderer();

		rayHandler = new RayHandler(world);
		rayHandler.setCombinedMatrix(camera.combined);

		createBodyLetter();
		
		new ConeLight(rayHandler, 5000, Color.WHITE, 400, w * 0.5f, h * 0.8f, 270, 60);
	}

	public void createBodyLetter() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(300, 300);
		Body body = world.createBody(bodyDef);

		PolygonShape dynamicBox = new PolygonShape();
		dynamicBox.setAsBox(10f, 10f);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = dynamicBox;
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 0.3f;
		body.createFixture(fixtureDef);

		dynamicBox.dispose();
	}

	@Override
	public void draw() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClearDepthf(GL20.GL_COLOR_BUFFER_BIT);

		debugRenderer.render(world, camera.combined);
		rayHandler.updateAndRender();

		world.step(step, velocityIterations, positionIterations);
	}
}

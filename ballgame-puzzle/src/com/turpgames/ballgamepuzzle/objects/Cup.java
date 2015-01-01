package com.turpgames.ballgamepuzzle.objects;

import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.box2d.Box2DObject;
import com.turpgames.box2d.IBody;
import com.turpgames.box2d.IShape;
import com.turpgames.box2d.IWorld;
import com.turpgames.box2d.builders.Box2DBuilders;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.impl.TexturedGameObject;

public class Cup extends Box2DObject implements IDrawable {
	private final float scale = 0.2f;
	private final IBody body;
	private final CupObj cupObj;

	public Cup(IWorld world, float x, float y) {
		IShape shape = Box2DBuilders.Shape.loopedChainBuilder()
				.addVertex(16 * scale, 13 * scale)
				.addVertex(28 * scale, 0)
				.addVertex(150 * scale, 0)
				.addVertex(159 * scale, 13 * scale)
				.addVertex(176 * scale, 160 * scale)
				.addVertex(160 * scale, 160 * scale)
				.addVertex(144 * scale, 16 * scale)
				.addVertex(32 * scale, 16 * scale)
				.addVertex(16 * scale, 160 * scale)
				.addVertex(0, 160 * scale)
				.build();

		body = Box2DBuilders.Body.staticBodyBuilder()
				.build(world,
						Box2DBuilders.Fixture.fixtureBuilder()
								.setElasticity(0.8f)
								.setDensity(1.2f)
								.setFriction(0.1f)
								.setShape(shape));

		shape.dispose();

		this.cupObj = new CupObj(Textures.cup);
		this.cupObj.setWidth(176 * scale);
		this.cupObj.setHeight(160 * scale);
		this.cupObj.getLocation().set(x, y);

		super.setContent(cupObj, body);
		syncWithObject();
	}

	@Override
	public void syncWithBody() {

	}

	@Override
	public void syncWithObject() {
		body.setTransform(cupObj.getLocation().x, cupObj.getLocation().y, 0f);
	}

	@Override
	public void draw() {
		cupObj.draw();
	}

	private class CupObj extends TexturedGameObject {
		public CupObj(ITexture texture) {
			super(texture);
		}
	}
}

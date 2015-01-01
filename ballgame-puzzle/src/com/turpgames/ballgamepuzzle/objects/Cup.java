package com.turpgames.ballgamepuzzle.objects;

import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.box2d.IShape;
import com.turpgames.box2d.IWorld;
import com.turpgames.box2d.builders.Box2DBuilders;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.impl.TexturedGameObject;

public class Cup implements IDrawable {
	private final float scale = 0.2f;
	private final CupObj cupObj;

	public Cup(IWorld world, float x, float y) {
		// Cup texture is 176 x 160

		IShape shape = Box2DBuilders.Shape.loopedChainBuilder()
				.addVertex(x + 16 * scale, y + 13 * scale)
				.addVertex(x + 28 * scale, y + 0 * scale)
				.addVertex(x + 150 * scale, y + 0 * scale)
				.addVertex(x + 159 * scale, y + 13 * scale)
				.addVertex(x + 176 * scale, y + 160 * scale)
				.addVertex(x + 160 * scale, y + 160 * scale)
				.addVertex(x + 144 * scale, y + 16 * scale)
				.addVertex(x + 32 * scale, y + 16 * scale)
				.addVertex(x + 16 * scale, y + 160 * scale)
				.addVertex(x + 0 * scale, y + 160 * scale)
				.build();

		Box2DBuilders.Body.staticBodyBuilder()
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

package com.turpgames.ballgamepuzzle.objects;

import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.box2d.IShape;
import com.turpgames.box2d.IWorld;
import com.turpgames.box2d.builders.Box2DBuilders;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.impl.TexturedGameObject;

public class Block implements IDrawable {
	private final BlockObj blockObj;

	public Block(IWorld world, float x, float y, float w, float h) {
		blockObj = new BlockObj();
		blockObj.setWidth(w);
		blockObj.setHeight(h);
		blockObj.getLocation().set(x, y);
		blockObj.setOriginAsCenter();
		blockObj.getRotation().setRotationZ(30f);

		IShape shape = Box2DBuilders.Shape.buildBox(w, h, x + w / 2f, y + h / 2f, 30f);

		Box2DBuilders.Body.staticBodyBuilder()
				.build(world, Box2DBuilders.Fixture.fixtureBuilder()
						.setElasticity(0.8f)
						.setDensity(0.5f)
						.setFriction(0.2f)
						.setShape(shape));

		shape.dispose();
	}

	@Override
	public void draw() {
		blockObj.draw();
	}

	class BlockObj extends TexturedGameObject {
		public BlockObj() {
			super(Textures.bg);
		}
	}
}

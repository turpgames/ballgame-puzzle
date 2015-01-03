package com.turpgames.ballgamepuzzle.objects;

import com.turpgames.ballgamepuzzle.levels.BlockMeta;
import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.box2d.IShape;
import com.turpgames.box2d.IWorld;
import com.turpgames.box2d.builders.Box2DBuilders;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.impl.TexturedGameObject;

public class Block implements IBallGameObject, IDrawable {
	private final BlockObj blockObj;

	public Block(IWorld world, BlockMeta meta) {
		blockObj = new BlockObj();
		blockObj.setWidth(meta.getWidth());
		blockObj.setHeight(meta.getHeight());
		blockObj.getLocation().set(meta.getX(), meta.getY());
		blockObj.setOriginAsCenter();
		blockObj.getRotation().setRotationZ(meta.getRotation());

		IShape shape = Box2DBuilders.Shape.buildBox(
				meta.getWidth(),
				meta.getHeight(),
				meta.getX() + meta.getWidth() / 2f,
				meta.getY() + meta.getHeight() / 2f,
				meta.getRotation());

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
			super(Textures.block);
		}
	}
}

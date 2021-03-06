package com.turpgames.ballgamepuzzle.objects;

import com.turpgames.ballgamepuzzle.components.Toolbar;
import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.box2d.IShape;
import com.turpgames.box2d.IWorld;
import com.turpgames.box2d.builders.Box2DBuilders;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.IDrawingInfo;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Rectangle;
import com.turpgames.framework.v0.util.ShapeDrawer;
import com.turpgames.framework.v0.util.TextureDrawer;

public class Walls implements IBallGameObject, IDrawable {
	private final static Color wallColor = Color.fromHex("#ffffffff");

	public final static float marginY = 20f;
	
	// (30,40) 740x390
	private final static float x = 30f;
	private final static float y = Toolbar.menuButtonSize;
	private final static float w = Game.getVirtualWidth() - 2 * x;
	private final static float h = Game.getVirtualHeight() - y - marginY;

	private final static Rectangle rect = new Rectangle(x, y, w, h);

	private WallsObject walls;
	private boolean drawWalls;

	public Walls(IWorld world) {
		this(world, false);
	}
	
	public Walls(IWorld world, boolean drawWalls) {
		this.drawWalls = drawWalls;
		
		walls = new WallsObject();
		walls.getLocation().set(x, y);
		walls.setWidth(w);
		walls.setHeight(h);
		walls.getColor().set(wallColor);

		IShape chain = Box2DBuilders.Shape.loopedChainBuilder()
				.addVertex(x, y)
				.addVertex(x + w, y)
				.addVertex(x + w, y + h)
				.addVertex(x, y + h)
				.build();

		Box2DBuilders.Body.staticBodyBuilder()
				.build(world,
						Box2DBuilders.Fixture.fixtureBuilder()
								.setElasticity(0.8f)
								.setDensity(1.2f)
								.setFriction(0.1f)
								.setShape(chain));

		chain.dispose();
	}

	@Override
	public void draw() {
		walls.draw();
	}

	public Rectangle getRect() {
		return rect;
	}

	private class WallsObject extends GameObject {
		@Override
		public void draw() {
			TextureDrawer.draw(Textures.room, IDrawingInfo.viewport);
			if (drawWalls)
				ShapeDrawer.drawRect(this, false);
		}
	}
}

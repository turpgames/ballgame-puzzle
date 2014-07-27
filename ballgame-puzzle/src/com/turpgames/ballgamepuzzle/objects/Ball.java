package com.turpgames.ballgamepuzzle.objects;

import com.turpgames.ballgamepuzzle.levels.BallMeta;
import com.turpgames.ballgamepuzzle.objects.balls.BounceBall;
import com.turpgames.ballgamepuzzle.objects.balls.EnemyBall;
import com.turpgames.ballgamepuzzle.objects.balls.PortalBall;
import com.turpgames.ballgamepuzzle.objects.balls.StoneBall;
import com.turpgames.ballgamepuzzle.objects.balls.SubjectBall;
import com.turpgames.ballgamepuzzle.objects.balls.TargetBall;
import com.turpgames.box2d.Box2DObject;
import com.turpgames.box2d.IBody;
import com.turpgames.box2d.IWorld;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.util.Game;

public abstract class Ball extends Box2DObject implements IDrawable {
	public final static int Blue = 1;
	public final static int Target = 2;
	public final static int Enemy = 3;
	public final static int Stone = 4;
	public final static int Bounce = 5;
	public final static int Purple = 6;
	public final static int Subject = 7;
	public final static int Portal = 8;

	public final static float Small = 15f;
	public final static float Medium = 25f;
	public final static float Large = 35f;

	public final static float ViewportCenterX = Game.getVirtualWidth() / 2f;
	public final static float ViewportCenterY = Game.getVirtualHeight() / 2f;

	protected final BallObject ball;
	protected final float radius;
	protected final IBody body;

	protected Ball(BallMeta meta, IWorld world, ITexture texture) {
		if (meta.getType() != this.getType())
			throw new IllegalArgumentException(String.format("Invalid ball type %d, expected %d", meta.getType(), getType()));

		float cx = meta.getCx();
		float cy = meta.getCy();

		this.radius = meta.getR();

		this.ball = new BallObject(texture);
		this.ball.setWidth(radius * 2);
		this.ball.setHeight(radius * 2);
		this.ball.getLocation().set(cx - radius, cy - radius);
		this.ball.getRotation().origin.set(cx, cy);
		this.ball.getColor().a = 0.9f;

		this.body = createBodyBuilder().build(world);
		
		super.setContent(ball, body);
		super.syncWithObject();
	}

	public abstract int getType();

	protected BallBodyBuilder createBodyBuilder() {
		return BallBodyBuilder.newBuilder(radius, getCenterX(), getCenterY(), isDynamic());
	}

	public float getRadius() {
		return radius;
	}

	public boolean isDynamic() {
		return false;
	}

	public float getCenterX() {
		return ball.getRotation().origin.x;
	}

	public float getCenterY() {
		return ball.getRotation().origin.y;
	}

	public void setCenter(float cx, float cy) {
		ball.getLocation().set(cx - radius, cy - radius);
		ball.getRotation().origin.set(cx, cy);
	}

	public float distanceTo(Ball other) {
		float dx = this.getCenterX() - other.getCenterX();
		float dy = this.getCenterY() - other.getCenterY();
		return (float) Math.sqrt(dx * dx + dy * dy);
	}

	@Override
	public void draw() {
		ball.draw();
	}

	public static Ball create(BallMeta meta, IWorld world) {
		Ball ball = null;
		switch (meta.getType()) {
		case Ball.Subject:
			ball = new SubjectBall(meta, world);
			break;
		case Ball.Stone:
			ball = new StoneBall(meta, world);
			break;
		case Ball.Bounce:
			ball = new BounceBall(meta, world);
			break;
		case Ball.Portal:
			ball = new PortalBall(meta, world);
			break;
		case Ball.Target:
			ball = new TargetBall(meta, world);
			break;
		case Ball.Enemy:
			ball = new EnemyBall(meta, world);
			break;

		default:
			throw new UnsupportedOperationException("Unknown ball type: " + meta.getType());
		}

		return ball;
	}
}

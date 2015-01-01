package com.turpgames.ballgamepuzzle.objects;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.ballgamepuzzle.effects.BreathEffect;
import com.turpgames.ballgamepuzzle.effects.CircularTripEffect;
import com.turpgames.ballgamepuzzle.effects.ExistanceEffect;
import com.turpgames.ballgamepuzzle.effects.HoleEffect;
import com.turpgames.ballgamepuzzle.effects.IBox2DEffect;
import com.turpgames.ballgamepuzzle.effects.PathTripEffect;
import com.turpgames.ballgamepuzzle.effects.RollingEffect;
import com.turpgames.ballgamepuzzle.effects.meta.BreathEffectMeta;
import com.turpgames.ballgamepuzzle.effects.meta.CircularTripEffectMeta;
import com.turpgames.ballgamepuzzle.effects.meta.ExistanceEffectMeta;
import com.turpgames.ballgamepuzzle.effects.meta.HoleEffectMeta;
import com.turpgames.ballgamepuzzle.effects.meta.IEffectMeta;
import com.turpgames.ballgamepuzzle.effects.meta.PathTripEffectMeta;
import com.turpgames.ballgamepuzzle.effects.meta.RollingEffectMeta;
import com.turpgames.ballgamepuzzle.levels.BallMeta;
import com.turpgames.ballgamepuzzle.objects.balls.BounceBall;
import com.turpgames.ballgamepuzzle.objects.balls.EnemyBall;
import com.turpgames.ballgamepuzzle.objects.balls.HoleBall;
import com.turpgames.ballgamepuzzle.objects.balls.PortalBall;
import com.turpgames.ballgamepuzzle.objects.balls.PurpleBall;
import com.turpgames.ballgamepuzzle.objects.balls.RedGrayBall;
import com.turpgames.ballgamepuzzle.objects.balls.StoneBall;
import com.turpgames.ballgamepuzzle.objects.balls.SubjectBall;
import com.turpgames.ballgamepuzzle.objects.balls.TargetBall;
import com.turpgames.box2d.Box2DObject;
import com.turpgames.box2d.IBody;
import com.turpgames.box2d.IBodyDef;
import com.turpgames.box2d.IFixture;
import com.turpgames.box2d.IWorld;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Vector;

public abstract class Ball extends Box2DObject implements IDrawable {
	public final static int Blue = 1;
	public final static int Target = 2;
	public final static int Enemy = 3;
	public final static int Stone = 4;
	public final static int Bounce = 5;
	public final static int Purple = 6;
	public final static int Subject = 7;
	public final static int Portal = 8;
	public final static int BlackHole = 9;
	public final static int WhiteHole = 10;

	public final static int RedGray = 100;

	public final static float Small = 2.5f;
	public final static float Medium = 5f;
	public final static float Large = 10f;

	public final static float ViewportCenterX = Game.getVirtualWidth() / 2f;
	public final static float ViewportCenterY = Game.getVirtualHeight() / 2f;

	protected boolean isHidden;
	protected boolean isSensor;

	protected final BallMeta meta;
	protected final BallObject ball;
	protected final float radius;
	protected final IBody body;

	protected final static float hitX = 7.5f;
	protected final static float hitY = 7.5f;
	private final static Vector hitVelocity = new Vector();

	protected final List<IBox2DEffect> effects;

	protected Ball(BallMeta meta, IWorld world, ITexture texture) {
		this.meta = meta;
		this.effects = new ArrayList<IBox2DEffect>();
		this.isHidden = meta.isHidden();

		float cx = meta.getCx();
		float cy = meta.getCy();

		this.radius = meta.getR();

		this.ball = new BallObject(texture);
		this.ball.setWidth(radius * 2);
		this.ball.setHeight(radius * 2);
		this.ball.getLocation().set(cx - radius, cy - radius);
		this.ball.getRotation().origin.set(cx, cy);

		this.body = createBodyBuilder().build(world);

		super.setContent(ball, body);
		super.syncWithObject();
	}

	public final int getBallType() {
		return meta.getType();
	}

	private IFixture getFixture() {
		return body.getFixtures().get(0);
	}

	protected BallBodyBuilder createBodyBuilder() {
		return BallBodyBuilder.newBuilder(radius, getCenterX(), getCenterY(), getBodyType());
	}

	public int getBodyType() {
		return meta.hasEffect() ? IBodyDef.Kinematic : IBodyDef.Static;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
		if (isHidden) {
			isSensor = getFixture().isSensor();
			getFixture().setSensor(true);
		}
		else {
			getFixture().setSensor(isSensor);
		}
	}

	public float getRadius() {
		return radius;
	}

	protected Vector getCenter() {
		return ball.getRotation().origin;
	}

	public float getCenterX() {
		return getCenter() .x;
	}

	public float getCenterY() {
		return getCenter() .y;
	}

	public float getRotation() {
		return ball.getRotation().angle.z;
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

	public void stopEffect() {
		for (IBox2DEffect effect : effects)
			effect.stop();
		effects.clear();
	}

	public void startEffect() {
		if (!meta.hasEffect())
			return;

		for (IEffectMeta effectMeta : meta.getEffects()) {
			IBox2DEffect effect = createEffect(effectMeta);
			effects.add(effect);
			effect.start();
		}
	}
	
	public void destroy() {
		body.getWorld().destroyBody(body);
		isHidden = true;
	}

	@Override
	public void draw() {
		if (!isHidden)
			ball.draw();
	}

	public static Vector calculateHitVelocity(float x, float y, Vector ballCenter) {
		float dx = ballCenter.x - x;
		float dy = ballCenter.y - y;

		hitVelocity.set(dx * hitX, dy * hitY);

		return hitVelocity;
	}

	public static Ball create(BallMeta meta, IWorld world) {
		if (meta.getType() == Ball.Subject)
			return new SubjectBall(meta, world);
		if (meta.getType() == Ball.Stone)
			return new StoneBall(meta, world);
		if (meta.getType() == Ball.Bounce)
			return new BounceBall(meta, world);
		if (meta.getType() == Ball.Purple)
			return new PurpleBall(meta, world);
		if (meta.getType() == Ball.Portal)
			return new PortalBall(meta, world);
		if (meta.getType() == Ball.Target)
			return new TargetBall(meta, world);
		if (meta.getType() == Ball.Enemy)
			return new EnemyBall(meta, world);
		if (meta.getType() == Ball.RedGray)
			return new RedGrayBall(meta, world);
		if (meta.getType() == Ball.BlackHole ||
			meta.getType() == Ball.WhiteHole)
			return HoleBall.createHoleBall(meta, world);
		throw new UnsupportedOperationException("Unknown ball type: " + meta.getType());
	}

	private IBox2DEffect createEffect(IEffectMeta effectMeta) {
		if (effectMeta instanceof PathTripEffectMeta) {
			PathTripEffectMeta meta = (PathTripEffectMeta) effectMeta;

			PathTripEffect effect = new PathTripEffect(this);

			effect.setDuration(meta.getTotalDuration());
			effect.setRoundTrip(meta.isRoundTrip());

			for (Vector node : meta.getPath())
				effect.addNode(node.x, node.y);

			return effect;
		}
		if (effectMeta instanceof CircularTripEffectMeta) {
			CircularTripEffectMeta meta = (CircularTripEffectMeta) effectMeta;

			CircularTripEffect effect = new CircularTripEffect(this);

			effect.setCenter(meta.getCenter().x, meta.getCenter().y);
			effect.setDuration(meta.getTotalDuration());
			effect.setClockWise(meta.isClockWise());

			return effect;
		}
		if (effectMeta instanceof RollingEffectMeta) {
			RollingEffectMeta meta = (RollingEffectMeta) effectMeta;

			RollingEffect effect = new RollingEffect(this);

			effect.setDuration(meta.getTotalDuration());
			effect.setClockWise(meta.isClockWise());

			return effect;
		}
		if (effectMeta instanceof BreathEffectMeta) {
			BreathEffectMeta meta = (BreathEffectMeta) effectMeta;

			BreathEffect effect = new BreathEffect(this);

			effect.setDuration(meta.getTotalDuration());
			effect.setMinScale(meta.getMinScale());
			effect.setMaxScale(meta.getMaxScale());
			effect.setStartFromMaxScale(meta.isStartFromMaxScale());

			return effect;
		}
		if (effectMeta instanceof ExistanceEffectMeta) {
			ExistanceEffectMeta meta = (ExistanceEffectMeta) effectMeta;

			ExistanceEffect effect = new ExistanceEffect(this);

			effect.setDuration(meta.getTotalDuration());
			effect.setDurations(meta.getDurations());
			effect.setLooping(meta.isLooping());
			effect.setHiddenAtStartup(meta.isHiddenAtStartup());

			return effect;
		}
		if (effectMeta instanceof HoleEffectMeta) {
			HoleEffectMeta meta = (HoleEffectMeta) effectMeta;

			HoleEffect effect = new HoleEffect((HoleBall) this);

			effect.setWhiteHole(meta.isWhiteHole());

			return effect;
		}
		return null;
	}
}

package com.turpgames.ballgamepuzzle.objects.balls;

import com.turpgames.ballgamepuzzle.effects.HoleEffect;
import com.turpgames.ballgamepuzzle.effects.IBox2DEffect;
import com.turpgames.ballgamepuzzle.levels.BallMeta;
import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.objects.BallBodyBuilder;
import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.box2d.IBody;
import com.turpgames.box2d.IBodyDef;
import com.turpgames.box2d.IWorld;
import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.impl.TexturedGameObject;

public class HoleBall extends Ball {
	private final static float holeFactor = 10f;

	private final SensorObj sensorObj;

	private IBody sensorBody;
	private HoleEffect holeEffect;

	private HoleBall(BallMeta meta, IWorld world, ITexture ballTexture, ITexture sensorTexture) {
		super(meta, world, ballTexture);

		this.sensorObj = new SensorObj(sensorTexture);

		sensorBody = BallBodyBuilder.newBuilder(radius * holeFactor, getCenterX(), getCenterY(), IBodyDef.Static)
				.setAsSensor()
				.build(world);

		sensorBody.setData(this);
	}

	@Override
	protected BallBodyBuilder createBodyBuilder() {
		return super.createBodyBuilder().setAsSensor().setAsBullet();
	}

	@Override
	public void startEffect() {
		super.startEffect();
		for (IBox2DEffect effect : effects) {
			if (effect instanceof HoleEffect) {
				holeEffect = (HoleEffect) effect;
				break;
			}
		}
	}

	public void enter(IBody body) {
		holeEffect.addBody(body);
	}

	public void leave(IBody body) {
		holeEffect.removeBody(body);
	}

	public IBody getSensorBody() {
		return sensorBody;
	}

	@Override
	public void draw() {
		sensorObj.draw();
		super.draw();
	}

	class SensorObj extends TexturedGameObject {
		public SensorObj(ITexture holeTexture) {
			setTexture(holeTexture);
			setWidth(radius * holeFactor * 2);
			setHeight(radius * holeFactor * 2);
			getLocation().set(getCenterX() - radius * holeFactor, getCenterY() - radius * holeFactor);
			getColor().a = 0.9f;
		}
	}

	public static Ball createHoleBall(BallMeta meta, IWorld world) {
		if (meta.getType() == Ball.BlackHole)
			return new HoleBall(meta, world, Textures.ball_black, Textures.black_hole);
		return new HoleBall(meta, world, Textures.ball_white, Textures.black_hole);
	}
}

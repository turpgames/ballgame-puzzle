package com.turpgames.ballgamepuzzle.objects.balls;

import com.turpgames.ballgamepuzzle.levels.BallMeta;
import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.objects.BallBodyBuilder;
import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.box2d.IBodyDef;
import com.turpgames.box2d.IWorld;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.impl.TexturedGameObject;
import com.turpgames.framework.v0.util.Timer;
import com.turpgames.framework.v0.util.Vector;

public class SubjectBall extends Ball {

	private boolean isGhost;
	private final Timer ghostTimer;

	private final BallShadow shadow;
	private final PortalState portalState;
	
	public SubjectBall(BallMeta meta, IWorld world) {
		super(meta, world, Textures.ball_dot);

		ghostTimer = new Timer();
		ghostTimer.setInterval(3f);
		ghostTimer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer timer) {
				unsetGhost();
			}
		});

		this.portalState = new PortalState(this);
		this.shadow = new BallShadow(ball);
	}

	@Override
	protected BallBodyBuilder createBodyBuilder() {
		return super.createBodyBuilder().setDeactive();//.setAsBullet();
	}

	@Override
	public int getBodyType() {
		return IBodyDef.Dynamic;
	}

	public void activate() {
		body.setActive(true);
	}

	public boolean isGhost() {
		return isGhost;
	}

	public void setAsGhost() {
		isGhost = true;
		ball.getColor().a = 0.5f;
		ghostTimer.start();
	}

	private void unsetGhost() {
		isGhost = false;
		ball.getColor().a = 1f;
		ghostTimer.stop();
	}

	@Override
	public void stopEffect() {
		super.stopEffect();
		ghostTimer.stop();
	}

	public void hit(float x, float y) {
		Vector velocity = calculateHitVelocity(x, y, getCenter());
		body.setVelocity(velocity.x, velocity.y);
		body.setAngularVelocity(180f);
	}

	public boolean enterPortal(PortalBall portal) {
		return portalState.enterPortal(portal);
	}

	public void leavePortal(PortalBall portal) {
		portalState.leavePortal(portal);
	}

	void bounce(float fx, float fy) {
		body.applyForceToCenter(fx, fy);
	}

	public boolean isMoving() {
		return !body.getVelocity().isZero();
	}

	@Override
	public void draw() {
		if (!isHidden) {
			shadow.draw();
			ball.draw();
		}
	}
	
	static class PortalState {
		private final SubjectBall ball;
		private long lastSourcePortalLeft;
		private PortalBall sourcePortal;
		
		public PortalState(SubjectBall ball) {
			this.ball = ball;
		}
		
		public boolean enterPortal (PortalBall portalBall) {
			if (canEnterPortal()) {
				doEnterPortal(portalBall);
				return true;
			}
			return false;
		}

		private boolean canEnterPortal() {
			if (System.currentTimeMillis() - lastSourcePortalLeft > 80L)
				return true;
			return false;
		}

		private void doEnterPortal(PortalBall portalBall) {
			sourcePortal = portalBall;
			PortalBall targetPortal = sourcePortal.getPair();

			sourcePortal.playInEfect();
			targetPortal.playOutEfect();

			ball.getCenter().set(targetPortal.getCenter());
			ball.syncWithObject();
		}
		
		public void leavePortal(PortalBall portalBall) {
			if (sourcePortal == portalBall) {
				sourcePortal = null;
				lastSourcePortalLeft = System.currentTimeMillis();
			}
		}
	}

	class BallShadow extends TexturedGameObject {
		private GameObject ball;
		private Shadow shd;

		public BallShadow(GameObject ball) {
			super(Textures.ball_shadowed);

			this.ball = ball;

			setWidth(ball.getWidth());
			setHeight(ball.getHeight());

			shd = new Shadow();
			shd.getLocation().y = 38;
		}

		@Override
		public void draw() {
			shd.getLocation().x = ball.getLocation().x;
			getLocation().set(ball.getLocation());
			shd.draw();
			super.draw();
		}

		class Shadow extends TexturedGameObject {
			Shadow() {
				super(Textures.ball_shadow);
				setWidth(ball.getWidth());
				setHeight(ball.getHeight() / 2f);
			}
		}
	}
}

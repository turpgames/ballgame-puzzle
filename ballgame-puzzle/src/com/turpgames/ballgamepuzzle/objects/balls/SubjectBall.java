package com.turpgames.ballgamepuzzle.objects.balls;

import com.turpgames.ballgamepuzzle.levels.BallMeta;
import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.box2d.IBodyDef;
import com.turpgames.box2d.IWorld;
import com.turpgames.framework.v0.util.Timer;

public class SubjectBall extends Ball {
	private final static float hitX = 5f;
	private final static float hitY = 5f;

	private PortalBall sourcePortal;
	private PortalBall targetPortal;

	private boolean isGhost;
	private final Timer ghostTimer;

	public SubjectBall(BallMeta meta, IWorld world) {
		super(meta, world, Textures.ball_white_dot);
		
		ghostTimer = new Timer();
		ghostTimer.setInterval(3f);
		ghostTimer.setTickListener(new Timer.ITimerTickListener() {
			@Override
			public void timerTick(Timer timer) {
				unsetGhost();
			}
		});
	}

	@Override
	public int getBodyType() {
		return IBodyDef.Dynamic;
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
		float dx = this.getCenterX() - x;
		float dy = this.getCenterY() - y;
		body.setVelocity(dx * hitX, dy * hitY);
		body.setAngularVelocity(180f);
	}
	
	public void enterPortal(PortalBall portal) {
		boolean canEnterPortal = sourcePortal == null && targetPortal == null; 
		if (canEnterPortal) {
			doEnterPortal(portal);
		}
	}

	private void doEnterPortal(PortalBall portal) {
		sourcePortal = portal;
		targetPortal = sourcePortal.getPair();
		
		sourcePortal.playInEfect();
		targetPortal.playOutEfect();
		
		this.setCenter(targetPortal.getCenterX(), targetPortal.getCenterY());
		this.syncWithObject();
	}

	public void leavePortal(PortalBall portal) {
		if (sourcePortal == portal) {
			sourcePortal = null;
		}
		else if (targetPortal == portal) {
			targetPortal = null;
		}
	}

	void bounce(float fx, float fy) {
		body.setVelocity(fx * hitX, fy * hitY);
	}

	public boolean isMoving() {
		return !body.getVelocity().isZero();
	}
}

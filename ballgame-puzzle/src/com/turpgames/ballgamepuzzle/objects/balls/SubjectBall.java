package com.turpgames.ballgamepuzzle.objects.balls;

import com.turpgames.ballgamepuzzle.levels.BallMeta;
import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.box2d.Box2DWorld;

public class SubjectBall extends Ball {
	private final static float hitX = 4f;
	private final static float hitY = 6f;

	private PortalBall sourcePortal;
	private PortalBall targetPortal;

	public SubjectBall(BallMeta meta, Box2DWorld world) {
		super(meta, world, Textures.ball_azure);
	}

	@Override
	public int getType() {
		return Ball.Subject;
	}

	@Override
	public boolean isDynamic() {
		return true;
	}

	public void hit(float x, float y) {
		float dx = this.getCenterX() - x;
		float dy = this.getCenterY() - y;
		float d = (float) Math.sqrt(dx * dx + dy * dy);
		body.setLinearVelocity((dx / d) * hitX, (dy / d) * hitY);
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
		body.setLinearVelocity(fx * hitX, fy * hitY);
	}

	public boolean isMoving() {
		return !body.getLinearVelocity().isZero();
	}
}

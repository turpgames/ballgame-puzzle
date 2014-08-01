package com.turpgames.ballgamepuzzle.collisionhandlers;

import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.objects.balls.SubjectBall;
import com.turpgames.ballgamepuzzle.objects.balls.PortalBall;
import com.turpgames.ballgamepuzzle.utils.Sounds;

public class PortalHandler extends BallCollisionHandler {
	public PortalHandler() {
		super(Ball.Portal);
	}

	@Override
	protected boolean handleBeginCollide(Ball b1, Ball b2) {
		if (b2.getBallType() != Ball.Subject)
			return false;
		if (((SubjectBall) b2).isGhost())
			return false;
		PortalBall portal = (PortalBall) b1;
		SubjectBall azureBall = (SubjectBall) b2;
		azureBall.enterPortal(portal);
		Sounds.portal.play();
		return true;
	}

	@Override
	protected boolean handleEndCollide(Ball b1, Ball b2) {
		if (b2.getBallType() != Ball.Subject)
			return false;
		PortalBall portal = (PortalBall) b1;
		SubjectBall azureBall = (SubjectBall) b2;
		azureBall.leavePortal(portal);
		return true;
	}
}

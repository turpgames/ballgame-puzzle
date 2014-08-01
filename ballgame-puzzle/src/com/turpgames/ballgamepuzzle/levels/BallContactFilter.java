package com.turpgames.ballgamepuzzle.levels;

import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.objects.balls.SubjectBall;
import com.turpgames.box2d.IBox2DObject;
import com.turpgames.box2d.IContactFilter;
import com.turpgames.box2d.IFixture;

public class BallContactFilter implements IContactFilter {

	public final static BallContactFilter instance = new BallContactFilter();

	private BallContactFilter() {

	}

	@Override
	public boolean shouldCollide(IFixture fixtureA, IFixture fixtureB) {
		IBox2DObject o1 = fixtureA.getBody().getData();
		IBox2DObject o2 = fixtureB.getBody().getData();

		if (o1 instanceof Ball && o2 instanceof Ball) {
			Ball b1 = (Ball) o1;
			Ball b2 = (Ball) o2;

			if (b1.isHidden() || b2.isHidden())
				return false;

			return areGhostSubjectAndTarget(b1, b2);
		}

		return true;
	}

	private boolean areGhostSubjectAndTarget(Ball b1, Ball b2) {
		if (b1.getBallType() == Ball.Subject) {
			if (((SubjectBall) b1).isGhost())
				return b2.getBallType() == Ball.Target;
		}

		if (b2.getBallType() == Ball.Subject) {
			if (((SubjectBall) b2).isGhost())
				return b1.getBallType() == Ball.Target;
		}
		
		return true;
	}
}

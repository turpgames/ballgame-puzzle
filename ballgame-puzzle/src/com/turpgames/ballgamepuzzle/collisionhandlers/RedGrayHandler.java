package com.turpgames.ballgamepuzzle.collisionhandlers;

import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.utils.Global;
import com.turpgames.ballgamepuzzle.utils.Sounds;
import com.turpgames.box2d.Box2D;
import com.turpgames.framework.v0.util.Vector;

public class RedGrayHandler extends BallCollisionHandler {
	public RedGrayHandler() {
		super(Ball.RedGray);
	}

	@Override
	protected boolean handleBeginCollide(Ball b1, Ball b2) {
		if (b2.getBallType() != Ball.Subject)
			return false;

		if (hitOnEnemySide(b1, b2)) {
			ensureSubjectPosition(b1, b2);
			Global.currentController.onHitEnemy();
			Sounds.enemy.play();
		} else {
			Sounds.stone.play();
		}
		return true;
	}

	private void ensureSubjectPosition(Ball redGray, Ball subject) {
		Vector o1 = redGray.getObject().getRotation().origin;
		Vector o2 = subject.getObject().getRotation().origin;

		Vector t = o2.tmp();
		t.sub(o1);

		float alpha = (float) Math.atan2(t.y, t.x);

		// find better solution
		float dist = redGray.getBody().getFixtures().get(0).getShape().getRadius() + subject.getRadius();

		subject.setCenter(
				o1.x + (float) Math.cos(alpha) * dist,
				o1.y + (float) Math.sin(alpha) * dist);

		subject.syncWithObject();
	}

	private boolean hitOnEnemySide(Ball redGray, Ball subject) {
		float angle = Box2D.viewportToWorldAngle(redGray.getRotation());
		float cx = redGray.getCenterX();
		float cy = redGray.getCenterY();

		float px = subject.getCenterX();
		float py = subject.getCenterY();

		float x = rotateBackAndGetX(cx, cy, px, py, angle);

		// what if balls have different radiuses, this may (or may not) require some more complex calculations. think about that...
		return x < cx;
	}

	float rotateBackAndGetX(float cx, float cy, float px, float py, float angle) {
		float s = (float) Math.sin(-angle);
		float c = (float) Math.cos(-angle);

		// translate point back to origin:
		px -= cx;
		py -= cy;

		// rotate point
		float xnew = px * c - py * s;
		float ynew = px * s + py * c;

		// translate point back:
		px = xnew + cx;
		py = ynew + cy;

		return px;
	}
}

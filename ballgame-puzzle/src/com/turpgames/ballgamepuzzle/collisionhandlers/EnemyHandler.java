package com.turpgames.ballgamepuzzle.collisionhandlers;

import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.ballgamepuzzle.objects.balls.SubjectBall;
import com.turpgames.ballgamepuzzle.utils.Global;
import com.turpgames.ballgamepuzzle.utils.Sounds;
import com.turpgames.framework.v0.util.Vector;

public class EnemyHandler extends BallCollisionHandler {
	public EnemyHandler() {
		super(Ball.Enemy);
	}

	@Override
	protected boolean handleBeginCollide(Ball b1, Ball b2) {
		if (b2.getBallType() != Ball.Subject)
			return false;
		if (((SubjectBall) b2).isGhost())
			return false;
		
		ensureSubjectPosition(b1, b2);
		
		Global.currentController.onHitEnemy();
		Sounds.enemy.play();
		return true;
	}
	
	private void ensureSubjectPosition(Ball enemy, Ball subject) {
		Vector o1 = enemy.getObject().getRotation().origin;
		Vector o2 = subject.getObject().getRotation().origin;

		Vector t = o2.tmp();
		t.sub(o1);

		float alpha = (float) Math.atan2(t.y, t.x);

		// find better solution
		float dist = enemy.getBody().getFixtures().get(0).getShape().getRadius() + subject.getRadius();

		subject.setCenter(
				o1.x + (float) Math.cos(alpha) * dist,
				o1.y + (float) Math.sin(alpha) * dist);

		subject.syncWithObject();
	}
}

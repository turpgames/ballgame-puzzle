package com.turpgames.ballgamepuzzle.effects;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.ballgamepuzzle.objects.balls.HoleBall;
import com.turpgames.ballgamepuzzle.objects.balls.SubjectBall;
import com.turpgames.box2d.Box2D;
import com.turpgames.box2d.IBody;
import com.turpgames.framework.v0.util.Vector;

public class HoleEffect extends Box2DEffect implements IBallGameEffect {
	private final IBody blackHoleBody;

	private final List<IBody> effectedBodies;
	private final Vector holeCenter;
	private boolean isWhiteHole;

	public HoleEffect(HoleBall blackHole) {
		super(blackHole);
		blackHoleBody = blackHole.getBody();
		effectedBodies = new ArrayList<IBody>();
		holeCenter = new Vector();
	}

	public void addBody(IBody body) {
		effectedBodies.add(body);
	}

	public void removeBody(IBody body) {
		effectedBodies.remove(body);
	}

	public void setWhiteHole(boolean isWhiteHole) {
		this.isWhiteHole = isWhiteHole;
	}

	@Override
	protected void onUpdate() {
		holeCenter.set(blackHoleBody.getCenter());
		holeCenter.x = Box2D.viewportToWorldX(holeCenter.x);
		holeCenter.y = Box2D.viewportToWorldY(holeCenter.y);

		for (IBody body : effectedBodies) {
			if (((SubjectBall) body.getData()).isGhost())
				continue;
			
			Vector bc = body.getCenter().tmp();

			bc.x = Box2D.viewportToWorldX(bc.x);
			bc.y = Box2D.viewportToWorldY(bc.y);

			float dx = holeCenter.x - bc.x;
			float dy = holeCenter.y - bc.y;
			float d2 = dx * dx + dy * dy;

			float fx = dx / d2;
			float fy = dy / d2;

			float f = 100f;
			if (isWhiteHole)
				f = -f;

			body.applyForceToCenter(fx * f, fy * (f + body.getMass() * 10));
		}
	}
}

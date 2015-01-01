package com.turpgames.ballgamepuzzle.objects;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.box2d.Box2D;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.ShapeDrawer;
import com.turpgames.framework.v0.util.Vector;

public class Spanner implements IDrawable {
	private final static float maxLength = 200f;
	private final static Color color = Color.white();

	private final Vector center;
	private final Vector touch;
	private final Vector hitPoint;
	private final FreeThrow freeThrow;
	private final List<Vector> points = new ArrayList<Vector>();

	public Spanner(float centerX, float centerY, float touchX, float touchY) {
		center = new Vector(centerX, centerY);
		touch = new Vector(touchX, touchY);
		hitPoint = new Vector(center);
		freeThrow = new FreeThrow(center);
	}

	public void update(float x, float y) {
		float dx = x - touch.x;
		float dy = y - touch.y;
		float len = (float) Math.sqrt(dx * dx + dy * dy);

		float ratio = maxLength / len; 
		
		if (ratio < 1) {
			x *= ratio;
			y *= ratio;
		}
		
		hitPoint.set(center.x + dx, center.y + dy);

		Vector initialVelocity = Ball.calculateHitVelocity(hitPoint.x, hitPoint.y, center);
		freeThrow.setInitialVelocity(initialVelocity);
		
		points.clear();
		for (float time = 0.1f; time <= 1.05f; time += 0.1f) {
			points.add(freeThrow.getPointAt(time));
		}
	}

	public Vector getHitPoint() {
		return hitPoint;
	}

	public float getPower() {
		return hitPoint.dist(center);
	}

	@Override
	public void draw() {
		// ShapeDrawer.drawLine(center.x, center.y, hitPoint.x, hitPoint.y,
		// color, false);
		for (Vector p : points)
			ShapeDrawer.drawCircle(p.x, p.y, 2, color, true, false);
	}

	class FreeThrow {
		private final Vector startPoint;
		private final Vector velocity;

		public FreeThrow(Vector startPoint) {
			this.startPoint = new Vector(startPoint);
			this.velocity = new Vector();
		}
		
		public void setInitialVelocity(Vector initialVelocity) {
			velocity.set(initialVelocity);
		}

		public Vector getPointAt(float time) {
			float gravity = Box2D.worldToViewport(Box2D.gravity);

			// vx*t + vy*t + 0.5*a*t^2
			return new Vector(startPoint)
					.add(velocity.x * time,
							velocity.y * time + 0.5f * gravity * time * time);
		}
	}
}

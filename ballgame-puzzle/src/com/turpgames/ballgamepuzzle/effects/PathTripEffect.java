package com.turpgames.ballgamepuzzle.effects;

import java.util.Iterator;
import java.util.LinkedList;

import com.turpgames.box2d.IBody;
import com.turpgames.box2d.IBox2DObject;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Vector;

public class PathTripEffect extends Box2DEffect {
	private final IBody body;

	private LinkedList<Vector> pathNodes;
	private Iterator<Vector> moveIter;
	private Vector from;
	private Vector to;
	private float totalDist;
	private boolean isRoundTrip;
	private float totalDuration;
	private float subDuration;

	public PathTripEffect(IBox2DObject obj) {
		super(obj);
		this.body = obj.getBody();
		this.pathNodes = new LinkedList<Vector>();
	}

	public void setRoundTrip(boolean isRoundTrip) {
		this.isRoundTrip = isRoundTrip;
	}

	public void setDuration(float duration) {
		this.totalDuration = duration;
		super.setDuration(duration);
	}

	public void addNode(float x, float y) {
		Vector newNode = new Vector(x, y);

		if (pathNodes.size() > 0) {
			Vector lastNode = pathNodes.getLast();
			totalDist += newNode.dist(lastNode);
		}

		pathNodes.add(newNode);
	}
	
	public void clearNodes() {
		pathNodes.clear();
	}

	@Override
	protected void onStart() {
		totalDist += pathNodes.getLast().dist(pathNodes.getFirst());

		moveIter = pathNodes.iterator();
		to = moveIter.next();

		updatePath();
	}

	@Override
	protected void onStop() {
		body.setVelocity(0, 0);
	}

	@Override
	protected void onUpdate() {
		float dt = Game.getDeltaTime();

		subDuration -= dt;

		if (subDuration <= 0)
			updatePath();
	}

	private void reversePath() {
		LinkedList<Vector> reversed = new LinkedList<Vector>();
		for (Vector node : pathNodes)
			reversed.addFirst(node);
		pathNodes = reversed;
	}

	private void updatePath() {
		if (!moveIter.hasNext()) {
			if (isRoundTrip) {
				moveIter = pathNodes.iterator();
			}
			else {
				reversePath();
				moveIter = pathNodes.iterator();
				to = moveIter.next();
			}
		}

		from = to;
		to = moveIter.next();

		float distToTarget = to.dist(from);
		subDuration = (distToTarget / totalDist) * totalDuration;

		body.setTransform(from.x, from.y, body.getRotation());

		body.setVelocity(
				(to.x - from.x) / subDuration,
				(to.y - from.y) / subDuration);
	}
}

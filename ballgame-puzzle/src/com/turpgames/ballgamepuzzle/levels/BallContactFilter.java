package com.turpgames.ballgamepuzzle.levels;

import com.turpgames.ballgamepuzzle.objects.Ball;
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
			if (((Ball) o1).isHidden() || ((Ball) o2).isHidden())
				return false;
		}

		return true;
	}

}

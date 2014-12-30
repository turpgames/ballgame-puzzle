package com.turpgames.ballgamepuzzle.objects;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.ShapeDrawer;
import com.turpgames.framework.v0.util.Vector;

public class Spanner implements IDrawable {
	private final static Color color = Color.black();
	
	private final Vector start;
	private final Vector end;
	
	public Spanner(float x, float y) {
		start = new Vector(x, y);
		end = new Vector(start);
	}
	
	public void update(float x, float y) {
		end.set(x, y);
	}

	@Override
	public void draw() {
		ShapeDrawer.drawLine(start.x, start.y, end.x, end.y, color, false);
	}
}

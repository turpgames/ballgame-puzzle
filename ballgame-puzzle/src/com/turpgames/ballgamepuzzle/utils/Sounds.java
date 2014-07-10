package com.turpgames.ballgamepuzzle.utils;

import com.turpgames.framework.v0.ISound;
import com.turpgames.framework.v0.util.Game;

public class Sounds
{
	public static final ISound hit = Game.getResourceManager().getSound("hit");
	public static final ISound enemy = Game.getResourceManager().getSound("red");
	public static final ISound portal = Game.getResourceManager().getSound("orange");
	public static final ISound bounce = Game.getResourceManager().getSound("yellow");
	public static final ISound stone = Game.getResourceManager().getSound("gray");
	public static final ISound target = Game.getResourceManager().getSound("green");
}

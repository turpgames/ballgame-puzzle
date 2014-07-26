package com.turpgames.ballgamepuzzle.utils;

import java.util.HashMap;
import java.util.Map;

import com.turpgames.ballgamepuzzle.levels.LevelMeta;
import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.util.Game;

public class Textures
{
	private final static Map<Integer, ITexture> stars = new HashMap<Integer, ITexture>();
	
	public static final ITexture ball_azure = Game.getResourceManager().getTexture("ball_azure");
	public static final ITexture ball_blue = Game.getResourceManager().getTexture("ball_blue");
	public static final ITexture ball_gray = Game.getResourceManager().getTexture("ball_gray");
	public static final ITexture ball_green = Game.getResourceManager().getTexture("ball_green");
	public static final ITexture ball_orange = Game.getResourceManager().getTexture("ball_orange");
	public static final ITexture ball_purple = Game.getResourceManager().getTexture("ball_purple");
	public static final ITexture ball_red = Game.getResourceManager().getTexture("ball_red");
	public static final ITexture ball_yellow = Game.getResourceManager().getTexture("ball_yellow");
	public static final ITexture bg = Game.getResourceManager().getTexture("bg");
	public static final ITexture tap = Game.getResourceManager().getTexture("tap");
	public static final ITexture bump = Game.getResourceManager().getTexture("bump");
	public static final ITexture swipe = Game.getResourceManager().getTexture("swipe");
	public static final ITexture level_item = Game.getResourceManager().getTexture("level_item");
	public static final ITexture locked = Game.getResourceManager().getTexture("locked");
	public static final ITexture unlocked = Game.getResourceManager().getTexture("unlocked");
	public static final ITexture star_empty = Game.getResourceManager().getTexture("star_empty");
	public static final ITexture star_half = Game.getResourceManager().getTexture("star_half");
	public static final ITexture star_full = Game.getResourceManager().getTexture("star_full");
	public static final ITexture button_green = Game.getResourceManager().getTexture("button_green");
	public static final ITexture button_blue = Game.getResourceManager().getTexture("button_blue");
	public static final ITexture info = Game.getResourceManager().getTexture("info");
	
	static {
		stars.put(LevelMeta.Star1, star_empty);
		stars.put(LevelMeta.Star2, star_half);
		stars.put(LevelMeta.Star3, star_full);
	}
	
	public static ITexture getStar(int star) {
		return stars.get(star);
	}
}
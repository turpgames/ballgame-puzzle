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
	public static final ITexture ball_red_gray = Game.getResourceManager().getTexture("ball_red_gray");
	public static final ITexture bg = Game.getResourceManager().getTexture("bg");
	public static final ITexture locked = Game.getResourceManager().getTexture("locked");
	public static final ITexture unlocked = Game.getResourceManager().getTexture("unlocked");
	public static final ITexture star_empty = Game.getResourceManager().getTexture("star_empty");
	public static final ITexture star_half = Game.getResourceManager().getTexture("star_half");
	public static final ITexture star_full = Game.getResourceManager().getTexture("star_full");
	public static final ITexture button_green = Game.getResourceManager().getTexture("button_green");
	public static final ITexture button_blue = Game.getResourceManager().getTexture("button_blue");
	public static final ITexture tb_info = Game.getResourceManager().getTexture("tb_info");
	public static final ITexture tb_reset = Game.getResourceManager().getTexture("tb_reset");
	public static final ITexture tb_back = Game.getResourceManager().getTexture("tb_back");
	public static final ITexture tb_sound_on = Game.getResourceManager().getTexture("tb_sound_on");
	public static final ITexture tb_sound_off = Game.getResourceManager().getTexture("tb_sound_off");
	public static final ITexture star = Game.getResourceManager().getTexture("star");
	public static final ITexture level_item = Game.getResourceManager().getTexture("level_item");
	public static final ITexture button = Game.getResourceManager().getTexture("button");
	
	static {
		stars.put(LevelMeta.Star1, star_empty);
		stars.put(LevelMeta.Star2, star_half);
		stars.put(LevelMeta.Star3, star_full);
	}
	
	public static ITexture getStar(int star) {
		return stars.get(star);
	}
}
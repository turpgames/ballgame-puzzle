package com.turpgames.ballgamepuzzle.components;

import com.turpgames.ballgamepuzzle.utils.Textures;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.effects.BreathEffect;
import com.turpgames.framework.v0.effects.IEffectEndListener;
import com.turpgames.framework.v0.impl.TexturedGameObject;
import com.turpgames.framework.v0.util.Game;

public class Stars implements IDrawable {
	private final static float resultStarSize = 128f;

	private final StarObj star1;
	private final StarObj star2;
	private final StarObj star3;
	private int starCount;

	public Stars() {
		star1 = new StarObj(star1EffectEndListener);
		star2 = new StarObj(star2EffectEndListener);
		star3 = new StarObj(null);
	}

	public void setupForResultScreen(int starCount) {
		this.starCount = starCount;

		updateStarSizes(resultStarSize);
		updateStarLocations();
		updateStarOrigins();
		deactivateStars();
	}

	public void setupForLevelSelectionMenu(int starCount, float x, float y, float w) {
		this.starCount = starCount;

		updateStarSizes(w / 2 - 2f);
		updateStarLocations(x, y, w);
		updateStarOrigins();
		deactivateStars();
		activateStars();
	}

	private void updateStarSizes(float size) {
		star1.setWidth(size);
		star1.setHeight(size);
		star2.setWidth(size);
		star2.setHeight(size);
		star3.setWidth(size);
		star3.setHeight(size);
	}

	private void updateStarLocations() {
		if (starCount == 1) {
			star1.getLocation().set(
					(Game.getVirtualWidth() - star1.getWidth()) / 2,
					Game.getVirtualHeight() - 200f);
		} else {
			star1.getLocation().set(
					Game.getVirtualWidth() / 2 - star1.getWidth(),
					Game.getVirtualHeight() - 200f);

			star2.getLocation().set(
					Game.getVirtualWidth() / 2,
					Game.getVirtualHeight() - 200f);

			if (starCount == 3) {
				star3.getLocation().set(
						(Game.getVirtualWidth() - star3.getWidth()) / 2,
						Game.getVirtualHeight() - 150f);
			}
		}
	}

	private void updateStarLocations(float x, float y, float w) {
		if (starCount == 1) {
			star1.getLocation().set(
					x + (w - star1.getWidth()) / 2,
					y + (w - star1.getWidth()) / 2);
		} else if (starCount == 2) {
			star1.getLocation().set(
					x + w / 2 - star1.getWidth(),
					y + (w - star1.getWidth()) / 2);

			star2.getLocation().set(
					x + w / 2,
					y + (w - star1.getWidth()) / 2);
		} else {
			star1.getLocation().set(
					x + w / 2 - star1.getWidth(),
					y + w * 0.15f);

			star2.getLocation().set(
					x + w / 2,
					y + w * 0.15f);

			star3.getLocation().set(
					x + (w - star3.getWidth()) / 2,
					y + w * 0.40f);
		}
	}

	private void updateStarOrigins() {
		star1.setOriginAsCenter();
		star2.setOriginAsCenter();
		star3.setOriginAsCenter();
	}

	private void deactivateStars() {
		star1.deactivate();
		star2.deactivate();
		star3.deactivate();
	}

	private void activateStars() {
		if (starCount > 0)
			star1.activate();
		if (starCount > 1)
			star2.activate();
		if (starCount > 2)
			star3.activate();
	}

	public void animateResult() {
		if (starCount == 0)
			return;
		star1.activate();
		star1.animate();
	}

	@Override
	public void draw() {
		star1.draw();
		star2.draw();
		star3.draw();
	}

	private IEffectEndListener star1EffectEndListener = new IEffectEndListener() {
		@Override
		public boolean onEffectEnd(Object obj) {
			if (starCount > 1) {
				star2.activate();
				star2.animate();
			}
			return true;
		}
	};

	private IEffectEndListener star2EffectEndListener = new IEffectEndListener() {
		@Override
		public boolean onEffectEnd(Object obj) {
			if (starCount > 2) {
				star3.activate();
				star3.animate();
			}
			return true;
		}
	};

	private static class StarObj extends TexturedGameObject {
		private final BreathEffect effect;

		private boolean isActive;

		StarObj(IEffectEndListener effectListener) {
			super(Textures.star);

			effect = new BreathEffect(this);
			effect.setDuration(0.5f);
			effect.setFinalScale(1.0f);
			effect.setMinFactor(0.8f);
			effect.setMaxFactor(1.2f);
			effect.setLooping(false);
			effect.setListener(effectListener);
		}

		void activate() {
			isActive = true;
		}

		void deactivate() {
			isActive = false;
		}

		void animate() {
			effect.start();
		}

		@Override
		public void draw() {
			if (isActive)
				super.draw();
		}
	}
}

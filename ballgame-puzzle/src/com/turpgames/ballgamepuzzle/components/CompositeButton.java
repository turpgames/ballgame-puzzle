package com.turpgames.ballgamepuzzle.components;

import com.turpgames.framework.v0.ITexture;
import com.turpgames.framework.v0.IView;
import com.turpgames.framework.v0.component.IButtonListener;
import com.turpgames.framework.v0.component.ImageButton;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.TextureDrawer;

public class CompositeButton implements IView {
	private final Text text;
	private final ImageButton button;
	private ITexture defaultTexture;
	private ITexture touchedTexture;

	public CompositeButton() {
		text = new Text();
		button = new ImageButton2();

		text.setAlignment(Text.HAlignCenter, Text.VAlignCenter);

		button.setDefaultColor(Color.white());
		button.setTouchedColor(Color.white());
		button.setScaleOnTouch(false);
	}

	public void setSize(float w, float h) {
		button.setWidth(w);
		button.setHeight(h);
		text.setWidth(w);
		text.setHeight(h);
	}

	public void setLocation(float x, float y) {
		button.getLocation().set(x, y);
		text.setLocation(x, y);
	}

	public void setFontScale(float scale) {
		text.setFontScale(scale);
	}

	public void setTexture(ITexture defaultTexture, ITexture touchedTexture) {
		this.defaultTexture = defaultTexture;
		this.touchedTexture = touchedTexture;
	}

	public void setText(String text) {
		this.text.setText(text);
	}

	public void setListener(IButtonListener listener) {
		this.button.setListener(listener);
	}

	@Override
	public void draw() {
		button.draw();
		text.draw();
	}

	@Override
	public String getId() {
		return toString();
	}

	@Override
	public void activate() {
		button.activate();
	}

	@Override
	public boolean deactivate() {
		button.deactivate();
		return true;
	}

	class ImageButton2 extends ImageButton {
		@Override
		public boolean ignoreViewport() {
			return false;
		}

		@Override
		protected void onDraw() {
			if (isTouched()) {
				TextureDrawer.draw(touchedTexture, this);
			} else {
				TextureDrawer.draw(defaultTexture, this);
			}
		}
	}
}

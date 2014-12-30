package com.turpgames.ballgamepuzzle.view;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.IInputListener;

public interface IScreenView {
	void registerDrawable(IDrawable drawable, int layer);

	void registerInputListener(IInputListener inputlistener);

	void unregisterDrawable(IDrawable drawable);

	void unregisterInputListener(IInputListener inputlistener);
}

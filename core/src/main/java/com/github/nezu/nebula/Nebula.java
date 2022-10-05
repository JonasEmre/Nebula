package com.github.nezu.nebula;

import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Nebula extends Game {
	@Override
	public void create() {
		setScreen(new FirstScreen());
	}
}
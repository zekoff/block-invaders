package net.zekoff.androidgames.framework;

import net.zekoff.androidgames.framework.impl.BlockInvadersGame;

public abstract class Screen {
	protected final BlockInvadersGame game;

	public Screen(BlockInvadersGame game) {
		this.game = game;
	}

	public abstract void update(float deltaTime);

	public abstract void present(float deltaTime);

	public abstract void pause();

	public abstract void resume();

	public abstract void dispose();
}

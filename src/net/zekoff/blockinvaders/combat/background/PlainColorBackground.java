package net.zekoff.blockinvaders.combat.background;

import net.zekoff.androidgames.framework.Graphics;

/**
 * Background to display a single solid color, set by the constructor.
 * 
 * @author Zekoff
 * 
 */
public class PlainColorBackground extends Background {

	int color;

	public PlainColorBackground(int color) {
		this.color = color;
	}

	@Override
	public void update(float deltaTime) {
		// do nothing
	}

	@Override
	public void drawBackground(Graphics g) {
		g.clear(color);
	}

}

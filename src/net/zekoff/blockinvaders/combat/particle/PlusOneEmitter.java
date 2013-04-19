package net.zekoff.blockinvaders.combat.particle;

import net.zekoff.androidgames.framework.Graphics;

public class PlusOneEmitter extends XpGainEmitter {

	public PlusOneEmitter(float x, float y) {
		super(x, y, 0);
	}

	@Override
	public void draw(Graphics g) {
		paint.setColor(color);
		g.drawText("+1", x, y, paint);
	}

}

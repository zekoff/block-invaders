package net.zekoff.blockinvaders.combat.bullet;

import net.zekoff.androidgames.framework.Graphics;

/**
 * Puts an oval-shaped skin on the standard RectBullet.
 * 
 * @author Zekoff
 * 
 */
public class OvalBullet extends RectBullet {

	protected OvalBullet() {
		super();
	}

	public OvalBullet(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	@Override
	public void draw(Graphics g) {
		g.drawOval(x, y, width, height, color);
	}

}

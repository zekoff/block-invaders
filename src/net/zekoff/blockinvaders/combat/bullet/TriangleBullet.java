package net.zekoff.blockinvaders.combat.bullet;

import net.zekoff.androidgames.framework.Graphics;

public class TriangleBullet extends CircleBullet {

	protected TriangleBullet() {
		super();
	}

	public TriangleBullet(float x, float y, float radius) {
		super(x, y, radius);
	}

	public TriangleBullet(float x, float y, float radius, int rotation) {
		this(x, y, radius);
		this.rotation = rotation;
	}

	@Override
	public void draw(Graphics g) {
		g.drawTriangle(x, y, radius, color, rotation, x, y);
	}
}

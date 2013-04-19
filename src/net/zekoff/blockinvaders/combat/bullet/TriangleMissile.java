package net.zekoff.blockinvaders.combat.bullet;

import net.zekoff.androidgames.framework.Graphics;

public class TriangleMissile extends Missile {
	float counter = 0;

	public TriangleMissile() {
		super();
	}

	public TriangleMissile(TargetingType tt, boolean reacquireTarget) {
		super(tt, reacquireTarget);
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		double temp = Math.atan2(yVel, xVel);
		rotation = (int) (temp / Math.PI * 360) + 180;
	}

	@Override
	public void draw(Graphics g) {
		g.drawTriangle(x, y, radius, color, rotation, x, y);
	}

}

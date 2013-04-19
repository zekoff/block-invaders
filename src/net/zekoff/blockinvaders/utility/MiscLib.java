package net.zekoff.blockinvaders.utility;

import net.zekoff.blockinvaders.combat.entity.Entity;

public class MiscLib {
	public static float getDistance(Entity e1, Entity e2) {
		float distance;
		distance = (float) Math.sqrt(Math.pow(e2.getX() - e1.getX(), 2)
				+ Math.pow(e2.getY() - e1.getY(), 2));
		return Math.abs(distance);
	}
}

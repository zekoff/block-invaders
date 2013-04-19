package net.zekoff.blockinvaders.combat.particle;

import net.zekoff.androidgames.framework.Graphics;
import android.graphics.Color;

/**
 * A particle that can be managed by a ParticleEmitter. Any particle can be
 * "morphed" via its type field so that particles can be pooled and reused,
 * cutting down on the number of distinct objects that must be instantiated.
 * 
 * @author Zekoff
 * 
 */
public class Particle {
	public float x;
	public float y;
	public float xVel;
	public float yVel;
	public ParticleType type;
	public float size;
	public int color;
	public int alpha;
	public boolean isExpired;
	public float rotation;
	public float rotationSpeed;

	public enum ParticleType {
		square, circle, line, point
	}

	public Particle() {
		reset();
	}

	public void update(float deltaTime) {
		x += xVel * deltaTime;
		y += yVel * deltaTime;
	}

	/**
	 * Uses the particle's type field to determine how the particle should be
	 * drawn to the screen.
	 * 
	 * @param g
	 *            The graphics context upon which to draw itself.
	 */
	public void draw(Graphics g) {
		if (type == ParticleType.point)
			g.drawPixel(x, y, color);
		else if (type == ParticleType.circle)
			g.drawCircle(x, y, size, color);
		else if (type == ParticleType.square)
			if (rotation == 0)
				g.drawRect(x - size / 2, y - size / 2, size, size, color);
			else
				g.drawRect(x - size / 2, y - size / 2, size, size, color,
						(int) rotation, x, y);
	}

	/**
	 * Reinitializes all fields of the particle so that previous uses of pooled
	 * particles do not interfere with new uses when they are pulled from the
	 * pool.
	 */
	public void reset() {
		x = 0;
		y = 0;
		xVel = 0;
		yVel = 0;
		type = ParticleType.point;
		size = 0;
		color = Color.WHITE;
		alpha = 0xFF;
		isExpired = false;
		rotation = 0;
		rotationSpeed = 0;
	}
}

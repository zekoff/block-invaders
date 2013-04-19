package net.zekoff.blockinvaders.combat.particle;

import java.util.ArrayList;
import java.util.Iterator;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.androidgames.framework.impl.Pool;
import net.zekoff.blockinvaders.combat.Assets;

/**
 * Base class from which real ParticleEmitters of different types can extend.
 * Takes care of boilerplate fields and the draw()/update() methods. Children
 * desiring standard implementations can choose not to override these methods.
 * <p>
 * An emitter managers some number of particles and chains update() and draw()
 * calls to them. When all of its member particles have expired, the emitter
 * flags itself for deletion as well. At least one particle, even a dummy
 * particle must be present or the emitter will immediately flag itself for
 * deletion.
 * 
 * @author Zekoff
 * 
 */
public abstract class ParticleEmitter {
	public ArrayList<Particle> particles = new ArrayList<Particle>();
	public float x = 0;
	public float y = 0;
	public boolean isExpired = false;
	public Particle toUpdate;
	public Pool<Particle> pool;
	Iterator<Particle> iterator;
	ParticleManager manager = Assets.pm;

	public ParticleEmitter() {
		pool = manager.pool;
	}

	/**
	 * Iterate through all particles managed by this ParticleEmitter and update
	 * their position, color, etc.
	 * 
	 * @param deltaTime
	 *            Time in seconds since last update.
	 */
	public void update(float deltaTime) {
		// Move particles to their new locations
		for (int i = 0; i < particles.size(); i++) {
			toUpdate = particles.get(i);
			toUpdate.x += toUpdate.xVel * deltaTime;
			toUpdate.y += toUpdate.yVel * deltaTime;
			toUpdate.rotation += toUpdate.rotationSpeed * deltaTime;
		}
		// Return expired particles to the pool
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isExpired) {
				pool.free(particles.get(i));
			}
		}
		iterator = particles.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().isExpired)
				iterator.remove();
		}
		if (particles.size() == 0)
			isExpired = true;
	}

	/**
	 * Iterate through particles managed by this ParticleEmitter and draw each
	 * of them to the graphics context.
	 * 
	 * @param g
	 *            The graphics context to which particles will be drawn.
	 */
	public void draw(Graphics g) {
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).draw(g);
		}
	}
}

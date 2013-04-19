package net.zekoff.blockinvaders.combat.particle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.androidgames.framework.impl.Pool;
import net.zekoff.androidgames.framework.impl.Pool.PoolObjectFactory;

/**
 * An object that manages all particle emitters and (indirectly) particles for
 * the game, including serving requests for new particle effects.
 * <p>
 * When adding a new particle emitter, this class takes the request, modifies it
 * if necessary to accommodate user preferences, then adds the new emitter if
 * possible. If for some reason a new emitter cannot be added, the particle
 * manager silently ignores the request.
 * 
 * @author Zekoff
 * 
 */
public class ParticleManager {
	public Pool<Particle> pool;
	public ArrayList<ParticleEmitter> emitters = new ArrayList<ParticleEmitter>();
	Random rand = new Random(System.nanoTime());
	Iterator<ParticleEmitter> iterator;
	public float preferencesModifier = 1;

	public enum EmitterType {
		burst, source, chunks
	}

	/**
	 * Sets up a new Pool to hold expired particles for reuse by new emitters.
	 */
	public ParticleManager() {
		PoolObjectFactory<Particle> factory = new PoolObjectFactory<Particle>() {
			public Particle createObject() {
				return new Particle();
			}
		};
		pool = new Pool<Particle>(factory, 1000);
	}

	/**
	 * @param x
	 *            A float signifying the X location of the new emitter.
	 * @param y
	 *            A float signifying the Y location of the new emitter.
	 * @param numParticles
	 *            The number of particles requested as part of the effect.
	 * @param color
	 *            An integer signifying the color the particles in the new
	 *            emitter should be.
	 */
	public void addBurstEmitter(float x, float y, int numParticles, int color) {
		emitters.add(new ParticleBurst(x, y,
				(int) (numParticles * preferencesModifier), color));
	}

	/**
	 * @param x
	 *            A float for the x location to put the new emitter.
	 * @param y
	 *            A float for the y location to put the new emitter.
	 * @param numParticles
	 *            The number of particles to use.
	 * @param color
	 *            An integer of the color of particles to use.
	 * @param size
	 *            How big each particle should be.
	 */
	public void addChunkEmitter(float x, float y, int numParticles, int color,
			int size) {
		emitters.add(new ParticleChunks(x, y,
				(int) (numParticles * preferencesModifier), color, size));
	}

	public void addXpEmitter(float x, float y, int xp) {
		emitters.add(new XpGainEmitter(x, y, xp));
	}

	public void addPlusOneEmitter(float x, float y) {
		emitters.add(new PlusOneEmitter(x, y));
	}

	public void addExplosionEmitter(float x, float y, float targetRadius) {
		emitters.add(new ExplosionEmitter(x, y, targetRadius));
	}

	/**
	 * Iterates through all ParticleEmitters being managed by the
	 * ParticleManager and chains the update() call to them for handling. If any
	 * ParticleEmitters have expired, removes them from the list of emitters.
	 * <p>
	 * Note that emitters are not pooled like particles are, because each
	 * emitter specifies different behavior of its component particles.
	 * Specifying all emitters generically enough that they are interchangable
	 * is not worth the minimal runtime savings.
	 * 
	 * @param deltaTime
	 *            Time in seconds since last update.
	 */
	public void update(float deltaTime) {
		for (int i = 0; i < emitters.size(); i++) {
			emitters.get(i).update(deltaTime);
		}
		iterator = emitters.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().isExpired) {
				iterator.remove();
			}
		}
	}

	/**
	 * Cycles through all emitters being managed by this object, chaining the
	 * draw() call down to them.
	 * 
	 * @param g
	 *            The graphics context upon which to draw.
	 */
	public void draw(Graphics g) {
		for (int i = 0; i < emitters.size(); i++) {
			emitters.get(i).draw(g);
		}
	}

	public void reinit() {
		emitters = new ArrayList<ParticleEmitter>();
	}
}

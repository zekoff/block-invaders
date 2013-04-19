package net.zekoff.blockinvaders.combat.particle;

/**
 * An emitter type implementing a single "burst" of particles. All particles
 * managed by this burst fade at the same configurable rate. The burst is
 * localized at some point in space, issues a single burst of particles, then
 * flags itself for destruction once all particles have expired.
 * 
 * @author Zekoff
 * 
 */

public class ParticleBurst extends ParticleEmitter {
	public static final float maxSpeed = 40;
	float decayTime = 1.2f;
	float decayTick = 0xFF / decayTime;

	public ParticleBurst(float x, float y, int numParticles, int color) {
		super();
		for (int i = 0; i < numParticles; i++) {
			toUpdate = manager.pool.newObject();
			toUpdate.reset();
			toUpdate.color = color;
			toUpdate.x = x;
			toUpdate.y = y;
			toUpdate.xVel = maxSpeed * (manager.rand.nextFloat() - 0.5f);
			toUpdate.yVel = maxSpeed * (manager.rand.nextFloat() - 0.5f);
			particles.add(toUpdate);
		}
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		for (int i = 0; i < particles.size(); i++) {
			// now, update alpha
			toUpdate = particles.get(i);
			float alpha = (toUpdate.color & 0xFF000000) >>> 24;
			alpha -= decayTick * deltaTime;
			if (alpha < 0) {
				alpha = 0;
				toUpdate.isExpired = true;
			}
			toUpdate.color = (toUpdate.color & 0x00FFFFFF)
					+ ((int) alpha << 24);
		}
	}
}

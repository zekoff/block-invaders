package net.zekoff.blockinvaders.combat.particle;

import net.zekoff.blockinvaders.combat.particle.Particle.ParticleType;

/**
 * An emitter type similar to the standard burst-type, but with larger,
 * rectangular particles, rather than point-based particles.
 * 
 * @author Zekoff
 * 
 */
public class ParticleChunks extends ParticleEmitter {
	public static final float maxSpeed = 60;
	float decayTime = 3f;
	float decayTick = 0xFF / decayTime;

	public ParticleChunks(float x, float y, int numParticles, int color,
			int size) {
		super();
		for (int i = 0; i < numParticles; i++) {
			toUpdate = manager.pool.newObject();
			toUpdate.reset();
			toUpdate.color = color;
			toUpdate.size = size;
			toUpdate.type = ParticleType.square;
			toUpdate.x = x;
			toUpdate.y = y;
			toUpdate.xVel = maxSpeed * (manager.rand.nextFloat() - 0.5f);
			toUpdate.yVel = maxSpeed * (manager.rand.nextFloat() - 0.5f);
			toUpdate.rotation = manager.rand.nextFloat() * 360;
			toUpdate.rotationSpeed = (manager.rand.nextFloat() * 360) - 180;
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

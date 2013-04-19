package net.zekoff.blockinvaders.combat.endless;

import net.zekoff.blockinvaders.combat.level.EndlessLevel;

/**
 * Controls the spawning of enemies for a sector in Endless Mode. This base
 * class serves as the interface that its subclasses use.
 * 
 * @author Zekoff
 * 
 */
public abstract class EnemyWave {

	EndlessLevel level;

	public EnemyWave(EndlessLevel level) {
		this.level = level;
	}

	public abstract void update(float deltaTime);

	public abstract String getDescription();

}

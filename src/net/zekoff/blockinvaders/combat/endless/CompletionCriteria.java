package net.zekoff.blockinvaders.combat.endless;

import net.zekoff.blockinvaders.combat.level.EndlessLevel;

/**
 * Checks for whether the sector has been completed. Can check kill count, time
 * expired, etc.
 * 
 * @author Zekoff
 * 
 */
public abstract class CompletionCriteria {
	EndlessLevel level;

	public CompletionCriteria(EndlessLevel level) {
		this.level = level;
	}

	public abstract void update(float deltaTime);
}
package net.zekoff.blockinvaders.combat.endless;

import net.zekoff.blockinvaders.combat.level.EndlessLevel;

public abstract class SectorQuest {
	EndlessLevel level;

	public SectorQuest(EndlessLevel level) {
		this.level = level;
	}

	public abstract void update(float deltaTime);

}

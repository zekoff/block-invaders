package net.zekoff.blockinvaders.combat.endless;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.level.EndlessLevel;

public class NoEnemies extends CompletionCriteria {

	public NoEnemies(EndlessLevel level) {
		super(level);
	}

	@Override
	public void update(float deltaTime) {
		if (Assets.em.enemies.size() == 0)
			level.sectorComplete();
	}

}

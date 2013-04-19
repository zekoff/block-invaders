package net.zekoff.blockinvaders.combat.endless;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.enemy.Enemy;
import net.zekoff.blockinvaders.combat.enemy.YWing;
import net.zekoff.blockinvaders.combat.level.EndlessLevel;
import net.zekoff.blockinvaders.combat.movement.MoveIfHit;
import net.zekoff.blockinvaders.combat.movement.SeekPlayerHorizontally;
import net.zekoff.blockinvaders.combat.movement.SmoothMoveTo;
import net.zekoff.blockinvaders.combat.movement.SmoothRandomMovement;
import net.zekoff.blockinvaders.combat.movement.SeekPlayerHorizontally.SeekAggression;

public class SpawnYWings extends EnemyWave {

	boolean spawned = false;

	public SpawnYWings(EndlessLevel level) {
		super(level);
	}

	@Override
	public void update(float deltaTime) {
		if (!spawned) {
			spawned = true;
			int numToSpawn = Assets.rand.nextInt(2) + 1 + (int) level.sector
					/ 2;
			for (int i = 0; i < numToSpawn; i++) {
				Enemy e = new YWing(Assets.pilot.level);
				e.x = Assets.rand.nextFloat() * 400 - 40;
				e.y = -30;
				e.movementOrders.add(new SmoothMoveTo(
						Assets.rand.nextFloat() * 280 + 20, Assets.rand
								.nextFloat() * 200 + 20));
				int movementType = Assets.rand.nextInt(3);
				switch (movementType) {
				case 0:
					e.movementOrders.add(new SmoothRandomMovement(20));
					break;
				case 1:
					e.movementOrders.add(new SeekPlayerHorizontally(
							SeekAggression.normal));
					break;
				case 2:
					e.movementOrders.add(new MoveIfHit());
					break;
				}
				Assets.em.enemies.add(e);
			}
		}
	}

	@Override
	public String getDescription() {
		return "The next sector features nimble opponents who will fire in short bursts.";
	}

}

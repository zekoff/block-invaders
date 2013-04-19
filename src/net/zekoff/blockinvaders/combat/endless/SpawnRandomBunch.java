package net.zekoff.blockinvaders.combat.endless;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.enemy.Enemy;
import net.zekoff.blockinvaders.combat.enemy.Fighter;
import net.zekoff.blockinvaders.combat.enemy.SlantTied;
import net.zekoff.blockinvaders.combat.enemy.Spinner;
import net.zekoff.blockinvaders.combat.enemy.Swooper;
import net.zekoff.blockinvaders.combat.enemy.TWing;
import net.zekoff.blockinvaders.combat.enemy.TiedFighter;
import net.zekoff.blockinvaders.combat.enemy.WingHouse;
import net.zekoff.blockinvaders.combat.enemy.YWing;
import net.zekoff.blockinvaders.combat.level.EndlessLevel;
import net.zekoff.blockinvaders.combat.movement.MoveIfHit;
import net.zekoff.blockinvaders.combat.movement.SeekPlayerHorizontally;
import net.zekoff.blockinvaders.combat.movement.SeekPlayerHorizontally.SeekAggression;
import net.zekoff.blockinvaders.combat.movement.SmoothMoveTo;
import net.zekoff.blockinvaders.combat.movement.SmoothRandomMovement;

public class SpawnRandomBunch extends EnemyWave {

	boolean spawned = false;
	int waves = 4;

	public SpawnRandomBunch(EndlessLevel level) {
		super(level);
	}

	@Override
	public void update(float deltaTime) {
		if (!spawned && waves-- > 0) {
			spawned = true;
			int numToSpawn = Assets.rand.nextInt(4) + 1 + (int) level.sector
					/ 2;
			for (int i = 0; i < numToSpawn; i++) {
				int enemyType = Assets.rand.nextInt(8);
				Enemy e = null;
				switch (enemyType) {
				case 0:
					e = new TiedFighter();
					break;
				case 1:
					e = new TWing();
					break;
				case 2:
					e = new WingHouse();
					break;
				case 3:
					e = new SlantTied();
					break;
				case 4:
					e = new YWing();
					break;
				case 5:
					e = new Swooper();
					break;
				case 6:
					e = new Fighter();
					break;
				case 7:
					e = new Spinner();
					break;
				default:
					break;
				}
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

		// TODO this is an awkward hack. just make it so the wave reports in to
		// the level when it's over
		if (Assets.em.enemies.size() < 2)
			spawned = false;
	}

	@Override
	public String getDescription() {
		return "The next sector features a variety of random opponents.";
	}

}

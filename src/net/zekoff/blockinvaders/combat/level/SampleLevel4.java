package net.zekoff.blockinvaders.combat.level;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.enemy.Enemy;
import net.zekoff.blockinvaders.combat.enemy.Fighter;
import net.zekoff.blockinvaders.combat.enemy.JointFighter;
import net.zekoff.blockinvaders.combat.enemy.SampleBoss;
import net.zekoff.blockinvaders.combat.enemy.Swooper;
import net.zekoff.blockinvaders.combat.enemy.TiedFighter;
import net.zekoff.blockinvaders.combat.enemy.WingHouse;
import net.zekoff.blockinvaders.combat.movement.MoveTo;
import net.zekoff.blockinvaders.combat.movement.RandomMovement;
import net.zekoff.blockinvaders.combat.movement.SineWave;
import net.zekoff.blockinvaders.combat.movement.TimerCondition;

public class SampleLevel4 extends Level {
	public int waveNumber = 0;

	@Override
	public void update(float deltaTime) {
		switch (waveNumber) {
		case 0:
			Assets.hud.queueMessage("ALL SYSTEMS ONLINE");
			Assets.hud
					.queueMessage("Super-long message to test the ellipsizing code of the message display window");

			Enemy e = new Fighter(5);
			e.x = -20;
			e.y = 50;
			e.movementOrders.add(new SineWave(160, 100, 50));
			e.movementOrders.add(new MoveTo(160, 200));
			Assets.em.enemies.add(e);

			e = new TiedFighter(2);
			e.x = 340;
			e.y = 150;
			e.movementOrders.add(new MoveTo(160, 100));
			e.movementOrders
					.add(new RandomMovement(100, new TimerCondition(10)));
			e.movementOrders.add(new MoveTo(160, -70));
			Assets.em.enemies.add(e);

			e = new Swooper(3);
			e.x = 160;
			e.y = -40;
			e.movementOrders.add(new MoveTo(160, 50));
			Assets.em.enemies.add(e);

			waveNumber++;
			break;
		case 1:
			if (Assets.em.enemies.size() == 0) {
				Assets.hud.queueHeader("INC DUDES");

				e = new TiedFighter(5);
				e.x = -20;
				e.y = 50;
				e.movementOrders.add(new SineWave(160, 100, 50));
				e.movementOrders.add(new MoveTo(160, 200));
				Assets.em.enemies.add(e);

				e = new Fighter(10);
				e.x = -30;
				e.y = 100;
				Assets.em.enemies.add(e);
				waveNumber++;

				e = new WingHouse(1);
				e.x = 330;
				e.y = 140;
				Assets.em.enemies.add(e);

				e = new JointFighter(1);
				e.x = 190;
				e.y = -30;
				e.movementOrders.add(new MoveTo(190, 70));
				Assets.em.enemies.add(e);
			}
			break;
		case 2:
			if (Assets.em.enemies.size() == 0) {

				e = new TiedFighter(5);
				e.x = -20;
				e.y = 50;
				e.movementOrders.add(new SineWave(160, 100, 50));
				e.movementOrders.add(new MoveTo(160, 200));
				Assets.em.enemies.add(e);

				e = new Fighter(10);
				e.x = -30;
				e.y = 100;
				Assets.em.enemies.add(e);
				waveNumber++;

				e = new WingHouse(1);
				e.x = 330;
				e.y = 140;
				Assets.em.enemies.add(e);

				e = new JointFighter(1);
				e.x = 190;
				e.y = -30;
				e.movementOrders.add(new MoveTo(190, 70));
				Assets.em.enemies.add(e);

				e = new Swooper(3);
				e.x = 160;
				e.y = -40;
				e.movementOrders.add(new MoveTo(160, 50));
				Assets.em.enemies.add(e);

				e = new Swooper(3);
				e.x = -40;
				e.y = 290;
				e.movementOrders.add(new MoveTo(160, 50));
				Assets.em.enemies.add(e);

			}
			break;
		case 3:
			if (Assets.em.enemies.size() == 0) {
				Assets.hud.queueHeader("INC BOSS");

				SampleBoss boss = new SampleBoss();
				boss.x = 160;
				boss.y = -40;
				boss.hp = 1700;
				Assets.em.enemies.add(boss);
			}
			break;
		}
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}

}

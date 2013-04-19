package net.zekoff.blockinvaders.combat.level;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.enemy.DoubleTurret;
import net.zekoff.blockinvaders.combat.enemy.Enemy;
import net.zekoff.blockinvaders.combat.enemy.Spinner;
import net.zekoff.blockinvaders.combat.enemy.TWing;
import net.zekoff.blockinvaders.combat.movement.MoveTo;

public class SampleLevel5 extends Level {
	int waveNumber = 0;
	float counter = 0;

	@Override
	public void update(float deltaTime) {
		Enemy e = null;
		switch (waveNumber) {
		case 0:
			Assets.hud.queueText("Testing the text-display system.");
			Assets.hud
					.queueText("At the top of the screen is your ship's Message Display Window. You'll get reports on critical ship systems and other events in this window.");

			waveNumber++;

			e = new TWing();
			e.x = 340;
			e.y = 340;
			e.movementOrders.add(new MoveTo(30, 50));
			Assets.em.enemies.add(e);

			e = new Spinner();
			e.x = 160;
			e.y = 160;
			Assets.em.enemies.add(e);

			e = new DoubleTurret();
			e.x = 70;
			e.y = 240;
			Assets.em.enemies.add(e);

			break;
		}

		if (Assets.em.enemies.size() == 0) {
		}

	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}

}

package net.zekoff.blockinvaders.combat.level;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.AreaEffect;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.enemy.Enemy;
import net.zekoff.blockinvaders.combat.enemy.Greenie;

public class SampleLevel3 extends Level {
	int counter = 0;

	public SampleLevel3() {
		for (int i = 0; i < 5; i++) {
			Enemy e = new Greenie();
			e.x = Assets.rand.nextFloat() * 320;
			e.y = Assets.rand.nextFloat() * 250;
			Assets.em.enemies.add(e);
		}
	}

	@Override
	public void update(float deltaTime) {
		if (Assets.rand.nextInt(30) == 0) {
			float randX = Assets.rand.nextFloat() * 300 + 10;
			float randY = Assets.rand.nextFloat() * 300 + 10;
			float randRadius = Assets.rand.nextInt(40) + 1;
			Bullet b = new AreaEffect(randX, randY, randRadius);
			Assets.bm.playerBullets.add(b);
		}

		if (Assets.em.enemies.size() < 35) {
			Enemy e = new Greenie();
			e.x = Assets.rand.nextFloat() * 320;
			e.y = Assets.rand.nextFloat() * 250;
			Assets.em.enemies.add(e);
			counter++;
		}
		if (counter > 25) {
			counter = 0;
			// Intent popup = new Intent(Assets.game, PopupDialog.class);
			// Assets.game.startActivity(popup);
		}
	}

	@Override
	public void draw(Graphics g) {

	}

}

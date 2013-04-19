package net.zekoff.blockinvaders.combat.level;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.AreaEffect;
import net.zekoff.blockinvaders.combat.bullet.Bullet;

public class AreaEffectTest extends Level {

	@Override
	public void update(float deltaTime) {
		if (Assets.rand.nextInt(30) == 0) {
			float randX = Assets.rand.nextFloat() * 300 + 10;
			float randY = Assets.rand.nextFloat() * 300 + 10;
			float randRadius = Assets.rand.nextInt(40) + 1;
			Bullet b = new AreaEffect(randX, randY, randRadius);
			Assets.bm.playerBullets.add(b);
		}
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}

}

package net.zekoff.blockinvaders.combat.firing;

import android.graphics.Color;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.bullet.CircleBullet;
import net.zekoff.blockinvaders.combat.enemy.Enemy;

public class FixedStarBurst extends FiringOrders {

	public FixedStarBurst() {
		cooldown = 3;
	}

	public FixedStarBurst(Enemy e) {
		super(e);
		cooldown = 3;
	}

	@Override
	void fire(Enemy self) {
		float maxSpeed = 300f;
		int numBullets = 20;
		for (int i = 0; i < numBullets; i++) {
			Bullet b = new CircleBullet(self.x, self.y, 6);
			b.color = Color.RED;
			double angle = ((double) i) / (float) numBullets * (2.0f * Math.PI);
			b.xVel = (float) Math.cos(angle) * maxSpeed;
			b.yVel = (float) Math.sin(angle) * maxSpeed;
			b.power = self.power * cooldown / 5;
			Assets.bm.enemyBullets.add(b);
		}

	}

}

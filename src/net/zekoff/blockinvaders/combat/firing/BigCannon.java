package net.zekoff.blockinvaders.combat.firing;

import android.graphics.Color;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.bullet.CircleBullet;
import net.zekoff.blockinvaders.combat.enemy.Enemy;

public class BigCannon extends FiringOrders {
	private float speed = 120;

	public BigCannon() {
		cooldown = 4;
	}

	public BigCannon(Enemy self) {
		super(self);
		cooldown = 4;
	}

	@Override
	void fire(Enemy self) {
		Bullet b = new CircleBullet(self.x, self.y, 14);
		b.yVel = speed;
		b.xVel = 30 * (Assets.rand.nextFloat() - 0.5f);
		b.color = Color.RED;
		b.power = self.power * cooldown;
		Assets.bm.enemyBullets.add(b);
	}

}

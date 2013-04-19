package net.zekoff.blockinvaders.combat.firing;

import android.graphics.Color;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.bullet.CircleBullet;
import net.zekoff.blockinvaders.combat.enemy.Enemy;

public class SimpleCannon extends FiringOrders {

	public SimpleCannon() {
		cooldown = 3;
	}

	public SimpleCannon(Enemy e) {
		super(e);
	}

	@Override
	void fire(Enemy self) {
		Bullet b = new CircleBullet(self.x, self.y, 9);
		b.color = Color.RED;
		b.power = self.power * cooldown;
		b.yVel = 150;
		Assets.bm.enemyBullets.add(b);
	}

}

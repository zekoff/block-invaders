package net.zekoff.blockinvaders.combat.firing;

import android.graphics.Color;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.bullet.OvalBullet;
import net.zekoff.blockinvaders.combat.enemy.Enemy;

public class SpreadShot extends FiringOrders {

	public SpreadShot() {
		super();
		cooldown = 1.5f;
	}

	public SpreadShot(Enemy e) {
		super(e);
		cooldown = 1.5f;
	}

	@Override
	void fire(Enemy self) {
		for (int i = -1; i < 2; i++) {
			Bullet b = new OvalBullet(self.x, self.y, 5, 10);
			b.color = Color.RED;
			b.power = self.power / 3 * cooldown;
			b.yVel = 300;
			b.xVel = 75 * i;
			Assets.bm.enemyBullets.add(b);
		}
	}

}

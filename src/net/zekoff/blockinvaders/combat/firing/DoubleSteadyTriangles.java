package net.zekoff.blockinvaders.combat.firing;

import android.graphics.Color;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.bullet.TriangleBullet;
import net.zekoff.blockinvaders.combat.enemy.Enemy;

public class DoubleSteadyTriangles extends FiringOrders {

	public DoubleSteadyTriangles() {
		cooldown = 1;
	}

	public DoubleSteadyTriangles(Enemy e) {
		super(e);
	}

	@Override
	void fire(Enemy self) {
		Bullet b = new TriangleBullet(self.x + 22, self.y, 4, 180);
		b.yVel = 200;
		b.power = self.power / 2 * cooldown;
		b.color = Color.RED;
		Assets.bm.enemyBullets.add(b);
		b = new TriangleBullet(self.x - 22, self.y, 4, 180);
		b.yVel = 200;
		b.power = self.power / 2 * cooldown;
		b.color = Color.RED;
		Assets.bm.enemyBullets.add(b);
	}

}

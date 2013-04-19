package net.zekoff.blockinvaders.combat.firing;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.bullet.OvalBullet;
import net.zekoff.blockinvaders.combat.enemy.Enemy;
import android.graphics.Color;

public class SimpleSteadySingleBullet extends FiringOrders {

	public SimpleSteadySingleBullet() {
		cooldown = 1.5f;
	}

	public SimpleSteadySingleBullet(Enemy e) {
		super(e);
	}

	@Override
	void fire(Enemy self) {
		Bullet b = new OvalBullet(self.x, self.y, 4, 7);
		b.yVel = 200;
		b.color = Color.RED;
		b.power = self.power * cooldown;
		Assets.bm.enemyBullets.add(b);
	}

}

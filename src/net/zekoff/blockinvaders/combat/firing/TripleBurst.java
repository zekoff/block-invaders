package net.zekoff.blockinvaders.combat.firing;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.bullet.OvalBullet;
import net.zekoff.blockinvaders.combat.enemy.Enemy;
import android.graphics.Color;

public class TripleBurst extends FiringOrders {
	public int counter = 0;

	public TripleBurst() {
		cooldown = 2.5f;
	}

	public TripleBurst(Enemy e) {
		super(e);
	}

	@Override
	public void update(Enemy self, float deltaTime) {
		currentCooldown += deltaTime;
		if (currentCooldown > cooldown && counter == 0) {
			fire(self);
			counter++;
		}
		if (currentCooldown > cooldown + cooldown * .1 && counter == 1) {
			fire(self);
			counter++;
		}
		if (currentCooldown > cooldown + cooldown * .2 && counter == 2) {
			counter = 0;
			currentCooldown = 0;
			fire(self);
		}
	}

	@Override
	void fire(Enemy self) {
		Bullet b = new OvalBullet(self.x, self.y, 5, 8);
		b.yVel = 180;
		b.color = Color.RED;
		b.power = self.power * cooldown / 3;
		Assets.bm.enemyBullets.add(b);
	}

}

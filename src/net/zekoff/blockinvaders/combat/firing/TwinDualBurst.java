package net.zekoff.blockinvaders.combat.firing;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.bullet.OvalBullet;
import net.zekoff.blockinvaders.combat.enemy.Enemy;
import android.graphics.Color;

public class TwinDualBurst extends FiringOrders {
	float currentCooldownTwo = 0;

	public TwinDualBurst() {

	}

	public TwinDualBurst(Enemy e) {
		super(e);
	}

	@Override
	public void update(Enemy self, float deltaTime) {
		currentCooldown += deltaTime;
		currentCooldownTwo += deltaTime;
		if (currentCooldown > cooldown) {
			currentCooldown = 0;
			currentCooldownTwo = -0.5f;
			fire(self);
		}
		if (currentCooldownTwo >= 0) {
			currentCooldownTwo = -100f;
			fire(self);
		}
	}

	@Override
	void fire(Enemy self) {
		Bullet b = null;

		b = new OvalBullet(self.x - 12, self.y, 4, 7);
		b.yVel = 200;
		b.power = self.power / 2 * cooldown / 2;
		b.color = Color.RED;
		Assets.bm.enemyBullets.add(b);

		b = new OvalBullet(self.x + 12, self.y, 4, 7);
		b.yVel = 200;
		b.power = self.power / 2 * cooldown / 2;
		b.color = Color.RED;
		Assets.bm.enemyBullets.add(b);
	}

}

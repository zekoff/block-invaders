package net.zekoff.blockinvaders.combat.firing;

import android.graphics.Color;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.bullet.CircleBullet;
import net.zekoff.blockinvaders.combat.enemy.Enemy;

public class AllComponentsFire extends FiringOrders {

	public AllComponentsFire() {
		cooldown = 3;
	}

	public AllComponentsFire(Enemy e) {
		super(e);
	}

	@Override
	void fire(Enemy self) {
		for (int i = 0; i < self.components.size(); i++) {
			float cx = self.components.get(i).x;
			float cy = self.components.get(i).y;
			Bullet b = new CircleBullet(self.x + cx, self.y + cy, 10);
			b.power = self.power * cooldown / self.components.size();
			b.yVel = 120;
			b.color = Color.RED;
			Assets.bm.enemyBullets.add(b);
		}
	}

}

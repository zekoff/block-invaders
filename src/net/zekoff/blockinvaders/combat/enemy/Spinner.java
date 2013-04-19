package net.zekoff.blockinvaders.combat.enemy;

import net.zekoff.blockinvaders.combat.entity.CircularComponent;
import net.zekoff.blockinvaders.combat.entity.RectangularComponent;
import net.zekoff.blockinvaders.combat.firing.BigCannon;

public class Spinner extends Enemy {

	public Spinner() {
		this(1);
	}

	public Spinner(int level) {
		super(level);
		speed = 120;
		acceleration = 220;
		xpModifier = 1.6f;
		startColor = 0xFFf3f60d;
		color = startColor;
		maxHp *= 3;
		hp = maxHp;

		components.add(new RectangularComponent(0, 0, 40, 40, 45));
		components.add(new RectangularComponent(0, 0, 70, 20, 45));
		components.add(new RectangularComponent(0, 0, 70, 20, -45));
		components.add(new CircularComponent(0, 30, 10));

		firingOrders = new BigCannon(this);
		firingOrders.randomizeCooldown();
	}

}

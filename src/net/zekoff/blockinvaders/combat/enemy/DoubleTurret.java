package net.zekoff.blockinvaders.combat.enemy;

import net.zekoff.blockinvaders.combat.entity.CircularComponent;
import net.zekoff.blockinvaders.combat.entity.RectangularComponent;

public class DoubleTurret extends Enemy {

	public DoubleTurret() {
		this(1);
	}

	public DoubleTurret(int level) {
		super(level);
		speed = 120;
		acceleration = 220;
		xpModifier = 1.6f;
		startColor = 0xFFff5f97;
		color = startColor;

		components.add(new CircularComponent(0, -10, 15));
		components.add(new RectangularComponent(-15, 11, 10, 30));
		components.add(new RectangularComponent(15, 11, 10, 30));
	}

}

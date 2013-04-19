package net.zekoff.blockinvaders.combat.enemy;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.entity.CircularComponent;
import net.zekoff.blockinvaders.combat.entity.RectangularComponent;
import net.zekoff.blockinvaders.combat.firing.SimpleSteadySingleBullet;

/**
 * Weak enemy type made for mission 2. 1/3 HP, 1/2 power.
 * 
 * @author Zekoff
 * 
 */
public class SlantTied extends Enemy {
	public static final float WEAPON_SPEED = 2.0f;
	public float cooldown = 0f;

	public SlantTied(int level) {
		super(level, EnemyType.weak);
		speed = 120;
		acceleration = 240;
		startColor = 0xFF2B91E3;
		color = startColor;

		components.add(new CircularComponent(0, 0, 9));
		components.add(new RectangularComponent(-12, 0, 8, 25, -20));
		components.add(new RectangularComponent(12, 0, 8, 25, 20));

		firingOrders = new SimpleSteadySingleBullet(this);
		firingOrders.randomizeCooldown();
	}

	public SlantTied() {
		this(Assets.pilot.level);
	}

}

package net.zekoff.blockinvaders.combat.enemy;

import net.zekoff.blockinvaders.combat.entity.CircularComponent;
import net.zekoff.blockinvaders.combat.entity.RectangularComponent;
import net.zekoff.blockinvaders.combat.entity.TriangularComponent;
import net.zekoff.blockinvaders.combat.firing.FixedStarBurst;

/**
 * Bug: this enemy is not recovering to the correct color after being damaged.
 * 
 * @author Zekoff
 * 
 */
public class TWing extends Enemy {

	public TWing() {
		this(1);
	}

	public TWing(int level) {
		super(level, EnemyType.shielded);
		speed = 170;
		acceleration = 300;
		xpModifier = 1.3f;
		startColor = 0xFF8119ec;
		color = startColor;

		components.add(new RectangularComponent(0, 0, 15, 15));
		components.add(new TriangularComponent(11, 0, 7, 90));
		components.add(new TriangularComponent(-11, 0, 7, -90));
		components.add(new TriangularComponent(0, 11, 7, 180));
		components.add(new CircularComponent(-19, 0, 6));
		components.add(new CircularComponent(19, 0, 6));
		
		firingOrders = new FixedStarBurst(this);
		firingOrders.randomizeCooldown();

	}

}

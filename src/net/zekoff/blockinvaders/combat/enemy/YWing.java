package net.zekoff.blockinvaders.combat.enemy;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.entity.RectangularComponent;
import net.zekoff.blockinvaders.combat.firing.TripleBurst;
import android.graphics.Color;

public class YWing extends Enemy {
	public float cooldown = 0;
	public int counter = 0;
	public static final float W_SPEED = 2.5f;

	public YWing() {
		this(Assets.pilot.level);
	}

	public YWing(int level) {
		super(level);
		speed = 140;
		acceleration = 280;
		xpModifier = 1.0f;
		startColor = Color.YELLOW;
		color = startColor;

		components.add(new RectangularComponent(0, 0, 12, 15));
		components.add(new RectangularComponent(0, 7, 16, 16, 45));
		components.add(new RectangularComponent(-4, -11, 5, 9, -30));
		components.add(new RectangularComponent(4, -11, 5, 9, 30));

		firingOrders = new TripleBurst(this);
		firingOrders.randomizeCooldown();
	}

}

package net.zekoff.blockinvaders.combat.enemy;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.entity.CircularComponent;
import net.zekoff.blockinvaders.combat.entity.RectangularComponent;
import net.zekoff.blockinvaders.combat.firing.TwinDualBurst;
import android.graphics.Color;
import android.graphics.Paint;

public class TiedFighter extends Enemy {
	public static final float WEAPON_SPEED = 3.0f;
	public float cooldown = 0f;
	public float cooldownTwo = 0f;

	public TiedFighter() {
		this(1);
	}

	public TiedFighter(int level) {
		super(level, EnemyType.weak);
		speed = 100;
		acceleration = 240;
		xpModifier = 1.2f;
		startColor = 0xFF2B91E3;
		color = startColor;

		components.add(new CircularComponent(0, 0, 9));
		components.add(new RectangularComponent(-12, 0, 8, 25));
		components.add(new RectangularComponent(12, 0, 8, 25));

		firingOrders = new TwinDualBurst(this);
		firingOrders.randomizeCooldown();
	}

	@Override
	public void draw(Graphics g) {
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(2);
		for (int i = 0; i < components.size(); i++) {
			if ((int) rotation == 0)
				components.get(i).draw(g, x, y, paint);
			else
				components.get(i).draw(g, x, y, paint, (int) rotation);
		}
		super.draw(g);
	}
}

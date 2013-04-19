package net.zekoff.blockinvaders.combat.enemy;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.entity.CircularComponent;
import net.zekoff.blockinvaders.combat.entity.RectangularComponent;
import net.zekoff.blockinvaders.combat.entity.TriangularComponent;
import net.zekoff.blockinvaders.combat.firing.TwinDualBurst;
import android.graphics.Color;
import android.graphics.Paint;

public class JointFighter extends Enemy {
	public static final float WEAPON_SPEED = 3.0f;
	public float cooldown = 0f;
	public float cooldownTwo = 0f;

	public JointFighter() {
		this(1);
	}

	public JointFighter(int level) {
		super(level, EnemyType.tough);
		speed = 150;
		acceleration = 240;
		xpModifier = 1.5f;
		startColor = 0xFFC24038;
		color = startColor;

		components.add(new RectangularComponent(0, 0, 45, 15));
		components.add(new CircularComponent(-13, -10, 7));
		components.add(new CircularComponent(13, -10, 7));
		components.add(new TriangularComponent(-9, 6, 12, 180));
		components.add(new TriangularComponent(9, 6, 12, 180));

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

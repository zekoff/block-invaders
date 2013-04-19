package net.zekoff.blockinvaders.combat.enemy;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.entity.CircularComponent;
import net.zekoff.blockinvaders.combat.entity.RectangularComponent;
import net.zekoff.blockinvaders.combat.entity.TriangularComponent;
import net.zekoff.blockinvaders.combat.firing.DumbHomingShot;
import net.zekoff.blockinvaders.combat.movement.LeftRight;
import android.graphics.Color;
import android.graphics.Paint;

public class Swooper extends Enemy {

	public Swooper() {
		this(1);
	}

	public Swooper(int level) {
		super(level);

		speed = 150;
		acceleration = 250;
		xpModifier = 1f;
		startColor = 0xFFBCC24B;
		color = startColor;

		components.add(new CircularComponent(0, -8, 10));
		components.add(new RectangularComponent(0, 0, 30, 10));
		components.add(new TriangularComponent(0, 5, 9, 180));

		firingOrders = new DumbHomingShot(this);
		firingOrders.randomizeCooldown();
	}

	@Override
	public void defaultMovement(float deltaTime) {
		movementOrders.add(new LeftRight(100, 220));
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

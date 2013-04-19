package net.zekoff.blockinvaders.combat.enemy;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.entity.CircularComponent;
import net.zekoff.blockinvaders.combat.entity.RectangularComponent;
import net.zekoff.blockinvaders.combat.entity.TriangularComponent;
import net.zekoff.blockinvaders.combat.firing.SpreadShot;
import android.graphics.Color;
import android.graphics.Paint;

public class WingHouse extends Enemy {
	public static final float WEAPON_SPEED = 3.0f;
	public float cooldown = 0f;

	public WingHouse() {
		this(1);
	}

	public WingHouse(int level) {
		super(level);
		speed = 150;
		acceleration = 240;
		xpModifier = 1.5f;
		startColor = 0xFFFF690F;
		color = startColor;

		components.add(new TriangularComponent(0, 8, 10, 180));
		components.add(new RectangularComponent(-10, -5, 7, 15));
		components.add(new RectangularComponent(10, -5, 7, 15));
		components.add(new CircularComponent(0, 0, 10));
		components.add(new RectangularComponent(-17, 3, 9, 3));
		components.add(new RectangularComponent(17, 3, 9, 3));

		firingOrders = new SpreadShot(this);
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

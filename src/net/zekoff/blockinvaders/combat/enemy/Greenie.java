package net.zekoff.blockinvaders.combat.enemy;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.entity.CircularComponent;
import net.zekoff.blockinvaders.combat.entity.RectangularComponent;
import net.zekoff.blockinvaders.combat.entity.TriangularComponent;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Probably the most basic enemy type. A green square that fires a small bullet
 * at the player at regular intervals. In the absence of overriding movement
 * orders, it simply moves left to right.
 * 
 * @author Zekoff
 * 
 */
public class Greenie extends Enemy {

	public float width = 20;
	public float height = 20;
	public static final float WEAPON_SPEED = 3.0f;
	public float cooldown = 0f;
	public int movementDirection = 1;

	public Greenie() {
		this(1);
	}

	public Greenie(int level) {
		super(level);

		// these guys have half the health of an average enemy
		maxHp /= 2;
		hp = maxHp;
		speed = 100;
		acceleration = 190;
		xpModifier = .5f;

		int componentType = Assets.rand.nextInt(4);
		if (componentType == 0) {
			components.add(new TriangularComponent(0, 0, 10, 180));
		} else if (componentType == 1)
			components.add(new RectangularComponent(0, 0, 20, 20, 45));
		else if (componentType == 2)
			components.add(new CircularComponent(0, 0, 10));
		else if (componentType == 3)
			components.add(new RectangularComponent(0, 0, 20, 20));
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

package net.zekoff.blockinvaders.combat.ship;

import java.util.ArrayList;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.collision.BoundingShape;
import net.zekoff.blockinvaders.combat.defense.Armor;
import net.zekoff.blockinvaders.combat.defense.Shield;
import net.zekoff.blockinvaders.combat.entity.Component;
import net.zekoff.blockinvaders.combat.entity.Entity;
import net.zekoff.blockinvaders.combat.hud.Hud;
import net.zekoff.blockinvaders.combat.loot.Pickup;
import net.zekoff.blockinvaders.combat.perk.Perk;
import net.zekoff.blockinvaders.combat.wingman.Wingman;
import net.zekoff.blockinvaders.utility.MiscLib;
import net.zekoff.blockinvaders.utility.StatLookup;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * A ship object to be used as a player ship. This object is instantiated by
 * calling getShip() from a ShipBlueprint and then modified off of player
 * preferences, perks, equipment, etc.
 * <p>
 * Implements the Entity interface to signify that it can be tested for
 * collisions, and that it can be targeted by enemy ships or bullets.
 * 
 * @author Zekoff
 * 
 */
public class Ship implements Entity {
	private static final float leftBound = 10;
	private static final float rightBound = 310;
	public float x = 0;
	public float y = 0;

	/**
	 * How fast the ship can move at maximum. 160-200 are "medium" values.
	 * Higher numbers increase speed.
	 */
	public float speed = 170;
	/**
	 * How fast and in what direction the ship is moving at this moment in time.
	 */
	public float xVel = 0;
	public float yVel = 0;

	ArrayList<Component> components = new ArrayList<Component>();;
	public ArrayList<MountPoint> mountPoints = new ArrayList<MountPoint>();
	Paint paint = new Paint();
	public int color = Color.BLUE;

	public BoundingShape hitbox = null;
	public BoundingShape fullHitbox = null;
	public boolean isHittable = true;
	public boolean phaseShifted = false;
	public boolean isExpired = false;

	public boolean weaponsDisabled = false;
	public boolean movementDisabled = false;

	// public float comboCounter = 1;
	public ComboCounter comboCounter = new ComboCounter();

	public float hp = 0;
	public float maxHp = 100;
	public Shield shield = null;
	public Armor armor = null;
	public float rotation = 0;

	/**
	 * Agility affects how fast the ship can change direction. 1000 is a
	 * reasonable value, with higher values giving better agility. The exception
	 * is values of 0 or less, which give instant directional change.
	 */
	public float agility = 1000;

	public float tractorBeam = 0;

	public Ship() {
		maxHp = StatLookup.lookup(Assets.pilot.level, StatLookup.rawHp);
		hp = maxHp;

		Perk p = Assets.pilot.hasPerkActive("Tractor Beam");
		if (p != null) {
			tractorBeam = p.getRankEffect(p.rank);
		}

		// Try new wingmen code
		p = Assets.pilot.hasPerkActive("Charismatic");
		if (p != null) {
			int numberOfWingmen = (int) p.getRankEffect(p.rank);
			for (int i = 0; i < numberOfWingmen; i++) {
				Assets.em.playerEntities.add(new Wingman(100 * i + 100, 300));
			}
		}
	}

	public BoundingShape getBoundingShape() {
		hitbox.x = x;
		hitbox.y = y;
		return hitbox;
	}

	public void moveLeft(float deltaTime) {
		if (movementDisabled) {
			noMovement(deltaTime);
			return;
		}
		if (xVel > -speed) {
			if (agility > 0)
				xVel -= agility * deltaTime;
			else
				xVel = -speed;
		}
		move(deltaTime);
	}

	public void moveRight(float deltaTime) {
		if (movementDisabled) {
			noMovement(deltaTime);
			return;
		}
		if (xVel < speed) {
			if (agility > 0)
				xVel += agility * deltaTime;
			else
				xVel = speed;
		}
		move(deltaTime);
	}

	public void noMovement(float deltaTime) {
		if (agility > 0) {
			if (xVel > 0) {
				xVel -= agility * deltaTime;
				if (xVel < 0)
					xVel = 0;
			}
			if (xVel < 0) {
				xVel += agility * deltaTime;
				if (xVel > 0)
					xVel = 0;
			}
		} else
			xVel = 0;
		move(deltaTime);
	}

	public void move(float deltaTime) {
		x += xVel * deltaTime;
		y += yVel * deltaTime;
		if (xVel < 0)
			rotation = 15 * (xVel / speed);
		if (xVel > 0)
			rotation = 15 * (xVel / speed);
		if (xVel == 0)
			rotation = 0;
		if (x < leftBound)
			x = leftBound;
		if (x > rightBound)
			x = rightBound;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	/**
	 * Ship objects chain any draw() call down to their component parts.
	 * 
	 * @param g
	 *            The graphics context upon which to draw.
	 */
	public void draw(Graphics g) {
		// Draw ship
		if (armor != null)
			drawArmor(g);
		drawOutline(g);
		drawFill(g);
		if (shield != null)
			shield.draw(g, x, y);

		// Draw weapons
		for (MountPoint mp : mountPoints) {
			if (mp.weapon != null)
				mp.weapon.draw(g, mp.x + x, mp.y + y);
		}
	}

	public void drawArmor(Graphics g) {
		paint.setColor(0xFFA0A0A0);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(10);
		for (int i = 0; i < components.size(); i++) {
			if ((int) rotation == 0)
				components.get(i).draw(g, x, y, paint);
			else
				components.get(i).draw(g, x, y, paint, (int) rotation);
		}
	}

	public void drawOutline(Graphics g) {
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(2);
		for (int i = 0; i < components.size(); i++) {
			if ((int) rotation == 0)
				components.get(i).draw(g, x, y, paint);
			else
				components.get(i).draw(g, x, y, paint, (int) rotation);
		}
	}

	public void drawFill(Graphics g) {
		paint.setColor(color);
		// if (scrambled) {
		// if (Assets.rand.nextInt(6) == 0)
		// paint.setColor(Color.YELLOW);
		// }
		paint.setStyle(Paint.Style.FILL);
		for (int i = 0; i < components.size(); i++) {
			if (rotation != 0)
				components.get(i).draw(g, x, y, paint, (int) rotation);
			else
				components.get(i).draw(g, x, y, paint);
		}
	}

	@Override
	public boolean isExpired() {
		return isExpired;
	}

	@Override
	public void update(float deltaTime) {
		updateComboCounter(deltaTime);

		if (shield != null)
			shield.update(deltaTime);

		// update hitboxes
		fullHitbox.x = x;
		fullHitbox.y = y;
		hitbox.x = x;
		hitbox.y = y;

		// fire weapons
		if (!weaponsDisabled) {
			for (int i = 0; i < mountPoints.size(); i++) {
				if (mountPoints.get(i).weapon != null)
					mountPoints.get(i).weapon.update(deltaTime);
			}
		}

		// tractorbeam
		if (tractorBeam > 0) {
			for (Pickup p : Assets.em.pickups) {
				if (MiscLib.getDistance(this, p) < tractorBeam) {
					// pull pickup toward ship
					p.pulledByTractorBeam = true;
				}
			}
		}
	}

	public void updateComboCounter(float deltaTime) {
		comboCounter.update(deltaTime);
	}

	@Override
	public boolean testCollision(Bullet b) {
		if (!isHittable)
			return false;
		boolean wasHit = BoundingShape.testCollision(hitbox, b.hitbox);
		if (phaseShifted && wasHit) {
			comboCounter.modifyCombo(.25f);
			return false;
		}
		return wasHit;
	}

	@Override
	public void collidedWith(Bullet b) {
		if (shield != null && !shield.isDisabled) {
			shield.takeDamage(b.power, b.damageType);
		} else {
			float damage = b.power;
			if (armor != null) {
				// reduce dmg by armor
				damage = armor.takeDamage(damage, null);
			}
			float previousHullPercentage = hp / maxHp;
			hp -= damage;
			if (hp / maxHp <= .30f && previousHullPercentage > .30f) {
				Hud.Text text = new Hud.Text("Hull integrity critical!",
						0xFFFF5050);
				Assets.hud.queueMessage(text);
			}
		}
		comboCounter.modifyCombo(-10);
		Log.d("BI_log_hitbox", "Player ship hit. Hitbox size: " + hitbox.height
				+ "x" + hitbox.width + "," + hitbox.radius);
	}
}

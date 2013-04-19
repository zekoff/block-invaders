package net.zekoff.blockinvaders.combat.enemy;

import java.util.ArrayList;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.SoundAssets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.collision.BoundingShape;
import net.zekoff.blockinvaders.combat.defense.Armor;
import net.zekoff.blockinvaders.combat.defense.ArmorBlueprint;
import net.zekoff.blockinvaders.combat.defense.Shield;
import net.zekoff.blockinvaders.combat.defense.ShieldBlueprint;
import net.zekoff.blockinvaders.combat.entity.Component;
import net.zekoff.blockinvaders.combat.entity.Entity;
import net.zekoff.blockinvaders.combat.firing.FiringOrders;
import net.zekoff.blockinvaders.combat.loot.MoneyPickup;
import net.zekoff.blockinvaders.combat.loot.Pickup;
import net.zekoff.blockinvaders.combat.movement.LeftRight;
import net.zekoff.blockinvaders.combat.movement.MovementOrders;
import net.zekoff.blockinvaders.combat.perk.Perk;
import net.zekoff.blockinvaders.combat.weapon.WeaponBlueprint;
import net.zekoff.blockinvaders.utility.LootTable;
import net.zekoff.blockinvaders.utility.StatLookup;
import net.zekoff.blockinvaders.utility.XpTable;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Provides most implementation of Enemy behavior. Standard enemies can simply
 * subclass it and implement certain bits of non-boilerplate behavior. All
 * enemies, even non-standard, should subclass this and optionally override any
 * behavior they wish.
 * <p>
 * In general, subclass enemies should provide in their (int) constructor a call
 * to their (int, EnemyType) constructor that represents the traits of the
 * "pure" version of the enemy. A variant of the enemy with a different trait
 * can be created by directly calling the (int, EnemyType) constructor.
 * 
 * @author Zekoff
 * 
 */
public abstract class Enemy implements Entity {
	public static final int BASE_HP = 150;

	public float x = 0;
	public float y = 0;
	public float speed = 100;
	public float xVel = 0;
	public float yVel = 0;
	public float acceleration = 200; // double the value of speed is appropriate

	public int level = 1;
	public float xpModifier = 1;

	public Paint paint = new Paint();
	public int color = Color.GREEN;
	public int startColor = Color.GREEN;

	public boolean isExpired = false;
	public float hp = 0;
	public float maxHp = 0;
	public boolean disableWeapons = false;
	public float power = 0;

	public ArrayList<Component> components = new ArrayList<Component>();
	public ArrayList<MovementOrders> movementOrders = new ArrayList<MovementOrders>();
	public EnemyFormation formation = null;
	public BoundingShape tempHitbox = null;

	public float rotation = 0;
	public boolean rotateByXVel = true;
	public float correctRotationSpeed = 25f;

	public boolean scrambled = false;
	public float scrambledRotation = -999;

	public Armor armor = null;
	public Shield shield = null;

	public boolean showHp = false;

	public EnemyType type = null;

	public FiringOrders firingOrders = null;

	/**
	 * Traits of the enemy above their basic stats.
	 * <p>
	 * May add things like commander, healer, tank here as well.
	 * 
	 * @author Zekoff
	 * 
	 */
	public enum EnemyType {
		armored, shielded, tough, weak
	}

	/**
	 * No-arg constructor gives the standard version of the enemy at the level
	 * of the pilot.
	 */
	public Enemy() {
		this(Assets.pilot.level);
	}

	/**
	 * This constructor gives the standard EnemyType version of the enemy at a
	 * level defined by the constructor.
	 * 
	 * @param level
	 *            The level the enemy should be.
	 */
	public Enemy(int level) {
		maxHp = StatLookup.lookup(level, BASE_HP);
		power = StatLookup.lookup(level, StatLookup.rawDps);
		hp = maxHp;
		this.level = level;

		// show HP?
		Perk p = Assets.pilot.hasPerkActive("Insightful");
		if (p != null) {
			showHp = true;
		}
	}

	/**
	 * Allows for a different variant of the enemy besides its standard variant.
	 * 
	 * @param level
	 *            The level of the enemy.
	 * @param type
	 *            The variant the enemy should be.
	 */
	public Enemy(int level, EnemyType type) {
		this(level);
		this.type = type;
		switch (type) {
		case armored:
			armor = ArmorBlueprint.getEnemyArmor(level);
			xpModifier *= 1.5f;
			break;
		case shielded:
			shield = ShieldBlueprint.getEnemyShield(level);
			xpModifier *= 1.5f;
			break;
		case tough:
			maxHp *= 2.5;
			xpModifier *= 1.5f;
			hp = maxHp;
			break;
		case weak:
			maxHp *= .3;
			xpModifier *= .3f;
			hp = maxHp;
			break;
		default:
			break;
		}
	}

	@Override
	public boolean isExpired() {
		return isExpired;
	}

	@Override
	public void update(float deltaTime) {
		if (!isExpired) {
			move(deltaTime);
			recoverColor(deltaTime);
			checkOutOfBounds();
			if (shield != null)
				shield.update(deltaTime);
			if (!disableWeapons && !scrambled)
				updateWeapons(deltaTime);
			if (hp <= 0)
				destroy();
			if (rotateByXVel && !scrambled) {
				float correctRotation = 0;
				if (xVel < 0) {
					correctRotation = -15 * (xVel / speed);
				}
				if (xVel > 0) {
					correctRotation = -15 * (xVel / speed);
				}
				if (xVel == 0) {
					correctRotation = 0;
				}
				if (rotation != correctRotation) {
					float toCorrect = correctRotation - rotation;
					if (Math.abs(toCorrect) <= correctRotationSpeed * deltaTime)
						rotation = correctRotation;
					else
						rotation += toCorrect * deltaTime;
				}
			} else if (scrambled) {
				if (scrambledRotation == -999)
					scrambledRotation = (Assets.rand.nextFloat() * 540) - 180;
				rotation += scrambledRotation * deltaTime;
			}
		}
	}

	public void updateWeapons(float deltaTime) {
		if (firingOrders != null)
			firingOrders.update(deltaTime);
	}

	/**
	 * If the enemy moves too far out of bounds, flag it for deletion.
	 */
	private void checkOutOfBounds() {
		if (y < -50 || y > 530)
			isExpired = true;
		if (x < -50 || x > 370)
			isExpired = true;
	}

	/**
	 * Checks for MovementOrders in its queue and processes the first one, then
	 * calls for a default movement scheme if no orders are present;
	 * 
	 * @param deltaTime
	 */
	public void move(float deltaTime) {
		if (scrambled) {
			moveByVelocity(deltaTime);
			return;
		}
		// no need to null-check b/c object created by default
		if (movementOrders.size() > 0) {
			movementOrders.get(0).move(this, deltaTime);
			if (movementOrders.get(0).isFinished) {
				movementOrders.remove(movementOrders.get(0));
			}
		} else {
			defaultMovement(deltaTime);
		}
	}

	/**
	 * Default movement if no MovementOrders are present to take precedence.
	 * 
	 * @param deltaTime
	 */
	public void defaultMovement(float deltaTime) {
		movementOrders.add(new LeftRight());
	}

	public void moveByVelocity(float deltaTime) {
		x += xVel * deltaTime;
		y += yVel * deltaTime;
	}

	@Override
	public void draw(Graphics g) {
		if (armor != null)
			drawArmor(g);
		drawOutline(g);
		drawFill(g);
		if (shield != null)
			shield.draw(g, x, y);

		if (showHp) {
			Assets.paint.setStyle(Paint.Style.STROKE);
			Assets.paint.setStrokeWidth(1);
			Assets.paint.setColor(0xa0ffffff);
			float hpBarWidth;
			int hpBarColor;
			if (type == EnemyType.tough) {
				hpBarColor = 0xa0ff8c00;
				hpBarWidth = 50;
			} else if (type == EnemyType.weak) {
				hpBarColor = 0xa00040ff;
				hpBarWidth = 8;
			} else {
				hpBarColor = 0xa000ff00;
				hpBarWidth = 20;
			}
			g.drawRect(x - hpBarWidth / 2, y - 30, hpBarWidth * hp / maxHp, 6,
					hpBarColor);
			g.drawRect(x - hpBarWidth / 2, y - 30, hpBarWidth, 6, Assets.paint);
		}
	}

	public void drawArmor(Graphics g) {
		paint.setColor(0xFFA0A0A0);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(12);
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
		if (scrambled) {
			if (Assets.rand.nextInt(6) == 0)
				paint.setColor(Color.YELLOW);
		}
		paint.setStyle(Paint.Style.FILL);
		for (int i = 0; i < components.size(); i++) {
			if (rotation != 0)
				components.get(i).draw(g, x, y, paint, (int) rotation);
			else
				components.get(i).draw(g, x, y, paint);
		}
	}

	@Override
	public boolean testCollision(Bullet b) {
		if (!isExpired) {
			// Possible refactoring for FPS: hold number of components that make
			// up the enemy in a field of the enemy object. This would prevent
			// the size() lookup every time.
			for (int i = 0; i < components.size(); i++) {
				// Directly accessing the hitbox of the component would mean
				// that the tempHitbox updates below affect the actual object
				// each time. Should refactor for FPS considerations.
				tempHitbox = components.get(i).getBoundingShape();
				tempHitbox.x += x;
				tempHitbox.y += y;
				if (BoundingShape.testCollision(tempHitbox, b.hitbox)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * When the enemy has sustained fatal damage, make the necessary updates and
	 * notifications. This includes awarding XP to the player, putting particle
	 * effects for the explosion in play, marking it as expired, etc.
	 * <p>
	 * This method must only be called once per enemy.
	 */
	public void destroy() {
		Assets.level.registerKilled(this);
		Assets.pm.addChunkEmitter(x, y, 6, startColor, 12);

		// Play explosion sounds
		// SoundAssets.smallerExplosion.play(1f);
		int randomSoundNumber = Assets.rand.nextInt(3);
		switch (randomSoundNumber) {
		case 0:
			SoundAssets.smallExplosion1.play(1);
			break;
		case 1:
			SoundAssets.smallExplosion2.play(1);
			break;
		// case 2:
		// // SoundAssets.smallExplosion3.play(1);
		// // break;
		// case 3:
		// // SoundAssets.smallExplosion4.play(1);
		// // break;
		case 2:
			SoundAssets.smallExplosion5.play(1);
			break;
		}

		// Assets.playerShip.comboCounter += xpModifier * 2;
		Assets.playerShip.comboCounter.enemyKilled(this);

		// Formula for XP gain from destroyed enemy
		int xpGain = (int) (XpTable.xpGain(Assets.pilot.level, level,
				xpModifier));
		xpGain = Assets.playerShip.comboCounter.modifyXp(xpGain);

		Perk p = Assets.pilot.hasPerkActive("Fast Learner");
		if (p != null) {
			xpGain *= p.getRankEffect(p.rank);
		}

		Assets.pilot.addXp(xpGain);
		for (WeaponBlueprint wb : Assets.pilot.shipBlueprint.weapons) {
			if (wb != null)
				wb.addXp(xpGain);
		}
		Assets.pm.addXpEmitter(x, y, xpGain);
		isExpired = true;
		dropLoot();
	}

	public void dropLoot() {
		int i = Assets.rand.nextInt(25);
		if (i == 0 || i == 1) {
			WeaponBlueprint w = LootTable.getRandomWeapon(this);
			Pickup p = new Pickup(w);
			p.x = x;
			p.y = y;
			Assets.em.pickups.add(p);
		} else if (i > 20) {
			// drop money box
			int value = StatLookup.lookup(level, 50);
			value *= xpModifier;
			float randomValue = (Assets.rand.nextFloat() * 2) - 1;
			randomValue *= value * .2f;
			value += randomValue;
			MoneyPickup m = new MoneyPickup(value);
			m.x = x;
			m.y = y;
			Assets.em.pickups.add(m);
		}
	}

	@Override
	public void collidedWith(Bullet b) {
		if (shield != null && !shield.isDisabled) {
			shield.takeDamage(b.power, b.damageType);
		} else {
			float damage = b.power;
			if (armor != null) {
				// reduce dmg by armor
				damage = armor.takeDamage(damage, b.damageType);
			}
			hp -= damage;
			updateColor(damage);
		}
	}

	/**
	 * Updates color based on damage sustained. Called whenever an enemy takes
	 * damage from a bullet. Turns the enemy more red and less of whatever other
	 * color they are normally. Recovers over time via the recoverColor()
	 * method.
	 * 
	 * @param power
	 *            A float value indicating how much damage was sustained.
	 */
	public void updateColor(float power) {
		// get percentage change
		float percentageChange = power / maxHp;
		if (percentageChange > .25f)
			percentageChange = .25f; // caps effect at 25% of max hp
		percentageChange *= 4; // normalizes percentage change to 0 through 1

		// add to red value
		int redValueToAdd = (int) (0xFF * percentageChange);
		int redValueCurrent = (color & 0x00FF0000) >>> 16;
		redValueCurrent += redValueToAdd;
		if (redValueCurrent > 0xFF)
			redValueCurrent = 0xFF;

		// decrease from blue and green values
		int startBlueValue = (int) (startColor & 0x000000FF);
		int startGreenValue = (int) (startColor & 0x0000FF00);
		startGreenValue = startGreenValue >>> 8;
		int blueValueToDecrease = (int) (startBlueValue * percentageChange);
		int greenValueToDecrease = (int) (startGreenValue * percentageChange);

		int blueValueCurrent = (color & 0x000000FF) - blueValueToDecrease;
		if (blueValueCurrent < 0)
			blueValueCurrent = 0;
		int greenValueCurrent = ((color & 0x0000FF00) >>> 8)
				- greenValueToDecrease;
		if (greenValueCurrent < 0)
			greenValueCurrent = 0;

		// reassemble color
		color = 0xFF000000 + (redValueCurrent << 16) + (greenValueCurrent << 8)
				+ blueValueCurrent;
	}

	public void recoverColor(float deltaTime) {
		float recoveryTime = 1f; // recover from full red in this many seconds

		// get current colors
		int redCurrent = (color & 0x00FF0000) >>> 16;
		int greenCurrent = (color & 0x0000FF00) >>> 8;
		int blueCurrent = color & 0x000000FF;

		// get starting colors
		int redStart = (startColor & 0x00FF0000) >>> 16;
		int greenStart = (startColor & 0x0000FF00) >>> 8;
		int blueStart = startColor & 0x000000FF;

		// get amount to recover
		int redToRecover;
		int greenToRecover;
		int blueToRecover;
		if (redCurrent > redStart) // if too much red, decrease it
			redToRecover = (int) -(deltaTime * ((redCurrent - redStart) / recoveryTime));
		else
			redToRecover = 0;
		if (greenStart > 0)
			greenToRecover = (int) (deltaTime * ((greenStart) / recoveryTime));
		else
			greenToRecover = 0;
		if (blueStart > 0)
			blueToRecover = (int) (deltaTime * ((blueStart) / recoveryTime));
		else
			blueToRecover = 0;

		// add to current colors
		redCurrent += redToRecover;
		greenCurrent += greenToRecover;
		blueCurrent += blueToRecover;

		// check boundaries
		if (redCurrent < redStart)
			redCurrent = redStart;
		if (greenCurrent > greenStart)
			greenCurrent = greenStart;
		if (blueCurrent > blueStart)
			blueCurrent = blueStart;

		// reassemble color
		color = 0xFF000000 + (redCurrent << 16) + (greenCurrent << 8)
				+ blueCurrent;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

}

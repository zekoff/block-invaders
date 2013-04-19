package net.zekoff.blockinvaders.combat.ability;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.MissileBatteryMissile;
import net.zekoff.blockinvaders.combat.bullet.TriangleMissile;
import net.zekoff.blockinvaders.utility.StatLookup;
import android.graphics.Color;

/**
 * Launches several homing missiles from the ship. Missile damage scales with
 * player level.
 * 
 * @author Zekoff
 * 
 */
public class MissileBarrage extends Ability {
	public boolean ready = true;
	// Bitmap bitmap = Bitmap.createBitmap(80, 80, Bitmap.Config.RGB_565);
	private int missilesLeft = 0;
	private int missilesTotal = 30; // 10
	private float timeSinceMissile = 0;
	private float timeBetweenMissiles = .15f;

	public MissileBarrage() {
		name = "Missile Barrage";
		cooldownTime = 15f;
		description = "Launches a barrage of homing missiles from your ship. The missiles will select the closest target upon launch, and will reacquire a new target if their initial or subsequent targets are lost for any reason."
				+ "\n\n"
				+ "This ability is excellent when engaging many small, fast targets, or alternatively when fighting a single target with high HP.";
	}

	@Override
	public void update(float deltaTime) {
		cooldown -= deltaTime;
		if (cooldown < 0) {
			ready = true;
			cooldown = 0;
		}
		if (missilesLeft > 0) {
			timeSinceMissile += deltaTime;
			if (timeSinceMissile >= timeBetweenMissiles) {
				missilesLeft--;
				timeSinceMissile = 0;
				// OLD MISSLE BARRAGE CODE
				// Missile missile = new Missile(TargetingType.closest, true);
				// missile.x = Assets.playerShip.x;
				// missile.y = Assets.playerShip.y;
				// missile.yVel = -missile.speed;
				// missile.power = StatLookup.lookup(Assets.pilot.level, 20) *
				// 1.5f;
				// missile.color = Color.CYAN;
				// missile.radius = 4;
				// Assets.bm.playerBullets.add(missile);
				// END OLD MISSILE BARRAGE CODE

				TriangleMissile b = null;

				b = new MissileBatteryMissile();
				b.radius = 3;
				b.speed = 430f;
				b.power = StatLookup.lookup(Assets.pilot.level,
						StatLookup.rawDps) / 3;
				b.color = Color.CYAN;
				b.x = Assets.playerShip.x;
				b.y = Assets.playerShip.y;
				b.xVel = 120 * (Assets.rand.nextFloat() - .5f);
				b.yVel = -50 * (Assets.rand.nextFloat());
				Assets.bm.playerBullets.add(b);

			}
		}
	}

	@Override
	public void draw(Graphics g) {
		if (icon == null) {
			icon = g.newPixmap("missile_barrage.png", null);
		}
	}

	@Override
	public boolean trigger() {
		if (ready) {
			ready = false;
			cooldown = cooldownTime;
			missilesLeft = missilesTotal;
			timeSinceMissile = timeBetweenMissiles;
		}
		return false;
	}

}

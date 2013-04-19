package net.zekoff.blockinvaders.combat.level;

import java.util.ArrayList;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.CampaignScreen;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.bullet.CircleBullet;
import net.zekoff.blockinvaders.combat.enemy.Enemy;
import net.zekoff.blockinvaders.combat.enemy.TWing;
import net.zekoff.blockinvaders.combat.movement.MoveTo;

public class CampaignLevel3Old extends CampaignLevel {

	float timeSinceStart = 0;
	float leftCounter = 0;
	float rightCounter = 1.5f;
	int deadEnemies = 0;

	public CampaignLevel3Old(CampaignScreen screen) {
		super(screen);
	}

	@Override
	public void update(float deltaTime) {
		if (timeSinceStart == 0) {
			Assets.hud.queueText("Enemy waves incoming. Kill 20 to advance!");
		}

		Enemy e = null;
		timeSinceStart += deltaTime;

		leftCounter += deltaTime;
		rightCounter += deltaTime;
		if (leftCounter >= 3) {
			leftCounter = 0;
			e = new TWing() {
				float cooldown = 0;
				final float WEAPON_SPEED = 2f;

				// @Override
				// public void fire() {
				// Bullet b = new CircleBullet(x, y, 5);
				// b.power = power;
				// b.speed = 200;
				// b.color = 0xFF6C9551;
				// double yDist = Assets.playerShip.y - y;
				// double xDist = Assets.playerShip.x - x;
				// double angle = Math.atan2(xDist, yDist);
				// b.xVel = (float) (Math.sin(angle) * b.speed);
				// b.yVel = (float) (Math.cos(angle) * b.speed);
				// Assets.bm.enemyBullets.add(b);
				// }

				// @Override
				// public void updateWeapons(float deltaTime) {
				// cooldown += deltaTime;
				// if (cooldown > WEAPON_SPEED) {
				// cooldown = 0;
				// fire();
				// }
				// }
			};
			e.speed = 90;
			e.x = -30;
			e.y = 50;
			e.movementOrders.add(new MoveTo(380, 200));
			Assets.em.enemies.add(e);
		}
		if (rightCounter >= 3) {
			rightCounter = 0;
			e = new TWing() {
				float cooldown = 0;
				final float WEAPON_SPEED = 2f;

				// @Override
				// public void fire() {
				// Bullet b = new CircleBullet(x, y, 5);
				// b.power = power;
				// b.speed = 200;
				// b.color = 0xFF6C9551;
				// double yDist = Assets.playerShip.y - y;
				// double xDist = Assets.playerShip.x - x;
				// double angle = Math.atan2(xDist, yDist);
				// b.xVel = (float) (Math.sin(angle) * b.speed);
				// b.yVel = (float) (Math.cos(angle) * b.speed);
				// Assets.bm.enemyBullets.add(b);
				// }

				// @Override
				// public void updateWeapons(float deltaTime) {
				// cooldown += deltaTime;
				// if (cooldown > WEAPON_SPEED) {
				// cooldown = 0;
				// fire();
				// }
				// }
			};
			e.speed = 90;
			e.x = 350;
			e.y = 50;
			e.movementOrders.add(new MoveTo(-60, 200));
			Assets.em.enemies.add(e);
		}

		if (deadEnemies >= 20) {
			screen.missionComplete = true;
		}
	}

	@Override
	public void draw(Graphics g) {
	}

	@Override
	public void registerKilled(Enemy e) {
		deadEnemies++;
		if (deadEnemies % 5 == 0 && deadEnemies < 20) {
			Assets.hud.queueMessage((20 - deadEnemies)
					+ " enemies left to kill");
		} else if (deadEnemies == 20) {
			Assets.hud.queueMessage("Mission complete!");
		}
	}

	@Override
	public String getDebriefing() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBriefing() {
		return "Kill some more dudes.";
	}

	@Override
	public ArrayList<String> getRewards() {
		// TODO Auto-generated method stub
		return null;
	}
}

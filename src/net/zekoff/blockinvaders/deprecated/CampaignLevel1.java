package net.zekoff.blockinvaders.combat.level;

import java.util.ArrayList;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.CampaignScreen;
import net.zekoff.blockinvaders.combat.enemy.Enemy;
import net.zekoff.blockinvaders.combat.enemy.WingHouse;
import net.zekoff.blockinvaders.combat.movement.MoveTo;
import net.zekoff.blockinvaders.combat.movement.SmoothMoveTo;
import net.zekoff.blockinvaders.combat.movement.SmoothRandomMovement;

public class CampaignLevel1 extends CampaignLevel {

	float timerFromStart = 0;
	float tempTimer = 3;
	int counter = 0;
	int enemiesKilled = 0;
	int wave = 0;

	boolean movedLeft = false;
	boolean movedRight = false;

	public CampaignLevel1(CampaignScreen screen) {
		super(screen);
	}

	@Override
	public void update(float deltaTime) {
		timerFromStart += deltaTime;
		switch (wave) {
		case 0:
			Assets.playerShip.weaponsDisabled = true;
			wave++;
			Assets.hud.flyup.registerForNotification(this);
			Assets.hud.flyup
					.queueComm("[COMMANDER RED] Listen up, rookie! We don't have time for the full tutorial, so you're gonna have to learn how to pilot that thing on the fly. Touch your heads-up-display to advance.");
			Assets.hud.flyup
					.queueComm("You can use the left and right buttons on the bottom of your HUD to control your movement. Try moving all the way to each side of the screen.");
			break;
		case 1:
			if (Assets.playerShip.x < 30)
				movedLeft = true;
			if (Assets.playerShip.x > 290)
				movedRight = true;
			if (movedLeft && movedRight) {
				Assets.hud.flyup
						.queueComm("[COMMANDER RED] Well son, looks like you've got opposable thumbs. That's about all we can ask from you at this point. Try to bring her back in one piece.");
				Assets.hud.flyup
						.queueComm("I'm pretty sure we installed a gun on your ship before we threw you out the airlock. Start shooting and go mop up some bad guys!");
				wave++;
			}
			break;
		case 2:
			break;
		case 3:
			tempTimer += deltaTime;
			if (tempTimer > 3 && counter < 4) {
				counter++;
				tempTimer = 0;

				// add enemy
				Enemy e = new WingHouse(1);
				e.x = -40;
				e.y = 300;
				e.movementOrders.add(new MoveTo(50, 50));
				e.movementOrders.add(new SmoothMoveTo(270, 140));
				e.movementOrders.add(new SmoothMoveTo(160, 100));
				e.movementOrders.add(new SmoothRandomMovement(100));
				addEnemy(e);
			}
			if (enemiesKilled >= 4) {
				wave++;
			}
			break;
		case 4:
			wave++;
			Assets.hud.flyup
					.queueComm("[COMMANDER RED] Good work, rookie! Bring her home.");
		}
	}

	@Override
	public void draw(Graphics g) {
	}

	@Override
	public void onFlyupClosed() {
		if (wave == 2) {
			wave++;
			Assets.playerShip.weaponsDisabled = false;
		}
		if (wave == 5) {
			missionComplete();
			Assets.hud.queueMessage("Mission complete!");
		}
	}

	@Override
	public void registerKilled(Enemy e) {
		enemiesKilled++;
	}

	@Override
	public String getDebriefing() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBriefing() {
		return "In the aftermath of a devastating attack on your homeworld, you have been conscripted into military service.";
	}

	@Override
	public ArrayList<String> getRewards() {
		ArrayList<String> rewards = new ArrayList<String>();
		if (Assets.pilot.levelCap < 3)
			Assets.pilot.levelCap = 3;
		rewards.add("Level cap raised to 3");
		return null;
	}

}

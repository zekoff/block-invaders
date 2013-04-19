package net.zekoff.blockinvaders.combat.level;

import java.util.ArrayList;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.androidgames.framework.Pixmap;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.CampaignScreen;
import net.zekoff.blockinvaders.combat.enemy.WingHouse;
import net.zekoff.blockinvaders.combat.hud.FlyupArea;
import net.zekoff.blockinvaders.combat.loot.Loot;
import net.zekoff.blockinvaders.combat.loot.Pickup;
import net.zekoff.blockinvaders.combat.movement.MoveTo;
import net.zekoff.blockinvaders.combat.movement.SmoothRandomMovement;
import net.zekoff.blockinvaders.combat.ship.Ship;
import net.zekoff.blockinvaders.combat.weapon.RapidShotBlueprint;
import android.graphics.Paint;

public class CampaignLevel1 extends CampaignLevel {

	Pixmap arrow = null;
	Pixmap cr = null;
	float arrowX = 0;
	float arrowY = 0;
	int arrowDir = 1;
	int wave = 0;
	float timer = 0;
	private boolean flyupClosed;
	private Paint paint = new Paint();
	private WingHouse enemy;
	private Pickup pickup;

	public CampaignLevel1(CampaignScreen screen) {
		super(screen);
	}

	@Override
	public void update(float deltaTime) {
		switch (wave) {
		case 0:
			wave++;
			Assets.playerShip.weaponsDisabled = true;
			arrow = Assets.game.getGraphics().newPixmap("bounce_arrow.png",
					null);
			cr = Assets.game.getGraphics().newPixmap(
					"commander_red_portrait.png", null);
			Assets.hud.flyup.registerForNotification(this);
			flyupClosed = false;
			FlyupArea.Comm introComm1 = Assets.hud.flyup.new Comm(
					"Listen up, scrub! We don’t have time to hold your hand through Basic Training, so this is all the tutorial you’re gonna get. You’d better learn how to fly that thing real quick. Wave those wings a bit for me so I know you found the controls.",
					cr, "COMMANDER RED");
			Assets.hud.flyup.queueComm(introComm1);
			break;
		case 1:
			if (flyupClosed) {
				wave++;
				Assets.hud
						.queueText("Use the arrow buttons to move left and right.");
				Assets.hud
						.queueText("Move your ship all the way to the right.");
				arrowX = 250;
				arrowY = 260;
				arrowDir = 1;
			}
			break;
		case 2:
			arrowY += 30 * deltaTime * arrowDir;
			if (arrowY > 290)
				arrowDir = -1;
			if (arrowY < 260)
				arrowDir = 1;
			if (Assets.playerShip.x > 280) {
				wave++;
				Assets.hud.clearTexts();
				Assets.hud.queueText("Now, move all the way to the left.");
				arrowX = 10;
			}
			break;
		case 3:
			arrowY += 30 * deltaTime * arrowDir;
			if (arrowY > 290)
				arrowDir = -1;
			if (arrowY < 260)
				arrowDir = 1;
			if (Assets.playerShip.x < 40) {
				wave++;
				Assets.hud.clearTexts();
				Assets.hud.queueText("Bring the ship back to the center.");
				arrowX = 130;
			}
			break;
		case 4:
			arrowY += 30 * deltaTime * arrowDir;
			if (arrowY > 290)
				arrowDir = -1;
			if (arrowY < 260)
				arrowDir = 1;
			if (Assets.playerShip.x > 160) {
				Assets.hud.clearTexts();
				wave++;
			}
			break;
		case 5:
			flyupClosed = false;
			wave++;
			FlyupArea.Comm introComm2 = Assets.hud.flyup.new Comm(
					"Well, looks like you’ve got opposable thumbs! That’s about all I can ask for, given the circumstances. Try to bring her back in one piece, will you?",
					cr, "COMMANDER RED");
			Assets.hud.flyup.queueComm(introComm2);
			break;
		case 6:
			if (flyupClosed) {
				flyupClosed = false;
				wave++;
				FlyupArea.Comm hudComm1 = Assets.hud.flyup.new Comm(
						"The various elements displayed on your screen make up your heads-up-display, or HUD. This area is your communications interface, where you will receive incoming transmissions. Tap it to advance or to make a choice if one is offered. The main area behind it is your viewscreen, where you see what's happening in the world around you.",
						null, "<TUTORIAL>");
				Assets.hud.flyup.queueComm(hudComm1);
				FlyupArea.Comm hudComm2 = Assets.hud.flyup.new Comm(
						"At the bottom of your HUD is your control panel, where you can interact with your ship using the arrow keys. Later, you will unlock abilities that can be triggered via this control panel. (You can also press the BACK button on your Android device to pause the game.)",
						null, "<TUTORIAL>");
				Assets.hud.flyup.queueComm(hudComm2);
				FlyupArea.Comm hudComm3 = Assets.hud.flyup.new Comm(
						"The green bar above your control panel is your Hull Points (HP) bar, representing the current integrity of your ship's hull. Enemy shots that hit you will cause you to lose HP, and losing all of your HP means your ship will explode. Note that the mission success rate of exploded spaceships is approximately 0%.",
						null, "<TUTORIAL>");
				Assets.hud.flyup.queueComm(hudComm3);
				FlyupArea.Comm hudComm4 = Assets.hud.flyup.new Comm(
						"At the very top of your HUD is the message display area, where you will get brief, one-line reports on your ship status, external conditions, and other information.",
						null, "<TUTORIAL>");
				Assets.hud.flyup.queueComm(hudComm4);
			}
			break;
		case 7:
			if (flyupClosed) {
				wave++;
				timer = 0;
				Loot loot = new RapidShotBlueprint(1);
				pickup = new Pickup(loot) {
					@Override
					public boolean testCollision(Ship s) {
						return false;
					}
				};
				pickup.MAX_SPEED = 0;
				enemy = new WingHouse(1) {
					@Override
					public void dropLoot() {
						pickup.x = x;
						pickup.y = y;
						Assets.em.pickups.add(pickup);
					}
				};
				enemy.x = -40;
				enemy.y = 380;
				enemy.movementOrders.add(new MoveTo(160, 140));
				enemy.movementOrders.add(new SmoothRandomMovement(15));
				enemy.disableWeapons = true;
				addEnemy(enemy);
			}
			break;
		case 8:
			timer += deltaTime;
			if (timer > 4) {
				flyupClosed = false;
				FlyupArea.Comm enemyComm1 = Assets.hud.flyup.new Comm(
						"Look alive, Rookie! Better get him before he sees you!",
						cr, "COMMANDER RED");
				Assets.hud.flyup.queueComm(enemyComm1);
				wave++;
			}
			break;
		case 9:
			if (flyupClosed) {
				flyupClosed = false;
				Assets.hud.queueText("Your weapons will fire automatically.");
				Assets.playerShip.weaponsDisabled = false;
				wave++;
			}
			break;
		case 10:
			if (noEnemies()) {
				flyupClosed = false;
				wave++;
				FlyupArea.Comm enemyComm2 = Assets.hud.flyup.new Comm(
						"Nice work Rookie, bagged your first one! Don’t get cocky though; they’ll start shooting back soon.",
						cr, "COMMANDER RED");
				Assets.hud.flyup.queueComm(enemyComm2);
				FlyupArea.Comm enemyComm1 = Assets.hud.flyup.new Comm(
						"That spinning box is some sort of equipment that he was carrying. The outline around it shows its quality. Looks like that one’s probably just junk, but you might as well pick it up. It might sell for something.",
						cr, "COMMANDER RED");
				Assets.hud.flyup.queueComm(enemyComm1);
				FlyupArea.Comm enemyComm3 = Assets.hud.flyup.new Comm(
						"You can equip any weapon, shield or armor that you find on your ship. There isn’t enough gear to go around as it is, and you’re on the bottom of the food chain, so you’re going to have to outfit yourself with whatever you can find in the field.",
						cr, "COMMANDER RED");
				Assets.hud.flyup.queueComm(enemyComm3);
				FlyupArea.Comm enemyComm4 = Assets.hud.flyup.new Comm(
						"Your equipment can be changed at any time between missions in the Shipyard. Experiment with different weapon combinations and see what style suits you.",
						cr, "COMMANDER RED");
				Assets.hud.flyup.queueComm(enemyComm4);
				FlyupArea.Comm enemyComm5 = Assets.hud.flyup.new Comm(
						"We’ll make a soldier out of you yet. Now get back here and let’s install that new hardware you’ve got there!",
						cr, "COMMANDER RED");
				Assets.hud.flyup.queueComm(enemyComm5);
			}
			break;
		case 11:
			if (flyupClosed) {
				wave++;
				missionComplete();
			}
			break;
		case 12:
			if (Assets.em.pickups.contains(pickup)) {
				if (Assets.playerShip.y < pickup.y)
					pickup.collidesWith(Assets.playerShip);
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		if (wave == 2 || wave == 3 || wave == 4) {
			paint.setColor(0x90FFFFFF);
			g.drawPixmap(arrow, arrowX, arrowY, paint);
		}
	}

	@Override
	public String getBriefing() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDebriefing() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getRewards() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onFlyupClosed() {
		flyupClosed = true;
	}

}

package net.zekoff.blockinvaders.combat.level;

import java.util.ArrayList;

import net.zekoff.androidgames.framework.Pixmap;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.CampaignScreen;
import net.zekoff.blockinvaders.combat.background.SpaceStationSpireBackground;
import net.zekoff.blockinvaders.combat.enemy.Enemy;
import net.zekoff.blockinvaders.combat.enemy.JointFighter;
import net.zekoff.blockinvaders.combat.enemy.SlantTied;
import net.zekoff.blockinvaders.combat.enemy.Swooper;
import net.zekoff.blockinvaders.combat.enemy.YWing;
import net.zekoff.blockinvaders.combat.hud.FlyupArea;
import net.zekoff.blockinvaders.combat.movement.LeftRight;
import net.zekoff.blockinvaders.combat.movement.MoveTo;
import net.zekoff.blockinvaders.combat.movement.MovementOrders;
import net.zekoff.blockinvaders.combat.movement.Parabola;
import net.zekoff.blockinvaders.combat.movement.SmoothMoveTo;
import net.zekoff.blockinvaders.combat.movement.SmoothNoMovement;
import net.zekoff.blockinvaders.combat.movement.SmoothRandomMovement;
import net.zekoff.blockinvaders.combat.movement.TimerCondition;

public class CampaignLevel2 extends CampaignLevel {

	boolean flyupClosed = false;
	float timeSinceStart = 0;
	float timer = 0;
	int counter = 0;
	int wave = 0;
	private int recommendedLevel = 1;
	Pixmap red_portrait = null;
	private Pixmap static_portrait = null;
	Pixmap merchant_portrait = null;

	public CampaignLevel2(CampaignScreen screen) {
		super(screen);
		Assets.background = new SpaceStationSpireBackground();
	}

	@Override
	public String toString() {
		return "\"Capitalism Strikes Back\"";
	}

	@Override
	public void update(float deltaTime) {
		timeSinceStart += deltaTime;
		switch (wave) {
		case 0:
			Assets.hud.flyup.registerForNotification(this);
			timer = 0;
			red_portrait = Assets.game.getGraphics().newPixmap(
					"commander_red_portrait.png", null);
			static_portrait = Assets.game.getGraphics().newPixmap(
					"static_portrait.png", null);
			merchant_portrait = Assets.game.getGraphics().newPixmap(
					"merchant_portrait.png", null);
			wave++;
			Assets.hud.queueHeader("MISSION 2");
			Assets.hud.queueText(toString());
			break;
		case 1:
			timer += deltaTime;
			if (timer > 3.5) {
				wave++;
				timer = 0;
			}
			break;
		case 2:
			wave++;
			FlyupArea.Comm comm1 = Assets.hud.flyup.new Comm(
					"Listen up, Rookie! We’re getting reports of small Block Invader strike groups doing fly-bys in civilian air space. Those good-for-nothing scrubs are quaking in their boots! Production of military assets has ground to a complete halt.",
					red_portrait, "COMMANDER RED");
			Assets.hud.flyup.queueComm(comm1);
			FlyupArea.Comm comm2 = Assets.hud.flyup.new Comm(
					"As much as I can’t stand the cowardly penny-pinchers, we’re not going to get far without a supply line behind us, and since they don’t seem to want to fight back for themselves, we’re going to have to do it for them.",
					red_portrait, "COMMANDER RED");
			Assets.hud.flyup.queueComm(comm2);
			FlyupArea.Comm comm3 = Assets.hud.flyup.new Comm(
					"I’ve got real problems to attend to elsewhere, so I thought to myself, “Self, who’s completely expendable?” Go mop ‘em up for me, Rookie.",
					red_portrait, "COMMANDER RED");
			Assets.hud.flyup.queueComm(comm3);
			break;
		case 3:
			if (flyupClosed) {
				wave++;
				flyupClosed = false;
				FlyupArea.Comm comboTutorial1 = Assets.hud.flyup.new Comm(
						"In the top left corner of your heads-up-display is the combo counter. As you kill enemies and perform other daring maneuvers, the combo counter will increase. Taking damage will cause the combo counter to decrease.",
						null, "<TUTORIAL>");
				Assets.hud.flyup.queueComm(comboTutorial1);
				FlyupArea.Comm comboTutorial2 = Assets.hud.flyup.new Comm(
						"As the combo counter rises, you will gain more XP for every enemy you destroy. In addition to this passive benefit, certain abilities, perks, and ship types you will encounter make use of the combo counter to increase their power in unique ways.",
						null, "<TUTORIAL>");
				Assets.hud.flyup.queueComm(comboTutorial2);
			}
			break;
		case 4:
			if (flyupClosed) {
				wave++;
				timer = 5;
				counter = 0;
			}
			break;
		case 5:
			timer += deltaTime;
			if (timer > 2 && counter < 6) {
				counter++;
				timer = 0;
				if (counter >= 4)
					wave++;
				Enemy e = new SlantTied(recommendedLevel);
				e.x = -40;
				e.y = -40;
				e.movementOrders.add(new MoveTo(240, 200));
				e.movementOrders.add(new SmoothMoveTo(60, 240));
				e.movementOrders.add(new SmoothMoveTo(120, 180));
				e.movementOrders.add(new SmoothMoveTo(160, 120));
				e.movementOrders.add(new SmoothRandomMovement(50));
				addEnemy(e);
			}
			break;
		case 6:
			if (noEnemies()) {
				wave++;
				for (int i = 0; i < 2; i++) {
					Enemy e = new Swooper(recommendedLevel);
					e.x = -40 + 400 * i;
					e.y = 240;
					e.movementOrders.add(new MoveTo(240 - 160 * i, 120));
					e.movementOrders.add(new LeftRight(100, 220));
					addEnemy(e);
				}
			}
			break;
		case 7:
			if (noEnemies()) {
				wave++;
				Enemy e = new YWing(recommendedLevel);
				e.x = 160;
				e.y = -40;
				e.movementOrders.add(new MoveTo(100, 80));
				e.movementOrders
						.add(new SmoothNoMovement(new TimerCondition(8)));
				e.movementOrders.add(new SmoothMoveTo(380, 0));
				addEnemy(e);
				e = new YWing(recommendedLevel);
				e.x = 160;
				e.y = -40;
				e.movementOrders.add(new MoveTo(140, 120));
				e.movementOrders
						.add(new SmoothNoMovement(new TimerCondition(8)));
				e.movementOrders.add(new SmoothMoveTo(380, 0));
				addEnemy(e);
				e = new YWing(recommendedLevel);
				e.x = 160;
				e.y = -40;
				e.movementOrders.add(new MoveTo(180, 120));
				e.movementOrders
						.add(new SmoothNoMovement(new TimerCondition(8)));
				e.movementOrders.add(new SmoothMoveTo(-60, 0));
				addEnemy(e);
				e = new YWing(recommendedLevel);
				e.x = 160;
				e.y = -40;
				e.movementOrders.add(new MoveTo(220, 80));
				e.movementOrders
						.add(new SmoothNoMovement(new TimerCondition(8)));
				e.movementOrders.add(new SmoothMoveTo(-60, 0));
				addEnemy(e);
			}
			break;
		case 8:
			if (noEnemies()) {
				wave++;
				timer = 2;
				counter = 0;
				FlyupArea.Comm comm41 = Assets.hud.flyup.new Comm(
						"[static]...REPEAT MAYDAY MAYDAY, WE'RE ALL GOING TO DIE! THIS IS THE MERCHANT SPIRE SPACE STATION, GOING DOWN IN FLAMES!",
						static_portrait, "[UNKNOWN TRANSMISSION SOURCE]");
				Assets.hud.flyup.queueComm(comm41);
				FlyupArea.Comm comm4 = Assets.hud.flyup.new Comm(
						"[static]...THE HORROR! THE HORROR... Wait. Are you not shooting at us?",
						merchant_portrait, "MERCHANT SPIRE");
				Assets.hud.flyup.queueComm(comm4);
				FlyupArea.Comm comm5 = Assets.hud.flyup.new Comm(
						"My mistake! SAVE US! SAVE US FROM THIS MADNESS...",
						merchant_portrait, "MERCHANT SPIRE");
				Assets.hud.flyup.queueComm(comm5);
				flyupClosed = false;
			}
			break;
		case 9:
			if (flyupClosed)
				wave++;
			break;
		case 10:
			timer += deltaTime;
			if (timer > 2 && counter < 7) {
				counter++;
				timer = 0;
				addRandomSlantTied();
			}
			if (counter >= 7) {
				wave++;
			}
			break;
		case 11:
			if (noEnemies()) {
				parabolaYWing(-40, 20, new Parabola(135, 10, -45, 0));
				parabolaYWing(360, 20, new Parabola(-135, 10, 45, 0));
				parabolaYWing(60, -40, new Parabola(30, 180, 0, -60));
				parabolaYWing(260, -40, new Parabola(-30, 180, 0, -60));
				wave++;
			}
			break;
		case 12:
			if (noEnemies()) {
				timer = 2;
				counter = 0;
				wave++;
			}
			break;
		case 13:
			timer += deltaTime;
			if (timer > 2 && counter < 7) {
				counter++;
				timer = 0;
				addRandomSlantTied();
			}
			if (counter >= 7) {
				wave++;
			}
			break;
		case 14:
			if (noEnemies()) {
				Enemy e = null;

				e = new YWing(recommendedLevel);
				e.x = -40;
				e.y = 320;
				e.movementOrders.add(new MoveTo(60, 260));
				e.movementOrders.add(new SmoothMoveTo(160, 200));
				e.movementOrders.add(new SmoothMoveTo(140, 160));
				e.movementOrders.add(new SmoothMoveTo(220, 60));
				e.movementOrders.add(new SmoothRandomMovement(20,
						new TimerCondition(8)));
				e.movementOrders.add(new SmoothMoveTo(-60, 0));
				addEnemy(e);

				e = new YWing(recommendedLevel);
				e.x = 360;
				e.y = 320;
				e.movementOrders.add(new MoveTo(260, 260));
				e.movementOrders.add(new SmoothMoveTo(160, 200));
				e.movementOrders.add(new SmoothMoveTo(180, 160));
				e.movementOrders.add(new SmoothMoveTo(100, 60));
				e.movementOrders.add(new SmoothRandomMovement(20,
						new TimerCondition(8)));
				e.movementOrders.add(new SmoothMoveTo(380, 0));
				addEnemy(e);

				wave++;
			}
			break;
		case 15:
			if (noEnemies()) {
				Enemy e = null;

				e = new SlantTied(recommendedLevel);
				e.x = -40;
				e.y = 360;
				e.movementOrders.add(new MoveTo(60, 200));
				e.movementOrders
						.add(new SmoothNoMovement(new TimerCondition(3)));
				e.movementOrders.add(new SmoothMoveTo(220, 80));
				e.movementOrders
						.add(new SmoothNoMovement(new TimerCondition(3)));
				e.movementOrders.add(new SmoothMoveTo(-60, 160));
				addEnemy(e);

				e = new SlantTied(recommendedLevel);
				e.x = -40;
				e.y = 320;
				e.movementOrders.add(new MoveTo(80, 160));
				e.movementOrders
						.add(new SmoothNoMovement(new TimerCondition(3)));
				e.movementOrders.add(new SmoothMoveTo(200, 100));
				e.movementOrders
						.add(new SmoothNoMovement(new TimerCondition(3)));
				e.movementOrders.add(new SmoothMoveTo(-60, 160));
				addEnemy(e);

				e = new SlantTied(recommendedLevel);
				e.x = -40;
				e.y = 280;
				e.movementOrders.add(new MoveTo(100, 120));
				e.movementOrders
						.add(new SmoothNoMovement(new TimerCondition(3)));
				e.movementOrders.add(new SmoothMoveTo(180, 120));
				e.movementOrders
						.add(new SmoothNoMovement(new TimerCondition(3)));
				e.movementOrders.add(new SmoothMoveTo(-60, 160));
				addEnemy(e);

				e = new SlantTied(recommendedLevel);
				e.x = -40;
				e.y = 240;
				e.movementOrders.add(new MoveTo(120, 80));
				e.movementOrders
						.add(new SmoothNoMovement(new TimerCondition(3)));
				e.movementOrders.add(new SmoothMoveTo(160, 140));
				e.movementOrders
						.add(new SmoothNoMovement(new TimerCondition(3)));
				e.movementOrders.add(new SmoothMoveTo(-60, 160));
				addEnemy(e);

				wave++;
			}
			break;
		case 16:
			if (noEnemies()) {
				Enemy e = null;

				e = new SlantTied(recommendedLevel);
				e.x = -40;
				e.y = 360;
				e.movementOrders.add(new MoveTo(60, 200));
				e.movementOrders
						.add(new SmoothNoMovement(new TimerCondition(3)));
				e.movementOrders.add(new SmoothMoveTo(220, 80));
				e.movementOrders
						.add(new SmoothNoMovement(new TimerCondition(3)));
				e.movementOrders.add(new SmoothMoveTo(-60, 160));
				addEnemy(e);

				e = new SlantTied(recommendedLevel);
				e.x = -40;
				e.y = 320;
				e.movementOrders.add(new MoveTo(80, 160));
				e.movementOrders
						.add(new SmoothNoMovement(new TimerCondition(3)));
				e.movementOrders.add(new SmoothMoveTo(200, 100));
				e.movementOrders
						.add(new SmoothNoMovement(new TimerCondition(3)));
				e.movementOrders.add(new SmoothMoveTo(-60, 160));
				addEnemy(e);

				e = new SlantTied(recommendedLevel);
				e.x = -40;
				e.y = 280;
				e.movementOrders.add(new MoveTo(100, 120));
				e.movementOrders
						.add(new SmoothNoMovement(new TimerCondition(3)));
				e.movementOrders.add(new SmoothMoveTo(180, 120));
				e.movementOrders
						.add(new SmoothNoMovement(new TimerCondition(3)));
				e.movementOrders.add(new SmoothMoveTo(-60, 160));
				addEnemy(e);

				e = new SlantTied(recommendedLevel);
				e.x = -40;
				e.y = 240;
				e.movementOrders.add(new MoveTo(120, 80));
				e.movementOrders
						.add(new SmoothNoMovement(new TimerCondition(3)));
				e.movementOrders.add(new SmoothMoveTo(160, 140));
				e.movementOrders
						.add(new SmoothNoMovement(new TimerCondition(3)));
				e.movementOrders.add(new SmoothMoveTo(-60, 160));
				addEnemy(e);

				wave++;
			}
			break;
		case 17:
			if (noEnemies()) {
				Enemy e = new JointFighter(recommendedLevel + 1) {
					private int defaultMovementCounter = 0;

					@Override
					public void defaultMovement(float deltaTime) {
						defaultMovementCounter++;
						switch (defaultMovementCounter % 3) {
						case 1:
							movementOrders.add(new MoveTo(60, 180));
							break;
						case 2:
							movementOrders.add(new MoveTo(260, 180));
							break;
						case 0:
							movementOrders.add(new MoveTo(160, 100));
							break;
						}
					}
				};
				e.x = 160;
				e.y = -40;
				e.movementOrders.add(new MoveTo(160, 100));
				addEnemy(e);
				wave++;
			}
			break;
		case 18:
			if (noEnemies()) {
				flyupClosed = false;
				wave++;
				timer = 0;
				FlyupArea.Comm merchantComm1 = Assets.hud.flyup.new Comm(
						"YOU'RE A HERO! WE'RE BACK IN BUSINESS!",
						merchant_portrait, "MERCHANT SPIRE");
				Assets.hud.flyup.queueComm(merchantComm1);
				FlyupArea.Comm merchantComm2 = Assets.hud.flyup.new Comm(
						"Well, really I'm a hero. My timely distress signal has clearly saved the day. Hey, I’ll buy anything you find out there, [name]. I also keep my eyes open for good deals on military surplus, so make sure to check out what I’ve got in stock when you come by.",
						merchant_portrait, "MERCHANT SPIRE");
				Assets.hud.flyup.queueComm(merchantComm2);
				FlyupArea.Comm merchantComm3 = Assets.hud.flyup.new Comm(
						"You can view my inventory, or sell things back to me, through the Shipyard. Check in anytime! And don't thank me for my heroism. It's just who I am.",
						merchant_portrait, "MERCHANT SPIRE");
				Assets.hud.flyup.queueComm(merchantComm3);
			}
			break;
		case 19:
			if (flyupClosed) {
				wave++;
				flyupClosed = false;
				FlyupArea.Comm levelupTutorial1 = Assets.hud.flyup.new Comm(
						"You’ve accumulated enough experience to gain a level. Level gains always bring HP and Skill Point boosts. HP affects how much direct damage your ship can take before exploding, while Skill Points can be spent to activate Perks and increase their effectiveness. Perks can have all sorts of effects, from increased weapon damage, to a smaller hitbox, to shorter ability cooldowns.",
						null, "<TUTORIAL>");
				Assets.hud.flyup.queueComm(levelupTutorial1);
				FlyupArea.Comm levelupTutorial2 = Assets.hud.flyup.new Comm(
						"Try spending your skill points and activating a Perk in the Pilot Data screen before your next mission. Skill Points can be refunded at any time, so feel free to experiment and find the combination of perks that best suits your playing style. Leveling up may also bring other special benefits from time to time.",
						null, "<TUTORIAL>");
				Assets.hud.flyup.queueComm(levelupTutorial2);
			}
			break;
		case 20:
			if (flyupClosed)
				wave++;
			break;
		case 21:
			screen.missionComplete = true;
			break;
		}
	}

	private void addRandomSlantTied() {
		Enemy e = new SlantTied(recommendedLevel);
		e.x = -40 + 400 * Assets.rand.nextInt(2);
		e.y = Assets.rand.nextFloat() * 200;
		float xDest = 30 + 260 * Assets.rand.nextFloat();
		float yDest = 20 + 200 * Assets.rand.nextFloat();
		e.movementOrders.add(new MoveTo(xDest, yDest));
		e.movementOrders.add(new SmoothRandomMovement(20));
		addEnemy(e);
	}

	private void parabolaYWing(float x, float y, MovementOrders mo) {
		Enemy e = new YWing(recommendedLevel) {
			public float cooldown = 0;
			public int counter = 0;
			public float Weap_SPEED = 3f;
			boolean doneFiring = false;

			@Override
			public void updateWeapons(float deltaTime) {
				if (!doneFiring)
					cooldown += deltaTime;
				if (cooldown > Weap_SPEED && counter == 0) {
					// fire();
					counter++;
				}
				if (cooldown > Weap_SPEED + Weap_SPEED * .08 && counter == 1) {
					// fire();
					counter++;
				}
				if (cooldown > Weap_SPEED + Weap_SPEED * .16 && counter == 2) {
					counter = 0;
					cooldown = 0;
					doneFiring = true;
					// fire();
				}
			}
		};
		e.x = x;
		e.y = y;
		e.movementOrders.add(mo);
		addEnemy(e);
	}

	@Override
	public String getBriefing() {
		return "While the main Block Invader fleet regroups, a few strike units are harassing civilians around major urban centers. The Merchant Spire Space Station, a major trade and production port, is under attack from one of these Block Invader teams. To get production kick-started again and give Earth’s commercial sector a fighting chance at recovery, the enemies must be removed from civilian territory.";
	}

	@Override
	public String getDebriefing() {
		return "You’ve eliminated the threat from civilian airspace, and people are once again poking their heads out into the starlight. Civilian supply runs have resumed. Morale on the Merchant Spire Space Station is high. Oh, and now the Block Invaders know you’re fighting back.";
	}

	@Override
	public ArrayList<String> getRewards() {
		ArrayList<String> rewards = new ArrayList<String>();
		rewards.add("Combo counter unlocked");
		if (!Assets.pilot.hasTitle("Businessman")) {
			rewards.add("New title unlocked: 'Businessman'");
			Assets.pilot.titles.add("Businessman");
		}
		rewards.add("New perks unlocked"); // TODO specify perks
		if (Assets.pilot.levelCap < 3) {
			Assets.pilot.levelCap = 3;
			rewards.add("Maximum pilot XP level increased to 3");
		}
		return rewards;
	}

	@Override
	public void onFlyupClosed() {
		flyupClosed = true;
	}

}

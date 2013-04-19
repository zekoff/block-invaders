package net.zekoff.blockinvaders.combat.level;

import java.util.ArrayList;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.CampaignScreen;
import net.zekoff.blockinvaders.combat.enemy.Enemy;
import net.zekoff.blockinvaders.combat.enemy.Fighter;
import net.zekoff.blockinvaders.combat.enemy.JointFighter;
import net.zekoff.blockinvaders.combat.enemy.Swooper;
import net.zekoff.blockinvaders.combat.enemy.TiedFighter;
import net.zekoff.blockinvaders.combat.enemy.WingHouse;
import net.zekoff.blockinvaders.combat.movement.MoveTo;
import net.zekoff.blockinvaders.combat.movement.SineWave;

public class CampaignLevel2Old extends CampaignLevel {

	public CampaignLevel2Old(CampaignScreen screen) {
		super(screen);
	}

	public int waveNumber = -1;
	private boolean flyupClosed = true;

	@Override
	public void update(float deltaTime) {
		Enemy e = null;
		switch (waveNumber) {
		case -1:
			Assets.hud.flyup.registerForNotification(this);
			flyupClosed = false;
			Assets.hud.flyup
					.queueComm("Yo, this needs to be a longer message to see if the line-breaking code for comms works and displays properly");
			Assets.hud.flyup.queueChoice("What is your choice?", "Go forward",
					"Go backwards");
			Assets.hud.queueMessage("ALL SYSTEMS ONLINE");
			waveNumber++;
		case 0:
			if (flyupClosed) {
				// e = new Fighter(5);
				// e.x = -20;
				// e.y = 50;
				// e.movementOrders.add(new SineWave(160, 100, 50));
				// e.movementOrders.add(new MoveTo(160, 200));
				// Assets.em.enemies.add(e);
				//
				// e = new TiedFighter(12);
				// e.x = 340;
				// e.y = 150;
				// e.movementOrders.add(new MoveTo(160, 100));
				// e.movementOrders.add(new SmoothRandomMovement(40,
				// new TimerCondition(10)));
				// e.movementOrders.add(new SmoothMoveTo(160, -70));
				// Assets.em.enemies.add(e);
				//
				// e = new TiedFighter(12);
				// e.x = -20;
				// e.y = 200;
				// e.movementOrders.add(new SmoothMoveTo(200, 100));
				// e.movementOrders.add(new SmoothMoveTo(50, 250));
				// e.movementOrders.add(new SmoothMoveTo(370, 100));
				// Assets.em.enemies.add(e);

				waveNumber++;
			}
			break;
		case 1:
			if (Assets.em.enemies.size() == 0) {
				Assets.hud.queueHeader("INC DUDES");

				e = new TiedFighter(5);
				e.x = -20;
				e.y = 50;
				e.movementOrders.add(new SineWave(160, 100, 50));
				e.movementOrders.add(new MoveTo(160, 200));
				Assets.em.enemies.add(e);

				e = new Fighter(10);
				e.x = -30;
				e.y = 100;
				Assets.em.enemies.add(e);
				waveNumber++;

				e = new WingHouse(1);
				e.x = 330;
				e.y = 140;
				Assets.em.enemies.add(e);

				e = new JointFighter(1);
				e.x = 190;
				e.y = -30;
				e.movementOrders.add(new MoveTo(190, 70));
				Assets.em.enemies.add(e);
			}
			break;
		case 2:
			if (Assets.em.enemies.size() == 0) {

				e = new TiedFighter(5);
				e.x = -20;
				e.y = 50;
				e.movementOrders.add(new SineWave(160, 100, 50));
				e.movementOrders.add(new MoveTo(160, 200));
				Assets.em.enemies.add(e);

				e = new Fighter(10);
				e.x = -30;
				e.y = 100;
				Assets.em.enemies.add(e);
				waveNumber++;

				e = new WingHouse(1);
				e.x = 330;
				e.y = 140;
				Assets.em.enemies.add(e);

				e = new JointFighter(1);
				e.x = 190;
				e.y = -30;
				e.movementOrders.add(new MoveTo(190, 70));
				Assets.em.enemies.add(e);

				e = new Swooper(3);
				e.x = 160;
				e.y = -40;
				e.movementOrders.add(new MoveTo(160, 50));
				Assets.em.enemies.add(e);

				e = new Swooper(3);
				e.x = -40;
				e.y = 290;
				e.movementOrders.add(new MoveTo(160, 50));
				Assets.em.enemies.add(e);

			}
			break;
		case 3:
			if (Assets.em.enemies.size() == 0) {
				waveNumber++;
				// Assets.hud.queueHeader("INC BOSS");
				//
				// SampleBoss boss = new SampleBoss();
				// boss.x = 160;
				// boss.y = -40;
				// boss.hp = 1700;
				// Assets.em.enemies.add(boss);
			}
			break;
		case 4:
			if (Assets.em.enemies.size() == 0) {
				Assets.hud.queueMessage("Mission complete!");
				screen.missionComplete = true;
			}
		}
	}

	@Override
	public void draw(Graphics g) {

	}

	@Override
	public void onFlyupClosed() {
		flyupClosed = true;
	}

	@Override
	public void onChoice(String choice) {
		Assets.hud.queueHeader(choice);
	}

	@Override
	public String getDebriefing() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBriefing() {
		return "Clean up attackers on the fringes of human-controlled space.";
	}

	@Override
	public ArrayList<String> getRewards() {
		// TODO Auto-generated method stub
		return null;
	}

}

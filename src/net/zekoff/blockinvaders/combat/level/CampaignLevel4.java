package net.zekoff.blockinvaders.combat.level;

import java.util.ArrayList;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.CampaignScreen;
import net.zekoff.blockinvaders.combat.enemy.Enemy;
import net.zekoff.blockinvaders.combat.enemy.TWing;
import net.zekoff.blockinvaders.combat.movement.SmoothMoveTo;
import net.zekoff.blockinvaders.combat.movement.SmoothNoMovement;
import net.zekoff.blockinvaders.combat.movement.SmoothRandomMovement;
import net.zekoff.blockinvaders.combat.movement.StoppedCondition;

public class CampaignLevel4 extends CampaignLevel {

	int wave = 1;
	float elapsedSinceStart = 0;
	int counter = 0;
	float timer = 0;

	public CampaignLevel4(CampaignScreen screen) {
		super(screen);
	}

	@Override
	public void update(float deltaTime) {
		elapsedSinceStart += deltaTime;
		switch (wave) {
		case 1:
			Assets.hud.queueHeader("MISSION 4");
			wave++;
			break;
		case 2:
			Enemy e = new TWing();
			e.x = -50;
			e.y = 300;
			e.movementOrders.add(new SmoothMoveTo(270, 60));
			e.movementOrders.add(new SmoothNoMovement(new StoppedCondition()));
			e.movementOrders.add(new SmoothMoveTo(160, 150));
			e.movementOrders.add(new SmoothNoMovement(new StoppedCondition()));
			e.movementOrders.add(new SmoothRandomMovement(15) {
				boolean setSpeed = false;

				@Override
				public void move(Enemy self, float deltaTime) {
					if (!setSpeed) {
						setSpeed = true;
						self.speed /= 4;
						self.acceleration /= 4;
					}
					super.move(self, deltaTime);
				}
			});
			add(e);
			wave++;
			break;
		case 3:
			if (Assets.em.enemies.size() == 0) {
				screen.missionComplete = true;
			}
			break;
		}
	}

	private void add(Enemy e) {
		Assets.em.enemies.add(e);
	}

	@Override
	public String getDebriefing() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBriefing() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getRewards() {
		// TODO Auto-generated method stub
		return null;
	}

}

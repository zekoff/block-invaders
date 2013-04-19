package net.zekoff.blockinvaders.combat.ability;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;

public class TimeWarp extends Ability {
	boolean active = false;
	float duration = 0;
	float slowdown = .4f;
	float maxDuration = 2.5f;
	float oldTimeFactor = 0;

	public TimeWarp() {
		name = "Time Warp";
		cooldownTime = 15f;
		description = "This ability forces the trans-light engines on your ship to produce a feedback loop, causing spacetime to bend around a single location. "
				+ "The net effect is to slow everything around you to a crawl -- except your ship."
				+ "\n\n"
				+ "This ability is great for weaving through tightly packed patterns of enemy fire, as well as building up a large number of shots that will devastate enemy ranks once the time warp is over (since your bullets are slowed, but the speed your guns fire is not).";
	}

	@Override
	public void update(float deltaTime) {
		cooldown -= deltaTime;
		if (cooldown <= 0)
			cooldown = 0;
		if (active) {
			duration += deltaTime;
			if (duration < maxDuration) {
				// slow down time
				if (Assets.gameSpeed > oldTimeFactor * slowdown) {
					Assets.gameSpeed -= 1.7 * deltaTime;
				}
			} else {
				// speed time back up to normal
				if (Assets.gameSpeed < oldTimeFactor) {
					Assets.gameSpeed += .7 * deltaTime;
				}
				if (Assets.gameSpeed >= oldTimeFactor) {
					Assets.gameSpeed = oldTimeFactor;
					active = false;
				}
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean trigger() {
		if (cooldown == 0) {
			cooldown = cooldownTime;
			active = true;
			duration = 0;
			oldTimeFactor = Assets.gameSpeed;
			return true;
		}
		return false;
	}

}

package net.zekoff.blockinvaders.combat.ability;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.SoundAssets;
import net.zekoff.blockinvaders.combat.entity.Barrier;

public class DeployBarrier extends Ability {
	public float duration = 10f;

	public DeployBarrier() {
		name = "Deploy Barrier";
		cooldownTime = 25f;
		cooldown = 0;
		description = "Set up an impenetrable barrier in front of your ship. The barrier is impervious to all damage, blocks all bullets, and lasts for "
				+ duration
				+ " seconds."
				+ "\n\n"
				+ "This ability is excellent when you need 'breathing room' to regenerate your shield or wait for another ability to come off cooldown.";
	}

	@Override
	public void update(float deltaTime) {
		cooldown -= deltaTime;
		if (cooldown < 0)
			cooldown = 0;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean trigger() {
		if (cooldown == 0) {
			SoundAssets.barrier.play(1);
			Barrier b = new Barrier(duration);
			b.x = Assets.playerShip.x;
			b.y = Assets.playerShip.y - 50;
			Assets.em.playerEntities.add(b);
			cooldown = cooldownTime;
			return true;
		}
		return false;
	}

}

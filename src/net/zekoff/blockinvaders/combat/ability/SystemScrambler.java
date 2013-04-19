package net.zekoff.blockinvaders.combat.ability;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.enemy.Enemy;

public class SystemScrambler extends Ability {
	public float duration = 4f; // currently unused

	public SystemScrambler() {
		cooldown = 0;
		cooldownTime = 20f;
		name = "System Scrambler";
		description = "This ability sends out a hacking signal to all enemy ships. Weaker enemies will have their systems completely overrun, causing them to spiral helplessly out of control. Stronger enemies may find their weapons disabled temporarily, or be unable to move for a short time."
				+ "\n\n"
				+ "This is a primarily defensive ability for when you find yourself overwhelmed. Weaker enemies will become sitting ducks ripe for comboing, while stronger enemies will becomes less of a threat temporarily.";
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
			cooldown = cooldownTime;
			for (Enemy e : Assets.em.enemies) {
				e.scrambled = true;
			}
		}
		return false;
	}

}

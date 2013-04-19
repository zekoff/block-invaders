package net.zekoff.blockinvaders.combat.ability;

import net.zekoff.androidgames.framework.Graphics;

/**
 * The manager used by Assets that holds the player's abilities. Chains calls on
 * the abilities to member fields.
 * 
 * @author Zekoff
 * 
 */
public class AbilityManager {
	public Ability ability1 = null;
	public Ability ability2 = null;

	public void update(float deltaTime) {
		if (ability1 != null)
			ability1.update(deltaTime);
		if (ability2 != null)
			ability2.update(deltaTime);
	}

	public void draw(Graphics g) {
		if (ability1 != null)
			ability1.draw(g);
		if (ability2 != null)
			ability2.draw(g);
	}

	public void reinit() {
		ability1 = null;
		ability2 = null;
	}
}

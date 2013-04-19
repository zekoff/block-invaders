package net.zekoff.blockinvaders.combat.ability;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.androidgames.framework.Pixmap;

/**
 * The abstract class from which all Abilities must inherit. Guarantees that the
 * implementing class can provide update(), draw(), and other necessary methods.
 * <p>
 * When the ability is button is pressed via the UI, trigger() is called to
 * notify the ability that it was pressed. It then acts or does not act as its
 * cooldown permits. The UI updates the ability cooldown mask via the cooldown
 * and cooldownTime fields.
 * 
 * @author Zekoff
 * 
 */
public abstract class Ability {
	public String name = "Ability";
	public transient Pixmap icon = null;
	public String description = "None.";

	@Override
	public String toString() {
		return name;
	}

	/**
	 * The current cooldown on the ability.
	 */
	public transient float cooldown = 0;

	/**
	 * The max cooldown after the ability has been activated.
	 */
	public float cooldownTime;

	/**
	 * Called once per frame by the main game context. Ability updates its state
	 * during this phase.
	 * 
	 * @param deltaTime
	 */
	public abstract void update(float deltaTime);

	/**
	 * If the ability does anything to draw itself to the screen, it does so
	 * here.
	 * 
	 * @param g
	 */
	public abstract void draw(Graphics g);

	/**
	 * Called by the control scheme when the player has attempted to trigger
	 * this ability.
	 * 
	 * @return A boolean value signifying whether the ability was successfully
	 *         triggered or not.
	 */
	public abstract boolean trigger();

	public float getCooldownPercentage() {
		float percentage = cooldownTime - cooldown;
		return percentage / cooldownTime;
	}
}

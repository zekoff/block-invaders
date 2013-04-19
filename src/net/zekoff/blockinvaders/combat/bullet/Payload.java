package net.zekoff.blockinvaders.combat.bullet;

public class Payload {
	public enum Type {
		ae, scramble
	}

	public Type type = Type.ae;

	/**
	 * For AreaEffects, the damage over 1 second.
	 */
	public float power = 0;

	/**
	 * Probability (between 0 and 1) of the effect triggering, normalized so
	 * that it occurs this % every 1 second.
	 */
	public float chance = 0;

	/**
	 * For AreaEffects, the duration of the explosion. For status effects, the
	 * duration of the status effect.
	 */
	public float duration = 3;

	/**
	 * For AreaEffects only; the targetRadius of the explosion.
	 */
	public float size = 0;
}

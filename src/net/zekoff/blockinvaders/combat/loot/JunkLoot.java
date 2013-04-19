package net.zekoff.blockinvaders.combat.loot;

import java.io.Serializable;

/**
 * "Grey-quality" loot that has no purpose other than selling. Selling junk loot
 * is the primary way in which players earn money. Junk loot has flavor text
 * that can be viewed.
 * 
 * @author Zekoff
 * 
 */
public abstract class JunkLoot implements Serializable, Loot {
	private static final long serialVersionUID = 4444383063643116502L;

	@Override
	public LootType getType() {
		return LootType.junk;
	}

	@Override
	public float getQuality() {
		return JUNK_QUALITY;
	}

	@Override
	public abstract String toString();
}

package net.zekoff.blockinvaders.utility;

import net.zekoff.blockinvaders.combat.loot.Loot.LootType;

public interface Blueprint {
	public String getName();

	public int getRating();

	public int getValue();

	public float getQuality();

	public LootType getType();
}
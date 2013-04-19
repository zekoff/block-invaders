package net.zekoff.blockinvaders.combat.perk;

import java.io.Serializable;

/**
 * A perk is some passive power that the player goes into combat with. These are
 * purchased and leveled up using skill points. If a player has access to perk,
 * it is placed in their perks list of their pilot data. This list represents
 * perks that have been unlocked, not necessarily perks that are active.
 * <p>
 * Perks have a certain number of ranks, and and effect and description
 * associated with each rank. It is up to the appropriate classes to convert the
 * effect of the perk, which is saved as just a float, into something usable.
 * 
 * @author Zekoff
 * 
 */
public abstract class Perk implements Serializable {
	private static final long serialVersionUID = -8036427886102625621L;

	public String name;
	public int numRanks;
	public int rank;

	public Perk(String name, int numRanks) {
		this.name = name;
		rank = 0;
		this.numRanks = numRanks;
	}

	public abstract String getDescription();

	public abstract float getRankEffect(int rank);

	public abstract String getRankDescription(int rank);

	public abstract int getRankCost(int rank);

	@Override
	public String toString() {
		return name;
	}
}

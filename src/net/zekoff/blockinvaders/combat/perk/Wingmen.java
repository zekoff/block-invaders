package net.zekoff.blockinvaders.combat.perk;

public class Wingmen extends Perk {

	public Wingmen() {
		super("Charismatic", 3);
	}

	@Override
	public String getDescription() {
		return "Get wingmen.";
	}

	@Override
	public float getRankEffect(int rank) {
		return rank;
	}

	@Override
	public String getRankDescription(int rank) {
		return "Get " + Integer.toString(rank) + " wingmen.";
	}

	@Override
	public int getRankCost(int rank) {
		// TODO Auto-generated method stub
		return 0;
	}

}

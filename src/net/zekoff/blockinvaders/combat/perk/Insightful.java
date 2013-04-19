package net.zekoff.blockinvaders.combat.perk;

public class Insightful extends Perk {

	public Insightful() {
		super("Insightful", 1);
	}

	@Override
	public String getDescription() {
		// TODO make this more descriptive
		return "See enemy HP bars.";
	}

	@Override
	public float getRankEffect(int rank) {
		return 1;
	}

	@Override
	public String getRankDescription(int rank) {
		switch (rank) {
		case 0:
			return "Nothing.";
		case 1:
			return "See enemy HP bars.";
		default:
			return "See enemy HP bars.";
		}
	}

	@Override
	public int getRankCost(int rank) {
		return 5;
	}

}

package net.zekoff.blockinvaders.combat.perk;

public class TractorBeam extends Perk {

	public TractorBeam() {
		super("Tractor Beam", 3);
	}

	@Override
	public String getDescription() {
		return "Draws pickups toward your ship.";
	}

	@Override
	public float getRankEffect(int rank) {
		switch (rank) {
		case 1:
			return 75;
		case 2:
			return 125;
		case 3:
			return 500;
		}
		return 0;
	}

	@Override
	public String getRankDescription(int rank) {
		return "Tractor beam functions at a distance of " + getRankEffect(rank)
				+ ".";
	}

	@Override
	public int getRankCost(int rank) {
		switch (rank) {
		case 1:
			return 5;
		case 2:
			return 5;
		case 3:
			return 5;
		}
		return 0;
	}

}

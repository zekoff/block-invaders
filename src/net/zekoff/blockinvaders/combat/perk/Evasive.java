package net.zekoff.blockinvaders.combat.perk;

/**
 * When this perk is active, the hitbox of the player ship should be smaller by
 * a percentage specified in the rank effect.
 * 
 * @author Zekoff
 * 
 */
public class Evasive extends Perk {
	public Evasive() {
		super("Evasive", 3);
	}

	@Override
	public String getDescription() {
		String perkDescription = "You're an expert at getting out of "
				+ "tight scrapes. With this perk, your hitbox will "
				+ "become smaller, giving enemies a smaller target "
				+ "to shoot at.";

		return perkDescription;
	}

	@Override
	public float getRankEffect(int rank) {
		switch (rank) {
		case 1:
			return .10f;
		case 2:
			return .25f;
		case 3:
			return .50f;
		default:
			throw new RuntimeException();
		}
	}

	@Override
	public String getRankDescription(int rank) {
		switch (rank) {
		case 1:
			return "You've picked up a few tricks about dodging and weaving, effectively giving you a 10% smaller hitbox.";
		case 2:
			return "You continue to perfect your nimble ship handling, and effectively have a 25% smaller hitbox.";
		case 3:
			return "All those tight scrapes you've gotten yourself out of have made you as a ghost in space. Your hitbox is 50% smaller.";
		default:
			throw new RuntimeException();
		}
	}

	@Override
	public int getRankCost(int rank) {
		switch (rank) {
		case 1:
			return 10;
		case 2:
			return 15;
		case 3:
			return 20;
		default:
			throw new RuntimeException();
		}
	}
}

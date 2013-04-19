package net.zekoff.blockinvaders.combat.perk;

/**
 * When this perk is active, all player weapons should fire faster by a
 * percentage specified in the rank's effect.
 * 
 * @author Zekoff
 * 
 */
public class RapidFire extends Perk {
	public RapidFire() {
		super("Itchy Trigger Finger", 3);
	}

	@Override
	public String getDescription() {
		return "You've noticed that your weapons have unlimited ammunition, and after much reflection decided that you should start pulling the trigger faster.";
	}

	@Override
	public float getRankEffect(int rank) {
		switch (rank) {
		case 1:
			return .08f;
		case 2:
			return .16f;
		case 3:
			return .25f;
		default:
			throw new RuntimeException();
		}
	}

	@Override
	public String getRankDescription(int rank) {
		switch (rank) {
		case 1:
			return "Finger excercises in your off-hours have resulted in an 8% increase to trigger-pulling speed.";
		case 2:
			return "Your finger is now a paragon of digital musculature. Firing speed increased by 16%.";
		case 3:
			return "Your uncanny trigger-spamming prowess has made you the self-proclaimed master of speedy firing. You shoot your weapons 25% faster than normal.";
		default:
			throw new RuntimeException();
		}
	}

	@Override
	public int getRankCost(int rank) {
		return 10;
	}
}

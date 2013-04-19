package net.zekoff.blockinvaders.combat.perk;

/**
 * When this perk is active, all bullets fired by player weapons should increase
 * their power by a percentage determined by the rank effect.
 * 
 * @author Zekoff
 * 
 */
public class DmgIncrease extends Perk {
	public DmgIncrease() {
		super("Well-Researched", 3);
	}

	@Override
	public String getDescription() {
		String perkDescription = "You've studied your opponents and learned how to hit them where it hurts. You aim for vulnerable spots and net a boost in damage to every shot that hits a target.";
		return perkDescription;
	}

	@Override
	public float getRankEffect(int rank) {
		switch (rank) {
		case 1:
			return .8f;
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
			return "Aiming for the exploding bits on enemy ships has seemed to produce good results. Your damage is increased by 8%.";
		case 2:
			return "You've picked up on some of the enemy's tactics, and become better at placing your shots. Damage increased by 16%.";
		case 3:
			return "You know the weaknesses of your enemy's ship better than he does. Your ability to predict his reactions borders on telepathic. Your damage increases by 25%.";
		default:
			throw new RuntimeException();
		}
	}

	@Override
	public int getRankCost(int rank) {
		return 10;
	}
}

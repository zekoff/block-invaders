package net.zekoff.blockinvaders.combat.perk;

public class IncreasedXp extends Perk {
	public IncreasedXp() {
		super("Fast Learner", 5);
	}

	@Override
	public String getDescription() {
		String perkDescription = "You've always been... well, kind of a nerd. Although your good grades got you beaten up more often that not in school, it turns out that being smart is a pretty sweet deal. As long as you don't want friends, of course. Your unparalled mental agility enables you to learn more from each enemy you destroy, netting you a boost to XP.";
		return perkDescription;
	}

	@Override
	public float getRankEffect(int rank) {
		switch (rank) {
		case 1:
			return 1.05f;
		case 2:
			return 1.1f;
		case 3:
			return 1.15f;
		case 4:
			return 1.2f;
		case 5:
			return 1.25f;
		default:
			throw new RuntimeException(
					"Attempted to check for a perk that doesn't exist.");
		}
	}

	@Override
	public String getRankDescription(int rank) {
		switch (rank) {
		case 1:
			return "You were a gamer in your younger years -- before it was cool. The social awkwardness gave you more time to develop your math skills. XP from enemy kills increased by 5%.";
		case 2:
			return "Once, you made the mistake of asking a girl on a date not realizing it was April Fool's Day. She said 'yes', and everyone just laughed and laughed. That night, as you went home alone, you did mental square roots of prime numbers to stave off the crushing loneliness. It prepared you for a 10% boost to XP from enemy kills later in life.";
		case 3:
			return "A lifetime without social contact drove you into the arms of academia. Watching mortals pass by your ivory tower has given a smug sense of self-satisfaction, equalled only by your ability to absorb new information 15% better than everyone else.";
		case 4:
			return "As you despair of ever hearing another human voice in the midst of your isolation from society, you begin to become one with your computer. Your brain functions solely in algorithms now, unfettered by the banal details of social interaction. You gain a 20% increase to XP gains from enemy kills (at the cost of any chance of happiness you had left).";
		case 5:
			return "010010010110111000100000011011010111010101100011011010000010000001110111011010010111001101100100011011110110110100100000011010010111001100100000011011010111010101100011011010000010000001110110011001010111100001100001011101000110100101101111011011100010110000100000011000010110111001100100001000000110100001100101001000000111011101101000011011110010000001101001011011100110001101110010011001010110000101110011011001010111001100100000011010110110111001101111011101110110110001100101011001000110011101100101001000000110100101101110011000110111001001100101011000010111001101100101011100110010000001110011011011110111001001110010011011110111011100101110";
		default:
			throw new RuntimeException(
					"Attempted to check for a perk that doesn't exist.");
		}
	}

	@Override
	public int getRankCost(int rank) {
		return 10;
	}
}

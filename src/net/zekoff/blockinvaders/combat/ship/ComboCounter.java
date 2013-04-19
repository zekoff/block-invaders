package net.zekoff.blockinvaders.combat.ship;

import net.zekoff.blockinvaders.combat.enemy.Enemy;

public class ComboCounter {
	private boolean freezeCombo = false;
	private float combo = 1;
	float timeSinceLastKill = 0;
	public float comboCap = 99.999999f;
	public float comboMin = 1;
	public float highestCombo = 0;

	public void enemyKilled(Enemy enemy) {
		float comboModifier = (float) (5 - Math.pow(timeSinceLastKill, 2));
		if (comboModifier < 1)
			comboModifier = 1;
		modifyCombo(enemy.xpModifier * comboModifier);
		// combo += enemy.xpModifier * comboModifier;
		timeSinceLastKill = 0;
	}

	public void modifyCombo(float amount) {
		combo += amount;
		if (combo > comboCap)
			combo = comboCap;
		if (combo < comboMin)
			combo = comboMin;
		if (combo > highestCombo)
			highestCombo = combo;
	}

	public float getCombo() {
		return combo;
	}

	public void update(float deltaTime) {
		if (!freezeCombo) {
			timeSinceLastKill += deltaTime;
			float comboDecrease = (float) (Math.pow(timeSinceLastKill, 1.2) * .15f);
			if (comboDecrease > 10)
				comboDecrease = 10;
			modifyCombo(-comboDecrease * deltaTime);
			// combo -= (comboDecrease * deltaTime);
		}

		if (combo < comboMin)
			combo = comboMin;

		if (combo > comboCap)
			combo = comboCap;
	}

	public void freezeCombo() {
		freezeCombo = true;
	}

	public void unfreezeCombo() {
		freezeCombo = false;
	}

	public int modifyXp(int xpGain) {
		return (int) (xpGain * (1 + (combo / 200)));
	}

}

package net.zekoff.blockinvaders.combat.ability;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.SoundAssets;

public class PhaseShift extends Ability {
	public boolean active = false;
	public int storedColor = 0;
	public int phasedColor = 0;
	public float counter = 0;
	public float duration = .5f;
	public boolean recentlyActivated = false;

	public PhaseShift() {
		name = "Phase Shift";
		cooldownTime = 2f;
		cooldown = 0;
		description = "This ability causes your ship to briefly shift into hyperspace, avoiding all shots that would have damaged you while you are phase shifted. In addition, any shot avoided in this way will cause your combo meter to increase rapidly."
				+ "\n\n"
				+ "This is a versatile ability that can be used both for avoiding damage and for building your combo meter.";
	}

	@Override
	public void update(float deltaTime) {
		cooldown -= deltaTime;
		if (cooldown < 0)
			cooldown = 0;
		counter += deltaTime;
		if (counter > duration && recentlyActivated) {
			recentlyActivated = false;
			disablePhaseShift();
		}
	}

	private void disablePhaseShift() {
		Assets.playerShip.phaseShifted = false;
		Assets.playerShip.color = storedColor;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean trigger() {
		if (cooldown == 0) {
			SoundAssets.phase_shift.play(1);
			cooldown = cooldownTime;
			counter = 0;
			Assets.playerShip.phaseShifted = true;
			recentlyActivated = true;
			storedColor = Assets.playerShip.color;
			phasedColor = (0x60 << 24) + (storedColor & 0x00FFFFFF);
			Assets.playerShip.color = phasedColor;
		}
		return false;
	}

}

package net.zekoff.blockinvaders.combat.ship;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.collision.Aabb;
import net.zekoff.blockinvaders.combat.perk.Perk;

public abstract class MediumShipBlueprint extends ShipBlueprint {

	@Override
	public abstract Ship getShip();

	@Override
	public String getShipClassName() {
		return "Cruiser";
	}

	@Override
	public void setupHitbox(Ship s) {
		s.hitbox = new Aabb(40, 30);
		s.fullHitbox = new Aabb(40, 30);
		Perk p = Assets.pilot.hasPerkActive("Evasive");
		if (p != null) {
			float hitboxSize = 1 - p.getRankEffect(p.rank);
			s.hitbox.height *= hitboxSize;
			s.hitbox.width *= hitboxSize;
		}
	}

	@Override
	public void setupSpeed(Ship s) {
		// use defaults
	}

	@Override
	public int getNumMountPoints() {
		return 4;
	}

	@Override
	public ShipClass getShipClass() {
		return ShipClass.medium;
	}

}

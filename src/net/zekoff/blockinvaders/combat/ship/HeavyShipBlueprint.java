package net.zekoff.blockinvaders.combat.ship;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.collision.Aabb;
import net.zekoff.blockinvaders.combat.perk.Perk;

public abstract class HeavyShipBlueprint extends ShipBlueprint {

	// expressed as percentage; i.e. 0.5f is 50% bonus
	public static final float ARMOR_BONUS = 1f;

	@Override
	public abstract Ship getShip();

	@Override
	public int getNumMountPoints() {
		return 5;
	}

	@Override
	public String getShipClassName() {
		return "Destroyer";
	}

	@Override
	public void setupHitbox(Ship s) {
		s.hitbox = new Aabb(50, 30);
		s.fullHitbox = new Aabb(50, 30);
		Perk p = Assets.pilot.hasPerkActive("Evasive");
		if (p != null) {
			float hitboxSize = 1 - p.getRankEffect(p.rank);
			s.hitbox.height *= hitboxSize;
			s.hitbox.width *= hitboxSize;
		}
	}

	@Override
	public void setupSpeed(Ship s) {
		s.speed = 120;
		s.agility = 500;
	}

	@Override
	public void setupEquipment(Ship s) {
		super.setupEquipment(s);
		if (armor != null)
			s.armor.flatReduction *= 1 + ARMOR_BONUS;
	}

	@Override
	public ShipClass getShipClass() {
		return ShipClass.heavy;
	}
}

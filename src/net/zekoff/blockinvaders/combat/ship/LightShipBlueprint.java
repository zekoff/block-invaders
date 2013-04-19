package net.zekoff.blockinvaders.combat.ship;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.collision.Circle;
import net.zekoff.blockinvaders.combat.perk.Perk;

public abstract class LightShipBlueprint extends ShipBlueprint {

	@Override
	public abstract Ship getShip();

	@Override
	public String getShipClassName() {
		return "Fighter";
	}

	@Override
	public void setupHitbox(Ship s) {
		s.hitbox = new Circle(17);
		s.fullHitbox = new Circle(17);
		Perk p = Assets.pilot.hasPerkActive("Evasive");
		if (p != null) {
			float hitboxSize = 1 - p.getRankEffect(p.rank);
			s.hitbox.height *= hitboxSize;
			s.hitbox.width *= hitboxSize;
			s.hitbox.radius *= hitboxSize;
		}
	}

	@Override
	public void setupSpeed(Ship s) {
		s.speed = 210;
		s.agility = 2000;
	}

	@Override
	public int getNumMountPoints() {
		return 3;
	}

	@Override
	public void setupEquipment(Ship s) {
		super.setupEquipment(s);
		if (shield != null)
			s.shield.regenRate *= 3;
	}

	@Override
	public ShipClass getShipClass() {
		return ShipClass.light;
	}

}

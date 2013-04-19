package net.zekoff.blockinvaders.combat.ship;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.collision.Aabb;
import net.zekoff.blockinvaders.combat.defense.Shield;
import net.zekoff.blockinvaders.combat.entity.RectangularComponent;
import net.zekoff.blockinvaders.combat.perk.Perk;

/**
 * Sample blueprint for testing multiple components making up a player ship.
 * 
 * @author Zekoff
 * 
 */
public class SquareTestBlueprint extends LightShipBlueprint {

	@Override
	public Ship getShip() {
		Ship s = new Ship();
		s.components.add(new RectangularComponent(-20, 20, 20, 20));
		s.components.add(new RectangularComponent(20, 20, 20, 20));
		s.components.add(new RectangularComponent(0, 0, 40, 40));
		s.hitbox = new Aabb(50, 30);
		s.fullHitbox = new Aabb(50, 30);
		Perk p = Assets.pilot.hasPerkActive("Evasive");
		if (p != null) {
			float hitboxSize = 1 - p.getRankEffect(p.rank);
			s.hitbox.height *= hitboxSize;
			s.hitbox.width *= hitboxSize;
		}
		s.mountPoints.add(new MountPoint(-30, 30));
		s.mountPoints.add(new MountPoint(30, 30));

		s.shield = new Shield();
		s.shield.maxCharge = 3;
		s.shield.currentCharge = s.shield.maxCharge;
		s.shield.regenRate = .2f;
		s.shield.recoveryTime = 5f;
		s.shield.color = 0x8000FF00;
		s.shield.size = 50;

		setupEquipment(s);

		return s;
	}

	@Override
	public int getNumMountPoints() {
		return 2;
	}
}

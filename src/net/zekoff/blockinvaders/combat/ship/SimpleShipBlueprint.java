package net.zekoff.blockinvaders.combat.ship;

import net.zekoff.blockinvaders.combat.entity.RectangularComponent;
import net.zekoff.blockinvaders.combat.entity.TriangularComponent;

/**
 * @author Zekoff
 * 
 */
public class SimpleShipBlueprint extends MediumShipBlueprint {

	@Override
	public Ship getShip() {
		Ship s = new Ship();
		s.components.add(new TriangularComponent(0, -6, 12));
		s.components.add(new RectangularComponent(0, 6, 40, 15));

		s.mountPoints.add(new MountPoint(-5, -5));
		s.mountPoints.add(new MountPoint(5, -5));
		s.mountPoints.add(new MountPoint(0, 0));
		s.mountPoints.add(new MountPoint(0, 10));

		setupAll(s);

		return s;
	}

	@Override
	public int getNumMountPoints() {
		return 4;
	}
}

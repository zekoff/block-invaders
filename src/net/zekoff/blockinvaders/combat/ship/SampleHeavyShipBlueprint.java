package net.zekoff.blockinvaders.combat.ship;

import net.zekoff.blockinvaders.combat.entity.RectangularComponent;
import net.zekoff.blockinvaders.combat.entity.TriangularComponent;

public class SampleHeavyShipBlueprint extends HeavyShipBlueprint {

	@Override
	public Ship getShip() {
		Ship s = new Ship();

		s.components.add(new RectangularComponent(0, 0, 50, 15));
		s.components.add(new TriangularComponent(-25, 0, 12));
		s.components.add(new TriangularComponent(25, 0, 12));
		s.components.add(new TriangularComponent(0, 0, 18));

		s.mountPoints.add(new MountPoint(-25, 0));
		s.mountPoints.add(new MountPoint(25, 0));
		s.mountPoints.add(new MountPoint(0, -6));
		s.mountPoints.add(new MountPoint(-12, 0));
		s.mountPoints.add(new MountPoint(12, 0));

		setupAll(s);

		return s;
	}

	@Override
	public int getNumMountPoints() {
		return 5;
	}

}

package net.zekoff.blockinvaders.combat.ship;

import net.zekoff.blockinvaders.combat.defense.Shield;
import net.zekoff.blockinvaders.combat.entity.TriangularComponent;

public class SimpleTriangularShipBlueprint extends LightShipBlueprint {

	public SimpleTriangularShipBlueprint() {
		this(0);
	}

	public SimpleTriangularShipBlueprint(int level) {
		color = 0xFF0000FF;
	}

	@Override
	public Ship getShip() {
		Ship s = new Ship();
		s.components.add(new TriangularComponent(0, 0, 15));

		s.mountPoints.add(new MountPoint(-6, -6));
		s.mountPoints.add(new MountPoint(6, -6));
		s.mountPoints.add(new MountPoint(0, 0));

		setupAll(s);

		return s;
	}

	@Override
	public int getNumMountPoints() {
		return 3;
	}
}

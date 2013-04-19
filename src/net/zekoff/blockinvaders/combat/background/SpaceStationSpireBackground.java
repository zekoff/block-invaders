package net.zekoff.blockinvaders.combat.background;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.androidgames.framework.Graphics.PixmapFormat;
import net.zekoff.androidgames.framework.Pixmap;

public class SpaceStationSpireBackground extends DynamicStarfield {

	Pixmap spaceStation = null;
	float ssX = 220;
	float ssY = -30;

	public SpaceStationSpireBackground() {
		super(40);
	}

	public SpaceStationSpireBackground(int numStars) {
		super(numStars);
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		ssY += deltaTime * speed / 100;
	}

	@Override
	public void drawBackground(Graphics g) {
		if (spaceStation == null) {
			spaceStation = g.newPixmap("space_station_spire_pixel.png",
					PixmapFormat.ARGB8888);
		}
		super.drawBackground(g);
		g.drawPixmap(spaceStation, ssX, ssY);
	}

}

package net.zekoff.blockinvaders.combat.background;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.androidgames.framework.Pixmap;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;

/**
 * A background for a weapons-depot level. Currently campaign level 3.
 * 
 * @author Zekoff
 * 
 */
public class WeaponsDepot extends DynamicStarfield {

	public float totalDistance = 0; // read-only
	public float distance = 0; // reset as needed
	public Area area = Area.preDepot;
	Pixmap backgroundLayer = null;
	Pixmap tunnelEntrance = null;
	Pixmap tunnelMid = null;
	Pixmap tunnelExit = null;
	public float backgroundDrawY = 0;
	public float backgroundLayer2DrawY = -150;
	public float tunnelY = 0;
	Paint layer1paint = new Paint();
	Paint layer2paint = new Paint();
	ColorFilter filter = new LightingColorFilter(0xFFb0b0b0, 0);
	ColorFilter filter2 = new LightingColorFilter(0xFF707070, 0);

	public enum Area {
		preDepot, depotEntrance, depotTunnel, depotHangar, depotControl, depotEscape
	}

	public WeaponsDepot() {
		super(40);
		layer1paint.setColorFilter(filter);
		layer2paint.setColorFilter(filter2);
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		totalDistance += deltaTime * speed;
		distance += deltaTime * speed;
		switch (area) {
		case preDepot:
			// scroll and brief
			break;
		case depotEntrance:
			if (speed > 70)
				speed -= deltaTime * 40;
			else {
				backgroundDrawY += deltaTime * speed;
				backgroundLayer2DrawY += deltaTime * speed * .7;
				if (backgroundDrawY > backgroundLayer.getHeight() * 2)
					backgroundDrawY = backgroundLayer.getHeight();
				if (backgroundLayer2DrawY > backgroundLayer.getHeight() * 2)
					backgroundLayer2DrawY = backgroundLayer.getHeight();
			}
			break;
		case depotTunnel: // mutually exclusive with depotHanger
			break;
		case depotHangar: // mutually exclusive with depotTunnel
			break;
		case depotControl:
			break;
		case depotEscape:
			if (speed < Background.REGULAR_SPEED)
				speed += deltaTime * 40;
			break;
		}
	}

	@Override
	public void drawBackground(Graphics g) {
		if (backgroundLayer == null)
			backgroundLayer = g.newPixmap("weapons_depot_background.png", null);
		if (tunnelEntrance == null)
			tunnelEntrance = g.newPixmap("weapons_depot_tunnel_entrance.png",
					null);
		if (tunnelMid == null)
			tunnelMid = g.newPixmap("weapons_depot_tunnel_mid.png", null);
		if (tunnelExit == null)
			tunnelExit = g.newPixmap("weapons_depot_tunnel_exit.png", null);
		super.drawBackground(g); // draw stars
		switch (area) {
		case preDepot:
			break;
		case depotEntrance:
			g.drawPixmap(backgroundLayer, -5, backgroundLayer2DrawY
					- backgroundLayer.getHeight(), layer2paint, 180, 160,
					backgroundLayer2DrawY - backgroundLayer.getHeight() / 2);
			g.drawPixmap(backgroundLayer, -5, backgroundLayer2DrawY
					- backgroundLayer.getHeight() * 2, layer2paint, 180, 160,
					backgroundLayer2DrawY - backgroundLayer.getHeight()
							- backgroundLayer.getHeight() / 2);
			g.drawPixmap(backgroundLayer, -10, backgroundDrawY
					- backgroundLayer.getHeight(), layer1paint);
			g.drawPixmap(backgroundLayer, -10, backgroundDrawY
					- backgroundLayer.getHeight() * 2, layer1paint);
			break;
		}
	}

	@Override
	public void drawForeground(Graphics g) {

	}

}

package net.zekoff.blockinvaders.combat.background;

import java.util.Random;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.androidgames.framework.Pixmap;
import net.zekoff.androidgames.framework.impl.AndroidPixmap;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Class to provide a static starfield as a framerate-friendly alternative to
 * the dynamic starfield.
 * 
 * @author Zekoff
 * 
 */
public class StaticStarfield extends Background {
	public static final int STAR_COLOR = 0xffb0b0b0;
	public float y;
	public Pixmap tile;
	public static final int NUM_OF_STARS = 40;

	/**
	 * Constructs a tile representation of a starfield that can be scrolled and
	 * stacked to simulate movement through space. At initialization, the object
	 * dynamically constructs a starfield and stores it in its tile field.
	 */
	public StaticStarfield() {
		Bitmap tempTile = Bitmap.createBitmap(320, 480, Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(tempTile);
		Paint paint = new Paint();
		paint.setColor(STAR_COLOR);
		Random rand = new Random();
		for (int i = 0; i < NUM_OF_STARS; i++) {
			canvas.drawPoint(rand.nextFloat() * 320, rand.nextFloat() * 480,
					paint);
		}
		tile = new AndroidPixmap(tempTile, Graphics.PixmapFormat.ARGB8888);
	}

	@Override
	public void update(float deltaTime) {
		y += speed * deltaTime;
		if (y > 480)
			y -= 480;
	}

	@Override
	public void drawBackground(Graphics g) {
		g.drawPixmap(tile, 0, (int) y);
		g.drawPixmap(tile, 0, (int) y - 480);
	}

}

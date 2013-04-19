package net.zekoff.blockinvaders.combat.loot;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.androidgames.framework.Pixmap;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.SoundAssets;
import net.zekoff.blockinvaders.combat.collision.Circle;
import net.zekoff.blockinvaders.combat.ship.Ship;
import android.graphics.Color;
import android.graphics.Paint;

public class MoneyPickup extends Pickup {

	public static final int size = 20;
	float width = size;
	float widthFactor = 1;
	Pixmap pixmap = null;
	Paint paint = new Paint();
	int value = 50;
	float widthShift = 1;

	public MoneyPickup(int value) {
		/*
		 * Bitmap bitmap = Bitmap .createBitmap(size, size,
		 * Bitmap.Config.ARGB_8888); Canvas canvas = new Canvas(bitmap); Paint
		 * paint = new Paint(); Typeface typeface = Typeface.create("monospace",
		 * Typeface.NORMAL); paint.setTypeface(typeface);
		 * paint.setTextAlign(Paint.Align.CENTER); paint.setTextSize(12);
		 * paint.setColor(Color.YELLOW); canvas.drawCircle(size / 2, size / 2,
		 * size / 2, paint); paint.setColor(Color.WHITE); canvas.drawText("$",
		 * size / 2, size / 2, paint); pixmap = new AndroidPixmap(bitmap,
		 * PixmapFormat.ARGB8888);
		 */

		hitbox = new Circle(15);
		borderColor = Color.WHITE;
		this.value = value;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		// change width
		widthFactor += widthShift * .8 * deltaTime;
		if (widthFactor > 1) {
			widthShift = -1;
			widthFactor = 1;
		}
		if (widthFactor < .15) {
			widthShift = 1;
			widthFactor = .15f;
		}
		width = size * widthFactor;
	}

	@Override
	public void draw(Graphics g) {
		g.drawOval(x - width / 2, y - size / 2, width, size, 0xFFFDD017);
		Assets.paint.setColor(borderColor);
		Assets.paint.setStrokeWidth(1);
		Assets.paint.setStyle(Paint.Style.STROKE);
		g.drawOval(x - width / 2, y - size / 2, width, size, Assets.paint);
		Assets.paint.setStyle(Paint.Style.FILL);
		Assets.paint.setTypeface(Assets.typeface);
		Assets.paint.setTextAlign(Paint.Align.CENTER);
		Assets.paint.setTextSize(12);
		Assets.paint.setColor(Color.WHITE);
		g.drawText("$", x, y + 5, Assets.paint);
	}

	public void collidesWith(Ship s) {
		isExpired = true;
		SoundAssets.get_loot.play(1);
		Assets.hud.queueMessage("Acquired $" + value);
		Assets.pilot.money += value;
	}
}

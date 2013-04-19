package net.zekoff.blockinvaders.combat.player;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class Rewards {
	public ArrayList<String> abilitiesGained = new ArrayList<String>();
	public int skillPointsGained = 0;
	public int hpGained = 0;
	public ArrayList<String> scenariosUnlocked = new ArrayList<String>();
	public ArrayList<String> weaponTypesUnlocked = new ArrayList<String>();
	public ArrayList<String> otherUnlocked = new ArrayList<String>();
	public ArrayList<String> perksUnlocked = new ArrayList<String>();
	public boolean isExpired = false;
	public int level = 0;
	Paint paint = new Paint();
	Typeface typeface = Typeface.create("sans", Typeface.NORMAL);

	public boolean startDrawing = false;
	public float counter = 0;

	public void update(float deltaTime) {
		counter += deltaTime;
		if (counter > 8)
			isExpired = true;
	}

	public void draw(Canvas canvas) {
		paint.setTypeface(typeface);
		paint.setTextSize(20);
		paint.setStyle(Paint.Style.FILL);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setColor(Color.WHITE);
		canvas.drawText("You've reached level " + level + "!", 160, 70, paint);
	}
}

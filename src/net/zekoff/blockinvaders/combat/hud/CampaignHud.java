package net.zekoff.blockinvaders.combat.hud;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.ability.Ability;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;

public class CampaignHud extends Hud {
	Typeface typeface = Typeface.create("monospace", Typeface.NORMAL);
	RectF oval = new RectF(0, 0, 40, 40);
	Paint paint = new Paint();
	Path lowerHudSeparator = new Path();
	Path hpOutline = new Path();
	Path hpFill = new Path();
	Path hudAbilitySeparator = new Path();
	Paint hudLinesPaint = new Paint();
	Paint hudBgPaint = new Paint();
	Paint hpOutlinePaint = new Paint();
	Paint hpFillPaint = new Paint();
	Path topHud = new Path();
	int dangerHpCounter = 0;

	public CampaignHud() {
		topHud.moveTo(-10, -10);
		topHud.lineTo(330, -10);
		topHud.lineTo(330, 20);
		topHud.lineTo(60, 20);
		topHud.lineTo(60, -10);
		topHud.lineTo(60, 20);
		topHud.lineTo(20, 50);
		topHud.lineTo(-10, 50);
		topHud.close();

		hudAbilitySeparator.moveTo(160, 480);
		hudAbilitySeparator.lineTo(160, 410);
		hudAbilitySeparator.close();

		hpOutline.moveTo(70, 395);
		hpOutline.lineTo(250, 395);
		hpOutline.lineTo(238, 403);
		hpOutline.lineTo(82, 403);
		hpOutline.close();
		hpOutlinePaint.setStyle(Paint.Style.STROKE);
		hpOutlinePaint.setColor(0xFFFFFFFF);
		hpOutlinePaint.setStrokeWidth(1);

		hpFillPaint.setStyle(Paint.Style.FILL);
		hpFillPaint.setColor(0x6000FF00);

		lowerHudSeparator.moveTo(-10, 390);
		lowerHudSeparator.lineTo(50, 390);
		lowerHudSeparator.lineTo(80, 410);
		lowerHudSeparator.lineTo(80, 480);
		lowerHudSeparator.lineTo(80, 410);

		// lowerHudSeparator.lineTo(160, 410);
		// lowerHudSeparator.lineTo(160, 480);
		// lowerHudSeparator.lineTo(160, 410);

		lowerHudSeparator.lineTo(240, 410);
		lowerHudSeparator.lineTo(240, 480);
		lowerHudSeparator.lineTo(240, 410);

		lowerHudSeparator.lineTo(270, 390);
		lowerHudSeparator.lineTo(330, 390);

		lowerHudSeparator.lineTo(330, 490);
		lowerHudSeparator.lineTo(-10, 490);
		lowerHudSeparator.close();

		hudLinesPaint.setStyle(Paint.Style.STROKE);
		hudLinesPaint.setColor(0x80FFFFFF);
		hudLinesPaint.setStrokeWidth(3);

		hudBgPaint.setStyle(Paint.Style.FILL);
		hudBgPaint.setColor(0xc0707070);

	}

	@Override
	public void draw(Graphics g) {

		g.drawPath(lowerHudSeparator, hudBgPaint);
		g.drawPath(lowerHudSeparator, hudLinesPaint);

		g.drawPath(topHud, hudBgPaint);
		g.drawPath(topHud, hudLinesPaint);

		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setTypeface(typeface);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTextSize(16);

		// display combo counter
		float rawFloat = Assets.playerShip.comboCounter.getCombo();
		String stringFloat = Float.toString(rawFloat);
		String decimal = stringFloat.substring(stringFloat.indexOf("."),
				stringFloat.length());
		g.drawText(
				Integer.toString((int) Math
						.floor(Assets.playerShip.comboCounter.getCombo()))
						+ "x", 20, 26, paint);
		g.drawArc(oval, 270, Float.parseFloat(decimal) * 360, true, 0x40FFFFFF);

		// display hp bar
		g.drawPath(hpOutline, hpOutlinePaint);
		float hpPercentage = Assets.playerShip.hp / Assets.playerShip.maxHp;
		float tr = 178 * hpPercentage + 71;
		float br = 0;
		if (tr >= 154)
			br = 154 + 81;
		else
			br = tr + 81;
		if (br > tr)
			br = tr;
		if (br < 83)
			br = 83;
		hpFill.reset();
		hpFill.moveTo(71, 396);
		hpFill.lineTo(tr, 396);
		hpFill.lineTo(br, 403);
		hpFill.lineTo(83, 403);
		hpFill.close();
		hpFillPaint.setColor(0x7000FF00);
		if (hpPercentage < 0.60)
			hpFillPaint.setColor(0x70FFC000);
		if (hpPercentage <= 0.30) {
			hpFillPaint.setColor(0x70FF0000);
			dangerHpCounter++;
			if (dangerHpCounter > 54) {
				hpFillPaint.setColor(0x70FFFFFF);
				if (dangerHpCounter > 60)
					dangerHpCounter = 0;
			}
		}
		if (hpPercentage > 0)
			g.drawPath(hpFill, hpFillPaint);

		// Draw ability UI
		paint.setTextSize(16);
		if (Assets.am.ability1 != null && Assets.am.ability2 != null) {
			if (Assets.am.ability1.icon != null) {
				g.drawPixmap(Assets.am.ability1.icon, 88, 414);
			} else
				g.drawText(Assets.am.ability1.name, 120, 435, paint);
			g.drawRect(80, 411, 80,
					70 * (1 - Assets.am.ability1.getCooldownPercentage()),
					0xA0FFFFFF);
			if (Assets.am.ability2 != null && Assets.am.ability2.icon != null) {
				g.drawPixmap(Assets.am.ability2.icon, 168, 414);
			} else
				g.drawText(Assets.am.ability2.name, 200, 465, paint);
			g.drawRect(160, 411, 80,
					70 * (1 - Assets.am.ability2.getCooldownPercentage()),
					0xA0FFFFFF);
			// draw line between them
			g.drawPath(hudAbilitySeparator, hudLinesPaint);
		} else if (Assets.am.ability1 == null && Assets.am.ability2 == null) {
			// draw nothing
		} else {
			// draw whichever one is equipped
			Ability ability = null;
			if (Assets.am.ability1 != null)
				ability = Assets.am.ability1;
			else
				ability = Assets.am.ability2;
			if (ability.icon != null) {
				g.drawPixmap(ability.icon, 128, 414);
			} else
				g.drawText(ability.name, 160, 435, paint);
			g.drawRect(80, 411, 160,
					70 * (1 - ability.getCooldownPercentage()), 0xA0FFFFFF);
		}

		super.draw(g);
	}

	@Override
	void displayHeader(Graphics g) {
		paint.setTypeface(headerTypeface);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTextSize(30);
		paint.setColor(headerList.get(0).color);
		if (!headerList.get(0).measured)
			setupText(headerList.get(0), paint);
		int y = 0;
		for (String s : headerList.get(0).textList) {
			g.drawText(s, 160, 120 + (y * paint.getFontSpacing()), paint);
			y++;
		}
	}

	@Override
	void displayText(Graphics g) {
		paint.setTypeface(textTypeface);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTextSize(16);
		paint.setColor(textList.get(0).color);
		if (!textList.get(0).measured)
			setupText(textList.get(0), paint);
		int y = 0;
		for (String s : textList.get(0).textList) {
			g.drawText(s, 160, 200 + (y * paint.getFontSpacing()), paint);
			y++;
		}
	}

	@Override
	void displayMessage(Graphics g) {
		paint.setTypeface(messageTypeface);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setTextSize(16);
		paint.setColor(messageList.get(0).color);
		if (!messageList.get(0).measured) {
			messageList.get(0).measured = true;
			int index = paint.breakText(messageList.get(0).text, true, 234,
					null);
			if (index < messageList.get(0).text.length()) {
				messageList.get(0).text = messageList.get(0).text.substring(0,
						(index - 3));
				messageList.get(0).text = messageList.get(0).text + "...";
			}
		}
		g.drawText(messageList.get(0).text, 64, 16, paint);
	}

	private void setupText(Text text, Paint paint) {
		text.measured = true;
		String s = text.text;
		int breakpoint;
		while (s.length() != (breakpoint = paint.breakText(s, true, 300, null))) {
			breakpoint = s.substring(0, breakpoint).lastIndexOf(" ");
			text.textList.add(s.substring(0, breakpoint));
			s = s.substring(breakpoint + 1);
		}
		text.textList.add(s);
	}

}

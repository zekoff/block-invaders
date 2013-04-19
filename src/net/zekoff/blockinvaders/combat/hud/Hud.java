package net.zekoff.blockinvaders.combat.hud;

import java.util.ArrayList;

import net.zekoff.androidgames.framework.Graphics;
import android.graphics.Color;
import android.graphics.Typeface;

public abstract class Hud {
	Typeface headerTypeface = Typeface.create("monospace", Typeface.BOLD);
	Typeface textTypeface = Typeface.create("sans", Typeface.NORMAL);
	Typeface messageTypeface = Typeface.create("sans", Typeface.NORMAL);
	float headerDuration = 0;
	float textDuration = 0;
	String header = null;
	String text = null;
	ArrayList<Hud.Text> headerList = new ArrayList<Hud.Text>();
	ArrayList<Hud.Text> textList = new ArrayList<Hud.Text>();
	ArrayList<Hud.Text> messageList = new ArrayList<Hud.Text>();

	public FlyupArea flyup = new FlyupArea();

	public static class Text {
		public String text = null;
		public ArrayList<String> textList = new ArrayList<String>();
		public float duration = 0;
		public float durationMax = 3;
		public boolean fadeIn = true;
		public boolean fadeOut = true;
		public float fadeSpeed = .5f;
		public int alpha = 0;
		public int color = Color.WHITE;
		public boolean isExpired = false;
		public boolean measured = false;

		public Text(String text) {
			this.text = text;
			alpha = 0;
		}

		public Text(String text, int color) {
			this(text);
			this.color = color;
		}

		public void update(float deltaTime) {
			if (fadeIn && alpha < 0xFF && duration == 0) {
				alpha += 0xFF / fadeSpeed * deltaTime;
				if (alpha > 0xFF)
					alpha = 0xFF;
				color = (alpha << 24) + (color & 0x00FFFFFF);
				return;
			}
			duration += deltaTime;
			if (fadeOut && duration > durationMax) {
				alpha -= 0xFF / fadeSpeed * deltaTime;
				if (alpha < 0)
					alpha = 0;
			}
			color = (alpha << 24) + (color & 0x00FFFFFF);

			if (duration > durationMax && alpha == 0)
				isExpired = true;
		}
	}

	public void update(float deltaTime) {
		if (headerList.size() > 0) {
			headerList.get(0).update(deltaTime);
			if (headerList.get(0).isExpired)
				headerList.remove(0);
		}
		if (textList.size() > 0) {
			textList.get(0).update(deltaTime);
			if (textList.get(0).isExpired)
				textList.remove(0);
		}
		if (messageList.size() > 0) {
			messageList.get(0).update(deltaTime);
			if (messageList.get(0).isExpired)
				messageList.remove(0);
		}
	}

	public void draw(Graphics g) {
		if (headerList.size() > 0) {
			displayHeader(g);
		}

		if (textList.size() > 0) {
			displayText(g);
		}

		if (messageList.size() > 0) {
			displayMessage(g);
		}
	}

	public void queueHeader(Text header) {
		header.update(0);
		headerList.add(header);
	}

	public void queueHeader(String header) {
		Text toAdd = new Text(header);
		// toAdd.fadeSpeed = 1;
		toAdd.update(0);
		headerList.add(toAdd);
	}

	public void queueText(Text text) {
		text.update(0);
		textList.add(text);
	}

	public void queueText(String text) {
		Text toAdd = new Text(text);
		toAdd.update(0);
		textList.add(toAdd);
	}

	public void queueMessage(Text text) {
		text.durationMax = 1.0f;
		text.fadeSpeed = .25f;
		text.update(0);
		messageList.add(text);
	}

	public void queueMessage(String message) {
		Text toAdd = new Text(message);
		toAdd.durationMax = 1.0f;
		toAdd.fadeSpeed = .25f;
		toAdd.update(0);
		messageList.add(toAdd);
	}

	public void clearTexts() {
		textList.clear();
	}

	public void clearHeaders() {
		headerList.clear();
	}

	public void clearMessages() {
		messageList.clear();
	}

	abstract void displayHeader(Graphics g);

	abstract void displayText(Graphics g);

	abstract void displayMessage(Graphics g);
}

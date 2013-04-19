package net.zekoff.blockinvaders.combat.hud;

import java.util.ArrayList;
import java.util.List;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.androidgames.framework.Input.TouchEvent;
import net.zekoff.androidgames.framework.Pixmap;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.loot.Pickup;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;

public class FlyupArea {
	public float moveSpeed = 960;
	public boolean open = false;
	public boolean moving = false;
	public static final float FULL_HEIGHT = 320;
	float height = 0;
	Path path = new Path();
	Paint paint = new Paint();
	Typeface font = Typeface.create("sans", Typeface.NORMAL);
	private ArrayList<FlyupListener> requestedNotification = new ArrayList<FlyupListener>();
	private ArrayList<Comm> comms = new ArrayList<Comm>();

	float playerTractorBeam = 0;

	public class Comm {
		boolean measured = false;
		Pixmap picture = null; // 64 x 64 pixels
		String text = "";
		String header = "INCOMING TRANSMISSION";
		ArrayList<String> parsedString = new ArrayList<String>();
		boolean isExpired = false;
		float timeCounter = 0;
		Paint paint = new Paint();
		Typeface font = Typeface.create("sans", Typeface.NORMAL);
		boolean prodded = false;

		public Comm() {

		}

		public Comm(String text) {
			this.text = text;
		}

		public Comm(String text, Pixmap picture) {
			this.text = text;
			this.picture = picture;
		}

		public Comm(String text, Pixmap picture, String header) {
			this.text = text;
			this.picture = picture;
			this.header = header;
		}

		public void update(float deltaTime) {
			timeCounter += deltaTime;
			List<TouchEvent> tes = Assets.game.getInput().getTouchEvents();
			if (tes.size() > 0 && timeCounter > .5f)
				for (TouchEvent te : tes) {
					if (te.type == TouchEvent.TOUCH_UP) {
						isExpired = true;
					}
				}
			if (timeCounter > 8 && !prodded) {
				prodToContinue();
			}
		}

		protected void prodToContinue() {
			prodded = true;
			Assets.hud.queueMessage("Touch screen to continue...");
		}

		public void draw(Graphics g) {
			paint.setTypeface(font);
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(Color.WHITE);

			paint.setTextSize(14);
			paint.setTextAlign(Paint.Align.CENTER);
			g.drawText(header, 160, 390 - height + 15, paint);

			float initialY = 110;
			if (picture != null) {
				initialY = 190;
				g.drawPixmap(picture, 128, 100);
			}

			paint.setTextSize(16);
			paint.setTextAlign(Paint.Align.LEFT);
			if (!measured)
				setupText();
			int line = 0;
			for (String s : parsedString) {
				g.drawText(s, 30, initialY + (line++ * paint.getFontSpacing()),
						paint);
			}
		}

		private void setupText() {
			measured = true;
			String s = text;
			int breakpoint;
			while (s.length() != (breakpoint = paint.breakText(s, true, 260,
					null))) {
				breakpoint = s.substring(0, breakpoint).lastIndexOf(" ");
				parsedString.add(s.substring(0, breakpoint));
				s = s.substring(breakpoint + 1);
			}
			parsedString.add(s);
		}
	}

	public class Choice extends Comm {

		static final float c1ub = 280;
		static final float c1bb = 310;
		static final float c2ub = 330;
		static final float c2bb = 360;
		static final float lb = 40;
		static final float rb = 280;
		boolean button1pressed = false;
		boolean button2pressed = false;

		String choice1 = "";
		String choice2 = "";

		public Choice(String text, String choice1, String choice2) {
			super(text);
			this.choice1 = choice1;
			this.choice2 = choice2;
		}

		@Override
		public void update(float deltaTime) {
			timeCounter += deltaTime;
			List<TouchEvent> tes = Assets.game.getInput().getTouchEvents();

			// If a touch_down event happened in button area, change its color
			for (TouchEvent te : tes) {
				if (te.type == TouchEvent.TOUCH_DOWN) {
					if (te.x >= lb && te.x <= rb) {
						if (te.y >= c1ub && te.y <= c1bb) {
							button1pressed = true;
						} else if (te.y >= c2ub && te.y <= c2bb) {
							button2pressed = true;
						}
					}
				}
			}

			// If touch_up, clear buttons and send choice if relevant
			for (TouchEvent te : tes) {
				if (te.type == TouchEvent.TOUCH_UP) {
					button1pressed = false;
					button2pressed = false;
					if (te.x >= lb && te.x <= rb) {
						if (te.y >= c1ub && te.y <= c1bb) {
							notifyChoice(choice1);
							isExpired = true;
						} else if (te.y >= c2ub && te.y <= c2bb) {
							notifyChoice(choice2);
							isExpired = true;
						}
					}
				}
			}
			if (timeCounter > 8 && !prodded) {
				prodToContinue();
			}
		}

		@Override
		public void draw(Graphics g) {
			super.draw(g);

			// Draw button fills
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(Color.BLACK);
			if (button1pressed)
				paint.setColor(0xFF606060);
			g.drawRect(lb, c1ub, rb - lb, c1bb - c1ub, paint);
			paint.setColor(Color.BLACK);
			if (button2pressed)
				paint.setColor(0xFF606060);
			g.drawRect(lb, c2ub, rb - lb, c2bb - c2ub, paint);

			// Draw button outlines
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(Color.WHITE);
			paint.setStrokeWidth(2);
			g.drawRect(lb, c1ub, rb - lb, c1bb - c1ub, paint);
			g.drawRect(lb, c2ub, rb - lb, c2bb - c2ub, paint);

			// Put text in buttons
			paint.setTypeface(font);
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(Color.WHITE);
			paint.setTextSize(14);
			paint.setTextAlign(Paint.Align.CENTER);
			g.drawText(choice1, 160, c1bb - 10, paint);
			g.drawText(choice2, 160, c2bb - 10, paint);
		}

		@Override
		protected void prodToContinue() {
			prodded = true;
			Assets.hud.queueMessage("Touch choice to continue...");
		}
	}

	public FlyupArea() {

	}

	public void update(float deltaTime) {
		moving = false;
		if (open) {
			Assets.control.left = false;
			Assets.control.right = false;
		}

		if (comms.size() > 0) {
			if (!open)
				open(deltaTime);
			else {
				comms.get(0).update(deltaTime);
			}
			if (comms.get(0).isExpired)
				comms.remove(0);
		}
		if (comms.size() == 0 && open)
			close(deltaTime);
	}

	public void draw(Graphics g) {
		if (open || moving) {
			// draw path background
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(0xB0202030);
			g.drawPath(path, paint);
			// draw path outline
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(0x80FFFFFF);
			paint.setStrokeWidth(3);
			g.drawPath(path, paint);
		}
		if (open && comms.size() > 0) {
			comms.get(0).draw(g);
		}
	}

	private void open(float deltaTime) {
		moving = true;
		height += deltaTime * moveSpeed;
		if (height > FULL_HEIGHT) {
			height = FULL_HEIGHT;
			open = true;
			notifyFinished();
		}
		setPath();
	}

	private void close(float deltaTime) {
		moving = true;
		height -= deltaTime * moveSpeed;
		if (height < 0) {
			height = 0;
			open = false;
			notifyFinished();
		}
		setPath();
	}

	private void setPath() {
		path.reset();
		path.moveTo(20, 390 - height);
		path.lineTo(300, 390 - height);
		if (height > 20) {
			path.lineTo(300, 390 - height + 20);
			path.lineTo(20, 390 - height + 20);
			path.lineTo(300, 390 - height + 20);
		}
		path.lineTo(300, 390);
		path.lineTo(270, 390);
		path.lineTo(240, 410);
		path.lineTo(80, 410);
		path.lineTo(50, 390);
		path.lineTo(20, 390);
		path.close();
	}

	public void registerForNotification(FlyupListener listener) {
		requestedNotification.add(listener);
	}

	public void unregisterForNotification(FlyupListener listener) {
		requestedNotification.remove(listener);
	}

	protected void notifyFinished() {
		for (FlyupListener f : requestedNotification) {
			if (open) {
				f.onFlyupOpen();
				Assets.bm.enemyBullets.clear();
				for (Pickup p : Assets.em.pickups) {
					p.pulledByTractorBeam = true;
				}
				Assets.playerShip.comboCounter.freezeCombo();
			} else {
				f.onFlyupClosed();
				Assets.playerShip.comboCounter.unfreezeCombo();
			}
		}
	}

	protected void notifyChoice(String choice) {
		for (FlyupListener f : requestedNotification) {
			f.onChoice(choice);
		}
	}

	public void queueComm(String text) {
		comms.add(new Comm(text));
	}

	public void queueComm(Comm comm) {
		comms.add(comm);
	}

	public void queueChoice(String text, String choice1, String choice2) {
		comms.add(new Choice(text, choice1, choice2));
	}
}

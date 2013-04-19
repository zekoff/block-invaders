package net.zekoff.androidgames.framework.impl;

import net.zekoff.androidgames.framework.Audio;
import net.zekoff.androidgames.framework.FileIO;
import net.zekoff.androidgames.framework.Game;
import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.androidgames.framework.Input;
import net.zekoff.androidgames.framework.Screen;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

/**
 * The base Activity in which the game screen runs. Handles setup of the view
 * area, and is filled by a full-screen SurfaceView upon which game elements are
 * drawn and refreshed. Suitable for subclassing by any component of the game
 * that is viewed on a Screen.
 * 
 * @author Zekoff
 * 
 */
public abstract class BlockInvadersGame extends Activity implements Game {
	public AndroidFastRenderView renderView;
	Graphics graphics;
	Audio audio;
	Input input;
	FileIO fileIO;
	Screen screen;
	WakeLock wakeLock;
	Handler handler;

	public boolean commitChanges = false;

	public static final int VICTORY = 0;
	public static final int DEFEAT = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
		int frameBufferWidth = isLandscape ? 480 : 320;
		int frameBufferHeight = isLandscape ? 320 : 480;
		Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
				frameBufferHeight, Config.RGB_565);

		float scaleX = (float) frameBufferWidth
				/ getWindowManager().getDefaultDisplay().getWidth();
		float scaleY = (float) frameBufferHeight
				/ getWindowManager().getDefaultDisplay().getHeight();

		renderView = new AndroidFastRenderView(this, frameBuffer);
		graphics = new AndroidGraphics(getAssets(), frameBuffer);
		fileIO = new AndroidFileIO(getAssets());
		audio = new AndroidAudio(this);
		input = new AndroidInput(this, renderView, scaleX, scaleY);
		screen = getStartScreen();
		setContentView(renderView);

		PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,
				"Block Invaders");
		handler = new Handler();
	}

	@Override
	public void onResume() {
		super.onResume();
		wakeLock.acquire();
		screen.resume();
		renderView.resume();
	}

	@Override
	public void onPause() {
		super.onPause();
		wakeLock.release();
		renderView.pause();
		screen.pause();

		if (isFinishing())
			screen.dispose();
	}

	@Override
	public Input getInput() {
		return input;
	}

	@Override
	public FileIO getFileIO() {
		return fileIO;
	}

	@Override
	public Graphics getGraphics() {
		return graphics;
	}

	@Override
	public Audio getAudio() {
		return audio;
	}

	@Override
	public void setScreen(Screen screen) {
		if (screen == null)
			throw new IllegalArgumentException("Screen must not be null");

		this.screen.pause();
		this.screen.dispose();
		screen.resume();
		this.screen = screen;
	}

	@Override
	public Screen getCurrentScreen() {
		return screen;
	}

	@Override
	public abstract Screen getStartScreen();

	/**
	 * Return the Handler for this Activity, which is used to post Toast windows
	 * as Runnables, etc.
	 * 
	 * @return The Handler associated with this Activity.
	 */
	public Handler getHandler() {
		return handler;
	}

	/**
	 * After mission has ended (by mission completion, death, aborting mission
	 * through UI, etc.) this method is called so that control can be passed to
	 * a level-up screen or whatever the next Screen needs to be.
	 * 
	 * @param condition
	 */
	public abstract void gameOver(int condition);

	/**
	 * This method is called when the game activity is completely done and no
	 * longer needed. It should start the next necessary Activity (if
	 * applicable) and finish() itself.
	 */
	public abstract void endGame();

}

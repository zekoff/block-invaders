package net.zekoff.androidgames.framework.impl;

import java.io.FileWriter;
import java.io.PrintWriter;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class AndroidFastRenderView extends SurfaceView implements Runnable {
	BlockInvadersGame game;
	Bitmap framebuffer;
	Thread renderThread = null;
	SurfaceHolder holder;
	volatile boolean running = false;

	public AndroidFastRenderView(BlockInvadersGame game, Bitmap framebuffer) {
		super(game);
		this.game = game;
		this.framebuffer = framebuffer;
		this.holder = getHolder();
	}

	public void resume() {
		running = true;
		renderThread = new Thread(this);

		boolean logErrors = false;
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(game);
		logErrors = prefs.getBoolean("log_errors", false);

		// Dump error trace to SD card (for use when not connected to Eclipse)
		if (logErrors) {
			renderThread
					.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

						@Override
						public void uncaughtException(Thread thread,
								final Throwable ex) {
							PrintWriter pw;
							try {
								pw = new PrintWriter(new FileWriter(Environment
										.getExternalStorageDirectory()
										+ "/bi.log"));
								ex.printStackTrace(pw);
								pw.flush();
								pw.close();
								game.getHandler().post(new Runnable() {

									@Override
									public void run() {
										ScrollView sv = new ScrollView(game);
										LinearLayout l = new LinearLayout(game);
										l.setOrientation(LinearLayout.VERTICAL);
										TextView e = new TextView(game);
										e.setText("Error report:\n\n");
										StackTraceElement[] stack = ex
												.getStackTrace();
										String trace = "";
										for (StackTraceElement s : stack) {
											trace += s.toString() + "\n\n";
										}
										e.setText(e.getText()
												+ trace
												+ "Error report logged to SD card in 'bi.log'");
										l.addView(e);
										Button b = new Button(game);
										b.setText("CLOSE");
										b.setOnClickListener(new OnClickListener() {
											@Override
											public void onClick(View v) {
												game.getHandler().post(
														new Runnable() {
															@Override
															public void run() {
																game.finish();
															}
														});
											}
										});
										l.addView(b);
										sv.addView(l);
										game.setContentView(sv);
									}
								});
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
		}

		renderThread.start();
	}

	@Override
	public void run() {
		Rect dstRect = new Rect();
		long startTime = System.nanoTime();
		while (running) {
			if (!holder.getSurface().isValid())
				continue;
			float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
			startTime = System.nanoTime();

			game.getCurrentScreen().update(deltaTime);
			game.getCurrentScreen().present(deltaTime);

			Canvas canvas = holder.lockCanvas();
			canvas.getClipBounds(dstRect);
			canvas.drawBitmap(framebuffer, null, dstRect, null);
			holder.unlockCanvasAndPost(canvas);
		}
	}

	public void pause() {
		running = false;
		while (true) {
			try {
				renderThread.join();
				break;
			} catch (InterruptedException e) {
				// retry
			}
		}
	}

}

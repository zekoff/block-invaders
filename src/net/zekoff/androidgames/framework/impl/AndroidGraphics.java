package net.zekoff.androidgames.framework.impl;

import java.io.IOException;
import java.io.InputStream;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.androidgames.framework.Pixmap;
import net.zekoff.blockinvaders.combat.entity.Component;
import net.zekoff.blockinvaders.combat.entity.Component.ComponentType;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

public class AndroidGraphics implements Graphics {
	AssetManager assets;
	Bitmap frameBuffer;
	Canvas canvas;
	Paint paint;
	Rect srcRect = new Rect();
	Rect dstRect = new Rect();
	Matrix identityMatrix = new Matrix();
	RectF rectF = new RectF();
	boolean aa = true;
	Path trianglePath;

	public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) {
		this.assets = assets;
		this.frameBuffer = frameBuffer;
		this.canvas = new Canvas(frameBuffer);
		this.paint = new Paint();
		trianglePath = new Path();
		trianglePath.moveTo(0, 1.2f);
		trianglePath.lineTo(1.2f, -.75f);
		trianglePath.lineTo(-1.2f, -.75f);
		trianglePath.close();
	}

	@Override
	public Pixmap newPixmap(String fileName, PixmapFormat format) {
		Config config = null;
		if (format == PixmapFormat.RGB565)
			config = Config.RGB_565;
		else if (format == PixmapFormat.ARGB4444)
			config = Config.ARGB_4444;
		else
			config = Config.ARGB_8888;

		Options options = new Options();
		options.inPreferredConfig = config;

		InputStream in = null;
		Bitmap bitmap = null;
		try {
			in = assets.open(fileName);
			bitmap = BitmapFactory.decodeStream(in);
			if (bitmap == null)
				throw new RuntimeException("Couldn't load bitmap from asset '"
						+ fileName + "'");
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load bitmap from asset '"
					+ fileName + "'");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// blah
				}
			}
		}
		if (bitmap.getConfig() == Config.RGB_565)
			format = PixmapFormat.RGB565;
		else if (bitmap.getConfig() == Config.ARGB_4444)
			format = PixmapFormat.ARGB4444;
		else
			format = PixmapFormat.ARGB8888;
		return new AndroidPixmap(bitmap, format);
	}

	@Override
	public void clear(int color) {
		canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
				(color & 0xff));
	}

	@Override
	public void drawPixel(float x, float y, int color) {
		paint.setColor(color);
		canvas.drawPoint(x, y, paint);
	}

	@Override
	public void drawLine(float x, float y, float x2, float y2, int color) {
		paint.setColor(color);
		canvas.drawLine(x, y, x2, y2, paint);
	}

	@Override
	public void drawRect(float x, float y, float width, float height, int color) {
		paint.setColor(color);
		paint.setStyle(Style.FILL);
		canvas.drawRect(x, y, x + width, y + height, paint);
	}

	@Override
	public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
			int srcWidth, int srcHeight) {
		srcRect.left = srcX;
		srcRect.top = srcY;
		srcRect.right = srcX + srcWidth - 1;
		srcRect.bottom = srcY + srcHeight - 1;

		dstRect.left = x;
		dstRect.top = y;
		dstRect.right = x + srcWidth - 1;
		dstRect.bottom = y + srcHeight - 1;

		canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, srcRect, dstRect,
				null);
	}

	@Override
	public void drawPixmap(Pixmap pixmap, float x, float y, Paint paint) {
		canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, x, y, paint);
	}

	@Override
	public void drawPixmap(Pixmap pixmap, float x, float y) {
		canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, x, y, null);
	}

	@Override
	public void drawPixmap(Pixmap pixmap, float x, float y, int rotation,
			float centerX, float centerY) {
		canvas.rotate(rotation, centerX, centerY);
		canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, x, y, null);
		canvas.setMatrix(identityMatrix);
	}

	@Override
	public void drawPixmap(Pixmap pixmap, float x, float y, Paint paint,
			int rotation, float centerX, float centerY) {
		canvas.rotate(rotation, centerX, centerY);
		canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, x, y, paint);
		canvas.setMatrix(identityMatrix);
	}

	@Override
	public int getWidth() {
		return frameBuffer.getWidth();
	}

	@Override
	public int getHeight() {
		return frameBuffer.getHeight();
	}

	@Override
	public void drawCircle(float x, float y, float radius, int color) {
		paint.setColor(color);
		canvas.drawCircle(x, y, radius, paint);
	}

	@Override
	public void drawLine(float x, float y, float x2, float y2, Paint paint) {
		paint.setAntiAlias(aa);
		canvas.drawLine(x, y, x2, y2, paint);
	}

	@Override
	public void drawRect(float x, float y, float width, float height,
			Paint paint) {
		paint.setAntiAlias(aa);
		canvas.drawRect(x, y, x + width, y + height, paint);
	}

	@Override
	public void drawCircle(float x, float y, float radius, Paint paint) {
		paint.setAntiAlias(aa);
		canvas.drawCircle(x, y, radius, paint);
	}

	@Override
	public void drawText(String text, float x, float y, Paint paint) {
		paint.setAntiAlias(aa);
		canvas.drawText(text, x, y, paint);
	}

	@Override
	public void drawArc(RectF oval, float startAngle, float sweepAngle,
			boolean useCenter, Paint paint) {
		paint.setAntiAlias(aa);
		canvas.drawArc(oval, startAngle, sweepAngle, useCenter, paint);
	}

	@Override
	public void drawArc(RectF oval, float startAngle, float sweepAngle,
			boolean useCenter, int color) {
		paint.setColor(color);
		canvas.drawArc(oval, startAngle, sweepAngle, useCenter, paint);
	}

	@Override
	public void drawRect(float x, float y, float width, float height,
			int color, int degrees, float centerX, float centerY) {
		canvas.rotate(degrees, centerX, centerY);
		paint.setColor(color);
		canvas.drawRect(x, y, x + width, y + height, paint);
		canvas.setMatrix(identityMatrix);
	}

	@Override
	public void drawRect(float x, float y, float width, float height,
			Paint paint, int degrees, float centerX, float centerY) {
		paint.setAntiAlias(aa);
		canvas.rotate(degrees, centerX, centerY);
		canvas.drawRect(x, y, x + width, y + height, paint);
		canvas.setMatrix(identityMatrix);
	}

	@Override
	public void drawOval(float x, float y, float width, float height, int color) {
		paint.setColor(color);
		rectF.left = x;
		rectF.top = y;
		rectF.right = x + width;
		rectF.bottom = y + height;
		canvas.drawOval(rectF, paint);
	}

	@Override
	public void drawOval(float x, float y, float width, float height,
			Paint paint) {
		paint.setAntiAlias(aa);
		rectF.left = x;
		rectF.top = y;
		rectF.right = x + width;
		rectF.bottom = y + height;
		canvas.drawOval(rectF, paint);
	}

	@Override
	public void drawOval(float x, float y, float width, float height,
			int color, int rotation, int centerX, int centerY) {
		canvas.rotate(rotation, centerX, centerY);
		paint.setColor(color);
		rectF.left = x;
		rectF.top = y;
		rectF.right = x + width;
		rectF.bottom = y + height;
		canvas.drawOval(rectF, paint);
		canvas.setMatrix(identityMatrix);
	}

	@Override
	public void drawOval(float x, float y, float width, float height,
			Paint paint, int rotation, int centerX, int centerY) {
		paint.setAntiAlias(aa);
		canvas.rotate(rotation, centerX, centerY);
		rectF.left = x;
		rectF.top = y;
		rectF.right = x + width;
		rectF.bottom = y + height;
		canvas.drawOval(rectF, paint);
		canvas.setMatrix(identityMatrix);
	}

	@Override
	public void setAntialiasing(boolean aa) {
		this.aa = aa;
		paint.setAntiAlias(aa);
	}

	@Override
	public void drawTriangle(float x, float y, float size, int color) {
		trianglePath.offset(x, y);
		canvas.scale(-size, -size, x, y);
		paint.setColor(color);
		canvas.drawPath(trianglePath, paint);
		canvas.setMatrix(identityMatrix);
		trianglePath.offset(-x, -y);
	}

	@Override
	public void drawTriangle(float x, float y, float size, Paint paint) {
		trianglePath.offset(x, y);
		canvas.scale(-size, -size, x, y);
		paint.setAntiAlias(aa);
		float sw = paint.getStrokeWidth();
		paint.setStrokeWidth(sw / size);
		canvas.drawPath(trianglePath, paint);
		paint.setStrokeWidth(sw);
		canvas.setMatrix(identityMatrix);
		trianglePath.offset(-x, -y);
	}

	@Override
	public void drawTriangle(float x, float y, float size, int color,
			int degrees, float centerX, float centerY) {
		trianglePath.offset(x, y);
		canvas.rotate(degrees, centerX, centerY);
		canvas.scale(-size, -size, x, y);
		paint.setColor(color);
		canvas.drawPath(trianglePath, paint);
		canvas.setMatrix(identityMatrix);
		trianglePath.offset(-x, -y);

	}

	@Override
	public void drawTriangle(float x, float y, float size, Paint paint,
			int degrees, float centerX, float centerY) {
		trianglePath.offset(x, y);
		canvas.rotate(degrees, centerX, centerY);
		canvas.scale(-size, -size, x, y);
		paint.setAntiAlias(aa);
		float sw = paint.getStrokeWidth();
		paint.setStrokeWidth(sw / size);
		canvas.drawPath(trianglePath, paint);
		paint.setStrokeWidth(sw);
		canvas.setMatrix(identityMatrix);
		trianglePath.offset(-x, -y);
	}

	@Override
	public void drawCircle(float x, float y, float radius, int color,
			int rotation, float centerX, float centerY) {
		canvas.rotate(rotation, centerX, centerY);
		paint.setColor(color);
		canvas.drawCircle(x, y, radius, paint);
		canvas.setMatrix(identityMatrix);
	}

	@Override
	public void drawCircle(float x, float y, float radius, Paint paint,
			int rotation, float centerX, float centerY) {
		paint.setAntiAlias(aa);
		canvas.rotate(rotation, centerX, centerY);
		canvas.drawCircle(x, y, radius, paint);
		canvas.setMatrix(identityMatrix);
	}

	@Override
	public void drawPath(Path path, float x, float y, Paint paint,
			int rotation, float centerX, float centerY) {
		paint.setAntiAlias(aa);
		canvas.translate(x, y);
		canvas.rotate(rotation, centerX, centerY);
		canvas.drawPath(path, paint);
		canvas.setMatrix(identityMatrix);
	}

	@Override
	public void drawPath(Path path, Paint paint) {
		paint.setAntiAlias(aa);
		canvas.drawPath(path, paint);
	}

	@Override
	public void drawRoundRect(float x, float y, float width, float height,
			Paint paint) {
		rectF.left = x;
		rectF.right = x + width;
		rectF.top = y;
		rectF.bottom = y + height;
		paint.setAntiAlias(aa);
		canvas.drawRoundRect(rectF, 15, 15, paint);
	}

	@Override
	public void drawRoundRect(RectF rect, Paint paint) {
		paint.setAntiAlias(aa);
		canvas.drawRoundRect(rect, 8, 8, paint);
	}

	@Override
	public void drawRoundRect(RectF rect, float bevel, Paint paint) {
		paint.setAntiAlias(aa);
		canvas.drawRoundRect(rect, bevel, bevel, paint);
	}

	@Override
	public void drawComponent(Component c, float x, float y, Paint paint,
			int rotation) {
		if (c.type == ComponentType.circle)
			drawCircle(x + c.x, y + c.y, c.radius, paint, rotation, x, y);
		else if (c.type == ComponentType.rect) {
			canvas.translate(x, y);
			canvas.rotate(rotation);
			canvas.translate(c.x, c.y);
			canvas.rotate(c.baseRotation);
			canvas.drawRect(-c.width / 2, -c.height / 2, c.width / 2,
					c.height / 2, paint);
			canvas.setMatrix(identityMatrix);
		} else if (c.type == ComponentType.triangle) {
			canvas.translate(x, y);
			canvas.rotate(rotation);
			canvas.translate(c.x, c.y);
			canvas.rotate(c.baseRotation);
			canvas.scale(-c.radius, -c.radius);
			paint.setAntiAlias(aa);
			float sw = paint.getStrokeWidth();
			paint.setStrokeWidth(sw / c.radius);
			canvas.drawPath(trianglePath, paint);
			paint.setStrokeWidth(sw);
			canvas.setMatrix(identityMatrix);
		}
	}
}

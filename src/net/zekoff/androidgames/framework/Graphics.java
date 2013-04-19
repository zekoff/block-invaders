package net.zekoff.androidgames.framework;

import net.zekoff.blockinvaders.combat.entity.Component;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public interface Graphics {
	public static enum PixmapFormat {
		ARGB8888, ARGB4444, RGB565
	}

	public Pixmap newPixmap(String fileName, PixmapFormat format);

	public void clear(int color);

	public void drawPixel(float x, float y, int color);

	public void drawLine(float x, float y, float x2, float y2, int color);

	public void drawLine(float x, float y, float x2, float y2, Paint paint);

	public void drawRect(float x, float y, float width, float height, int color);

	public void drawRect(float x, float y, float width, float height,
			Paint paint);

	public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
			int srcWidth, int srcHeight);

	public void drawPixmap(Pixmap pixmap, float x, float y);

	public void drawCircle(float x, float y, float radius, int color);

	public void drawCircle(float x, float y, float radius, int color,
			int rotation, float centerX, float centerY);

	public void drawCircle(float x, float y, float radius, Paint paint);

	public void drawCircle(float x, float y, float radius, Paint paint,
			int rotation, float centerX, float centerY);

	public void drawArc(RectF oval, float startAngle, float sweepAngle,
			boolean useCenter, Paint paint);

	public void drawArc(RectF oval, float startAngle, float sweepAngle,
			boolean useCenter, int color);

	public void drawText(String text, float x, float y, Paint paint);

	public int getWidth();

	public int getHeight();

	void drawRect(float x, float y, float width, float height, int color,
			int degrees, float centerX, float centerY);

	void drawRect(float x, float y, float width, float height, Paint paint,
			int degrees, float centerX, float centerY);

	void drawOval(float x, float y, float width, float height, int color);

	void drawOval(float x, float y, float width, float height, Paint paint);

	void drawOval(float x, float y, float width, float height, int color,
			int rotation, int centerX, int centerY);

	void drawOval(float x, float y, float width, float height, Paint paint,
			int rotation, int centerX, int centerY);

	void drawTriangle(float x, float y, float size, int color);

	void drawTriangle(float x, float y, float size, Paint paint);

	void drawTriangle(float x, float y, float size, int color, int degrees,
			float centerX, float centerY);

	void drawTriangle(float x, float y, float size, Paint paint, int degrees,
			float centerX, float centerY);

	void setAntialiasing(boolean aa);

	void drawPixmap(Pixmap pixmap, float x, float y, Paint paint);

	void drawPath(Path path, Paint paint);

	void drawRoundRect(float x, float y, float width, float height, Paint paint);

	void drawRoundRect(RectF rectF, Paint paint);

	void drawRoundRect(RectF rect, float bevel, Paint paint);

	void drawPath(Path path, float x, float y, Paint paint, int rotation,
			float centerX, float centerY);

	void drawComponent(Component c, float x, float y, Paint paint, int rotation);

	void drawPixmap(Pixmap pixmap, float x, float y, int rotation,
			float centerX, float centerY);

	void drawPixmap(Pixmap pixmap, float x, float y, Paint paint, int rotation,
			float centerX, float centerY);
}

package net.zekoff.androidgames.framework.impl;

import net.zekoff.androidgames.framework.Graphics.PixmapFormat;
import net.zekoff.androidgames.framework.Pixmap;
import android.graphics.Bitmap;

public class AndroidPixmap implements Pixmap {
	Bitmap bitmap;
	PixmapFormat format;

	public AndroidPixmap(Bitmap bitmap, PixmapFormat format) {
		this.bitmap = bitmap;
		this.format = format;
	}

	@Override
	public int getWidth() {
		return bitmap.getWidth();
	}

	@Override
	public int getHeight() {
		return bitmap.getHeight();
	}

	@Override
	public PixmapFormat getFormat() {
		return format;
	}

	@Override
	public void dispose() {
		bitmap.recycle();
	}

}

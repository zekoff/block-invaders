package net.zekoff.androidgames.framework;

import net.zekoff.androidgames.framework.Graphics.PixmapFormat;

public interface Pixmap {
	public int getWidth();
	
	public int getHeight();
	
	public PixmapFormat getFormat();
	
	public void dispose();
}

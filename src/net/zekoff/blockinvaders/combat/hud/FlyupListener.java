package net.zekoff.blockinvaders.combat.hud;

public interface FlyupListener {
	public void onFlyupOpen();

	public void onFlyupClosed();
	
	public void onChoice(String choice);
}

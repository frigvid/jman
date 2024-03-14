package com.frigvid.jman.game;

/**
 * A class to represent a tick of the game.
 *
 * @author frigvid
 * @created 2024-02-29
 */
public class TickController
{
	private long lastTickTime = 0;
	private static final long TICK_INTERVAL_MS = 25; // 25 milliseconds per tick
	
	private static TickController instance;
	
	private TickController() {}
	
	public static synchronized TickController getInstance()
	{
		if (instance == null)
		{
			instance = new TickController();
		}
		
		return instance;
	}
	
	/**
	 * A method to use in an if-check before moving entities.
	 *
	 * @return True if it's time to move, false otherwise.
	 */
	public boolean onNextTick()
	{
		long currentTime = System.currentTimeMillis();
		
		if (currentTime - lastTickTime >= TICK_INTERVAL_MS)
		{
			lastTickTime = currentTime;
			return true;
		}
		
		return false;
	}
}

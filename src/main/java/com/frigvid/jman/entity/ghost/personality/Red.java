package com.frigvid.jman.entity.ghost.personality;

import com.frigvid.jman.entity.ghost.Ghost;
import com.frigvid.jman.game.map.Map;

/**
 * Red Ghost, otherwise named "Blinky."
 * <p/>
 * Blinky is the most aggressive of the four ghosts.
 * It will actively chase the player, and will not
 * stop until it catches them. Because of this, it
 * has the shortest action delay.
 */
public class Red
	extends Ghost
{
	public Red(Map map)
	{
		super(map);
		
		setGhostSprite("blinky/blinky.png");
		
		// Personality.
		setActionDelay(10);
		enableChaseMode();
	}
}

package com.frigvid.jman.entity.ghost.personality;

import com.frigvid.jman.entity.ghost.Ghost;
import com.frigvid.jman.game.map.Map;

/**
 * Orange Ghost, otherwise named "Clyde."
 * <p/>
 * Clyde switches between chasing the player and running away from them.
 * It has a default duration.
 */
public class Orange
	extends Ghost
{
	public Orange(Map map)
	{
		super(map);
		
		setGhostSprite("clyde/clyde.png");
		
		// Personality.
		setActionDelay(16);
		setRandomness(2.0);
		enableChaseMode();
	}
}

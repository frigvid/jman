package com.frigvid.jman.entity.ghost.personality;

import com.frigvid.jman.entity.ghost.Ghost;
import com.frigvid.jman.game.map.Map;

/**
 * Pink Ghost, otherwise named "Pinky."
 * <p/>
 * Pinky tries to ambush the player, placing itself in front of them.
 * It has a default duration.
 */
public class Pink
	extends Ghost
{
	public Pink(Map map)
	{
		super(map);
		
		setGhostSprite("pinky/pinky.png");
		
		// Personality.
		setActionDelay(12);
		enableChaseMode();
	}
}

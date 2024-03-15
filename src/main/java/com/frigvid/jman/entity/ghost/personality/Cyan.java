package com.frigvid.jman.entity.ghost.personality;

import com.frigvid.jman.entity.ghost.Ghost;
import com.frigvid.jman.game.map.Map;

/**
 * Cyan Ghost, otherwise named "Inky."
 * <p/>
 * Inky tries to ambush the player, placing itself in front of them.
 * It has a default duration.
 */
public class Cyan
	extends Ghost
{
	public Cyan(Map map)
	{
		super(map);
		
		setGhostSprite("inky/inky.png");
		
		// Personality.
		setActionDelay(12);
		enableChaseMode();
	}
}

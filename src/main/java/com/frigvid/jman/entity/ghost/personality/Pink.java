package com.frigvid.jman.entity.ghost.personality;

import com.frigvid.jman.entity.ghost.Ghost;
import com.frigvid.jman.game.map.Map;
import javafx.scene.image.ImageView;

// Pinky.
public class Pink
	extends Ghost
{
	public Pink(Map map)
	{
		super(map);
		
		setGhostSprite("pinky/pinky.png");
		
	}
	
	public Pink(Map map, ImageView entitySprite)
	{
		super(map, entitySprite);
	}
}

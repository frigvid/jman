package com.frigvid.jman.entity.ghost;

import com.frigvid.jman.entity.Direction;
import com.frigvid.jman.entity.Entity;
import com.frigvid.jman.game.map.Map;
import com.frigvid.jman.game.map.TileType;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

public class Ghost
	extends Entity
{
	public Ghost(Map map, ImageView entitySprite)
	{
		super(map, entitySprite);
	}
	
	@Override
	public void load(Group gameBoard)
	{
	
	}
	
	@Override
	public void move(Direction direction, Map map)
	{
	
	}
	
	public void setSpawn(TileType[][] logicGrid)
	{
	
	}
}

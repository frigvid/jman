package com.frigvid.jman.entity.ghost;

import com.frigvid.jman.entity.Direction;
import com.frigvid.jman.entity.Entity;
import com.frigvid.jman.level.Level;
import com.frigvid.jman.map.TileMap;
import com.frigvid.jman.map.TileType;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

public class Ghost
	extends Entity
{
	public Ghost(Level level, ImageView entitySprite)
	{
		super(level, entitySprite);
	}
	
	@Override
	public void load(Group gameBoard)
	{
	
	}
	
	@Override
	public void move(Direction direction, Level level, TileMap tileMap)
	{
	
	}
	
	@Override
	protected void setSpawn(TileType tileType)
	{
	
	}
	
	@Override
	protected void validateMove()
	{
	
	}
}

package com.frigvid.jman.entity;

import com.frigvid.jman.Constants;
import com.frigvid.jman.level.Level;
import com.frigvid.jman.map.TileType;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

public abstract class Entity
{
	protected Level level;
	protected int spawnRow;
	protected int spawnColumn;
	protected ImageView entitySprite;
	
	public Entity(Level level, ImageView entitySprite)
	{
		this.level = level;
		this.entitySprite = entitySprite;
		setStartingPosition();
		updateSpritePosition();
	}
	
	public abstract void load(Group gameBoard);
	protected abstract void setSpawn(TileType tileType);
	
	protected void setStartingPosition()
	{
		this.spawnRow = level.getPlayerSpawnRow();
		this.spawnColumn = level.getPlayerSpawnCol();
	}
	
	public void move(Direction direction, Level level)
	{
		int rows = level.getLevelWidth();
		int columns = level.getLevelHeight();
		int nextRow = spawnRow;
		int nextColumn = spawnColumn;
		
		switch (direction)
		{
			case LEFT -> nextColumn = Math.max(0, spawnColumn - 1);
			case RIGHT -> nextColumn = Math.min(rows - 1, spawnColumn + 1);
			case UP -> nextRow = Math.max(0, spawnRow - 1);
			case DOWN -> nextRow = Math.min(columns - 1, spawnRow + 1);
		}
		
		if (nextRow >= 0 && nextColumn >= 0)
		{
			TileType nextTile = level.getTileType(nextColumn, nextRow);
			
			if (Constants.DEBUG_ENABLED)
			{
				System.out.println("Next tile: " + nextTile);
			}
			
			if (nextTile != TileType.WALL)
			{
				spawnRow = nextRow;
				spawnColumn = nextColumn;
				updateSpritePosition();
			}
		}
	}
	
	/**
	 * Validate entity-specific movement.
	 */
	protected abstract void validateMove();
	
	public void updateSpritePosition()
	{
		entitySprite.setX(spawnColumn * Constants.TILE_SIZE * Constants.SCALE_FACTOR);
		entitySprite.setY(spawnRow * Constants.TILE_SIZE * Constants.SCALE_FACTOR);
	}
	
	/* Setters. */
	protected void setSprite(ImageView imageView)
	{
		this.entitySprite = imageView;
	}
	
	/* Getters. */
	public ImageView getSprite()
	{
		return entitySprite;
	}
}

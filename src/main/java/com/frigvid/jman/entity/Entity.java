package com.frigvid.jman.entity;

import com.frigvid.jman.Constants;
import com.frigvid.jman.level.Level;
import com.frigvid.jman.map.TileMap;
import com.frigvid.jman.map.TileType;
import com.frigvid.jman.view.GameBoard;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

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
	public abstract void move(Direction direction, Level level, TileMap tileMap);
	protected abstract void setSpawn(TileType tileType);
	
	protected void setStartingPosition()
	{
		this.spawnRow = level.getPlayerSpawnRow();
		this.spawnColumn = level.getPlayerSpawnCol();
	}
	
	/**
	 * Validate entity-specific movement.
	 */
	protected abstract void validateMove();
	
	protected void updateSpritePosition()
	{
		entitySprite.setX(spawnColumn * Constants.TILE_SIZE * Constants.SCALE_FACTOR);
		entitySprite.setY(spawnRow * Constants.TILE_SIZE * Constants.SCALE_FACTOR);
	}
	
	protected void teleportIfNecessary(int nextColumn, int nextRow)
	{
		if (level.getTileType(nextColumn, nextRow) == TileType.TELEPORT)
		{
			Pair<Integer, Integer> newLocation = level.findRandomTeleportLocation(nextColumn, nextRow);
			
			if (newLocation != null)
			{
				spawnColumn = newLocation.getKey();
				spawnRow = newLocation.getValue();
			}
		}
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

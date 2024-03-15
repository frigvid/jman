package com.frigvid.jman.entity;

import com.frigvid.jman.Constants;
import com.frigvid.jman.game.map.Map;
import com.frigvid.jman.game.map.TileType;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

public abstract class Entity
	implements IEntity
{
	protected Map map;
	protected int spawnRow;
	protected int spawnColumn;
	protected ImageView entitySprite;
	
	public Entity(Map map, ImageView entitySprite)
	{
		this.map = map;
		this.entitySprite = entitySprite;
	}
	
	public abstract void load(Group gameBoard);
	public abstract void setSpawn(TileType[][] logicGrid);
	
	protected void updateSpritePosition()
	{
		if (Constants.DEBUG_ENABLED && Constants.DEBUG_LEVEL >= 1)
		{
			System.out.println(
				"Entity: Updating sprite position!"
				+ "\n┣ Column (X): " + spawnColumn
				+ "\n┗ Row (Y): " + spawnRow
			);
		}
		
		entitySprite.setY(spawnRow * Constants.TILE_SIZE * Constants.SCALE_FACTOR);
		entitySprite.setX(spawnColumn * Constants.TILE_SIZE * Constants.SCALE_FACTOR);
	}
	
	protected void teleportIfNecessary(int nextColumn, int nextRow)
	{
		if (map.getTileType(nextColumn, nextRow) == TileType.TELEPORT)
		{
			Pair<Integer, Integer> newLocation = map.findRandomTeleportLocation(nextColumn, nextRow);
			
			if (newLocation != null)
			{
				spawnColumn = newLocation.getKey();
				spawnRow = newLocation.getValue();
			}
		}
	}
	
	/* Setters. */
	protected void setSprite(ImageView entitySprite)
	{
		this.entitySprite = entitySprite;
	}
	
	/* Getters. */
	public ImageView getSprite()
	{
		return entitySprite;
	}
}

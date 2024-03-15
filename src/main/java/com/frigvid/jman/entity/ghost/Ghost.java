package com.frigvid.jman.entity.ghost;

import com.frigvid.jman.Constants;
import com.frigvid.jman.entity.Direction;
import com.frigvid.jman.entity.Entity;
import com.frigvid.jman.game.map.Map;
import com.frigvid.jman.game.map.TileType;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Ghost
	extends Entity
{
	//protected String spritePath;
	
	public Ghost(Map map)
	{
		super(map, null);
	}
	
	public Ghost(Map map, ImageView entitySprite)
	{
		super(map, entitySprite);
	}
	
	@Override
	public void load(Group gameBoard)
	{
		setSpawn(map.getLogicGrid());
		
		gameBoard.getChildren()
			.add(this.getSprite());
	}
	
	@Override
	public void move(Direction direction, Map map)
	{
	
	}
	
	/**
	 * Sets the spawn location for the ghost.
	 * <p/>
	 * Do note that this sets a random spawn location, if
	 * TileType.SPAWN_GHOST_1 and TileType.SPAWN_GHOST_2 are
	 * present in the logic grid.
	 *
	 * @param logicGrid The logic grid of the map.
	 */
	public void setSpawn(TileType[][] logicGrid)
	{
		int spawn1Row = 0;
		int spawn1Column = 0;
		int spawn2Row = 0;
		int spawn2Column = 0;
		
		// Iterate through the logic grid to find the spawn locations.
		for (int row = 0; row < logicGrid.length; row++)
		{
			for (int col = 0; col < logicGrid[0].length; col++)
			{
				if (logicGrid[row][col] == TileType.SPAWN_GHOST_1)
				{
					spawn1Row = row;
					spawn1Column = col;
				}
				else if (logicGrid[row][col] == TileType.SPAWN_GHOST_2)
				{
					spawn2Row = row;
					spawn2Column = col;
				}
			}
		}
		
		// Randomize spawn location.
		if (Math.random() > 0.5)
		{
			spawnRow = spawn1Row;
			spawnColumn = spawn1Column;
		}
		else
		{
			spawnRow = spawn2Row;
			spawnColumn = spawn2Column;
		}
		
		if (Constants.DEBUG_ENABLED)
		{
			System.out.println(
				"Ghost: Setting spawn!"
				+ "\n┣ Ghost spawn row: " + spawnRow
				+ "\n┣ Ghost spawn column: " + spawnColumn
			);
		}
		
		updateSpritePosition();
	}
	
	/**
	 * Wrapper method for {@link #setSprite(ImageView)}.
	 * <p/>
	 * This method has a basepath, so you only need to specify the path to the sprite
	 * relative to the enitity/ghost resource directory. E.g. "blinky/blinky.png" and
	 * not "/com/frigvid/jman/entity/ghost/blinky/blinky.png".
	 *
	 * @param spritePath The path to the sprite relative to entity/ghost.
	 */
	protected void setGhostSprite(String spritePath)
	{
		//setSpritePath("/com/frigvid/jman/entity/ghost/" + spritePath);
		
		try
		{
			Image image = new Image(
				Objects.requireNonNull(
					getClass().getResourceAsStream("/com/frigvid/jman/entity/ghost/" + spritePath)
				)
			);
			
			ImageView sprite = new ImageView(image);
			sprite.setFitWidth(Constants.TILE_SIZE * Constants.SCALE_FACTOR);
			sprite.setFitHeight(Constants.TILE_SIZE * Constants.SCALE_FACTOR);
			
			setSprite(sprite);
		}
		catch (Exception e)
		{
			System.out.println("Setting the ghost sprite failed!");
			e.printStackTrace(System.err);
		}
	}
	
	/* Setters. */
	//private void setSpritePath(String spritePath)
	//{
	//	this.spritePath = spritePath;
	//}
}

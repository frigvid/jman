package com.frigvid.jman.entity.ghost;

import com.frigvid.jman.Constants;
import com.frigvid.jman.entity.Direction;
import com.frigvid.jman.entity.Entity;
import com.frigvid.jman.entity.player.Player;
import com.frigvid.jman.game.TickController;
import com.frigvid.jman.game.map.Map;
import com.frigvid.jman.game.map.TileType;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

import java.util.*;

public class Ghost
	extends Entity
{
	protected boolean chaseMode = false;
	protected boolean isAfraid;
	private boolean isDead;
	protected int actionDelay;
	
	public Ghost(Map map)
	{
		super(map, null);
	}
	
	public Ghost(Map map, ImageView entitySprite)
	{
		super(map, entitySprite);
	}
	
	/* Utility. */
	/**
	 * Loads the ghost onto the game board.
	 *
	 * @param gameBoard The game board to load the ghost onto.
	 */
	@Override
	public void load(Group gameBoard)
	{
		setSpawn(map.getLogicGrid());
		
		gameBoard.getChildren()
			.add(this.getSprite());
	}
	
	/**
	 * Moves the ghost in a given direction.
	 * <p/>
	 * This method is called by the {@link TickController} to move the ghost.
	 * The ghost will move in the given direction if the next tile is not a wall.
	 *
	 * @param direction The {@link Direction} to move in.
	 * @param map The Map object to move in.
	 */
	@Override
	public void move(Direction direction, Map map)
	{
		// NOTE: Not sure what I was smoking at the time, rows and columns are inversed. It works though.
		int rows = map.getMapWidth();
		int columns = map.getMapHeight();
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
			TileType nextTile = map.getTileType(nextColumn, nextRow);
			
			if (Constants.DEBUG_ENABLED && Constants.DEBUG_AI)
			{
				System.out.println("Next tile: " + nextTile);
			}
			
			if (nextTile != TileType.WALL)
			{
				spawnRow = nextRow;
				spawnColumn = nextColumn;
				teleportIfNecessary(spawnColumn, spawnRow);
				updateSpritePosition();
			}
		}
	}
	
	/**
	 * Starts the ghost's "AI."
	 *
	 * @param player The Player Entity to track.
	 */
	public void start(Player player)
	{
		if (player.isAlive())
		{
			TickController.getInstance().onNextTick(this, ghost ->
			{
				//trackPlayer(player, map);
				
				// Schedule the next move.
				start(player);
			}, actionDelay);
		}
	}
	
	/**
	 * Returns a random {@link Direction} for the ghost to move in.
	 *
	 * @return A random {@link Direction}.
	 */
	public Direction randomDirection()
	{
		return Direction.values()[new Random().nextInt(Direction.values().length)];
	}
	
	/* Setters. */
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
	
	protected void enableChaseMode()
	{
		this.chaseMode = true;
	}
	
	protected void setActionDelay(int tickDelay)
	{
		if (tickDelay >= 1 && tickDelay <= 40)
		{
			this.actionDelay = tickDelay;
		}
		else
		{
			this.actionDelay = 10;
		}
	}
}

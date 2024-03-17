package com.frigvid.jman.entity.player;

import com.frigvid.jman.Constants;
import com.frigvid.jman.Main;
import com.frigvid.jman.entity.Direction;
import com.frigvid.jman.entity.Entity;
import com.frigvid.jman.game.TickController;
import com.frigvid.jman.game.map.Map;
import com.frigvid.jman.game.map.TileType;
import com.frigvid.jman.view.GameBoard;
import com.frigvid.jman.view.views.MapCompletion;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Player
	extends Entity
{
	private boolean isAlive = true;
	private boolean isInvincible = false;
	
	public Player(Map map)
	{
		super(map, null);
		
		// This sets the "entitySprite" in super.
		setPlayerSprite(Constants.RESOURCE_BASE_PATH + "entity/player/jman.gif");
	}
	
	/**
	 * Sets the spawn position of the Player Entity.
	 * <p/>
	 * Iterates through the logic grid and sets the
	 * {@code TileType.SPAWN_PLAYER} to the spawn
	 * position.
	 *
	 * @param logicGrid The logic grid of the map.
	 */
	public void setSpawn(TileType[][] logicGrid)
	{
		for (int row = 0; row < logicGrid.length; row++)
		{
			for (int col = 0; col < logicGrid[0].length; col++)
			{
				if (logicGrid[row][col] == TileType.SPAWN_PLAYER)
				{
					spawnRow = row;
					spawnColumn = col;
				}
			}
		}
		
		if (Constants.DEBUG_ENABLED)
		{
			System.out.println(
				"Player: Setting spawn!"
				+ "\n┣ Player spawn row: " + spawnRow
				+ "\n┗ Player spawn column: " + spawnColumn
			);
		}
		
		updateSpritePosition();
	}
	
	/**
	 * Moves the Player Entity in the specified direction.
	 * <p/>
	 * If the next tile is not a wall, the Player Entity
	 * will move to the next tile.
	 *
	 * @param direction The direction to move the Player Entity.
	 * @param map The map to move the Player Entity on.
	 */
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
			
			if (Constants.DEBUG_ENABLED)
			{
				System.out.println("Next tile: " + nextTile);
			}
			
			if (nextTile != TileType.WALL)
			{
				spawnRow = nextRow;
				spawnColumn = nextColumn;
				
				// Check if you've completed the map.
				if
				(
					map.getCountPellets() == 0 &&
					map.getCountPowerups() == 0 &&
					nextTile == TileType.TELEPORT
				)
				{
					// Once all pellets and power-ups are collected, the player must enter
					// a TileType.TELEPORT tile to complete the map.
					//this.killPlayer();
					TickController.getInstance().stop();
					System.out.println("Map complete!");
					
					/* Switch to the map completion view.
					 * Normally would do this by saving it to IViewState,
					 * but map completion requires some extra details. */
					MapCompletion mapComplete = new MapCompletion();
					mapComplete.setCurrentMap(map);
					mapComplete.setMapScore(GameBoard.getHighScore());
					mapComplete.setPlayer(this);
					mapComplete.start(Main.disgustingHack);
				}
				else
				{
					teleportIfNecessary(spawnColumn, spawnRow);
				}
				
				updateSpritePosition();
				
				// Assert values for use in lambda.
				int finalNextRow = nextRow;
				int finalNextColumn = nextColumn;
				
				// Delete pellets and powerups, and increase score.
				map.getVisualGrid().getChildren().removeIf(node ->
				{
					if (node instanceof Circle circle)
					{
						boolean intersectsPlayer = circle.getBoundsInParent()
							.intersects(entitySprite.getBoundsInParent());
						
						if (intersectsPlayer)
						{
							map.replaceLogicGridElement(finalNextRow, finalNextColumn, TileType.OPEN_SPACE);
							
							if (circle.getRadius() == Constants.PELLET_SIZE)
							{
								GameBoard.increaseScoreBy(Constants.SCORE_PELLET);
								map.decreasePelletCountBy(1);
								return true;
							}
							else if (circle.getRadius() == Constants.POWERUP_SIZE)
							{
								GameBoard.increaseScoreBy(Constants.SCORE_POWERUP);
								map.decreasePowerupCountBy(1);
								beInvincible(Constants.PLAYER_INVINCIBLE_DURATION);
								return true;
							}
						}
					}
					return false;
				});
			}
		}
	}
	
	/**
	 * Loads the Player Entity onto the game board.
	 *
	 * @param gameBoard The game board to load the Player Entity onto.
	 */
	@Override
	public void load(Group gameBoard)
	{
		setSpawn(map.getLogicGrid());
		
		gameBoard.getChildren()
					.add(this.getSprite());
	}
	
	/**
	 * Sets the sprite for the Player Entity.
	 *
	 * @param spritePath The path to the sprite.
	 */
	private void setPlayerSprite(String spritePath)
	{
		// FIXME: This should be extracted to its own class. This is re-used in multiple classes.
		try
		{
			Image playerImage = new Image(
				Objects.requireNonNull(
					getClass().getResourceAsStream(spritePath)
				)
			);
			
			ImageView sprite = new ImageView(playerImage);
			sprite.setFitWidth(Constants.TILE_SIZE * Constants.SCALE_FACTOR);
			sprite.setFitHeight(Constants.TILE_SIZE * Constants.SCALE_FACTOR);
			
			setSprite(sprite);
		}
		catch (Exception e)
		{
			System.out.println("Setting the player sprite failed!");
			e.printStackTrace(System.err);
		}
	}
	
	/* Setters. */
	/**
	 * If the Player Entity intercects with a Ghost Entity,
	 * they should call for this method so that the Player
	 * "dies."
	 */
	public void killPlayer()
	{
		isAlive = false;
		
		MapCompletion mapComplete = new MapCompletion();
		mapComplete.setCurrentMap(map);
		mapComplete.setMapScore(GameBoard.getHighScore());
		mapComplete.setPlayer(this);
		mapComplete.start(Main.disgustingHack);
	}
	
	/**
	 * Toggles a boolean for the Player entity's invincibility state.
	 * <p/>
	 * This is used in conjunction with {@link #isInvincible()} in the
	 * Ghost entity's {@link com.frigvid.jman.entity.ghost.Ghost#trackPlayer(Player, Map)}
	 * method.
	 * <p/>
	 * Example usage:
	 * {@snippet id="beInvincibleExample" lang="java" :
	 * 	if (player.isInvincible())
	 * 	{
	 * 		System.out.println("Player: I'm invincible!");
	 * 	}
	 * }
	 * @param duration The duration of the invincibility.
	 */
	public void beInvincible(int duration)
	{
		if (Constants.DEBUG_ENABLED)
		{
			System.out.println("Player: Invincibility enabled!");
		}
		
		isInvincible = true;
		
		// Disable invincibility after a set duration.
		try
		{
			// NOTE: Normally you'd use this with a try-with-resource block, however, if that is done
			//			the thread is paused until execution is finished. Effectively pausing the game in
			//			the meantime.
			ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
			executorService.schedule(() ->
			{
				if (Constants.DEBUG_ENABLED)
				{
					System.out.println("Player: Invincibility disabled!");
				}
				
				isInvincible = false;
			}, duration, TimeUnit.SECONDS);
			
			executorService.shutdown();
		}
		catch (Exception e)
		{
			System.out.println("Player: Executor service failed!");
			e.printStackTrace(System.err);
		}
	}
	
	/* Getters. */
	/**
	 * Returns the current row of the Player Entity.
	 *
	 * @return The current row of the Player Entity.
	 */
	public int getCurrentRow()
	{
		return spawnRow;
	}
	
	/**
	 * Returns the current column of the Player Entity.
	 *
	 * @return The current column of the Player Entity.
	 */
	public int getCurrentColumn()
	{
		return spawnColumn;
	}
	
	/**
	 * Used by Ghost entities to determine if the Player
	 * Entity is "alive" and thus if the hunt should
	 * continue.
	 *
	 * @return Whether the Player Entity is "alive."
	 */
	public boolean isAlive()
	{
		return isAlive;
	}
	
	public boolean isInvincible()
	{
		return isInvincible;
	}
}

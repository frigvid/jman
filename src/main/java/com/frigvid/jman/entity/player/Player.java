package com.frigvid.jman.entity.player;

import com.frigvid.jman.Constants;
import com.frigvid.jman.entity.Direction;
import com.frigvid.jman.entity.Entity;
import com.frigvid.jman.game.map.Map;
import com.frigvid.jman.game.map.TileType;
import com.frigvid.jman.view.GameBoard;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.util.Objects;

public class Player
	extends Entity
{
	private final String spritePath = "/com/frigvid/jman/entity/player/jman.gif";
	
	public Player(Map map)
	{
		super(map, null);
	}
	
	public Player(Map map, ImageView playerSprite)
	{
		super(map, playerSprite);
	}
	
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
			
			if (Constants.DEBUG_ENABLED)
			{
				System.out.println("Next tile: " + nextTile);
			}
			
			if (nextTile != TileType.WALL)
			{
				spawnRow = nextRow;
				spawnColumn = nextColumn;
				teleportIfNecessary(spawnColumn, spawnRow);
				updateSpritePosition();
				
				// Delete pellets and powerups, and increase score.
				map.getVisualGrid().getChildren().removeIf(node ->
				{
					if (node instanceof Circle circle)
					{
						boolean intersectsPlayer = circle.getBoundsInParent()
							.intersects(entitySprite.getBoundsInParent());
						
						if (intersectsPlayer)
						{
							if (circle.getRadius() == Constants.PELLET_SIZE)
							{
								GameBoard.increaseScoreBy(Constants.SCORE_PELLET);
								return true;
							}
							else if (circle.getRadius() == Constants.POWERUP_SIZE)
							{
								GameBoard.increaseScoreBy(Constants.SCORE_POWERUP);
								return true;
							}
						}
					}
					return false;
				});
			}
		}
	}
	
	@Override
	public void load(Group gameBoard)
	{
		setPlayerSprite(spritePath);
		setSpawn(map.getLogicGrid());
		
		gameBoard.getChildren()
					.add(this.getSprite());
	}
	
	private void setPlayerSprite(String spritePath)
	{
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
			System.out.println("Setting the player sprite failed. Error:\n" + e.getMessage());
		}
	}
}

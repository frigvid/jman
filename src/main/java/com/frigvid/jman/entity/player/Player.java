package com.frigvid.jman.entity.player;

import com.frigvid.jman.Constants;
import com.frigvid.jman.entity.Direction;
import com.frigvid.jman.entity.Entity;
import com.frigvid.jman.level.Level;
import com.frigvid.jman.map.TileMap;
import com.frigvid.jman.map.TileType;
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
	//private ImageView sprite;
	
	public Player() {
		super(null, null);
	};
	
	public Player(Level level, ImageView imageView)
	{
		super(level, imageView);
		//setSpawn(TileType.SPAWN_PLAYER);
	}
	
	public void setSpawn(TileType tileType)
	{
		String position = level.findTilePosition(tileType);
		String[] positionArray = position.split(",");
		if (Constants.DEBUG_ENABLED)
		{
			System.out.println("Player spawn position: " + position);
			System.out.println("Player spawn row: " + positionArray[0]);
			System.out.println("Player spawn column: " + positionArray[1]);
		}
		
		this.spawnRow = Integer.parseInt(positionArray[0]);
		this.spawnColumn = Integer.parseInt(positionArray[1]);
		//this.spawnRow = level.getPlayerSpawnRow();
		//this.spawnColumn = level.getPlayerSpawnCol();
	}
	
	@Override
	public void move(Direction direction, Level level, TileMap tileMap)
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
				teleportIfNecessary(spawnColumn, spawnRow);
				updateSpritePosition();
				
				// Delete pellets and powerups, and increase score.
				tileMap.getRoot().getChildren().removeIf(node ->
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
	protected void validateMove()
	{
		// 💀
	}
	
	@Override
	public void load(Group gameBoard)
	{
		setPlayerSprite(spritePath);
		
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
			
			//this.sprite = sprite;
		}
		catch (Exception e)
		{
			System.out.println("Setting the player sprite failed. Error:\n" + e.getMessage());
		}
	}
}

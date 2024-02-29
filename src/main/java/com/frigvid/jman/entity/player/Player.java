package com.frigvid.jman.entity.player;

import com.frigvid.jman.Constants;
import com.frigvid.jman.entity.Entity;
import com.frigvid.jman.level.Level;
import com.frigvid.jman.map.TileType;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Player
	extends Entity
{
	private final String spritePath = "/com/frigvid/jman/entity/player/jman.gif";
	//private ImageView sprite;
	
	public Player(Level level, ImageView imageView)
	{
		super(level, imageView);
		//setSpawn(TileType.SPAWN_PLAYER);
	}
	
	@Override
	protected void setSpawn(TileType tileType)
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

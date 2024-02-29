package com.frigvid.jman.entity;

import com.frigvid.jman.Constants;
import com.frigvid.jman.level.Level;
import com.frigvid.jman.map.TileType;
import javafx.scene.image.ImageView;

public class Entity
{
	private int row;
	private int column;
	private ImageView imageView;
	
	public Entity(int row, int column, ImageView imageView)
	{
		this.row = row;
		this.column = column;
		this.imageView = imageView;
		updateImagePosition();
	}
	
	public void move(Direction direction, Level level)
	{
		int rows = level.getLevelWidth();
		int columns = level.getLevelHeight();
		int nextRow = row;
		int nextColumn = column;
		
		switch (direction)
		{
			case LEFT -> nextColumn = Math.max(0, column - 1);
			case RIGHT -> nextColumn = Math.min(rows - 1, column + 1);
			case UP -> nextRow = Math.max(0, row - 1);
			case DOWN -> nextRow = Math.min(columns - 1, row + 1);
		}
		
		if (nextRow >= 0 && nextColumn >= 0)
		{
			TileType nextTile = level.getLevelElement(nextColumn, nextRow);
			
			if (Constants.DEBUG_ENABLED)
			{
				System.out.println("Next tile: " + nextTile);
			}
			
			if (nextTile != TileType.WALL)
			{
				row = nextRow;
				column = nextColumn;
				updateImagePosition();
			}
		}
	}
	
	private void updateImagePosition()
	{
		imageView.setX(column * Constants.TILE_SIZE * Constants.SCALE_FACTOR);
		imageView.setY(row * Constants.TILE_SIZE * Constants.SCALE_FACTOR);
	}
	
	public ImageView getImageView()
	{
		return imageView;
	}
}

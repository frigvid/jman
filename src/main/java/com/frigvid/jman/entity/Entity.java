package com.frigvid.jman.entity;

import com.frigvid.jman.Constants;
import javafx.scene.image.ImageView;

public class Entity
{
	private int row;
	private int column;
	private ImageView imageView; // To hold the player's image
	
	public Entity(int row, int column, ImageView imageView) {
		this.row = row;
		this.column = column;
		this.imageView = imageView;
		updateImagePosition();
	}
	
	public void move(Direction direction, int rows, int columns) {
		switch (direction) {
			case LEFT -> column = Math.max(0, column - 1);
			case RIGHT -> column = Math.min(rows - 1, column + 1);
			case UP -> row = Math.max(0, row - 1);
			case DOWN -> row = Math.min(columns - 1, row + 1);
		}
		updateImagePosition();
	}
	
	private void updateImagePosition() {
		imageView.setX(column * Constants.TILE_SIZE * Constants.SCALE_FACTOR);
		imageView.setY(row * Constants.TILE_SIZE * Constants.SCALE_FACTOR);
	}
	
	public ImageView getImageView() {
		return imageView;
	}
}

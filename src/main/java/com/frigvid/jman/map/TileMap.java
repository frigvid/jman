package com.frigvid.jman.map;

import com.frigvid.jman.Constants;
import com.frigvid.jman.level.Level;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * This is more like the map "grid." Though it's not wrong
 * to say it's tile-based though. Probably.
 *
 * This first iteration is a static tilemap. Meaning maps
 * are limited in size, to not exceed the screen size, and
 * thus needing a dynamic camera.
 *
 * - Dynamically sized, using a level's width and height.
 * - Defines the tile size.
 * - Lets you "draw" to a tile.
 * - Visual grid.
 * - Logic grid.
 */
public class TileMap
{
	private double tileSize = Constants.TILE_SIZE * Constants.SCALE_FACTOR;
	private Pane root;
	private Level level;

	public TileMap(Level level)
	{
		this.level = level;
	}

	/* Utilities. */
	/**
	 * Renders the tilemap.
	 * <br/><br/>
	 * NOTE: This should probably use Canvas instead.
	 */
	public Pane render() {
		root = new Pane();

		for (int row = 0; row < level.getLevelHeight(); row++) {
			for (int col = 0; col < level.getLevelWidth(); col++) {
				double x = col * tileSize;
				double y = row * tileSize;

				Rectangle tile = new Rectangle(x, y, tileSize, tileSize);
				tile.setFill(Color.BLACK);
				if (Constants.DEBUG_ENABLED && Constants.DEBUG_LEVEL == 1)
				{
					// Set border to tiles.
					tile.setStyle("-fx-stroke: grey; -fx-stroke-width: 5;");
				}
				root.getChildren().add(tile);

				switch (level.getLevelElement(col, row)) {
					case WALL:
						tile.setFill(
							Color.valueOf("#0000bf")
						);
						tile.setSmooth(false); // Fixes tiny spacing issue when scaling dynamically.
						break;
					case SMALL_DOT:
						Circle point = new Circle(x + Constants.TILE_SIZE, y + Constants.TILE_SIZE, 5);
						point.setFill(Color.GOLD);
						root.getChildren().add(point);
						break;
					case BIG_DOT:
						Circle powerUp = new Circle(x + Constants.TILE_SIZE, y + Constants.TILE_SIZE, 10);
						powerUp.setFill(Color.ORANGE);
						root.getChildren().add(powerUp);
						break;
					case SPAWN_GHOST_1:
					case SPAWN_GHOST_2:
						if (Constants.DEBUG_ENABLED)
						{
							Rectangle spawn = new Rectangle(x, y, tileSize, tileSize);
							spawn.setFill(Color.RED);
							root.getChildren().add(spawn);
						}
						break;
					case SPAWN_PLAYER:
						if (Constants.DEBUG_ENABLED)
						{
							Rectangle spawn = new Rectangle(x, y, tileSize, tileSize);
							spawn.setFill(Color.GREEN);
							root.getChildren().add(spawn);
						}
						break;
					case EXIT:
					case TELEPORT:
						if (Constants.DEBUG_ENABLED)
						{
							Rectangle teleport = new Rectangle(x, y, tileSize, tileSize);
							teleport.setFill(Color.PURPLE);
							root.getChildren().add(teleport);
						}
						break;
					case OPEN_SPACE:
					default:
						break;
				}
			}
		}

		root.setPrefSize(level.getLevelWidth() * tileSize, level.getLevelHeight() * tileSize);

		return root;
	}

	/* Setters. */
	/* Getters. */
}
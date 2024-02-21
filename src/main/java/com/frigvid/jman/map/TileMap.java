package com.frigvid.jman.map;

import com.frigvid.jman.Constants;
import com.frigvid.jman.level.Level;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
	private final Level level;

	public TileMap(Level level)
	{
		this.level = level;
	}

	/* Utilities. */

	/**
	 * Renders the tilemap.
	 */
	public void render()
	{
		for (int col = 0; col < level.getLevelHeight(); col++)
		{
			for (int row = 0; row < level.getLevelWidth(); row++)
			{
				String title = level.getTitle();
				int x = col * 25; // Tile size.
				int y = row * 25; // Tile size.
				System.out.println("Title: " + title + " X: " + x + " Y: " + y);
			};
		};
	}

	public void renderMeDaddy() {
		int tileSize = 25 * (int) Constants.SCALE_FACTOR;

		Stage primaryStage = new Stage();
		primaryStage.setTitle("TileMap Renderer");

		Pane root = new Pane();

		for (int row = 0; row < level.getLevelHeight(); row++) {
			for (int col = 0; col < level.getLevelWidth(); col++) {
				int x = col * tileSize;
				int y = row * tileSize;

				Rectangle tile = new Rectangle(x, y, tileSize, tileSize);

				//System.out.println(tileType);
				switch (level.getLevelElement(col, row)) {
					case WALL:
						tile.setFill(Color.BLUE);
						break;
					case OPEN_SPACE:
						tile.setFill(Color.GRAY);
						break;
					case SMALL_DOT:
						tile.setFill(Color.YELLOW);
						break;
					case BIG_DOT:
						tile.setFill(Color.ORANGE);
						break;
					case SPAWN_GHOST_1:
					case SPAWN_GHOST_2:
						tile.setFill(Color.RED);
						break;
					case SPAWN_PLAYER:
						tile.setFill(Color.GREEN);
						break;
					case EXIT:
					case TELEPORT:
						tile.setFill(Color.PURPLE);
						break;
					default:
						tile.setFill(Color.BLACK);
						break;
				}

				root.getChildren().add(tile);
			}
		}

		// Create a scene and set it to the stage
		Scene scene = new Scene(root, level.getLevelWidth() * tileSize, level.getLevelHeight() * tileSize); // Adjust size as needed
		primaryStage.setScene(scene);

		// Show the window
		primaryStage.show();
	}

	/* Setters. */
	/* Getters. */
}

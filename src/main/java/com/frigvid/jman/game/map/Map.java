package com.frigvid.jman.game.map;

import com.frigvid.jman.Constants;
import com.frigvid.jman.entity.ghost.Ghost;
import com.frigvid.jman.entity.ghost.personality.Cyan;
import com.frigvid.jman.entity.ghost.personality.Orange;
import com.frigvid.jman.entity.ghost.personality.Pink;
import com.frigvid.jman.entity.ghost.personality.Red;
import com.frigvid.jman.entity.player.Player;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import static java.lang.System.out;

/**
 * Map object.
 * <p/>
 * This contains both the visual and logic grid.
 * It also contains some logic for certain operations.
 * <p/>
 * This combines the previous TileMap and Level classes.
 *
 * @author frigvid
 * @created 2024-03-07
 */
public class Map
{
	// Map grids.
	private Pane visualGrid;
	private TileType[][] logicGrid;
	
	// Logic grid.
	private String title;
	private IntegerProperty counterPellets = new SimpleIntegerProperty(0);
	private IntegerProperty counterPowerups = new SimpleIntegerProperty(0);
	private ImageView playerSprite;
	private Player player;
	private Ghost cyan;
	private Ghost orange;
	private Ghost pink;
	private Ghost red;
	private final String filePath;
	
	// Visual grid.
	private final double TILE_SIZE = Constants.TILE_SIZE * Constants.SCALE_FACTOR;
	
	/**
	 * Constructor method.
	 *
	 * @param fileName The level's filename without the extension.
	 */
	public Map(String fileName)
	{
		filePath = "/com/frigvid/jman/levels/" + fileName + ".level";
		
		player = new Player(this);
		// NOTE: Investigate necessity of defining these here instead of in GameBoard.java.
		cyan = new Cyan(this);
		orange = new Orange(this);
		pink = new Pink(this);
		red = new Red(this);
		
		createLogicGrid();
		createVisualGrid();
	}
	
	/* Grids. */
	private void createLogicGrid()
	{
		try (
			InputStream resource = getClass().getResourceAsStream(this.filePath);
			InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(resource), StandardCharsets.UTF_8);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
		)
		{
			if (Constants.DEBUG_ENABLED)
			{
				out.println(
					"Map: Creating logic grid." +
					"\n┗ Reading level data from file: " + filePath
				);
			}
			
			// Grab the title and move to next line.
			setTitle(bufferedReader.readLine());
			
			// Store the logic grid.
			this.logicGrid = bufferedReader.lines()
				// Remove spaces and convert each line into a TileType array.
				.map(line -> line.replace(" ", "").chars()
					.mapToObj(c -> mapCharToTileType((char) c))
					.toArray(TileType[]::new))
				// Collect into a list.
				.toArray(TileType[][]::new);
			
			// Store amount of pellets and power-ups.
			for (TileType[] row : this.logicGrid)
			{
				for (TileType tile : row)
				{
					if (tile == TileType.SMALL_DOT)
					{
						increasePelletCountBy(1);
					}
					else if (tile == TileType.BIG_DOT)
					{
						increasePowerupCountBy(1);
					}
				}
			}
			
			if (Constants.DEBUG_ENABLED)
			{
				out.println(
					"Map: level information."
					+ "\n┣ Title: " + this.title
					+ "\n┣ Rows: " + getMapWidth()
					+ "\n┣ Columns: " + getMapHeight()
					+ "\n┣ Pellets: " + getCountPellets()
					+ "\n┗ Power-ups: " + getCountPowerups()
				);
				if (Constants.DEBUG_LEVEL == 1)
				{
					char[][] rawLines = createRawGrid();
					out.println("Map: Level data.");
					
					// NOTE: This could use "char[] line : rawLines" instead, but this is just for more clarity.
					assert rawLines != null;
					for (int i = 0; i < rawLines.length; i++)
					{
						char[] line = rawLines[i];
						if (i != rawLines.length - 1)
						{
							out.println("┣ " + Arrays.toString(line));
						}
						else
						{
							out.println("┗ " + Arrays.toString(line));
						}
					}
				}
			}
		}
		catch (FileNotFoundException e)
		{
			out.println("File not found!");
			e.printStackTrace(System.err);
		}
		catch (IOException e)
		{
			out.println("An I/O exception occurred!");
			e.printStackTrace(System.err);
		}
		catch (Exception e)
		{
			out.println("Something unexpected happened!");
			e.printStackTrace(System.err);
		}
		
	}
	
	/**
	 * Create the visual grid Pane, and saves it to local variable.
	 */
	private void createVisualGrid()
	{
		visualGrid = new Pane();
		
		for (int row = 0; row < getMapHeight(); row++)
		{
			for (int col = 0; col < getMapWidth(); col++)
			{
				double x = col * TILE_SIZE;
				double y = row * TILE_SIZE;
				// Calculate the center of the tile.
				double centerX = x + TILE_SIZE / 2;
				double centerY = y + TILE_SIZE / 2;
				
				Rectangle tile = new Rectangle(x, y, TILE_SIZE, TILE_SIZE);
				tile.setFill(Color.BLACK);
				if (Constants.DEBUG_ENABLED && Constants.DEBUG_LEVEL == 1)
				{
					// Set border to tiles.
					tile.setStyle("-fx-stroke: grey; -fx-stroke-width: 5;");
				}
				visualGrid.getChildren().add(tile);
				
				// FIXME: Extract this debug section.
				// Draw center lines for alignment debugging.
				if (Constants.DEBUG_ENABLED && Constants.DEBUG_LEVEL == 2)
				{
					// Define the length of each line.
					double length = TILE_SIZE / 4;
					
					Line horizontalLine = new Line(centerX - length, centerY, centerX + length, centerY);
					Line verticalLine = new Line(centerX, centerY - length, centerX, centerY + length);
					horizontalLine.setStroke(Color.RED);
					verticalLine.setStroke(Color.RED);
					
					visualGrid.getChildren().addAll(
						horizontalLine,
						verticalLine
					);
				}
				
				// NOTE: Consider extracting this switch-case.
				switch (getTileType(col, row))
				{
					case WALL:
						tile.setFill(
							Color.valueOf("#0000bf")
						);
						tile.setSmooth(false); // Fixes tiny spacing issue when scaling dynamically.
						break;
					case SMALL_DOT:
						Circle point = new Circle(centerX, centerY, 5);
						point.setFill(Color.GOLD);
						visualGrid.getChildren().add(point);
						break;
					case BIG_DOT:
						Circle powerUp = new Circle(centerX, centerY, 10);
						powerUp.setFill(Color.ORANGE);
						visualGrid.getChildren().add(powerUp);
						break;
					case SPAWN_GHOST_1:
					case SPAWN_GHOST_2:
						if (Constants.DEBUG_ENABLED)
						{
							Rectangle spawn = new Rectangle(x, y, TILE_SIZE, TILE_SIZE);
							spawn.setFill(Color.RED);
							visualGrid.getChildren().add(spawn);
						}
						break;
					case SPAWN_PLAYER:
						if (Constants.DEBUG_ENABLED)
						{
							Rectangle spawn = new Rectangle(x, y, TILE_SIZE, TILE_SIZE);
							spawn.setFill(Color.GREEN);
							visualGrid.getChildren().add(spawn);
						}
						break;
					case EXIT:
					case TELEPORT:
						if (Constants.DEBUG_ENABLED)
						{
							Rectangle teleport = new Rectangle(x, y, TILE_SIZE, TILE_SIZE);
							teleport.setFill(Color.PURPLE);
							visualGrid.getChildren().add(teleport);
						}
						break;
					case OPEN_SPACE:
					default:
						break;
				}
			}
		}
		
		visualGrid.setPrefSize(getMapWidth() * TILE_SIZE, getMapHeight() * TILE_SIZE);
	}
	
	/**
	 * Create a 2-dimensional char array.
	 *
	 * @return A 2-dimensional char array.
	 * @warning This is only meant for debugging purposes.
	 */
	private char[][] createRawGrid()
	{
		try
		{
			InputStream resource = getClass().getResourceAsStream(this.filePath);
			assert resource != null;
			InputStreamReader inputStreamReader = new InputStreamReader(resource);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			// Skip the first line.
			bufferedReader.readLine();
			
			return bufferedReader.lines()
				.map(line -> line.replace(" ", ""))
				.map(String::toCharArray)
				.toArray(char[][]::new);
		}
		catch (Exception e)
		{
			out.println("Something unexpected went wrong!");
			e.printStackTrace(System.err);
		}
		
		return null;
	}
	
	/* Utilities. */
	/**
	 * Used to "convert" a character-based level into a
	 * TileType based one.
	 *
	 * @param character The char to change to a TileType.
	 * @return The TileType corresponding to the character.
	 * @see com.frigvid.jman.game.map.TileType
	 */
	private TileType mapCharToTileType(char character)
	{
		return switch (character)
		{
			case 'W' -> TileType.WALL;
			case 'S' -> TileType.SMALL_DOT;
			case 'B' -> TileType.BIG_DOT;
			case 'O' -> TileType.OPEN_SPACE;
			case 'E' -> TileType.EXIT;
			case 'T' -> TileType.TELEPORT;
			case 'P' -> TileType.SPAWN_PLAYER;
			case '1' -> TileType.SPAWN_GHOST_1;
			case '2' -> TileType.SPAWN_GHOST_2;
			default -> throw new IllegalArgumentException("Invalid character: " + character);
		};
	}
	
	public Pair<Integer, Integer> findRandomTeleportLocation(int currentCol, int currentRow)
	{
		ArrayList<Pair<Integer, Integer>> teleportLocations = new ArrayList<>();
		
		for (int row = 0; row < getMapHeight(); row++)
		{
			for (int col = 0; col < getMapWidth(); col++)
			{
				if (getTileType(col, row) == TileType.TELEPORT && (col != currentCol || row != currentRow))
				{
					teleportLocations.add(new Pair<>(col, row));
				}
			}
		}
		
		if (!teleportLocations.isEmpty())
		{
			return teleportLocations.get(new Random().nextInt(teleportLocations.size()));
		}
		
		return null;
	}
	
	/**
	 * A pretty heavy-handed and ugly approach to "cleaning up the board" as it were.
	 * <p/>
	 * This is to be used in conjunction with the {@link Player} movement (move) method.
	 * Once an element of the visual grid intercects with the player, you can call this
	 * method, and replace the element (e.g. a pellet or a power-up) and remove it from
	 * the logic grid as well.
	 *
	 * @param row The row of the element to remove.
	 * @param column The column of the element to remove.
	 * @param tileType The TileType you're replacing the element with.
	 */
	public void replaceLogicGridElement(int row, int column, TileType tileType)
	{
		this.logicGrid[row][column] = tileType;
	}
	
	/* Wrapper methods. */
	/**
	 * Wrapper method for {@link #setCountPellet(boolean, int)}.
	 * <p/>
	 * Allows for easy pellet counter increase.
	 * <p/>
	 * <strong>Role:</strong> Utility.
	 * @param value The value to increase the pellet counter by.
	 * @see #setCountPellet(boolean, int)
	 */
	public void increasePelletCountBy(int value)
	{
		setCountPellet(false, value);
	}
	
	/**
	 * Wrapper method for {@link #setCountPellet(boolean, int)}.
	 * <p/>
	 * Allows for easy pellet counter decrease.
	 * <p/>
	 * <strong>Role:</strong> Utility.
	 * @param value The value to decrease the pellet counter by.
	 * @see #setCountPellet(boolean, int)
	 */
	public void decreasePelletCountBy(int value)
	{
		setCountPellet(true, value);
	}
	
	/**
	 * Wrapper method for {@link #setCountPowerup(boolean, int)}.
	 * <p/>
	 * Allows for easy power-up counter decrease.
	 * <p/>
	 * <strong>Role:</strong> Utility.
	 * @param value The value to increase the power-up counter by.
	 * @see #setCountPowerup(boolean, int)
	 */
	public void increasePowerupCountBy(int value)
	{
		setCountPowerup(false, value);
	}
	
	/**
	 * Wrapper method for {@link #setCountPowerup(boolean, int)}.
	 * <p/>
	 * Allows for easy power-up counter decrease.
	 * <p/>
	 * <strong>Role:</strong> Utility.
	 * @param value The value to decrease the power-up counter by.
	 * @see #setCountPowerup(boolean, int)
	 */
	public void decreasePowerupCountBy(int value)
	{
		setCountPowerup(true, value);
	}
	
	/* Setters. */
	private void setTitle(String title)
	{
		this.title = title;
	}
	
	/**
	 * Generic multipurpose method for increasing or decreasing the pellet count.
	 *
	 * @param subtract Whether to subtract from the count.
	 * @param value The value to add to, or subtract from the count.
	 */
	private void setCountPellet(boolean subtract, int value)
	{
		if (subtract)
		{
			counterPellets.set(
				counterPellets.get() - value
			);
		}
		else
		{
			counterPellets.set(
				counterPellets.get() + value
			);
		}
	}
	
	/**
	 * Generic multipurpose method for increasing or decreasing the power-up count.
	 *
	 * @param subtract Whether to subtract from the count.
	 * @param value The value to add to, or subtract from the count.
	 */
	private void setCountPowerup(boolean subtract, int value)
	{
		if (subtract)
		{
			counterPowerups.set(
				counterPowerups.get() - value
			);
		}
		else
		{
			counterPowerups.set(
				counterPowerups.get() + value
			);
		}
	}
	
	/* Getters. */
	/**
	 * Gets the level's title.
	 *
	 * @return The level's title.
	 */
	public String getTitle()
	{
		return this.title;
	}
	
	/**
	 * Get the map's logic grid.
	 *
	 * @return The 2-dimensional array representing the level's tile placement.
	 */
	public TileType[][] getLogicGrid()
	{
		return this.logicGrid;
	}
	
	/**
	 * Get the map's visual grid.
	 *
	 * @return The Pane containing the map's tile visual components.
	 */
	public Pane getVisualGrid()
	{
		return this.visualGrid;
	}
	
	/**
	 * Get the level's height as an int.
	 * <p/>
	 * Since the map is stored in a 2-dimensional array,
	 * you're actually getting the length of the first
	 * array.
	 *
	 * @return The width of the map in tiles.
	 */
	public int getMapHeight()
	{
		return this.logicGrid.length;
	}
	
	/**
	 * Get the level's width as an int.
	 * <p/>
	 * Since the map is stored in a 2-dimensional array,
	 * you're actually getting the length of the nested
	 * array.
	 *
	 * @return The width of the map in tiles.
	 */
	public int getMapWidth()
	{
		return this.logicGrid[0].length;
	}
	
	public int getCountPellets()
	{
		return this.counterPellets.get();
	}
	
	public int getCountPowerups()
	{
		return this.counterPowerups.get();
	}
	
	/**
	 * Get an element by its position in the tile map
	 * arraydata representing the level layout.
	 *
	 * @param row The index id for the first array.
	 * @param col The index id for the second array.
	 * @return The element in that position.
	 */
	public TileType getTileType(int col, int row)
	{
		int levelWidth = getMapWidth();
		int levelHeight = getMapHeight();
		
		if (row > levelHeight)
		{
			throw new ArrayIndexOutOfBoundsException("Your row exceeds the possible range. The level is only " + levelHeight + " high.");
		}
		
		if (col > levelWidth)
		{
			throw new ArrayIndexOutOfBoundsException("Your col exceeds the possible range. The level is only " + levelWidth + " wide.");
		}
		
		return this.logicGrid[row][col];
	}
	
	/**
	 * Finds the position of a unique tile tileType.
	 * <p/>
	 * Unique tiles:
	 * - SPAWN_GHOST_1
	 * - SPAWN_GHOST_2
	 * - SPAWN_PLAYER
	 * <p/>
	 * Forgive the deceptive title.
	 *
	 * @param tileType The tile to find.
	 * @return The position of the tile as a string.
	 */
	public String findTilePosition(TileType tileType)
	{
		for (int row = 0; row < getMapHeight(); row++)
		{
			for (int col = 0; col < getMapWidth(); col++)
			{
				if (getTileType(col, row) == tileType)
				{
					return row + "," + col;
				}
			}
		}
		
		return "Tile not found!";
	}
}

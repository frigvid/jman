package com.frigvid.jman.level;

import com.frigvid.jman.Constants;
import com.frigvid.jman.map.TileType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

import static java.lang.System.out;

/**
 * Level object.
 *
 * @author frigvid
 * @created 2024-02-19
 */
public class Level
{
	private final String BASE_PATH = "/com/frigvid/jman/levels/";
	private String filePath;
	private String title;
	private TileType[][] level;
	private int playerSpawnRow;
	private int playerSpawnCol;
	
	public Level(String fileName)
	{
		filePath = BASE_PATH + fileName + ".level";
		
		try (
			InputStream resource = getClass().getResourceAsStream(filePath);
			InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(resource), StandardCharsets.UTF_8);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		)
		{
			if (Constants.DEBUG_ENABLED)
			{
				out.println("Reading level data from " + filePath);
			}
			
			// Grab the title and move to next line.
			setTitle(bufferedReader.readLine());
			setPlayerSpawnRow(Integer.parseInt(bufferedReader.readLine()));
			setPlayerSpawnCol(Integer.parseInt(bufferedReader.readLine()));
			
			TileType[][] lines = createTileTypeLogicGrid(bufferedReader);
			
			// Store the level.
			setLevel(lines);
			
			if (Constants.DEBUG_ENABLED)
			{
				out.println("Level title: " + this.title);
				out.println(
					"Level columns: " + getLevelHeight() +
					"\nLevel rows: " + getLevelWidth()
				);
				if (Constants.DEBUG_LEVEL == 1)
				{
					out.println("Level data:");
					
					for (TileType[] type : lines)
					{
						out.println(Arrays.toString(type));
					}
				}
			}
		}
		catch (FileNotFoundException e)
		{
			out.println("File not found! " + e);
		}
		catch (IOException e)
		{
			out.println("An I/O exception occurred! " + e);
		}
		catch (Exception e)
		{
			out.println("Something went wrong! " + e);
		}
	}
	
	/* Utilities. */
	/**
	 * Used to "convert" a character-based level into a
	 * TileType based one.
	 *
	 * @param character The char to change to a TileType.
	 * @return The TileType corresponding to the character.
	 * @see com.frigvid.jman.map.TileType
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
	
	/**
	 * Create a 2-dimensional TileType array from a BufferedReader.
	 *
	 * @param br The BufferedReader to read from.
	 * @return A 2-dimensional TileType array.
	 */
	private TileType[][] createTileTypeLogicGrid(BufferedReader br)
	{
		// TODO: Get position of player spawn, and save it.
		return br.lines()
			// Remove spaces and convert each line into a TileType array.
			.map(line -> line.replace(" ", "").chars()
				.mapToObj(c -> mapCharToTileType((char) c))
				.toArray(TileType[]::new))
			// Collect into a list.
			.toArray(TileType[][]::new);
	}
	
	/**
	 * Create a 2-dimensional char array from a BufferedReader.
	 *
	 * @param br The BufferedReader to read from.
	 * @return A 2-dimensional char array.
	 */
	private char[][] createRawLogicGrid(BufferedReader br)
	{
		return br.lines()
			// Remove spaces and convert each line into a char array.
			.map(line -> line.replace(" ", "").toCharArray())
			// Collect into a list.
			.toArray(char[][]::new);
	}
	
	/* Setters. */
	private void setTitle(String title)
	{
		this.title = title;
	}
	
	private void setPlayerSpawnRow(int playerSpawnRow)
	{
		this.playerSpawnRow = playerSpawnRow;
	}
	
	private void setPlayerSpawnCol(int playerSpawnCol)
	{
		this.playerSpawnCol = playerSpawnCol;
	}
	
	private void setLevel(TileType[][] level)
	{
		this.level = level;
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
	
	public int getPlayerSpawnRow()
	{
		return this.playerSpawnRow;
	}
	
	public int getPlayerSpawnCol()
	{
		return this.playerSpawnCol;
	}
	
	/**
	 * Get the whole level.
	 *
	 * @return The 2-dimensional array representing the level's tile placement.
	 */
	public TileType[][] getLevel()
	{
		return this.level;
	}
	
	/**
	 * Get the level's height as an int.
	 * <br/><br/>
	 * Since the map is stored in a 2-dimensional array,
	 * you're actually getting the length of the first
	 * array.
	 *
	 * @return The width of the map in tiles.
	 */
	public int getLevelHeight()
	{
		return this.level.length;
	}
	
	/**
	 * Get the level's width as an int.
	 * <br/><br/>
	 * Since the map is stored in a 2-dimensional array,
	 * you're actually getting the length of the nested
	 * array.
	 *
	 * @return The width of the map in tiles.
	 */
	public int getLevelWidth()
	{
		return this.level[0].length;
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
		int levelWidth = getLevelWidth();
		int levelHeight = getLevelHeight();
		
		if (row > levelHeight)
		{
			throw new ArrayIndexOutOfBoundsException("Your row exceeds the possible range. The level is only " + levelHeight + " high.");
		}
		
		if (col > levelWidth)
		{
			throw new ArrayIndexOutOfBoundsException("Your col exceeds the possible range. The level is only " + levelWidth + " wide.");
		}
		
		if (Constants.DEBUG_ENABLED && Constants.DEBUG_LEVEL == 1)
		{
			out.println("getLevelElement: " + this.level[row][col]);
		}
		
		return this.level[row][col];
	}
	
	/**
	 * Finds the position of a unique tile tileType.
	 * <br/><br/>
	 * Unique tiles:
	 * - SPAWN_GHOST_1
	 * - SPAWN_GHOST_2
	 * - SPAWN_PLAYER
	 * <br/><br/>
	 * Forgive the deceptive title.
	 * @param tileType The tile to find.
	 * @return The position of the tile as a string.
	 */
	public String findTilePosition(TileType tileType)
	{
		for (int row = 0; row < getLevelHeight(); row++)
		{
			for (int col = 0; col < getLevelWidth(); col++)
			{
				if (getTileType(col, row) == tileType)
				{
					return col + "," + row;
				}
			}
		}
		
		return "Tile not found!";
	}
}

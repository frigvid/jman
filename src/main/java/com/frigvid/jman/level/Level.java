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
	
	public Level(String fileName)
	{
		filePath = BASE_PATH + fileName + ".level";
		//filePath = "/com/frigvid/jman/levels/map1.level";
		
		try (
			InputStream resource = getClass().getResourceAsStream(filePath);
			InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(resource), StandardCharsets.UTF_8);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		)
		{
			if (Constants.DEBUG_ENABLED) {out.println("Reading level data from " + filePath);}

			// Grab the title and move to next line.
			setTitle(bufferedReader.readLine());
			
			//char[][] lines = bufferedReader.lines()
			//	// Remove spaces and convert each line into a char array.
			//	.map(line -> line.replace(" ", "").toCharArray())
			//	// Collect into a list.
			//	.toArray(char[][]::new);

			TileType[][] lines = bufferedReader.lines()
				// Remove spaces and convert each line into a TileType array.
				.map(line -> line.replace(" ", "").chars()
					.mapToObj(c -> mapCharToTileType((char) c))
					.toArray(TileType[]::new))
				// Collect into a list.
				.toArray(TileType[][]::new);

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
	private TileType mapCharToTileType(char character) {
		switch (character) {
			case 'W':
				return TileType.WALL;
			case 'S':
				return TileType.SMALL_DOT;
			case 'B':
				return TileType.BIG_DOT;
			case 'O':
				return TileType.OPEN_SPACE;
			case 'E':
				return TileType.EXIT;
			case 'T':
				return TileType.TELEPORT;
			case 'P':
				return TileType.SPAWN_PLAYER;
			case '1':
				return TileType.SPAWN_GHOST_1;
			case '2':
				return TileType.SPAWN_GHOST_2;
			default:
				throw new IllegalArgumentException("Invalid character: " + character);
		}
	}
	
	/* Setters. */
	private void setTitle(String title)
	{
		this.title = title;
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
	 *
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
	 *
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
	public TileType getLevelElement(int col, int row)
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

		if (Constants.DEBUG_ENABLED && Constants.DEBUG_LEVEL == 1) {out.println("getLevelElement: " + this.level[row][col]);}

		return this.level[row][col];
	}
}

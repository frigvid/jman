package com.frigvid.jman.level;

import com.frigvid.jman.Constants;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
	private char[][] level;
	
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
			System.out.println("Reading level data from " + filePath);
			
			// Grab the title and move to next line.
			setTitle(bufferedReader.readLine());
			
			char[][] lines = bufferedReader.lines()
				// Remove spaces and convert each line into a char array.
				.map(line -> line.replace(" ", "").toCharArray())
				// Collect into a list.
				.toArray(char[][]::new);

			// Store the level.
			setLevel(lines);

			if (Constants.DEBUG_ENABLED)
			{
				System.out.println("Title: " + this.title);
				System.out.println("Level data:");
				System.out.println("Test: " + lines[0][1]);
				for (char[] chars : lines)
				{
					System.out.println(Arrays.toString(chars));
				}
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File not found! " + e);
		}
		catch (IOException e)
		{
			System.out.println("An I/O exception occurred! " + e);
		}
		catch (Exception e)
		{
			System.out.println("Something went wrong! " + e);
		}
	}
	
	/* Setters. */
	private void setTitle(String title)
	{
		this.title = title;
	}
	
	private void setLevel(char[][] level)
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
	public char[][] getLevel()
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
	 * @return The element (char) in that position.
	 */
	public char getLevelElement(int row, int col)
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

		return this.level[row][col];
	}
}

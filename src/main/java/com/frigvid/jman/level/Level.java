package com.frigvid.jman.level;

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
			
			//List<char[]> lines = bufferedReader.lines()
			//	// Remove spaces and convert each line into a char array.
			//	.map(line -> line.replace(" ", "").toCharArray())
			//	// Collect into a list.
			//	.toList();
			
			char[][] lines = bufferedReader.lines()
				// Remove spaces and convert each line into a char array.
				.map(line -> line.replace(" ", "").toCharArray())
				// Collect into a list.
				.toArray(char[][]::new);
			
			// Print data.
			System.out.println("Title: " + this.title);
			System.out.println("Level data:");
			System.out.println("Test: " + lines[0][1]);
			for (char[] chars : lines)
			{
				System.out.println(Arrays.toString(chars));
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
	
	//private void setLevel(char[][] level)
	//{
	//	this.level = level;
	//}
	
	/* Getters. */
	public String getTitle()
	{
		return this.title;
	}
	
	
	
	//public Level(String title, String[][] levelData)
	//{
	//	this.title = title;
	//	this.levelData = levelData;
	//}
	//
	//public String getTitle()
	//{
	//	return this.title;
	//}
	//
	//public String[][] getLevelData()
	//{
	//	return this.levelData;
	//}
	//
	//public void setTitle(String title)
	//{
	//	this.title = title;
	//}
	//
	//public void setLevelData(String[][] levelData)
	//{
	//	this.levelData = levelData;
	//}
	//
	//public String toString()
	//{
	//	return this.title;
	//}
}

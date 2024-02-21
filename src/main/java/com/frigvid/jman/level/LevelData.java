package com.frigvid.jman.level;

/**
 * An enum for the different types of level data.
 * <br/><br/>
 * This is similar to Constants, but dissimilar enough
 * that I chose to use an enum instead of a final class.
 *
 * @author frigvid
 * @created 2024-02-19
 * @since 0.1
 * @see com.frigvid.jman.Constants
 */
public enum LevelData
{
	BIG_DOT("B"),
	EXIT("E"),
	OPEN_SPACE("O"),
	SMALL_DOT("S"),
	SPAWN_GHOST_1("1"),
	SPAWN_GHOST_2("2"),
	SPAWN_PLAYER("P"),
	TELEPORT("T"),
	WALL("W");
	
	private String type;
	
	LevelData(String type)
	{
		this.type = type;
	}
	
	public String getType()
	{
		return this.type;
	}
}

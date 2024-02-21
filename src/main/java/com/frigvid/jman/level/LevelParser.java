package com.frigvid.jman.level;

/**
 * Parses level save data, ?into a level object?.
 *
 * @author frigvid
 * @created 2024-02-19
 * @since 0.1
 */
public class LevelParser
{
	// Temporary level data:
	// First line is the level name, the rest is the level data.
	private static final String LEVEL_DATA = """
			Beginnings
			W W W W W W W W W W W W W W W W W W W
			W S S S S S S S S W S S S S S S S S W
			W B W W S W W W W W W W W W S W W B W
			W S S S S S S S S S S S S S S S S S W
			W S W W S W S W W W W W S W S W W S W
			W S S S S W S S S W S S S W S S S S W
			W W W W S W W W O W O W W W S W W W W
			O O O W S W O O O O O O O W S W O O O
			W W W W S W O W O W O W O W S W W W W
			O O O O S O O W 1 O 2 W O O S O O O O
			W W W W S W O W W W W W O W S W W W W
			O O O W S W O O O O O O O W S W O O O
			W W W W S W O W W W W W O W S W W W W
			W S S S S S S S S W S S S S S S S S W
			W B W W S W W W W W W W W W S W W B W
			W S S W S S S S S P S S S S S W S S W
			W W S W S W S W W W W W S W S W S W W
			W S S S S W S S S W S S S W S S S S W
			W S W W W W W W S W S W W W W W W S W
			W S S S S S S S S S S S S S S S S S W
			W W W W W W W W W W W W W W W W W W W
		""";
	
}

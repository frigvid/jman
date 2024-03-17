package com.frigvid.jman.game.map;

/**
 * An enum for the different types of level data.
 * <p/>
 * This is similar to Constants, but dissimilar enough
 * that I chose to use an enum instead of a final class.
 *
 * @author frigvid
 * @created 2024-02-19
 * @since 0.1
 * @see com.frigvid.jman.Constants
 */
public enum TileType
{
	BIG_DOT,
	EXIT,
	OPEN_SPACE,
	SMALL_DOT,
	SPAWN_GHOST_1,
	SPAWN_GHOST_2,
	SPAWN_PLAYER,
	TELEPORT,
	WALL;
}

package com.frigvid.jman;

import javafx.stage.Screen;

/**
 * A class for storing constants used in the application.
 * <br/><br/>
 * Usage:
 * <pre>
 *    import static com.frigvid.jman.Constants.MENU_BUTTON_STYLE;
 *    Constants.MENU_BUTTON_STYLE
 * </pre>
 */
public final class Constants
{
	private Constants()
	{
		// Restricted instantiation.
	}

	/* DEBUGGING. */
	// Switch to true to enable debug logging.
	public static final boolean DEBUG_ENABLED = false;
	// 0 = default. 1 = full logging. 2 = Unnecessary logging.
	public static final int DEBUG_LEVEL = 0;
	public static final boolean DEBUG_AI = false;

	/* SCALING. */
	/* Save screen size, for scaling.
	 * NOTE: Use JavaFX 21+, sub 21 has issues with scaling on 2k/4k screens.
	 */
	public static final double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
	public static final double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();
	public static final double SCREEN_SIZE = SCREEN_WIDTH + SCREEN_HEIGHT;

	// Scale window relative to screen.
	public static final double WINDOW_WIDTH = SCREEN_WIDTH / 2;
	public static final double WINDOW_HEIGHT = SCREEN_HEIGHT / 2;

	// Element scaling factor.
	// Alternative: Math.abs(SCREEN_WIDTH / WINDOW_WIDTH);
	public static final double SCALE_FACTOR_WIDTH = Screen.getPrimary().getOutputScaleX();
	// Alternative: Math.abs(SCREEN_HEIGHT / WINDOW_HEIGHT);
	public static final double SCALE_FACTOR_HEIGHT = Screen.getPrimary().getOutputScaleY();
	// Use this for things like font size.
	public static final double SCALE_FACTOR = Math.min(SCALE_FACTOR_HEIGHT, SCALE_FACTOR_WIDTH);

	/* GAME/UI RELATED. */
	public static final String GAME_TITLE = DEBUG_ENABLED ? "(DEBUG) J-Man!" : "J-Man!";
	public static final String GAME_FIRST_MAP = "map1";
	public static final String WINDOW_BACKGROUND_COLOR = "-fx-background-color: black;";
	public static final String TEXT_BASE_STYLE = "-fx-font-weight: bold;"
															 + "-fx-font-family: 'Arial';";
	public static final String MENU_BUTTON_STYLE = "-fx-background-color: yellow;"
																+ "-fx-font-weight: bold;"
																+ "-fx-font-family: 'Arial';"
																+ "-fx-font-size: " + 14 * SCALE_FACTOR + "px;"
																+ "-fx-min-width: " + 100 * SCALE_FACTOR + "px;"
																+ "-fx-min-height: " + 20 * SCALE_FACTOR + "px;";
	public static final String RESOURCE_BASE_PATH = "/com/frigvid/jman/";
	// 3000 is 1920 + 1080, above, it's a ghetto attempt to scale for 2k/4k.
	//public static final double TILE_SIZE = SCREEN_SIZE > 3000 ? 25.0 * (SCALE_FACTOR / 1.50) : 25.0 / (SCALE_FACTOR / 1.75);
	public static final long TICK_RATE = 25; // 40 ticks per second.
	public static final double TILE_SIZE = 25.0;
	public static final double PELLET_SIZE = 5.0;
	public static final double POWERUP_SIZE = 10.0;
	public static final int SCORE_PELLET = 10;
	public static final int SCORE_POWERUP = 50;
	public static final int SCORE_GHOST = 200; // 200, 400, 800, 1600
}

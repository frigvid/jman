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
	// 0 = default. 1 = full logging.
	public static final int DEBUG_LEVEL = 0;

	/* SCALING. */
	// Save screen size, for scaling.
	// NOTE: Should probably check that this gets the primary screen, and not secondaries.
	public static final double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
	public static final double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();

	// Scale window relative to screen.
	public static final double WINDOW_WIDTH = SCREEN_WIDTH / 2;
	public static final double WINDOW_HEIGHT = SCREEN_HEIGHT / 2;

	// Element scaling factor.
	public static final double SCALE_FACTOR_WIDTH = Math.abs(SCREEN_WIDTH / WINDOW_WIDTH);
	public static final double SCALE_FACTOR_HEIGHT = Math.abs(SCREEN_HEIGHT / WINDOW_HEIGHT);
	// Use this for things like font size.
	public static final double SCALE_FACTOR = Math.min(SCALE_FACTOR_HEIGHT, SCALE_FACTOR_WIDTH);

	/* GAME/UI RELATED. */
	public static final String GAME_TITLE = "J-Man!";
	public static final String WINDOW_BACKGROUND_COLOR = "-fx-background-color: black;";
	public static final String MENU_BUTTON_STYLE = "-fx-background-color: yellow;"
																+ "-fx-font-weight: bold;"
																+ "-fx-font-family: 'Arial';"
																+ "-fx-font-size: " + 14 * SCALE_FACTOR + "px;"
																+ "-fx-min-width: " + 100 * SCALE_FACTOR + "px;"
																+ "-fx-min-height: " + 20 * SCALE_FACTOR + "px;";
	public static final double TILE_SIZE = 25.0;
}

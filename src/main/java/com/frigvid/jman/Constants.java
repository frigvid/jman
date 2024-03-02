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

	/* SCALING. */
	/* Save screen size, for scaling.
	 * NOTE: AWT and JavaFX both get the *scaled* resolution, never the native resolution.
	 *			This *will* cause weird issues if you're not at native resolution above 1080p
	 *			or 2k or so, basically high DPI. 4k@100%+ scaling gets weird man. For example,
	 * 		main menu → start game → back to main menu causes the menu to shift down
	 * 		towards the right hand side.
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
	public static final String WINDOW_BACKGROUND_COLOR = "-fx-background-color: black;";
	public static final String MENU_BUTTON_STYLE = "-fx-background-color: yellow;"
																+ "-fx-font-weight: bold;"
																+ "-fx-font-family: 'Arial';"
																+ "-fx-font-size: " + 14 * SCALE_FACTOR + "px;"
																+ "-fx-min-width: " + 100 * SCALE_FACTOR + "px;"
																+ "-fx-min-height: " + 20 * SCALE_FACTOR + "px;";
	// 3000 is 1920 + 1080, above, it's a ghetto attempt to scale for 2k/4k.
	//public static final double TILE_SIZE = SCREEN_SIZE > 3000 ? 25.0 * (SCALE_FACTOR / 1.50) : 25.0 / (SCALE_FACTOR / 1.75);
	public static final double TILE_SIZE = 25.0;
}

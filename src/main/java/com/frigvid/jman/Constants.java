package com.frigvid.jman;

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
	
	public final static String GAME_TITLE = "J-Man!";
	
	public final static int WINDOW_WIDTH = 800;
	public final static int WINDOW_HEIGHT = 600;
	
	public static final String WINDOW_BACKGROUND_COLOR = "-fx-background-color: black;";
	
	// This is perhaps a bit of an uncommon convention, but I find it easier to read and modify this way.
	public static final String MENU_BUTTON_STYLE = """
			-fx-background-color: yellow;
			-fx-font-weight: bold;
			-fx-font-family: 'Arial';
			-fx-font-size: 14px;
			-fx-min-width: 100px;
			-fx-min-height: 20px;
		""";
}

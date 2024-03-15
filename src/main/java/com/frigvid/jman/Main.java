package com.frigvid.jman;

import com.frigvid.jman.view.state.ViewStateManager;
import javafx.application.Application;
import javafx.stage.Stage;

import static com.frigvid.jman.Constants.*;

/**
 * The main class for the application.
 * <br/><br/>
 * This class is the entry point for the application,
 * and is used to start the main menu.
 * <br/><br/>
 * <b>Responsibility: </b>To provide the entry point
 * for the application, and to start the main menu.
 * <br/><br/>
 * <b>Reasons for use: </b>To provide a common entry
 * point for the application, and to start the main menu.
 * <br/><br/>
 * @see com.frigvid.jman.view.MainMenu
 * @author frigvid
 * @created 2024-02-14
 * @since 0.1
 */
public class Main
	extends Application
{
	/**
	 * See {@link com.frigvid.jman.entity.player.Player} approximately at
	 * line 123 for usage.
	 * <p/>
	 * Essentially, the player's movement shouldn't have a hard argument
	 * requirement for usage. This is ergo a disgusting, eye-bleeding and
	 * vomit-inducing hack to get around that.
	 */
	public static Stage disgustingHack;
	
	public static void main(String[] args)
	{
		launch();
	}
	
	@Override
	public void start(Stage stage)
	{
		if (Constants.DEBUG_ENABLED)
		{
			logConstants();
		}

		ViewStateManager viewStateManager = new ViewStateManager();
		viewStateManager.startMainMenu(stage);
		stage.setMinWidth(WINDOW_WIDTH / SCALE_FACTOR);
		stage.setMinHeight(WINDOW_HEIGHT);
		stage.setWidth(WINDOW_WIDTH);
		stage.setHeight(WINDOW_HEIGHT);
		stage.centerOnScreen();
		stage.setMaximized(true);
		
		disgustingHack = stage;
	}

	public static void logConstants()
	{
		System.out.println(
			"Values of constants:"
			+ "\n┣ DEBUG_ENABLED: " + Constants.DEBUG_ENABLED
			+ "\n┣ DEBUG_LEVEL: " + Constants.DEBUG_LEVEL
			+ "\n┣ GAME_TITLE: " + Constants.GAME_TITLE
			+ "\n┣ SCREEN_WIDTH: " + Constants.SCREEN_WIDTH
			+ "\n┣ SCREEN_HEIGHT: " + Constants.SCREEN_HEIGHT
			+ "\n┣ SCREEN_SIZE: " + Constants.SCREEN_SIZE
			+ "\n┣ WINDOW_WIDTH: "  + WINDOW_WIDTH
			+ "\n┣ WINDOW_HEIGHT: "  + WINDOW_HEIGHT
			+ "\n┣ SCALE_FACTOR_WIDTH: "  + Constants.SCALE_FACTOR_WIDTH
			+ "\n┣ SCALE_FACTOR_HEIGHT: " + Constants.SCALE_FACTOR_HEIGHT
			+ "\n┣ SCALE_FACTOR: " + Constants.SCALE_FACTOR
			+ "\n┣ WINDOW_BACKGROUND_COLOR: " + Constants.WINDOW_BACKGROUND_COLOR
			+ "\n┣ MENU_BUTTON_STYLE: " + Constants.MENU_BUTTON_STYLE
			+ "\n┗ TILE_SIZE: " + Constants.TILE_SIZE
		);
	}
}

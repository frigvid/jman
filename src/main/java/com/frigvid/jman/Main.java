package com.frigvid.jman;

import com.frigvid.jman.view.state.ViewStateManager;
import javafx.application.Application;
import javafx.stage.Stage;

import static com.frigvid.jman.Constants.*;

/**
 * The main class for the application.
 * <p/>
 * This class is the entry point for the application,
 * and is used to start the main menu.
 * <p/>
 * <b>Responsibility: </b>To provide the entry point
 * for the application, and to start the main menu.
 * <p/>
 * <b>Reasons for use: </b>To provide a common entry
 * point for the application, and to start the main menu.
 *
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
			+ "\n┣ DEBUG_ENABLED: " + DEBUG_ENABLED
			+ "\n┣ DEBUG_LEVEL: " + DEBUG_LEVEL
			+ "\n┣ DEBUG_AI: " + DEBUG_AI
			+ "\n┣ SCREEN_WIDTH: " + SCREEN_WIDTH
			+ "\n┣ SCREEN_HEIGHT: " + SCREEN_HEIGHT
			+ "\n┣ SCREEN_SIZE: " + SCREEN_SIZE
			+ "\n┣ WINDOW_WIDTH: "  + WINDOW_WIDTH
			+ "\n┣ WINDOW_HEIGHT: "  + WINDOW_HEIGHT
			+ "\n┣ SCALE_FACTOR_WIDTH: "  + SCALE_FACTOR_WIDTH
			+ "\n┣ SCALE_FACTOR_HEIGHT: " + SCALE_FACTOR_HEIGHT
			+ "\n┣ SCALE_FACTOR: " + SCALE_FACTOR
			+ "\n┣ GAME_TITLE: " + GAME_TITLE
			+ "\n┣ WINDOW_BACKGROUND_COLOR: " + WINDOW_BACKGROUND_COLOR
			+ "\n┣ TEXT_BASE_STYLE: " + TEXT_BASE_STYLE
			+ "\n┣ MENU_BUTTON_STYLE: " + MENU_BUTTON_STYLE
			+ "\n┣ RESOURCE_BASE_PATH: " + RESOURCE_BASE_PATH
			+ "\n┣ TICK_RATE: " + TICK_RATE
			+ "\n┣ TILE_SIZE: " + TILE_SIZE
			+ "\n┣ PLAYER_INVINCIBLE_DURATION: " + PLAYER_INVINCIBLE_DURATION + "(seconds)"
			+ "\n┣ PELLET_SIZE: " + PELLET_SIZE
			+ "\n┣ POWERUP_SIZE: " + POWERUP_SIZE
			+ "\n┣ SCORE_PELLET: " + SCORE_PELLET
			+ "\n┣ SCORE_POWERUP: " + SCORE_POWERUP
			+ "\n┗ SCORE_GHOST: " + SCORE_GHOST
		);
	}
}

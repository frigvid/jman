package com.frigvid.jman;

import com.frigvid.jman.view.state.ViewStateManager;
import javafx.application.Application;
import javafx.stage.Stage;

import static com.frigvid.jman.Constants.WINDOW_WIDTH;
import static com.frigvid.jman.Constants.WINDOW_HEIGHT;

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
	public static void main(String[] args)
	{
		launch();
	}
	
	@Override
	public void start(Stage stage)
	{
		if (Constants.DEBUG_ENABLED) {logConstants();};

		ViewStateManager viewStateManager = new ViewStateManager();
		viewStateManager.startMainMenu(stage);
		stage.setWidth(WINDOW_WIDTH);
		stage.setHeight(WINDOW_HEIGHT);
		stage.centerOnScreen();
		stage.setMaximized(true);
	}

	private void logConstants()
	{
		System.out.println("Values of constants:"
			+ "\nDEBUG_ENABLED: " + Constants.DEBUG_ENABLED
			+ "\nDEBUG_LEVEL: " + Constants.DEBUG_LEVEL
			+ "\nGAME_TITLE: " + Constants.GAME_TITLE
			+ "\nSCREEN_WIDTH: " + Constants.SCREEN_WIDTH
			+ "\nSCREEN_HEIGHT: " + Constants.SCREEN_HEIGHT
			+ "\nWINDOW_WIDTH: "  + WINDOW_WIDTH
			+ "\nWINDOW_HEIGHT: "  + WINDOW_HEIGHT
			+ "\nSCALE_FACTOR_WIDTH: "  + Constants.SCALE_FACTOR_WIDTH
			+ "\nSCALE_FACTOR_HEIGHT: " + Constants.SCALE_FACTOR_HEIGHT
			+ "\nSCALE_FACTOR: " + Constants.SCALE_FACTOR
			+ "\nWINDOW_BACKGROUND_COLOR: " + Constants.WINDOW_BACKGROUND_COLOR
			+ "\nMENU_BUTTON_STYLE: " + Constants.MENU_BUTTON_STYLE
		);
	}
}

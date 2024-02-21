package com.frigvid.jman;

import com.frigvid.jman.level.Level;
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
		
		/* Okay, listen, this is going to sound a bit stupid, but hear me out.
		 *
		 * As you can see, I'm setting the width and height manually. But since
		 * the builder class lets you skip defining the width and height, since
		 * it grabs the width and height from the stage, this shouldn't be
		 * necessary . . . right?
		 *
		 * Wrong. Apparently, due to how JavaFX handles resizing, even if you
		 * grab the scene's width and height, it'll be a bit bigger each time.
		 *
		 * And, apparently, just manually defining it, will fix the issue.
		 * Regardless of if you resize the window afterward, or if you
		 * maximize it.
		 *
		 * It's probably just a problem with my code, but seriously, if not,
		 * this is kind of figuratively giving me an aneurysm because the fix
		 * is just so stupid. I hate it. And love it for being so easy.
		 */
		stage.setWidth(WINDOW_WIDTH);
		stage.setHeight(WINDOW_HEIGHT);
		stage.centerOnScreen();

		// testing.
		Level level = new Level("map1");
		//TileMap tileMap = new TileMap(level);
		//tileMap.renderMeDaddy();
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

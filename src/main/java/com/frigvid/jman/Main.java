package com.frigvid.jman;

import com.frigvid.jman.view.state.ViewStateManager;
import javafx.application.Application;
import javafx.stage.Stage;

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
 * @version 0.1
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
		ViewStateManager viewStateManager = new ViewStateManager();
		viewStateManager.startMainMenu(stage);
		stage.setMaximized(true);
	}
}

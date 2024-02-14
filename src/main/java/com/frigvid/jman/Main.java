package com.frigvid.jman;

import com.frigvid.jman.view.ViewStateManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
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
	}
}

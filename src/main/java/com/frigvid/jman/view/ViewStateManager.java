package com.frigvid.jman.view;

import javafx.stage.Stage;

public class ViewStateManager
{
	private ViewState currentState;
	
	public void startMainMenu(Stage stage)
	{
		currentState = new MainMenu();
		currentState.start(stage);
	}
}

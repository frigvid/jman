package com.frigvid.jman.view.state;

import com.frigvid.jman.view.MainMenu;
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

package com.frigvid.jman.view.state;

import com.frigvid.jman.view.MainMenu;
import javafx.stage.Stage;

/**
 * A class representing the view state manager.
 * <br/><br/>
 * This class is part of the Strategy pattern.
 * <br/><br/>
 * <b>Pattern: </b>Strategy
 * <br/><br/>
 * <b>Role: </b>Context
 * <br/><br/>
 * <b>Collaborators: </b>ViewState
 * <br/><br/>
 * <b>Responsibility: </b>To provide a more flexible way of managing
 * the different states of the application, and to avoid having to send
 * around the stage directly.
 * <br/><br/>
 * <b>Reasons for use: </b>To provide a more flexible way of managing
 * the different states of the application, and to avoid having to send
 * around the stage directly.
 * <br/><br/>
 * @see ViewState
 * @see MainMenu
 * @see com.frigvid.jman.view.MainMenu
 * @see com.frigvid.jman.view.GameBoard
 * @see com.frigvid.jman.view.state.ViewState
 * @see com.frigvid.jman.view.state.ViewStateManager
 * @author frigvid
 * @created 2024-02-14
 * @since 0.1
 * @version 0.1
 */
public class ViewStateManager
{
	private ViewState currentState;
	
	public void startMainMenu(Stage stage)
	{
		currentState = new MainMenu();
		currentState.start(stage);
	}
}

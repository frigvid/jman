package com.frigvid.jman.view.state;

import com.frigvid.jman.view.MainMenu;
import javafx.stage.Stage;

/**
 * A class representing the view state manager.
 * <p/>
 * This class is part of the Strategy pattern.
 * <p/>
 * <b>Pattern: </b>Strategy
 * <p/>
 * <b>Role: </b>Context
 * <p/>
 * <b>Collaborators: </b>ViewState
 * <p/>
 * <b>Responsibility: </b>To provide a more flexible way of managing
 * the different states of the application, and to avoid having to send
 * around the stage directly.
 * <p/>
 * <b>Reasons for use: </b>To provide a more flexible way of managing
 * the different states of the application, and to avoid having to send
 * around the stage directly.
 *
 * @see IViewState
 * @see MainMenu
 * @see com.frigvid.jman.view.MainMenu
 * @see com.frigvid.jman.view.GameBoard
 * @see IViewState
 * @see com.frigvid.jman.view.state.ViewStateManager
 * @author frigvid
 * @created 2024-02-14
 * @since 0.1
 * @version 0.1
 */
public class ViewStateManager
{
	private IViewState currentState;
	
	public void startMainMenu(Stage stage)
	{
		currentState = new MainMenu();
		currentState.start(stage);
	}
}

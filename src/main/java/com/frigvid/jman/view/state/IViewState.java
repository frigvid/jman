package com.frigvid.jman.view.state;

import javafx.stage.Stage;

/**
 * A view state interface for the different
 * states of the application.
 * <br/><br/>
 * This interface should be implemented by
 * classes that represent different states
 * of the application, such as the main menu,
 * the game board, etc.
 * <br/><br/>
 * This is to provide a more flexible way of
 * managing the different states of the application,
 * and to avoid having to send around the stage
 * directly.
 * <br/><br/>
 * This interface is part of the Strategy pattern.
 * <br/><br/>
 * <b>Pattern: </b>Strategy
 * <br/><br/>
 * <b>Role: </b>Interface
 * <br/><br/>
 * <b>Collaborators: </b>One or more classes that
 * implement this interface, to represent different
 * states of the application.
 * <br/><br/>
 * <b>Responsibility: </b>To provide a common
 * interface for different states of the application,
 * to allow for a more flexible way of managing the
 * different states, and to avoid having to send around
 * the stage directly.
 * <br/><br/>
 * <b>Reasons for use: </b>To provide a more flexible
 * way of managing the different states of the application,
 * and to avoid having to send around the stage directly.
 * <br/><br/>
 * @see ViewStateManager
 * @see IViewState
 * @see com.frigvid.jman.view.MainMenu
 * @see com.frigvid.jman.view.GameBoard
 * @author frigvid
 * @created 2024-02-14
 * @since 0.1
 * @version 1.0
 */
public interface IViewState
{
	void start(Stage stage);
}

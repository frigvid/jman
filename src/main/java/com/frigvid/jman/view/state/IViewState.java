package com.frigvid.jman.view.state;

import javafx.stage.Stage;

/**
 * A view state interface for the different
 * states of the application.
 * <p/>
 * This interface should be implemented by
 * classes that represent different states
 * of the application, such as the main menu,
 * the game board, etc.
 * <p/>
 * This is to provide a more flexible way of
 * managing the different states of the application,
 * and to avoid having to send around the stage
 * directly.
 * <p/>
 * This interface is part of the Strategy pattern.
 * <p/>
 * <b>Pattern: </b>Strategy
 * <p/>
 * <b>Role: </b>Interface
 * <p/>
 * <b>Collaborators: </b>One or more classes that
 * implement this interface, to represent different
 * states of the application.
 * <p/>
 * <b>Responsibility: </b>To provide a common
 * interface for different states of the application,
 * to allow for a more flexible way of managing the
 * different states, and to avoid having to send around
 * the stage directly.
 * <p/>
 * <b>Reasons for use: </b>To provide a more flexible
 * way of managing the different states of the application,
 * and to avoid having to send around the stage directly.
 *
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

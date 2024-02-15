package com.frigvid.jman.view;

import com.frigvid.jman.view.state.IViewState;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static com.frigvid.jman.Constants.WINDOW_BACKGROUND_COLOR;
import static com.frigvid.jman.Constants.MENU_BUTTON_STYLE;

/**
 * A class representing the game board view state.
 * <br/><br/>
 * This class is part of the Strategy pattern.
 * <br/><br/>
 * <b>Pattern: </b>Strategy
 * <br/><br/>
 * <b>Role: </b>Concrete Strategy
 * <br/><br/>
 * <b>Collaborators: </b>ViewStateManager, SceneBuilder
 * <br/><br/>
 * <b>Responsibility: </b>To provide the view state for the game board,
 * and to allow for a more flexible way of managing the different states
 * of the application.
 * <br/><br/>
 * <b>Reasons for use: </b>To provide a more flexible way of managing
 * the different states of the application, and to avoid having to send
 * around the stage directly.
 * <br/><br/>
 *
 * @author frigvid
 * @version 0.1
 * @created 2024-02-14
 * @see com.frigvid.jman.view.MainMenu
 * @see com.frigvid.jman.view.SceneBuilder
 * @see com.frigvid.jman.view.state.ViewStateManager
 * @see com.frigvid.jman.view.state.IViewState
 * @since 0.1
 */
public class GameBoard
	implements IViewState
{
	private static final String WINDOW_TITLE = "J-Man! Game Stage";
	private static int highScore = 0;
	
	@Override
	public void start(Stage stage)
	{
		// Root layout.
		BorderPane root = new BorderPane();
		root.setStyle(WINDOW_BACKGROUND_COLOR);
		
		// Header bar, for score and such.
		HBox headerHBox = new HBox();
		headerHBox.setAlignment(javafx.geometry.Pos.CENTER);
		
		// Game board, where the actual level will be drawn.
		// NOTE: Consider using SubScene for the actual game?
		Pane gameBoard = new Pane();
		
		// FIXME: This is a temporary high score value.
		setHighScore(69420);
		
		// Header score counter.
		// TODO: Implement a way to update the score dynamically.
		Label labelHighScore = new Label("High Score: " + getHighScore());
		labelHighScore.setFont(new Font(20.0));
		labelHighScore.setStyle("""
				-fx-text-fill: white;
				-fx-font-weight: bold;
			""");
		
		// Noob button.
		Button buttonQuitToMainMenu = new Button("Quit to Main Menu");
		buttonQuitToMainMenu.setStyle(MENU_BUTTON_STYLE);
		buttonQuitToMainMenu.setOnAction(e ->
		{
			IViewState view = new MainMenu();
			view.start(stage);
		});
		
		headerHBox.getChildren()
			.addAll(
				buttonQuitToMainMenu,
				labelHighScore
			);
		
		// Add elements to root.
		root.setTop(headerHBox);
		root.setCenter(gameBoard);
		
		new SceneBuilder()
			.setStage(stage)
			.setRoot(root)
			.setTitle(WINDOW_TITLE)
			.build();
		
		stage.show();
	}
	
	/**
	 * Set the high score.
	 *
	 * @param highScore The high score.
	 */
	public static void setHighScore(int highScore)
	{
		GameBoard.highScore = highScore;
	}
	
	/**
	 * Get the high score.
	 *
	 * @return The high score.
	 */
	public static int getHighScore()
	{
		return highScore;
	}
}

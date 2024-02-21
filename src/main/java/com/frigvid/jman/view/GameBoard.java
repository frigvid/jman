package com.frigvid.jman.view;

import com.frigvid.jman.Constants;
import com.frigvid.jman.view.state.IViewState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static com.frigvid.jman.Constants.GAME_TITLE;
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
	private static final String WINDOW_TITLE = GAME_TITLE + " Game Stage";
	private static int highScore = 0;
	
	@Override
	public void start(Stage stage)
	{
		if (Constants.DEBUG_ENABLED) {System.out.println("GameBoard: `start` class called.");};

		// Root layout.
		BorderPane root = new BorderPane();
		root.setStyle(WINDOW_BACKGROUND_COLOR);
		
		// Header bar, for score and such.
		StackPane header = new StackPane();
		header.setPadding(new Insets(10));
		
		// For the button.
		HBox headerLeft = new HBox();
		headerLeft.setAlignment(Pos.CENTER_LEFT);
		
		// For the label.
		HBox headerCenter = new HBox();
		headerCenter.setAlignment(Pos.CENTER);
		
		// Game board, where the actual level will be drawn.
		// NOTE: Consider using SubScene for the actual game?
		Pane gameBoard = new Pane();
		
		// FIXME: This is a temporary high score value.
		setHighScore(69420);
		
		// Header score counter.
		// TODO: Implement a way to update the score dynamically.
		Label labelHighScore = new Label("High Score: " + getHighScore());
		labelHighScore.setFont(new Font(20.0 * Constants.SCALE_FACTOR));
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

		headerLeft.getChildren()
			.add(buttonQuitToMainMenu);
		
		headerCenter.getChildren()
			.add(labelHighScore);
		
		/* If you're wondering why they're added in reverse order, it has to do with how StackPane works.
		 * I'm using it as a kind of pretty ugly workaround to get the elements to align properly. However,
		 * since it actually stacks them on top of each other, you'll be clicking the HBox instead of the
		 * button in the normal order.
		 *
		 * In a reverse order, the button's HBox is first, and the button is thusly reachable.
		 *
		 * I hate it, and there's likely a much cleaner way of doing this, but I don't know of it at this
		 * moment, so this is what it'll be for the foreseeable future.
		 */
		header.getChildren()
			.addAll(
				headerCenter,
				headerLeft
			);
		
		// Add elements to root.
		root.setTop(header);
		root.setCenter(gameBoard);
		
		new SceneBuilder()
			.setStage(stage)
			.setRoot(root)
			.setTitle(WINDOW_TITLE)
			.build();

		if (Constants.DEBUG_ENABLED) {System.out.println("GameBoard: Showing stage.");}

		stage.show();
	}
	
	/* Setters & Getters. */
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

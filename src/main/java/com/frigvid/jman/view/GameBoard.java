package com.frigvid.jman.view;

import com.frigvid.jman.Constants;
import com.frigvid.jman.entity.Direction;
import com.frigvid.jman.entity.Entity;
import com.frigvid.jman.level.Level;
import com.frigvid.jman.map.TileMap;
import com.frigvid.jman.view.state.IViewState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

import static com.frigvid.jman.Constants.*;

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
	private Level level;
	private Entity player;
	private ImageView playerView;
	private Group gameBoard;
	
	@Override
	public void start(Stage stage)
	{
		if (Constants.DEBUG_ENABLED)
		{
			System.out.println("GameBoard: `start` class called.");
		}
		;
		
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
		//gameBoard = new Group();
		// Testing, this works:
		level = new Level("map1");
		//TileMap tileMap = new TileMap(level);
		//gameBoard.getChildren().add(tileMap.render());
		
		gameBoard = new Group();
		TileMap tileMap = new TileMap(level);
		gameBoard.getChildren().add(tileMap.render());
		
		// Create a SubScene for the gameBoard
		SubScene gameBoardSubScene = new SubScene(gameBoard, 0, 0);
		
		// Bind the SubScene's size to the parent BorderPane's size
		gameBoardSubScene.widthProperty().bind(root.widthProperty());
		gameBoardSubScene.heightProperty().bind(root.heightProperty());
		
		gameBoardSubScene.setFocusTraversable(true); // Allow it to capture key events
		
		// Attach key event handlers to the SubScene
		gameBoardSubScene.setOnKeyPressed(event ->
		{
			// Your key event handling logic here
			if (player != null)
			{
				switch (event.getCode())
				{
					case LEFT ->
					{
						if (Constants.DEBUG_ENABLED)
						{
							System.out.println("Player's current direction: LEFT");
						}
						player.move(Direction.LEFT, level.getLevelWidth(), level.getLevelHeight());
					}
					case RIGHT ->
					{
						if (Constants.DEBUG_ENABLED)
						{
							System.out.println("Player's current direction: RIGHT");
						}
						player.move(Direction.RIGHT, level.getLevelWidth(), level.getLevelHeight());
					}
					case UP ->
					{
						if (Constants.DEBUG_ENABLED)
						{
							System.out.println("Player's current direction: UP");
						}
						player.move(Direction.UP, level.getLevelWidth(), level.getLevelHeight());
					}
					case DOWN ->
					{
						if (Constants.DEBUG_ENABLED)
						{
							System.out.println("Player's current direction: DOWN");
						}
						player.move(Direction.DOWN, level.getLevelWidth(), level.getLevelHeight());
					}
				}
			}
			
			event.consume();
		});
		
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
		//root.setCenter(gameBoard);
		StackPane centeredGameBoard = new StackPane(gameBoardSubScene);
		root.setCenter(centeredGameBoard);
		
		new SceneBuilder()
			.setStage(stage)
			.setRoot(root)
			.setTitle(WINDOW_TITLE)
			.build();
		
		loadPlayer();
		
		if (Constants.DEBUG_ENABLED)
		{
			System.out.println("GameBoard: Showing stage.");
		}
		
		stage.show();
	}
	
	private void loadPlayer()
	{
		Image playerImage = new Image(
			Objects.requireNonNull(
				getClass().getResourceAsStream(
					"/com/frigvid/jman/entity/player/jman.gif"
				)
			)
		);
		
		playerView = new ImageView(playerImage);
		playerView.setFitWidth(Constants.TILE_SIZE * Constants.SCALE_FACTOR);
		playerView.setFitHeight(Constants.TILE_SIZE * Constants.SCALE_FACTOR);
		
		player = new Entity(
			level.getPlayerSpawnRow(),
			level.getPlayerSpawnCol(),
			playerView
		);
		
		gameBoard.getChildren()
					.add(player.getImageView());
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

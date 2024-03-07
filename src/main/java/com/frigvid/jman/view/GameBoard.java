package com.frigvid.jman.view;

import com.frigvid.jman.Constants;
import com.frigvid.jman.entity.Direction;
import com.frigvid.jman.entity.Entity;
import com.frigvid.jman.entity.player.Player;
import com.frigvid.jman.level.Level;
import com.frigvid.jman.map.TileMap;
import com.frigvid.jman.view.state.IViewState;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
	private static IntegerProperty score = new SimpleIntegerProperty(0);
	private static Label labelScore;
	private final String debugPlayerDirection = "Player direction: ";
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
							System.out.println(debugPlayerDirection + "LEFT");
						}
						player.move(Direction.LEFT, level, tileMap);
					}
					case RIGHT ->
					{
						if (Constants.DEBUG_ENABLED)
						{
							System.out.println(debugPlayerDirection + "RIGHT");
						}
						player.move(Direction.RIGHT, level, tileMap);
					}
					case UP ->
					{
						if (Constants.DEBUG_ENABLED)
						{
							System.out.println(debugPlayerDirection + "UP");
						}
						player.move(Direction.UP, level, tileMap);
					}
					case DOWN ->
					{
						if (Constants.DEBUG_ENABLED)
						{
							System.out.println(debugPlayerDirection + "DOWN");
						}
						player.move(Direction.DOWN, level, tileMap);
					}
				}
			}
			
			event.consume();
		});
		
		// Header score counter.
		//drawScore();
		labelScore = new Label("High Score: " + getHighScore());
		labelScore.setFont(new Font(20.0 * Constants.SCALE_FACTOR));
		labelScore.setStyle("""
				-fx-text-fill: white;
				-fx-font-weight: bold;
			""");
		
		labelScore.textProperty().bind(score.asString());
		
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
			.add(labelScore);
		
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
		
		//player = new Player(
		//	level,
		//	playerView
		//);
		//
		//player.load(gameBoard);
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
	
		player = new Player(
			level,
			playerView
		);
	
		gameBoard.getChildren()
					.add(player.getSprite());
	}
	
	/**
	 * Wrapper method for {@link #setScore}.
	 * <p/>
	 * Allows for easy score increase.
	 * <p/>
	 * <strong>Role:</strong> Utility.
	 * @param value The value to increase the score by.
	 * @see #setScore(boolean, int)
	 */
	public static void increaseScoreBy(int value)
	{
		setScore(false, value);
	}
	
	/**
	 * Wrapper method for {@link #setScore}.
	 * <p/>
	 * Allows for easy score subtraction.
	 * <p/>
	 * <strong>Role:</strong> Utility.
	 * @param value The value to decrease the score by.
	 * @see #setScore(boolean, int)
	 */
	public static void reduceScoreBy(int value)
	{
		setScore(true, value);
	}
	
	/**
	 * Get the high score.
	 *
	 * @return The high score.
	 */
	public static int getHighScore()
	{
		return score.get();
	}
	
	/* Setters. */
	/**
	 * Add to, or subtract from the score.
	 * <p/>
	 * Method is static to allow for easy access from other classes.
	 *
	 * @param subtract Whether to subtract from the score.
	 * @param value The value to add to, or subtract from the score.
	 */
	public static void setScore(boolean subtract, int value)
	{
		if (subtract)
		{
			score.set(
				score.get() - value
			);
		}
		else
		{
			score.set(
				score.get() + value
			);
		}
	}
}

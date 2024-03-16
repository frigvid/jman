package com.frigvid.jman.view;

import com.frigvid.jman.Constants;
import com.frigvid.jman.entity.Direction;
import com.frigvid.jman.entity.ghost.Ghost;
import com.frigvid.jman.entity.ghost.personality.Cyan;
import com.frigvid.jman.entity.ghost.personality.Orange;
import com.frigvid.jman.entity.ghost.personality.Pink;
import com.frigvid.jman.entity.ghost.personality.Red;
import com.frigvid.jman.entity.player.Player;
import com.frigvid.jman.game.TickController;
import com.frigvid.jman.game.map.Map;
import com.frigvid.jman.view.state.IViewState;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
	private BorderPane root;
	private Player player;
	private String mapBasename;
	private Map map;
	private Group board;
	
	@Override
	public void start(Stage stage)
	{
		if (Constants.DEBUG_ENABLED)
		{
			System.out.println("GameBoard: `start` class called.");
		}
		
		// Ensure TickController starts.
		TickController.getInstance();
		
		// Root layout.
		root = new BorderPane();
		root.setStyle(WINDOW_BACKGROUND_COLOR);
		
		// Add elements to root.
		root.setTop(createHeader(stage));
		root.setCenter(createGameBoard());
		
		new SceneBuilder()
			.setStage(stage)
			.setRoot(root)
			.setTitle(WINDOW_TITLE)
			.build();
		
		player = new Player(map);
		player.load(board);
		
		// Initialize ghosts.
		Ghost cyan = new Cyan(map);
		cyan.load(board);
		cyan.start(player);
		
		Ghost orange = new Orange(map);
		orange.load(board);
		orange.start(player);
		
		Ghost pink = new Pink(map);
		pink.load(board);
		pink.start(player);
		
		Ghost red = new Red(map);
		red.load(board);
		red.start(player);
		
		if (Constants.DEBUG_ENABLED)
		{
			System.out.println("GameBoard: Showing stage.");
		}
		
		stage.show();
		
		// Stop TickController thread if the window closes.
		stage.setOnCloseRequest(
			event -> {
				TickController.getInstance().stop();
				
				/* Murder that poor bastard.
				 *
				 * Just in case the thread refuses to close.
				 * It's sadly not that rare.
				 */
				System.exit(0);
			}
		);
	}
	
	/* Utilities. */
	/**
	 * Loads the game board.
	 *
	 * @return The game board SubScene.
	 */
	private SubScene createGameBoard()
	{
		board = new Group();
		board.getChildren().add(map.getVisualGrid());
		
		SubScene boardGame = new SubScene(board, 0, 0);
		boardGame.widthProperty()
			.bind(map.getVisualGrid().widthProperty());
		boardGame.heightProperty()
			.bind(map.getVisualGrid().heightProperty());
		// Allow it to capture key events.
		boardGame.setFocusTraversable(true);
		
		// Attach key event handlers to the SubScene.
		boardGame.setOnKeyPressed(event ->
		{
			if (player != null && player.isAlive())
			{
				switch (event.getCode())
				{
					case LEFT ->
					{
						if (Constants.DEBUG_ENABLED)
						{
							System.out.println(debugPlayerDirection + "LEFT");
						}
						
						TickController.getInstance()
							.onNextTick(player, player -> this.player.move(Direction.LEFT, map));
					}
					case RIGHT ->
					{
						if (Constants.DEBUG_ENABLED)
						{
							System.out.println(debugPlayerDirection + "RIGHT");
						}
						
						TickController.getInstance()
							.onNextTick(player, player -> this.player.move(Direction.RIGHT, map));
					}
					case UP ->
					{
						if (Constants.DEBUG_ENABLED)
						{
							System.out.println(debugPlayerDirection + "UP");
						}
						
						TickController.getInstance()
							.onNextTick(player, player -> this.player.move(Direction.UP, map));
					}
					case DOWN ->
					{
						if (Constants.DEBUG_ENABLED)
						{
							System.out.println(debugPlayerDirection + "DOWN");
						}
						
						TickController.getInstance()
							.onNextTick(player, player -> this.player.move(Direction.DOWN, map));
					}
				}
			}
			
			event.consume();
		});
		
		return boardGame;
	}
	
	/**
	 * Loads the header.
	 *
	 * @param stage The stage to load the header into.
	 * @return The header StackPane.
	 */
	private StackPane createHeader(Stage stage)
	{
		// Header bar, for score and such.
		StackPane header = new StackPane();
		header.setPadding(new Insets(10));
		
		HBox headerButton = new HBox() {{ setAlignment(Pos.CENTER_LEFT); }};
		HBox headerLabel = new HBox() {{ setAlignment(Pos.CENTER); }};
		
		// Header score counter.
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
		buttonQuitToMainMenu.setCursor(Cursor.HAND);
		buttonQuitToMainMenu.setOnAction(e ->
		{
			TickController.getInstance().stop();
			IViewState view = new MainMenu();
			view.start(stage);
		});
		
		headerButton.getChildren()
			.add(buttonQuitToMainMenu);
		
		headerLabel.getChildren()
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
				headerLabel,
				headerButton
			);
		
		return header;
	}
	
	/**
	 * Wrapper method for {@link #setScore}.
	 * <p/>
	 * Allows for easy score increase.
	 * <p/>
	 * <strong>Role:</strong> Utility.
	 *
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
	 *
	 * @param value The value to decrease the score by.
	 * @see #setScore(boolean, int)
	 */
	public static void reduceScoreBy(int value)
	{
		setScore(true, value);
	}
	
	/* Getters. */
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
	 * @param value    The value to add to, or subtract from the score.
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
	
	/**
	 * Set the map to be used.
	 * <p/>
	 * This is used outside of this class, as it not only
	 * stores the map filename to an internal variable,
	 * but also creates a new Map object.
	 * <p/>
	 * Example usage:
	 * {@snippet id="setMap" :
	 * 	GameBoard gameBoard = new GameBoard();
	 * 	gameBoard.setMap(Constants.GAME_FIRST_MAP);
	 * 	gameBoard.start(stage);
	 * }
	 *
	 * @param mapFilename The filename of the map to use.
	 */
	public void setMap(String mapFilename)
	{
		this.mapBasename = mapFilename;
		map = new Map(mapBasename);
	}
}

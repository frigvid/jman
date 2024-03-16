package com.frigvid.jman.view;

import com.frigvid.jman.Constants;
import com.frigvid.jman.view.state.IViewState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

import static java.lang.System.exit;

/**
 * The main menu.
 *
 * @author frigvid
 * @created 2024-02-14
 * @since 0.1
 */
public class MainMenu
	implements IViewState
{
	private static final String WINDOW_TITLE = Constants.GAME_TITLE + " Main Menu";
	private static final double GHOST_FIT_WIDTH = 43.0 * Constants.SCALE_FACTOR_WIDTH;
	private static final double GHOST_FIT_HEIGHT = 69.0 * Constants.SCALE_FACTOR_HEIGHT;
	private static final String GHOST_PATH_PREFIX = Objects.requireNonNull(MainMenu.class.getResource(Constants.RESOURCE_BASE_PATH + "entity/ghost/")).toExternalForm();
	
	@Override
	public void start(Stage stage)
	{
		StackPane root = new StackPane();
		root.setStyle(Constants.WINDOW_BACKGROUND_COLOR);
		
		VBox mainVBox = new VBox();
		mainVBox.setAlignment(Pos.CENTER);
		mainVBox.setLayoutY(20);
		mainVBox.setSpacing(10);
		
		/* Row of ghosts above the menu title.
		 *
		 * Building the images uses the same pattern, so it could be extracted into a method.
		 * However, since there's only four, I don't really see a reason to do so. Hard-coding
		 * it for each allows for modifications to each (which, mind you, would still be possible
		 * in a method), but eh, whatever. I prefer it like this.
		 */
		HBox ImageHBox = new HBox();
		ImageHBox.setAlignment(Pos.CENTER);
		ImageHBox.setSpacing(15.0);
		
		ImageView blinkyIV = new ImageView(new Image(GHOST_PATH_PREFIX + "blinky/blinky.png"));
		blinkyIV.setFitHeight(GHOST_FIT_HEIGHT);
		blinkyIV.setFitWidth(GHOST_FIT_WIDTH);
		blinkyIV.setPickOnBounds(true);
		blinkyIV.setPreserveRatio(true);
		
		ImageView pinkyIV = new ImageView(new Image(GHOST_PATH_PREFIX + "pinky/pinky.png"));
		pinkyIV.setFitHeight(GHOST_FIT_HEIGHT);
		pinkyIV.setFitWidth(GHOST_FIT_WIDTH);
		pinkyIV.setPickOnBounds(true);
		pinkyIV.setPreserveRatio(true);
		
		ImageView inkyIV = new ImageView(new Image(GHOST_PATH_PREFIX + "inky/inky.png"));
		inkyIV.setFitHeight(GHOST_FIT_HEIGHT);
		inkyIV.setFitWidth(GHOST_FIT_WIDTH);
		inkyIV.setPickOnBounds(true);
		inkyIV.setPreserveRatio(true);
		
		ImageView clydeIV = new ImageView(new Image(GHOST_PATH_PREFIX + "clyde/clyde.png"));
		clydeIV.setFitHeight(GHOST_FIT_HEIGHT);
		clydeIV.setFitWidth(GHOST_FIT_WIDTH);
		clydeIV.setPickOnBounds(true);
		clydeIV.setPreserveRatio(true);
		
		ImageHBox.getChildren().addAll(
			blinkyIV,
			pinkyIV,
			inkyIV,
			clydeIV
		);
		
		// Menu title.
		Label labelTitle = new Label(Constants.GAME_TITLE);
		labelTitle.setStyle(
			  "-fx-text-fill: yellow;"
			+ "-fx-font-weight: bold;"
			+ "-fx-font-family: 'Arial';"
			+ "-fx-font-size: " + 24 * Constants.SCALE_FACTOR + "px;"
			+ "-fx-padding-down: " + 200 * Constants.SCALE_FACTOR + "px;"
		);
		// Add some padding between the images and buttons.
		VBox.setMargin(labelTitle, new Insets(5.0, 0, 10.0, 0));
		
		VBox buttonVBox = new VBox();
		buttonVBox.setAlignment(Pos.CENTER);
		buttonVBox.setSpacing(20);
		
		// Menu buttons.
		Button buttonStartGame = new Button("Start Game");
		buttonStartGame.setStyle(Constants.MENU_BUTTON_STYLE);
		buttonStartGame.setCursor(Cursor.HAND);
		
		buttonStartGame.setOnAction(event ->
		{
			GameBoard gameBoard = new GameBoard();
			// Hard-coded for first map.
			gameBoard.setMap(Constants.GAME_FIRST_MAP);
			gameBoard.start(stage);
		});
		
		Button buttonHighScores = new Button("High Scores");
		buttonHighScores.setStyle(Constants.MENU_BUTTON_STYLE);
		buttonHighScores.setDisable(true);
		buttonHighScores.setCursor(Cursor.HAND);
		
		Button buttonMapEditor = new Button("Map Editor");
		buttonMapEditor.setStyle(Constants.MENU_BUTTON_STYLE);
		buttonMapEditor.setDisable(true);
		buttonMapEditor.setCursor(Cursor.HAND);
		
		Button buttonSettings = new Button("Settings");
		buttonSettings.setStyle(Constants.MENU_BUTTON_STYLE);
		buttonSettings.setDisable(true);
		buttonSettings.setCursor(Cursor.HAND);
		
		Button buttonQuit = new Button("Quit");
		buttonQuit.setStyle(Constants.MENU_BUTTON_STYLE);
		buttonQuit.setCursor(Cursor.HAND);
		
		// Graceful exit.
		buttonQuit.setOnAction(event ->
		{
			exit(0); // Normal exit.
		});
		
		buttonVBox.getChildren()
			.addAll(
				buttonStartGame,
				buttonHighScores,
				buttonMapEditor,
				buttonSettings,
				buttonQuit
			);
		
		mainVBox.getChildren()
			.addAll(
				ImageHBox,
				labelTitle,
				buttonVBox
			);
		
		root.getChildren()
			.add(mainVBox);

		// Create the scene.
		new SceneBuilder()
			.setStage(stage)
			.setRoot(root)
			.setTitle(WINDOW_TITLE)
			.build();
		
		stage.show();
	}
}

package com.frigvid.jman.view;

import com.frigvid.jman.view.state.ViewState;
import javafx.geometry.Insets;
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
 * @version 0.5
 * @created 2024-02-14
 * @since 0.1
 */
public class MainMenu
	implements ViewState
{
	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 500;
	private static final String MENU_TITLE = "J-Man!";
	private static final String WINDOW_TITLE = MENU_TITLE + " Main Menu";
	private static final double GHOST_FIT_HEIGHT = 69.0;
	private static final double GHOST_FIT_WIDTH = 43.0;
	private static final String GHOST_PATH_PREFIX = Objects.requireNonNull(MainMenu.class.getResource("/com/frigvid/jman/entity/ghost/")).toExternalForm();
	private static final String MENU_BUTTON_STYLE = "-fx-background-color: yellow; -fx-font-weight: bold; -fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-min-width: 100px; -fx-min-height: 20px;";
	
	@Override
	public void start(Stage stage)
	{
		StackPane root = new StackPane();
		root.setPrefSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		root.setStyle("-fx-background-color: black;");
		
		VBox mainVBox = new VBox();
		mainVBox.setAlignment(javafx.geometry.Pos.CENTER);
		mainVBox.setLayoutY(20);
		mainVBox.setSpacing(10);
		
		// Row of ghosts above the menu title.
		HBox ImageHBox = new HBox();
		ImageHBox.setAlignment(javafx.geometry.Pos.CENTER);
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
		Label titleLabel = new Label(MENU_TITLE);
		titleLabel.setStyle("""
					-fx-text-fill: yellow;
					-fx-font-weight: bold;
					-fx-font-family: 'Arial';
					-fx-font-size: 24px;
					-fx-padding-down: 200px;
				""");
		VBox.setMargin(titleLabel, new Insets(5.0, 0, 10.0, 0));
		
		VBox buttonsVBox = new VBox();
		buttonsVBox.setAlignment(javafx.geometry.Pos.CENTER);
		buttonsVBox.setSpacing(20);
		
		// Menu buttons.
		Button startGameButton = new Button("Start Game");
		startGameButton.setStyle(MENU_BUTTON_STYLE);
		startGameButton.setCursor(Cursor.HAND);
		
		startGameButton.setOnAction(event ->
		{
			ViewState view = new GameBoard();
			view.start(stage);
		});
		
		Button highScoresButton = new Button("High Scores");
		highScoresButton.setStyle(MENU_BUTTON_STYLE);
		highScoresButton.setDisable(true);
		highScoresButton.setCursor(Cursor.HAND);
		
		Button mapEditorButton = new Button("Map Editor");
		mapEditorButton.setStyle(MENU_BUTTON_STYLE);
		mapEditorButton.setDisable(true);
		mapEditorButton.setCursor(Cursor.HAND);
		
		Button settingsButton = new Button("Settings");
		settingsButton.setStyle(MENU_BUTTON_STYLE);
		settingsButton.setDisable(true);
		settingsButton.setCursor(Cursor.HAND);
		
		Button quitButton = new Button("Quit");
		quitButton.setStyle(MENU_BUTTON_STYLE);
		quitButton.setCursor(Cursor.HAND);
		
		// Graceful exit.
		quitButton.setOnAction(event ->
		{
			exit(0); // Normal exit.
		});
		
		buttonsVBox.getChildren()
			.addAll(
				startGameButton,
				highScoresButton,
				mapEditorButton,
				settingsButton,
				quitButton
			);
		
		mainVBox.getChildren()
			.addAll(
				ImageHBox,
				titleLabel,
				buttonsVBox
			);
		
		root.getChildren()
			.add(mainVBox);
		
		new SceneBuilder()
			.setStage(stage)
			.setRoot(root)
			.setTitle(WINDOW_TITLE)
			.setWidth(DEFAULT_WIDTH)
			.setHeight(DEFAULT_HEIGHT)
			.build();
		
		stage.setMaximized(true);
		stage.show();
	}
}

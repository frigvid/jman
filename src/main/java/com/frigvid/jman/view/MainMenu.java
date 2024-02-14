package com.frigvid.jman.view;

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
	private static final String TITLE = "J-Man! Main Menu";
	
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
		
		HBox ghostImagesHBox = new HBox();
		ghostImagesHBox.setAlignment(javafx.geometry.Pos.CENTER);
		ghostImagesHBox.setSpacing(15.0);
		
		String ghostImagePathPrefix = Objects.requireNonNull(getClass().getResource("/com/frigvid/jman/entity/ghost/")).toExternalForm();
		
		ImageView blinkyImageView = new ImageView(new Image(ghostImagePathPrefix + "blinky/blinky.png"));
		blinkyImageView.setFitHeight(69.0);
		blinkyImageView.setFitWidth(43.0);
		blinkyImageView.setPickOnBounds(true);
		blinkyImageView.setPreserveRatio(true);
		
		ImageView pinkyImageView = new ImageView(new Image(ghostImagePathPrefix + "pinky/pinky.png"));
		pinkyImageView.setFitHeight(69.0);
		pinkyImageView.setFitWidth(43.0);
		pinkyImageView.setPickOnBounds(true);
		pinkyImageView.setPreserveRatio(true);
		
		ImageView inkyImageView = new ImageView(new Image(ghostImagePathPrefix + "inky/inky.png"));
		inkyImageView.setFitHeight(69.0);
		inkyImageView.setFitWidth(43.0);
		inkyImageView.setPickOnBounds(true);
		inkyImageView.setPreserveRatio(true);
		
		ImageView clydeImageView = new ImageView(new Image(ghostImagePathPrefix + "clyde/clyde.png"));
		clydeImageView.setFitHeight(69.0);
		clydeImageView.setFitWidth(43.0);
		clydeImageView.setPickOnBounds(true);
		clydeImageView.setPreserveRatio(true);
		
		ghostImagesHBox.getChildren().addAll(blinkyImageView, pinkyImageView, inkyImageView, clydeImageView);
		
		Label titleLabel = new Label("J-Man");
		titleLabel.setStyle("-fx-text-fill: yellow; -fx-font-weight: bold; -fx-font-family: 'Arial'; -fx-font-size: 24px; -fx-padding-down: 200px;");
		VBox.setMargin(titleLabel, new Insets(5.0, 0, 10.0, 0));
		
		VBox buttonsVBox = new VBox();
		buttonsVBox.setAlignment(javafx.geometry.Pos.CENTER);
		buttonsVBox.setSpacing(20);
		
		Button startGameButton = new Button("Start Game");
		startGameButton.setStyle("-fx-background-color: yellow; -fx-font-weight: bold; -fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-min-width: 100px; -fx-min-height: 20px;");
		startGameButton.setCursor(Cursor.HAND);
		
		startGameButton.setOnAction(event -> {
			ViewState view = new GameBoard();
			view.start(stage);
		});
		
		Button highScoresButton = new Button("High Scores");
		highScoresButton.setStyle("-fx-background-color: yellow; -fx-font-weight: bold; -fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-min-width: 100px; -fx-min-height: 20px;");
		highScoresButton.setDisable(true);
		highScoresButton.setCursor(Cursor.HAND);
		
		Button mapEditorButton = new Button("Map Editor");
		mapEditorButton.setStyle("-fx-background-color: yellow; -fx-font-weight: bold; -fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-min-width: 100px; -fx-min-height: 20px;");
		mapEditorButton.setDisable(true);
		mapEditorButton.setCursor(Cursor.HAND);
		
		Button settingsButton = new Button("Settings");
		settingsButton.setStyle("-fx-background-color: yellow; -fx-font-weight: bold; -fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-min-width: 100px; -fx-min-height: 20px;");
		settingsButton.setDisable(true);
		settingsButton.setCursor(Cursor.HAND);
		
		Button quitButton = new Button("Quit");
		quitButton.setStyle("-fx-background-color: yellow; -fx-font-weight: bold; -fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-min-width: 100px; -fx-min-height: 20px;");
		quitButton.setCursor(Cursor.HAND);
		
		quitButton.setOnAction(event -> {
			exit(0); // Normal exit.
		});
		
		buttonsVBox.getChildren().addAll(startGameButton, highScoresButton, mapEditorButton, settingsButton, quitButton);
		
		mainVBox.getChildren().addAll(ghostImagesHBox, titleLabel, buttonsVBox);
		
		root.getChildren().add(mainVBox);
		
		new SceneBuilder()
			.setStage(stage)
			.setRoot(root)
			.setTitle(TITLE)
			.setWidth(DEFAULT_WIDTH)
			.setHeight(DEFAULT_HEIGHT)
			.build();
		
		stage.setMaximized(true);
		stage.show();
	}
}

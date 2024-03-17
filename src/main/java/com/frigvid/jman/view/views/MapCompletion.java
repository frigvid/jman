package com.frigvid.jman.view.views;

import com.frigvid.jman.Constants;
import com.frigvid.jman.entity.player.Player;
import com.frigvid.jman.game.map.Map;
import com.frigvid.jman.view.GameBoard;
import com.frigvid.jman.view.MainMenu;
import com.frigvid.jman.view.SceneBuilder;
import com.frigvid.jman.view.state.IViewState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static com.frigvid.jman.Constants.*;

public class MapCompletion
	implements IViewState
{
	private static final String WINDOW_TITLE = GAME_TITLE + " Map Completion";
	private GameBoard gameBoard;
	private Player player;
	private int mapScore;
	private int totalScore;
	private Map currentMap;
	private String currentMapName;
	private boolean isLastMap;
	
	// FIXME: Components should probably be extracted to their own methods to make it a bit
	//			 easier to read and keep logic separate.
	@Override
	public void start(Stage stage)
	{
		BorderPane root = new BorderPane();
		
		Text title;
		Text p1;
		Text p3;
		
		if (player.isAlive())
		{
			title = new Text("Congratulations!");
			title.setStyle(Constants.TEXT_BASE_STYLE
				+ "-fx-font-size: " + 48 * SCALE_FACTOR + "px;"
				+ "-fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, gold 0%, yellow 50%);"
			);
			
			p1 = new Text("You've completed the map \"" + currentMapName + "\"!");
			p1.setStyle(Constants.TEXT_BASE_STYLE
				+ "-fx-fill: white;"
				+ "-fx-font-size: " + 12 * SCALE_FACTOR + "px;"
			);
			
			p3 = new Text("Would you like to exit to the menu or continue to the next map?");
			p3.setStyle(Constants.TEXT_BASE_STYLE
				+ "-fx-fill: white;"
				+ "-fx-font-size: " + 12 * SCALE_FACTOR + "px;"
			);
		}
		else
		{
			title = new Text("YOU ARE DEAD!");
			title.setStyle(Constants.TEXT_BASE_STYLE
				+ "-fx-font-size: " + 48 * SCALE_FACTOR + "px;"
				+ "-fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, darkred 0%, red 50%);"
			);
			
			p1 = new Text("You've died on map \"" + currentMapName + "\" ...");
			p1.setStyle(Constants.TEXT_BASE_STYLE
				+ "-fx-fill: white;"
				+ "-fx-font-size: " + 12 * SCALE_FACTOR + "px;"
			);
			
			p3 = new Text("Would you like to exit to the menu or retry?");
			p3.setStyle(Constants.TEXT_BASE_STYLE
				+ "-fx-fill: white;"
				+ "-fx-font-size: " + 12 * SCALE_FACTOR + "px;"
			);
		}
		
		Text p2 = new Text("You scored " + mapScore + ", for a total of " + totalScore + "!");
		p2.setStyle(Constants.TEXT_BASE_STYLE
			+ "-fx-fill: white;"
			+ "-fx-font-size: " + 12 * SCALE_FACTOR + "px;"
		);
		
		Button buttonQuit = new Button("Exit");
		buttonQuit.setStyle(Constants.MENU_BUTTON_STYLE);
		buttonQuit.setCursor(Cursor.HAND);
		buttonQuit.setOnAction(e ->
		{
			IViewState view = new MainMenu();
			view.start(stage);
		});
		
		Button buttonContinue = new Button("Continue");
		buttonContinue.setStyle(Constants.MENU_BUTTON_STYLE);
		buttonContinue.setCursor(Cursor.HAND);
		if (isLastMap)
		{
			buttonContinue.setDisable(true);
		}
		buttonContinue.setOnAction(e ->
		{
			gameBoard.start(stage);
		});
		
		Button buttonRetry = new Button("Retry!");
		buttonRetry.setStyle(Constants.MENU_BUTTON_STYLE);
		buttonRetry.setCursor(Cursor.HAND);
		buttonRetry.setOnAction(e ->
		{
			gameBoard = new GameBoard();
			gameBoard.setMap(currentMap.getBasename());
			gameBoard.start(stage);
		});
		
		VBox message;
		message = new VBox(20, p1, new Region(), p2, new Region(), p3);
		message.setAlignment(Pos.CENTER);
		message.setMinHeight(40);
		message.setPadding(
			new Insets(50, 0, 50, 0)
		);
		
		HBox buttons;
		
		if (player.isAlive())
		{
			buttons = new HBox(20, buttonQuit, buttonContinue);
		}
		else
		{
			buttons = new HBox(20, buttonQuit, buttonRetry);
		}
		
		buttons.setAlignment(Pos.CENTER);
		buttons.setMinHeight(40);
		
		VBox main;
		
		if (player.isAlive())
		{
			main = new VBox(20, title, message, buttons, buttonRetry);
			main.setStyle("-fx-background-color: black;"
				+ "-fx-border-color: yellow;"
				+ "-fx-border-width: 2px;"
			);
		}
		else
		{
			main = new VBox(20, title, message, buttons);
			main.setStyle("-fx-background-color: black;"
				+ "-fx-border-color: darkred;"
				+ "-fx-border-width: 2px;"
			);
		}
		
		main.setAlignment(Pos.CENTER);
		main.setPrefSize(500, 300);
		
		root.setCenter(main);
		
		new SceneBuilder()
			.setStage(stage)
			.setRoot(root)
			.setTitle(WINDOW_TITLE)
			.build();
		
		stage.show();
	}
	
	/* Setters. */
	public void setCurrentMap(Map currentMap)
	{
		this.currentMap = currentMap;
		setNextMap(currentMap);
		currentMapName = this.currentMap.getTitle();
	}
	
	private void setNextMap(Map nextMap)
	{
		gameBoard = new GameBoard();
		
		// FIXME: This is pretty ugly and throws a stacktrace if there's
		// 		 no next map. But it works, and I have other things to do atm lol.
		try
		{
			gameBoard.setMap(nextMap.getNextMap());
		}
		catch (Exception e)
		{
			isLastMap = true;
		}
	}
	
	public void setMapScore(int mapScore)
	{
		this.mapScore = mapScore;
		totalScore += mapScore;
	}
	
	public void setPlayer(Player player)
	{
		this.player = player;
	}
}

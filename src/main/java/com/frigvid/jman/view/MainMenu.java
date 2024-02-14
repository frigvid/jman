package com.frigvid.jman.view;

import javafx.stage.Stage;

/**
 * The main menu contains five buttons:
 * - Start Game.
 *   - Goes to: Map Select.
 * - High Scores.
 * - Map Editor.
 * - Settings.
 * - Quit.
 */
public class MainMenu
	implements ViewState
{
	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 500;
	
	@Override
	public void start(Stage stage)
	{
		
		new SceneBuilder()
			.setStage(stage)
			.setFxml("/com/frigvid/jman/view/main-menu.fxml")
			.setTitle("J-Man! Main Menu")
			.setWidth(DEFAULT_WIDTH)
			.setHeight(DEFAULT_HEIGHT)
			.build();
		
		stage.show();
	}
	
	public void StartGame(Stage stage)
	{
		ViewState view = new GameBoard();
		view.start(stage);
	}
}

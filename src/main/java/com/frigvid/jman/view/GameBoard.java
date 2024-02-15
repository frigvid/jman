package com.frigvid.jman.view;

import com.frigvid.jman.view.state.IViewState;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

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
 * @see com.frigvid.jman.view.MainMenu
 * @see com.frigvid.jman.view.SceneBuilder
 * @see com.frigvid.jman.view.state.ViewStateManager
 * @author frigvid
 * @created 2024-02-14
 * @see com.frigvid.jman.view.state.IViewState
 * @since 0.1
 * @version 0.1
 */
public class GameBoard
	implements IViewState
{
	@Override
	public void start(Stage stage)
	{
		Pane gameLayout = new Pane();
		Rectangle gameArea = new Rectangle(100, 100, 200, 200);
		gameArea.setFill(Color.BLUE);
		
		gameLayout.getChildren().add(gameArea);
		
		new SceneBuilder()
			.setStage(stage)
			.setRoot(gameLayout)
			.setTitle("J-Man! Game Stage")
			//.setWidth()
			//.setHeight()
			.build();
		
		stage.show();
	}
}

package com.frigvid.jman.view;

import com.frigvid.jman.view.state.ViewState;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameBoard
	implements ViewState
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

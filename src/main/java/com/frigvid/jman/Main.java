package com.frigvid.jman;

import com.frigvid.jman.view.SceneBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application
{
	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 500;
	
	public static void main(String[] args)
	{
		launch();
	}
	
	@Override
	public void start(Stage stage) throws IOException
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/frigvid/jman/view/main-menu.fxml"));
		
		new SceneBuilder()
			.setStage(stage)
			.setRoot(fxmlLoader.load())
			.setTitle("J-Man! Main Menu")
			.setWidth(DEFAULT_WIDTH)
			.setHeight(DEFAULT_HEIGHT)
			.build();
		
		stage.show();
	}
}

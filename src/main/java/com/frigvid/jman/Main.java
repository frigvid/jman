package com.frigvid.jman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 500;
    
    @Override
    public void start(Stage stage) throws IOException {
        //BorderPane root = new BorderPane();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/frigvid/jman/view/main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
        stage.setTitle("J-Man!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
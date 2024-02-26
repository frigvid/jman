package com.frigvid.jman.view;

import com.frigvid.jman.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.frigvid.jman.Constants.DEBUG_ENABLED;
import static com.frigvid.jman.Constants.GAME_TITLE;

/**
 * A builder class for creating JavaFX scenes,
 * following the Builder pattern.
 * <br/><br/>
 * This allows for a less cumbersome way of switching
 * between different menues and the like, without
 * sending the stage around directly.
 *
 * @author frigvid
 * @version 0.5
 * @created 2024-02-14
 * @since 0.1
 */
public class SceneBuilder
{
	private Stage stage;
	private Parent root;
	private String title = GAME_TITLE;
	private double width;
	private double height;
	private String fxmlPath;
	
	public SceneBuilder setStage(Stage stage)
	{
		this.stage = stage;
		return this;
	}
	
	public SceneBuilder setRoot(Parent root)
	{
		this.root = root;
		return this;
	}
	
	public SceneBuilder setTitle(String title)
	{
		this.title = title;
		return this;
	}
	
	public SceneBuilder setWidth(double width)
	{
		this.width = width;
		return this;
	}
	
	public SceneBuilder setHeight(double height)
	{
		this.height = height;
		return this;
	}
	
	public SceneBuilder setFxml(String fxmlPath)
	{
		this.fxmlPath = fxmlPath;
		return this;
	}
	
	public void build()
	{
		// Catch generic errors first.
		if (stage == null)
		{
			throw new IllegalStateException("Primary stage not set!");
		}
		else if (root == null && fxmlPath == null)
		{
			throw new IllegalStateException("Root element or FXML path not set!");
		}
		
		/* Check if the root is an FXML file.
		 *
		 * NOTE: Ignore the warning that fxmlPath is always true.
		 */
		if (root == null && fxmlPath != null)
		{
			try
			{
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
				root = fxmlLoader.load();
			}
			catch (IOException e)
			{
				// This is kind of ugly, but it works.
				System.err.println("Failed to load FXML file: " + fxmlPath);
				return; // Guarantee exit.
			}
		}

		if (DEBUG_ENABLED) {
			System.out.println("SceneBuilder:\nWidth: " + width + "\nHeight: " + height);
		}
		
		// Check if the width and height are set, and use the stage size if not.
		if (width <= 0 || height <= 0)
		{
			width = stage.getWidth();
			height = stage.getHeight();
		}

		Scene scene = new Scene(root, width, height);
		stage.setTitle(title);
		stage.setWidth(width);
		stage.setHeight(height);
		stage.setScene(scene);
	}
}

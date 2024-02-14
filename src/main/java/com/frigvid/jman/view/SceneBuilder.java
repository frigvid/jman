package com.frigvid.jman.view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author frigvid
 * @created 2024-02-14
 * @since 0.1
 * @version 0.1
 */
public class SceneBuilder
{
	private Stage stage;
	private Parent root;
	private String title = "J-Man!";
	private int width = 250;
	private int height = 250;
	
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
	
	public SceneBuilder setWidth(int width)
	{
		this.width = width;
		return this;
	}
	
	public SceneBuilder setHeight(int height)
	{
		this.height = height;
		return this;
	}
	
	public void build()
	{
		if (stage == null)
		{
			throw new IllegalStateException("Primary stage not set!");
		}
		else if (root == null)
		{
			throw new IllegalStateException("Root element not set!");
		}
		
		Scene scene = new Scene(root, width, height);
		stage.setTitle(title);
		stage.setScene(scene);
	}
}

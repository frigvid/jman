package com.frigvid.jman.level;

/**
 *
 * @author frigvid
 * @created 2024-02-19
 * @since 0.1
 */
public class LevelBuilder
{
	//private Level level;
	//private String levelTitle;
	//
	//public LevelBuilder parseLevel(String path)
	//{
	//	// Take the path, grab the contents of the file, parse it.
	//	return this;
	//}
	//
	//public LevelBuilder setLevel(Level level)
	//{
	//	this.level = level;
	//	return this;
	//}
	//
	//public LevelBuilder setTitle(String title)
	//{
	//	this.levelTitle = title;
	//	return this;
	//}
	//
	//public LevelBuilder setWalls()
	//{
	//
	//}
	//
	//public void build()
	//{
	//	// Catch generic errors first.
	//	if (stage == null)
	//	{
	//		throw new IllegalStateException("Primary stage not set!");
	//	}
	//	else if (root == null && fxmlPath == null)
	//	{
	//		throw new IllegalStateException("Root element or FXML path not set!");
	//	}
	//
	//	/* Check if the root is an FXML file.
	//	 *
	//	 * NOTE: Ignore the warning that fxmlPath is always true.
	//	 */
	//	if (root == null && fxmlPath != null)
	//	{
	//		try
	//		{
	//			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
	//			root = fxmlLoader.load();
	//		}
	//		catch (IOException e)
	//		{
	//			// This is kind of ugly, but it works.
	//			System.err.println("Failed to load FXML file: " + fxmlPath);
	//			return; // Guarantee exit.
	//		}
	//	}
	//
	//	// Check if the width and height are set, and use the stage size if not.
	//	// Also, see my insane ramblings regarding this in Main.java.
	//	if (width == 0 || height == 0)
	//	{
	//		width = (int) stage.getWidth();
	//		height = (int) stage.getHeight();
	//	}
	//
	//	Scene scene = new Scene(root, width, height);
	//	stage.setTitle(title);
	//	stage.setScene(scene);
	//}
}

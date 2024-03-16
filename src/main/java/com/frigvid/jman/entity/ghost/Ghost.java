package com.frigvid.jman.entity.ghost;

import com.frigvid.jman.Constants;
import com.frigvid.jman.entity.Direction;
import com.frigvid.jman.entity.Entity;
import com.frigvid.jman.entity.player.Player;
import com.frigvid.jman.game.TickController;
import com.frigvid.jman.game.map.Map;
import com.frigvid.jman.game.map.TileType;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

import java.util.*;

public class Ghost
	extends Entity
{
	protected Player player;
	protected boolean chaseMode = false;
	protected boolean isAfraid;
	private boolean isDead;
	protected int actionDelay;
	
	public Ghost(Map map)
	{
		super(map, null);
	}
	
	/* Utility. */
	/**
	 * Loads the ghost onto the game board.
	 *
	 * @param gameBoard The game board to load the ghost onto.
	 */
	@Override
	public void load(Group gameBoard)
	{
		setSpawn(map.getLogicGrid());
		
		gameBoard.getChildren()
			.add(this.getSprite());
	}
	
	/**
	 * Starts the ghost's "AI."
	 *
	 * @param player The Player Entity to track.
	 */
	public void start(Player player)
	{
		this.player = player;
		
		if (player.isAlive())
		{
			TickController.getInstance().onNextTick(this, ghost ->
			{
				trackPlayer(player, map);
				
				// Schedule the next move.
				start(player);
			}, actionDelay);
		}
	}
	
	/**
	 * Returns a random {@link Direction} for the ghost to move in.
	 *
	 * @return A random {@link Direction}.
	 */
	public Direction randomDirection()
	{
		return Direction.values()[new Random().nextInt(Direction.values().length)];
	}
	
	/* Setters. */
	/**
	 * Sets the spawn location for the ghost.
	 * <p/>
	 * Do note that this sets a random spawn location, if
	 * TileType.SPAWN_GHOST_1 and TileType.SPAWN_GHOST_2 are
	 * present in the logic grid.
	 *
	 * @param logicGrid The logic grid of the map.
	 */
	public void setSpawn(TileType[][] logicGrid)
	{
		int spawn1Row = 0;
		int spawn1Column = 0;
		int spawn2Row = 0;
		int spawn2Column = 0;
		
		// Iterate through the logic grid to find the spawn locations.
		for (int row = 0; row < logicGrid.length; row++)
		{
			for (int col = 0; col < logicGrid[0].length; col++)
			{
				if (logicGrid[row][col] == TileType.SPAWN_GHOST_1)
				{
					spawn1Row = row;
					spawn1Column = col;
				}
				else if (logicGrid[row][col] == TileType.SPAWN_GHOST_2)
				{
					spawn2Row = row;
					spawn2Column = col;
				}
			}
		}
		
		// Randomize spawn location.
		if (Math.random() > 0.5)
		{
			spawnRow = spawn1Row;
			spawnColumn = spawn1Column;
		}
		else
		{
			spawnRow = spawn2Row;
			spawnColumn = spawn2Column;
		}
		
		if (Constants.DEBUG_ENABLED)
		{
			System.out.println(
				"Ghost: Setting spawn!"
					+ "\n┣ Ghost spawn row: " + spawnRow
					+ "\n┣ Ghost spawn column: " + spawnColumn
			);
		}
		
		updateSpritePosition();
	}
	
	/**
	 * Wrapper method for {@link #setSprite(ImageView)}.
	 * <p/>
	 * This method has a basepath, so you only need to specify the path to the sprite
	 * relative to the enitity/ghost resource directory. E.g. "blinky/blinky.png" and
	 * not "/com/frigvid/jman/entity/ghost/blinky/blinky.png".
	 *
	 * @param spritePath The path to the sprite relative to entity/ghost.
	 */
	protected void setGhostSprite(String spritePath)
	{
		//setSpritePath("/com/frigvid/jman/entity/ghost/" + spritePath);
		
		try
		{
			Image image = new Image(
				Objects.requireNonNull(
					getClass().getResourceAsStream("/com/frigvid/jman/entity/ghost/" + spritePath)
				)
			);
			
			ImageView sprite = new ImageView(image);
			sprite.setFitWidth(Constants.TILE_SIZE * Constants.SCALE_FACTOR);
			sprite.setFitHeight(Constants.TILE_SIZE * Constants.SCALE_FACTOR);
			
			setSprite(sprite);
		}
		catch (Exception e)
		{
			System.out.println("Setting the ghost sprite failed!");
			e.printStackTrace(System.err);
		}
	}
	
	protected void enableChaseMode()
	{
		this.chaseMode = true;
	}
	
	protected void setActionDelay(int tickDelay)
	{
		if (tickDelay >= 1 && tickDelay <= 40)
		{
			this.actionDelay = tickDelay;
		}
		else
		{
			this.actionDelay = 10;
		}
	}
	
	/* Pathfinding section.
	 *
	 * This is kind of eye-bleeding, and may or may not be
	 * traumatizing. Viewer discretion is advised.
	 *
	 * NOTE: Add visual debugging that draws a line from the ghost
	 * 		to the player.
	 * FIXME: Add controlled randomness to movement to help Ghost
	 * 		 entities from getting stuck at corners in rare cases.
	 * FIXME: Add a "scatter" mode for the Ghost entities with
	 * 		 customizable duration.
	 * FIXME: Add a return-to-spawn mode for the Ghost entities.
	 * FIXME: Add ability to ignore TileType.WALL when "dead."
	 */
	
	/**
	 * Tracks the player using an A* algorithm.
	 *
	 * @param player The Player Entity to track.
	 * @param map The Map object to track the player in.
	 */
	public void trackPlayer(Player player, Map map)
	{
		List<Pair<Integer, Integer>> path = findPath(player, map);
		
		if (path.size() > 1)
		{
			Pair<Integer, Integer> nextStep = path.get(1);
			Pair<Integer, Integer> previousStep = new Pair<>(spawnRow, spawnColumn);
			
			if (nextStep.equals(previousStep) && path.size() > 2)
			{
				nextStep = path.get(2);
			}
			
			spawnRow = nextStep.getKey();
			spawnColumn = nextStep.getValue();
			
			if (Constants.DEBUG_ENABLED && Constants.DEBUG_AI)
			{
				System.out.println("Ghost: Moving to " + spawnRow + ", " + spawnColumn);
				System.out.println("Ghost: Player at " + player.getCurrentRow() + ", " + player.getCurrentColumn());
			}
			
			if
			(
				spawnRow == player.getCurrentRow()
				&& spawnColumn == player.getCurrentColumn()
			)
			{
				if (Constants.DEBUG_ENABLED && Constants.DEBUG_AI)
				{
					System.out.println("Ghost: Player killed!");
				}
				
				player.killPlayer();
			}
			
			teleportIfNecessary(spawnColumn, spawnRow);
			updateSpritePosition();
		}
	}
	
	/**
	 * Finds a path to the player using an A* algorithm.
	 *
	 * @param player The Player Entity to find a path to.
	 * @param map The Map object to find a path in.
	 * @return A list of coordinates representing the path to the player.
	 */
	private List<Pair<Integer, Integer>> findPath(Player player, Map map)
	{
		Pair<Integer, Integer> start = new Pair<>(spawnRow, spawnColumn);
		Pair<Integer, Integer> goal = new Pair<>(player.getCurrentRow(), player.getCurrentColumn());
		
		PriorityQueue<Pair<Integer, Integer>> openList = new PriorityQueue<>(
			Comparator.comparingDouble(n -> heuristic(n, goal))
		);
		
		/* The set of nodes already evaluated. */
		Set<Pair<Integer, Integer>> closedList = new HashSet<>();
		java.util.Map<Pair<Integer, Integer>, Pair<Integer, Integer>> cameFrom = new HashMap<>();
		java.util.Map<Pair<Integer, Integer>, Double> gScore = new HashMap<>();
		java.util.Map<Pair<Integer, Integer>, Double> fScore = new HashMap<>();
		
		openList.add(start);
		gScore.put(start, 0.0);
		fScore.put(start, heuristic(start, goal));
		
		/* The main loop of the A* algorithm. */
		while (!openList.isEmpty())
		{
			Pair<Integer, Integer> current = openList.poll();
			
			if (current.equals(goal))
			{
				return constructPath(cameFrom, current);
			}
			
			closedList.add(current);
			
			/* Iterate through the neighbors of the current node. */
			for (Pair<Integer, Integer> neighbor : getNeighbors(current, map))
			{
				if (closedList.contains(neighbor))
				{
					continue;
				}
				
				double tentativeGScore = gScore.getOrDefault(current, Double.MAX_VALUE) + 1;
				
				if (!openList.contains(neighbor))
				{
					openList.add(neighbor);
				}
				else if (tentativeGScore >= gScore.getOrDefault(neighbor, Double.MAX_VALUE))
				{
					continue;
				}
				
				cameFrom.put(neighbor, current);
				gScore.put(neighbor, tentativeGScore);
				fScore.put(neighbor, tentativeGScore + heuristic(neighbor, goal));
			}
		}
		
		return Collections.emptyList();
	}
	
	/**
	 * Constructs a path from the cameFrom map.
	 *
	 * @param cameFrom The cameFrom map.
	 * @param current The current node.
	 * @return A list of coordinates representing the path.
	 */
	private List<Pair<Integer, Integer>> constructPath
	(
		java.util.Map<Pair<Integer, Integer>,
		Pair<Integer, Integer>> cameFrom,
		Pair<Integer, Integer> current
	)
	{
		List<Pair<Integer, Integer>> path = new ArrayList<>();
		
		while (current != null)
		{
			path.add(current);
			current = cameFrom.get(current);
		}
		
		Collections.reverse(path);
		
		return path;
	}
	
	/**
	 * Gets the neighbors of a node.
	 *
	 * @param node The node to get the neighbors of.
	 * @param map The Map object to get the neighbors in.
	 * @return A list of coordinates representing the neighbors of the node.
	 */
	private List<Pair<Integer, Integer>> getNeighbors(Pair<Integer, Integer> node, Map map)
	{
		List<Pair<Integer, Integer>> neighbors = new ArrayList<>();
		
		int[][] directions = {
			{-1, 0},
			{1, 0},
			{0, -1},
			{0, 1}
		};
		
		for (int[] direction : directions)
		{
			int newRow = node.getKey() + direction[0];
			int newCol = node.getValue() + direction[1];
			
			if
			(
				newRow >= 0
					&& newRow < map.getMapHeight()
					&& newCol >= 0
					&& newCol < map.getMapWidth()
					&& map.getTileType(newCol, newRow) != TileType.WALL
			)
			{
				Pair<Integer, Integer> neighbor = new Pair<>(newRow, newCol);
				neighbors.add(neighbor);
			}
		}
		
		return neighbors;
	}
	
	/**
	 * Calculates the heuristic for the A* algorithm.
	 *
	 * @param start The start node.
	 * @param goal The goal node.
	 * @return The heuristic value.
	 */
	private double heuristic(Pair<Integer, Integer> start, Pair<Integer, Integer> goal)
	{
		return Math.abs(start.getKey() - goal.getKey()) + Math.abs(start.getValue() - goal.getValue());
	}
}

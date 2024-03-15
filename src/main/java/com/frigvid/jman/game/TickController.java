package com.frigvid.jman.game;

import com.frigvid.jman.Constants;
import com.frigvid.jman.entity.Entity;
import javafx.application.Platform;

import java.util.*;
import java.util.function.Consumer;

/**
 * The TickController class is a singleton that manages the game's tick
 * cycle.
 * <p/>
 * It uses a Timer to schedule a task that runs every 25 milliseconds,
 * which is the equivalent of 40 ticks per second.
 * <p/>
 * The class is designed to be used with the Observer pattern, where
 * entities can register actions to be executed on the next tick. This
 * is done by calling the {@link #onNextTick(Entity, Consumer)} method
 * with an entity and a Runnable. The Runnable will be executed on the
 * next tick.
 * <p/>
 * The class also supports delayed actions, which can be scheduled to
 * run after a certain number of ticks. This is done by calling the
 * {@link #onNextTick(Entity, Consumer, int)} method with an entity,
 * a Runnable, and an integer delay. The Runnable will be executed
 * after the specified number of ticks.
 *
 * @author frigvid
 * @created 2024-02-29
 * @version 1.0
 * @see com.frigvid.jman.entity.player.Player
 * @see com.frigvid.jman.entity.ghost.Ghost
 */
public class TickController
{
	private static TickController instance;
	private boolean running;
	private Timer timer;
	
	/**
	 * A map of entities and their actions to be executed on the next tick.
	 */
	private final Map<Entity, Runnable> tickActions;
	
	/**
	 * A map of entities and their actions to be executed after a certain
	 * number of ticks.
	 */
	private final Map<Entity, Map<Integer, Runnable>> delayedTickActions = new HashMap<>();
	
	/**
	 * This is an ugly workaround for "effectively final" variable usage
	 * in a lambda statement. Given that Java has in-built handling of
	 * over- and under-flow, I'm not particularly concerned. It'd take
	 * some 1.7 years for an overflow to occur given 40 ticks per second.
	 */
	final int[] tickCounter = {0};
	
	/**
	 * The constructor is private to prevent instantiation from outside
	 * the class.
	 */
	private TickController()
	{
		running = true;
		tickActions = new HashMap<>();
		run();
	}
	
	/**
	 * Returns the instance of the TickController.
	 * <p/>
	 * Example usage:
	 * {@snippet id="getInstanceExample" :
	 * 	TickController.getInstance();
	 * }
	 *
	 * @return The instance of the TickController.
	 */
	public static synchronized TickController getInstance()
	{
		if (instance == null || !instance.running)
		{
			instance = new TickController();
		}
		
		return instance;
	}
	
	/**
	 * Starts the tick controller.
	 * <p/>
	 * Example usage:
	 * {@snippet id="startExample" :
	 * 	private TickController()
	 * 	{
	 * 		// ...
	 * 		run();
	 * 	}
	 * }
	 */
	private void run()
	{
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask()
		{
			@Override
			public void run()
			{
				tickCounter[0]++;
				
				if (Constants.DEBUG_ENABLED && Constants.DEBUG_LEVEL == 3)
				{
					System.out.println("TickController: Tick " + tickCounter[0]);
				}
				
				/* Execute immediate actions. */
				tickActions.values().forEach(Platform::runLater);
				tickActions.clear();
				
				/* Execute delayed actions. */
				delayedTickActions.forEach((entity, actions) ->
				{
					Runnable action = actions.get(tickCounter[0]);
					
					if (action != null)
					{
						Platform.runLater(action);
						actions.remove(tickCounter[0]);
					}
				});
			}
		}, 0, Constants.TICK_RATE);
	}
	
	/**
	 * Registers an action to be executed on the next tick.
	 * <p/>
	 * Example usage:
	 * {@snippet id="onNextTickExample" :
	 * 	TickController.getInstance().onNextTick(player, player -> player.move(Direction.LEFT, map));
	 * }
	 *
	 * @param entity The entity to register the action for.
	 * @param action The action to be executed on the next tick.
	 */
	public void onNextTick(Entity entity, Consumer<Entity> action)
	{
		if (!tickActions.containsKey(entity))
		{
			tickActions.put(entity, () -> action.accept(entity));
		}
	}
	
	/**
	 * Registers an action to be executed after a certain number of ticks.
	 * <p/>
	 * Example usage:
	 * {@snippet id="onNextTickExampleWithDelay" :
	 * 	TickController.getInstance().onNextTick(entity, entityAction -> {
	 * 		trackPlayer(player, map);
	 * 		// Schedule the next move.
	 * 		start(player);
	 * 	}, actionDelay);
	 * }
	 *
	 * @param entity The entity to register the action for.
	 * @param action The action to be executed after the specified number of ticks.
	 * @param delay The number of ticks to wait before executing the action.
	 */
	public void onNextTick(Entity entity, Consumer<Entity> action, int delay)
	{
		if (!delayedTickActions.containsKey(entity))
		{
			delayedTickActions.put(entity, new HashMap<>());
		}
		
		delayedTickActions.get(entity).put(tickCounter[0] + delay, () -> action.accept(entity));
	}
	
	/**
	 * Stops the tick controller.
	 * <p/>
	 * Example usage:
	 * {@snippet id="stopExample" :
	 * 	TickController.getInstance().stop();
	 * }
	 */
	public void stop()
	{
		running = false;
		
		if (timer != null)
		{
			timer.cancel();
		}
	}
}

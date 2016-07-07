	package scripts.miner;

import org.tribot.api.General;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.util.AntiBan;
import scripts.util.BotTask;
import scripts.util.names.Animations;
import scripts.util.names.Locations;
import scripts.util.names.ObjectNames;

public class TaskMineRocks extends BotTask {
	private static final int MAX_DIST = 15;

	private RSObject rockToMine = null;
	private boolean error = false;

	public TaskMineRocks() {
		Miner.plugin.println("Looking for rocks!");

		// Setup mining
		FIND_ROCKS( Locations.get( Player.getPosition() ).getRandomizedPosition() );
	}

	private void FIND_ROCKS( RSTile lookFrom ) {
		rockToMine = ObjectNames.get( MinerVars.DESIRED_ROCK, lookFrom, MAX_DIST );

		if ( rockToMine == null ) {
			error = true;
			Miner.plugin.println("Cound not find rocks!");
		}
	}

	@Override
	public BotTask getNextTask() {
		return error?new TaskWalkToMine():new TaskWalkToBank();
	}

	@Override
	public boolean isTaskComplete() {
		if ( error )
			return true;

		if ( !Inventory.isFull() ) {
			// Update the current rock we're mining
			RSObject[] newRock = Objects.getAt( rockToMine.getPosition() );
			if ( newRock == null ) {
				error = true;
				return false;
			}
			rockToMine = newRock[0];

			// If the rock we want to mine is out of ore
			if ( !ObjectNames.isA( rockToMine, MinerVars.DESIRED_ROCK ) ) {

				// If we are still mining
				if ( Animations.isA( Player.getAnimation(), Animations.MINING ) ) {
					// Find new rock because someone else is here
					Miner.plugin.println("Someone is mining my rock. Finding a new one...");
					FIND_ROCKS( Player.getPosition() );
				} else {
					// If we're not mining, then find new rock if it is an expensive rock
					boolean findNew = MinerVars.DESIRED_ROCK != ObjectNames.ORE_COPPER &&
									  MinerVars.DESIRED_ROCK != ObjectNames.ORE_TIN &&
									  MinerVars.DESIRED_ROCK != ObjectNames.ORE_IRON;

					if ( findNew ) {
						FIND_ROCKS( Player.getPosition() );
					}
				}
				return false;
			} else {
				// Do some antiban stuff while we're sitting there mining.
				AntiBan.timedActions();
			}

			// If our rock is not out of ore, and we are not mining, then click the rock!
			if ( Animations.isA( Player.getAnimation(), Animations.NONE ) ) {
				rockToMine.click("Mine");
				if ( (int)(Math.random() * 5) == 2 ) {
					Camera.turnToTile( MinerVars.DESIRED_LOCATION.getRandomizedPosition() );
				}
				Miner.plugin.sleep( General.random(200, 2000));
			}

			return false;
		}

		return true;
	}

	@Override
	public String getTaskName() {
		return "Banking Items";
	}

	@Override
	public void init() {
		//
	}
}

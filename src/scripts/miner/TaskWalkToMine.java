package scripts.miner;

import scripts.util.BotTask;
import scripts.util.BotTaskWalk;
import scripts.util.Locations;

public class TaskWalkToMine extends BotTaskWalk {

	public TaskWalkToMine() {
		super( MinerVars.DESIRED_LOCATION, true );
	}

	@Override
	public BotTask getNextTask() {
		return new TaskMineRocks();
	}

	@Override
	public void init() {
		//
	}
}

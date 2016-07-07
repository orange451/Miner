package scripts.miner;

import scripts.util.BotTask;
import scripts.util.BotTaskWalk;
import scripts.util.names.Locations;

public class TaskWalkToBank extends BotTaskWalk {

	public TaskWalkToBank() {
		super( Locations.closestLocation("BANK", MinerVars.DESIRED_LOCATION), false );
	}

	@Override
	public BotTask getNextTask() {
		return new TaskBankItems();
	}

	@Override
	public void init() {
		//
	}
}
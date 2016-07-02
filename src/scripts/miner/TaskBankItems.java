package scripts.miner;

import org.tribot.api2007.Banking;

import scripts.util.BotTask;

public class TaskBankItems extends BotTask {

	@Override
	public BotTask getNextTask() {
		return new TaskWalkToMine();
	}

	@Override
	public boolean isTaskComplete() {
		Miner.plugin.println("Trying to bank");

		if ( Banking.isBankScreenOpen() ) {
			Banking.depositAll();
			return true;
		} else {
			Banking.openBank();
		}
		return false;
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

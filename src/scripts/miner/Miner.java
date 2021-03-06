package scripts.miner;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

import org.tribot.api2007.Inventory;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.EventBlockingOverride;
import org.tribot.script.interfaces.Painting;

import scripts.gui.RSGui;
import scripts.gui.RSGuiFrame;
import scripts.util.AntiBan;
import scripts.util.BotTask;
import scripts.util.names.Locations;
import scripts.util.names.ObjectNames;

@ScriptManifest(authors = { "orange451" }, category = "Mining", name = "Mining Plugin", version = 1.00, description = "Mines some ores", gameMode = 1)
public class Miner extends Script implements Painting,EventBlockingOverride {
	public static Miner plugin;
	private BotTask currentTask;
	private RSGui minerGui;

	public static ArrayList<String> supportedLocations = new ArrayList<String>();
	public static ArrayList<String> supportedOres = new ArrayList<String>();

	@Override
	public void run() {
		plugin = this;

		supportedLocations.add( Locations.VARROK_MINE_EAST.getName());
		supportedLocations.add( Locations.VARROK_MINE_WEST.getName());
		supportedLocations.add( Locations.ALKHARID_MINE.getName());
		supportedLocations.add( Locations.ALKHARID_MINE_DEEP.getName());
		Collections.sort(supportedLocations);

		supportedOres.add( ObjectNames.ORE_COPPER.getName());
		supportedOres.add( ObjectNames.ORE_TIN.getName());
		supportedOres.add( ObjectNames.ORE_IRON.getName());
		Collections.sort(supportedOres);

		minerGui = new MinerGui();

		while(true) {
			long randomWait = (long) (Math.random() * 2000); // Average of 2 seconds waiting
			sleep( 50L + randomWait );

			AntiBan.afk( 30000 );

			// Step along task
			if ( currentTask != null && currentTask.isTaskComplete() ) {
				currentTask = currentTask.getNextTask();
			}
		}
	}

	public void startTask() {
		if ( Inventory.isFull() ) {
			currentTask = new TaskWalkToBank();
		}

		currentTask = new TaskWalkToMine();
	}

	public void stopTask() {
		currentTask = null;
	}

	public void onPaint(Graphics g) {
		minerGui.onPaint(g);
	}

	@Override
	public OVERRIDE_RETURN overrideKeyEvent(KeyEvent arg0) {
		return minerGui.keyEvent(arg0);
	}

	@Override
	public OVERRIDE_RETURN overrideMouseEvent(MouseEvent arg0) {
		return minerGui.mouseEvent(arg0);
	}

}

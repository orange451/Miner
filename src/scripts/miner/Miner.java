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

import scripts.gui.RSGuiFrame;
import scripts.util.BotTask;
import scripts.util.InteractiveObjects;
import scripts.util.Locations;

@ScriptManifest(authors = { "orange451" }, category = "Mining", name = "Mining Plugin", version = 1.00, description = "Mines some ores", gameMode = 1)
public class Miner extends Script implements Painting,EventBlockingOverride {
	public static Miner plugin;
	private BotTask currentTask;
	private RSGuiFrame minerGui;

	public static ArrayList<String> supportedLocations = new ArrayList<String>();
	public static ArrayList<String> supportedOres = new ArrayList<String>();

	@Override
	public void run() {
		plugin = this;

		supportedLocations.add( Locations.VARROK_MINE_EAST.toString());
		supportedLocations.add( Locations.VARROK_MINE_WEST.toString());
		supportedLocations.add( Locations.ALKHARID_MINE.toString());
		Collections.sort(supportedLocations);

		supportedOres.add( InteractiveObjects.ORE_COPPER.toString());
		supportedOres.add( InteractiveObjects.ORE_TIN.toString());
		supportedOres.add( InteractiveObjects.ORE_IRON.toString());
		Collections.sort(supportedOres);

		minerGui = new MinerGui();

		while(true) {
			long randomWait = (long) (Math.random() * 2000); // Average of 2 seconds waiting
			randomWait += Math.pow( Math.random() * Math.random(), 8 ) * 25000; // Make it very rarely AFK for 20 seconds
			sleep( 50L + randomWait );

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

package scripts.miner;

import java.awt.Graphics;
import java.util.ArrayList;

import scripts.gui.RSGui;
import scripts.gui.RSGuiBox;
import scripts.gui.RSGuiButton;
import scripts.gui.RSGuiDropDown;
import scripts.gui.RSGuiFrame;
import scripts.gui.RSGuiMouseListener;
import scripts.gui.RSGuiPanel;
import scripts.gui.RSGuiTextLabel;
import scripts.util.InteractiveObjects;
import scripts.util.Locations;

public class MinerGui extends RSGui {

	public MinerGui() {
		super("scripts/miner/icon.png");

		RSGuiPanel panel = this.getBotPanel();

		RSGuiBox box = new RSGuiBox(0, 0, -1, -1);
		box.setPadding(8);
		panel.add(box);

		// Mining location dropdown
		box.add( new RSGuiTextLabel(0, 0, "Mine location:").setShadow(true) );
		final RSGuiDropDown d = new RSGuiDropDown(0, 16, box.getWidth());
		box.add(d);
		ArrayList<String> locs = Miner.supportedLocations;
		for (int i = 0; i < locs.size(); i++) {
			String c = locs.get(i).toLowerCase().replace("_", " ");
			d.addChoice(c);
		}

		// Mining ore dropdown
		box.add( new RSGuiTextLabel(0, 48, "Mine Ore:").setShadow(true) );
		final RSGuiDropDown d2 = new RSGuiDropDown(0, 64, box.getWidth());
		box.add(d2);
		ArrayList<String> ores = Miner.supportedOres;
		for (int i = 0; i < ores.size(); i++) {
			String c = ores.get(i).toLowerCase().replace("_", " ");
			d2.addChoice(c);
		}

		// Start button
		final RSGuiButton b = new RSGuiButton( "Start Mining!" );
		b.setLocation(box.getWidth()/2 - b.getWidth()/2, box.getHeight() - b.getHeight());
		box.add(b);
		b.addMouseListener( new RSGuiMouseListener() {
			@Override public void onMouseDown(int x, int y) { }
			@Override public void onMouseUpdate(int x, int y) { }

			@Override
			public boolean onMousePress(int x, int y) {
				if ( b.getText().toLowerCase().contains("start") ) {
					MinerVars.DESIRED_LOCATION = Locations.valueOf(d.getCurrentChoice().toUpperCase().replace(" ", "_"));
					MinerVars.DESIRED_ROCK = InteractiveObjects.valueOf(d2.getCurrentChoice().toUpperCase().replace(" ", "_"));
					b.setText("Stop Mining!");
					Miner.plugin.startTask();
					close();
				} else {
					b.setText("Start Mining!");
					Miner.plugin.stopTask();
				}

				return true;
			}
		});
	}
}

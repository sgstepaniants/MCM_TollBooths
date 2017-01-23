package main;

import java.util.*;

public class Simulator {
	public static final double maxLength = 1000;
	public static double time;
	public static final double dt = 1;
	public static ArrayList<LaneManager> lanes;
	
	public Simulator() {
		// start time at 0
		this.time = 0;
		
		// create lane configuration
		lanes = new ArrayList<LaneManager>();
		LaneManager singleLane = new LaneManager(0, maxLength, 0);
		lanes.add(singleLane);
		
		for (; this.time < 1000000000; this.time += dt) {
			for (LaneManager lane : lanes) {
				// at every timestep, the lane is updated which in turn, updates every car
				// in the lane in random order
				lane.update();
			}
		}
	}
}

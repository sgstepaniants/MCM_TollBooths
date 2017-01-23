package main;

import java.util.*;

public class Simulator {
	public static final double maxLength = 1000;
	double time;
	public static final double dt = 1;
	ArrayList<LaneManager> lanes;
	
	public Simulator() {
		// start time at 0
		this.time = 0;
		
		// create lane configuration
		lanes = new ArrayList<LaneManager>();
		LaneManager singleLane = new LaneManager(this, 0, maxLength, 0);
		lanes.add(singleLane);
		
		for (; this.time < 1000000000; this.time += dt) {
			for (LaneManager lane : lanes) {
				lane.update();
			}
		}
	}
	
	public LaneManager getLane(int laneRank) {
		return lanes.get(laneRank);
	}
}

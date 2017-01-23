package main;

import java.util.*;

public class Simulator {
	double time;
	public static final double dt = 1;
	ArrayList<LaneManager> lanes;
	
	public Simulator() {
		this.time = 0;
		
		for (; this.time < 1000000000; this.time += dt) {
			for (LaneManager lane : lanes) {
				// populate lanes
				
			}
		}
	}
	
	public LaneManager getLane(int laneRank) {
		return lanes.get(laneRank);
	}
}

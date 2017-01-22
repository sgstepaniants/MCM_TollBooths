package main;

import java.util.*;

public class Simulator {
	public static final double dt = 0.01;
	ArrayList<LaneManager> lanes;
	
	public Simulator() {
		for (double time = 0; time < 1000000000; time += dt) {
			for (LaneManager lane : lanes) {
				// populate lanes
				
			}
		}
	}
	
	public LaneManager getLane(int laneRank) {
		return lanes.get(laneRank);
	}
}

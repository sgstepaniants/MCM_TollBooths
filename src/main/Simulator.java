package main;

import java.util.*;

public class Simulator {
	public static final double dt = 0.01;
	ArrayList<LaneManager> lanes;
	
	public Simulator() {
		for (LaneManager lane : this.lanes) {
			// update lane
			// progress vehicles in lane and add merging vehicles
		}
	}
	
	public LaneManager getLane(int laneRank) {
		return lanes.get(laneRank);
	}
}

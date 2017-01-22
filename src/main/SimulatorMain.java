package main;

import java.util.*;

public class SimulatorMain {
	public static final double dt = 0.01;
	ArrayList<LaneManager> lanes;
	
	public LaneManager getLane(int laneRank) {
		return lanes.get(laneRank);
	}
}

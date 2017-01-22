package main;

import java.util.*;

public class SimulatorMain {
	ArrayList<LaneManager> lanes;
	
	public LaneManager getLane(int laneRank) {
		return lanes.get(laneRank);
	}
}

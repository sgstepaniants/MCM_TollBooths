package main;

import java.util.*;

public class SimulatorMain {
	public static final double dt = 0.01;
	public static ArrayList<LaneManager> lanes;
	
	public static void main(String[] args) {
		for(LaneManager lane : lanes) {
			//stuff
		}
	}
	
	public LaneManager getLane(int laneRank) {
		return lanes.get(laneRank);
	}
}

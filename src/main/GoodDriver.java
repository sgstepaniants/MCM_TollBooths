package main;

import java.util.*;
public class GoodDriver extends Vehicle {
	public static final double alpha = 20;

	public GoodDriver(double velocity, LaneManager lane) {
		position = 0;
		this.velocity = velocity;
		this.primaryLane = lane;
	}
	
	
	public void update() {
		double acceleration = 0;
		
		try {
			int nextCarIndex = primaryLane.binarySearch(position);
			if (primaryLane.vehicles.get(nextCarIndex).position == this.position) {
				nextCarIndex += 1;
			}
			Vehicle v = primaryLane.vehicles.get(nextCarIndex);
			double dV = v.velocity - velocity;
			double dX = v.position - position;
			acceleration = alpha * dV * velocity / (dX * dX);
		} catch (NullPointerException e) { // top of lane
			if (primaryLane.edgeLane) {
				double dX = primaryLane.length - position;

			}
			double dV = 60 - velocity;
			acceleration = alpha * dV;
		}
		velocity += acceleration * Simulator.dt;
	}
	
	public boolean shouldMergeEdge(LaneManager lane) {
		return false;
	}
	
	public boolean shouldMergeCenter(LaneManager lane) {
		if (Math.random() <= probableMerge) {
			
		}
		return false;
	}
}

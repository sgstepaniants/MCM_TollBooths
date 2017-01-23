package main;

import java.util.*;
public class GoodDriver extends Vehicle {
	public static final double alpha = 20;

	public GoodDriver(double velocity, LaneManager lane) {
		// index of car in arraylist
		this.index = 0;
		// position of car in miles
		this.position = 0;
		// velocity of car in miles/second, number of miles you travel in one dt
		this.velocity = velocity;
		// lane that car is currently in
		this.primaryLane = lane;
	}
	
	
	public void update() {
		double acceleration = 0;
		
		// if car is not at the front of the lane
		if (this.index < primaryLane.vehicles.size()) {
			Vehicle v = primaryLane.vehicles.get(this.index + 1);
			double dV = v.velocity - velocity;
			double dX = v.position - position;
			acceleration = alpha * dV * velocity / (dX * dX);
		} else { // if car is at the front of the lane
			// if the lane is a center lane
			if (primaryLane.laneCategory != 0) {
				double dX = primaryLane.length - position;

			}
			
			double dV = 60 - velocity;
			acceleration = alpha * dV;
		}
		velocity += acceleration * Simulator.dt;
		position += velocity * Simulator.dt;
	}
	
	public boolean shouldMerge(LaneManager lane) {
		// if you are in a center lane
		if(primaryLane.laneCategory == 0) {
			if (Math.random() <= probableMerge) { // merge if possible
				return (primaryLane.getCongestion() / lane.getCongestion() > 1.3) &&
						lane.openSpace(position + velocity * Simulator.dt) > 2 * velocity;
			}
			return false;
		} else { // if you are in an edge lane
			return lane.openSpace(position + velocity * Simulator.dt) > 2 * velocity;
		}
	}
}

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
		
		// see if you need to merge before anything else
		if (!this.hasMerged) {
			// if car is not in center lane
			if (primaryLane.laneCategory != 0) {
				// see if you should merge to neighboring lane since you are in an edge lane
				if (primaryLane.laneRank + primaryLane.laneCategory >= 0 &&
						this.shouldMerge(Simulator.lanes.get(primaryLane.laneRank + primaryLane.laneCategory))) {
					// merge into lane(primaryLane.laneRank + primaryLane.laneCategory)
				}
			} else {
				// you are in a center lane and have two center lanes to choose from to
				// merge into, choose one randomly
				if (primaryLane.laneRank > 0 && this.shouldMerge(Simulator.lanes.get(primaryLane.laneRank - 1)) && this.shouldMerge(Simulator.lanes.get(primaryLane.laneRank + 1))) {
					if (Math.random() < 5) {
						// merge into lane(primaryLane.laneRank - 1)
					} else {
						// merge into lane(primaryLane.laneRank + 1)
					}
				} else if (this.shouldMerge(Simulator.lanes.get(primaryLane.laneRank + 1))) {
					// merge into lane(primaryLane.laneRank + 1)
				} else if (primaryLane.laneRank > 0 && this.shouldMerge(Simulator.lanes.get(primaryLane.laneRank - 1))) {
					// merge into lane(primaryLane.laneRank - 1)
				}
			}
		}
		
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
		
		this.hasMerged = false;
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

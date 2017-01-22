package main;

import java.util.*;
public abstract class LaneManager {
    ArrayList<Vehicle> vehicles;
    ArrayList<Vehicle> mergingVehicles;
    SimulatorMain manager;
    
    int laneRank;
    int neighor;
    
    boolean edgeLane;
    double length;
    
    public LaneManager(int laneRank, double length) {
        this.laneRank = laneRank;
        this.length = length;
    }
    
    public void updateVehicles() { // updates positions, velocities and lanes of vehicles
        for (int i = vehicles.size() - 1; i >= 0; i--) {
            if (!vehicles.get(i).isMerging) {
                if (edgeLane) {
                    edgeUpdate(i);
                } else {
                    centerUpdate(i);
                }
            } else {
                vehicles.get(i).isMerging = false;
            }
        }
    }
    
    //	public void addMergingVehicles() { // adds merging vehicles to current vehicles
    //		for (Vehicle vehicle : mergingVehicles) {
    //			vehicles.add(binarySearch(vehicle.position));
    //		}
    //		mergingVehicles.clear();
    //	}
    
    public void merge(int i, Vehicle v, LaneManager lane) { // merges vehicle i into LaneManager lane
        vehicles.remove(i);
        lane.mergingVehicles.add(i, v);
        v.isMerging = true;
    }
    
    public void edgeUpdate(int i) { // updates vehicle i in an edge lane
        Vehicle v = vehicles.get(i);
        if (v.shouldMergeEdge(manager.getLane(neighor))) {
            merge(i, v, manager.getLane(neighor));
        }
        v.update();
    }
    
    public void centerUpdate(int i) { // updates vehicle i in a center lane
        Vehicle v = vehicles.get(i);
        if (v.shouldMergeCenter(manager.getLane(laneRank - 1)) && v.shouldMergeCenter(manager.getLane(laneRank + 1))) {
            if ((new Random()).nextBoolean()) {
                merge(i, v, manager.getLane(laneRank - 1));
            } else {
                merge(i, v, manager.getLane(laneRank + 1));
            }
        } else if (v.shouldMergeCenter(manager.getLane(laneRank + 1))) {
            merge(i, v, manager.getLane(laneRank + 1));
        } else if (v.shouldMergeCenter(manager.getLane(laneRank - 1))) {
            merge(i, v, manager.getLane(laneRank - 1));
        } else {
            v.position += v.velocity * SimulatorMain.dt;
        }
        v.update();
    }
    
    public boolean centerShouldMerge(int i, int laneRank) {
        LaneManager lane = manager.getLane(laneRank);
        Vehicle v = vehicles.get(i);
        return (!lane.edgeLane) && v.shouldMergeCenter(lane);
    }
    
    // returns minimum distance between position and nearest car. includes positions of merging cars
    public double openSpace(double position) {
        int topPosition = binarySearch(position);
        if (topPosition == 0) {
            return vehicles.get(topPosition).position - position;
        }
        return Math.min(vehicles.get(topPosition).position - position, position - vehicles.get(topPosition - 1).position);
        //		int topPositionV = binarySearch(position, vehicles);
        //		int topPositionT = binarySearch(position, mergingVehicles);
        //		if (vehicles.get(topPositionV).position == topPositionV || mergingVehicles.get(topPositionT).position == 0) {
        //			return 0;
        //		} else {
        //			return Math.min(Math.min(vehicles.get(topPositionT).position - position,
        //					position - vehicles.get(topPositionT - 1).position),
        //					Math.min(mergingVehicles.get(topPositionV).position - position,
        //							position - vehicles.get(topPositionV - 1).position));
        //		}
    }
    
    // searches Vehicle ArrayList array for index of vehicle with smallest position larger than the given position
    public int binarySearch(double position) {
        return binarySearch(position, vehicles.size(), 0);
    }
    // Gets index of first vehicle with larger position
    public int binarySearch(double position, int max, int min) {
        int guess = (max - min) / 2;
        if (guess == min) {
            return max;
        }
        if (vehicles.get(guess).position > position) {
            return binarySearch(position, guess, min);
        } else if (vehicles.get(guess).position < position) {
            return binarySearch(position, max, guess);
        } else {
            return guess;
        }
    }
    
    public double getCongestion() {
        return vehicles.size() / length;
    }
    
}

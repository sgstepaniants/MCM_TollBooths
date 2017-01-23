package main;

import java.util.*;
public class LaneManager {
	Queue<Vehicle> queue = new LinkedList<Vehicle>();
    ArrayList<Vehicle> vehicles;
    
    int laneRank;
    int neighbor;
    
    double lambda;
    Random rand = new Random();
    int prodRate;
    int processingRate;
    
    // laneCategory is -1 if lane is top edge lane, 0 if lane is center lane,
    // 1 if lane is bottom edge lane
    int laneCategory;
    double length;
    
    public LaneManager(int laneRank, double length, int laneCategory) {
        this.laneRank = laneRank;
        this.laneCategory = laneCategory;
        this.length = length;
        this.lambda = 1;
        this.prodRate = (int) Math.ceil(Math.log(1-rand.nextDouble())/(-lambda));
        this.processingRate = 1;
    }
    
    public void update() {
    	if (Simulator.time % this.prodRate == 0) {
    		// give this vehicle all the necessary properties
    		queue.add(new GoodDriver(0, this));
    		// update production rate
    		this.prodRate = (int) Math.ceil(Math.log(1-rand.nextDouble())/(-lambda));
    	}
    	
    	if (Simulator.time % this.processingRate == 0) {
			if (!queue.isEmpty()) {
	    		Vehicle car = queue.remove();
	    		if (!vehicles.isEmpty()) {
	    			car.velocity = vehicles.get(1).velocity;
	    		} else {
	    			car.velocity = 60;
	    		}
	    		
	    		vehicles.add(car);
	    		
	    		// update index of all cars in arraylist when new car is added
	    		for (Vehicle veh : vehicles) {
	    			veh.index++;
	    		}
    		}
    	}
    	
    	// update the position and velocity
    	ArrayList<Vehicle> copy = vehicles;
    	Collections.shuffle(copy);
    	
    	Iterator<Vehicle> iter = copy.iterator();
    	while(iter.hasNext()) {
    		Vehicle car = iter.next();
    		if (car.position >= Simulator.maxLength) {
    			vehicles.remove(car);
    		} else {
    			car.update();
    		}
    	}
    	
        /*for (int i = vehicles.size() - 1; i >= 0; i--) {
        	vehicles.get(i).update();
            if (!vehicles.get(i).isMerging) {
                if (edgeLane) {
                    edgeUpdate(i);
                } else {
                    centerUpdate(i);
                }
            } else {
                vehicles.get(i).isMerging = false;
            }
        }*/
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
        v.hasMerged = true;
        v.primaryLane = lane;
    }
    
    public void edgeUpdate(int i) { // updates vehicle i in an edge lane
        Vehicle v = vehicles.get(i);
        if (v.shouldMerge(Simulator.lanes.get(neighbor))) {
            merge(i, v, Simulator.lanes.get(neighbor));
        }
    }
    
    public void centerUpdate(int i) { // updates vehicle i in a center lane
        Vehicle v = vehicles.get(i);
        if (v.shouldMerge(Simulator.lanes.get(laneRank - 1)) && v.shouldMerge(Simulator.lanes.get(laneRank + 1))) {
            if ((new Random()).nextBoolean()) {
                merge(i, v, Simulator.lanes.get(laneRank - 1));
            } else {
                merge(i, v, Simulator.lanes.get(laneRank + 1));
            }
        } else if (v.shouldMerge(Simulator.lanes.get(laneRank + 1))) {
            merge(i, v, Simulator.lanes.get(laneRank + 1));
        } else if (v.shouldMerge(Simulator.lanes.get(laneRank - 1))) {
            merge(i, v, Simulator.lanes.get(laneRank - 1));
        } else {
            v.position += v.velocity * Simulator.dt;
        }
    }
    
    public boolean centerShouldMerge(int i, int laneRank) {
        LaneManager lane = Simulator.lanes.get(laneRank);
        Vehicle v = vehicles.get(i);
        return (this.laneCategory == 0) && v.shouldMerge(lane);
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

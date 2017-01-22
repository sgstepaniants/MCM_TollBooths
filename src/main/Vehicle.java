package main;

import main.*;
public abstract class Vehicle implements Comparable<Vehicle>  {
	public double position;
	public double velocity;
	public LaneManager primaryLane;
	public boolean isMerging;
	public double probableMerge;
	public double dummy;
	
	public abstract void update();
	
	public abstract boolean shouldMergeCenter(LaneManager lane);
	
	public abstract boolean shouldMergeEdge(LaneManager lane);
	
	@Override
	public int compareTo(Vehicle o) {
		return (int) (position - o.position);
	}
	
}

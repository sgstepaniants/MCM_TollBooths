package main;

import main.*;
public abstract class Vehicle implements Comparable<Vehicle>  {
	public double position;
	public double velocity;
	public LaneManager primaryLane;
	public LaneManager secondaryLane;
	public boolean isMerging;
	public double probableMerge;
	public double dummy;
	
	public void update() {
//		if (secondaryLane == null) {
//			if (primaryLane instanceof EdgeLane) { // needs to merge
//				
//			} else { // is in the middle, doesn't need to merge
//				
//			}
//		} else { // is merging
//			d
//		}
	}
	
	public abstract boolean shouldMergeCenter(LaneManager lane);
	
	public abstract boolean shouldMergeEdge(LaneManager lane);
	
	@Override
	public int compareTo(Vehicle o) {
		return (int) (position - o.position);
	}
	
}

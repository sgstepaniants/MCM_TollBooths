package main;

public abstract class Vehicle implements Comparable<Vehicle>  {
	public int index;
	public double position;
	public double velocity;
	public LaneManager primaryLane;
	public boolean hasMerged;
	public double probableMerge = 1;
	
	public abstract void update();
	
	public abstract boolean shouldMerge(LaneManager lane);
	
	@Override
	public int compareTo(Vehicle o) {
		return (int) (position - o.position);
	}
	
}

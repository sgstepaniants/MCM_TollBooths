package main;

public class GoodDriver extends Vehicle {
	public static final double alpha = 20;

	public GoodDriver(double velocity, LaneManager lane) {
		position = 0;
		this.velocity = velocity;
		this.primaryLane = lane;
	}
	
	
	public void update() {
		
	}
}

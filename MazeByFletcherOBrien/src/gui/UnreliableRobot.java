package gui;

/**
 * @author Fletcher O'Brien
 * Class: UnreliableRobot
 * Responsibilities: Have unreliable sensors
 * Collaborators: ReliableRobot, UnreliableSensor
 */
public class UnreliableRobot extends ReliableRobot {
	
	//forward sensor variable
	private DistanceSensor forwards;
	//right sensor variable
	private DistanceSensor rights;
	//backward sensor variable
	private DistanceSensor backwards;
	//left sensor variable
	private DistanceSensor lefts;
	//ignore failures if a sensor is down
	private boolean sensordown;
	
	/**
	 * Checks if the sensor is operational
	 * @return true if the sensor is down, false if it is not
	 */
	public boolean isSensorDown() {
		return sensordown;
	}
	
	/**
	 * Changes the sensordown boolean from true to false, or false to true
	 */
	public void flipSensorStatus() {
		if (!sensordown) {
		sensordown = true;
		} else {
			sensordown = false;
		}
	}
	
	//the rest is the same as reliable robot, just no jumping
	/**
	 * Using the wallfollower algorithmn, as the unreliable robot does, it cannot jump
	 */
	@Override
	public void jump() {
		System.out.println("The unreliable robot uses the wallfollower algorithmn and does not jump.");
	}
	
}

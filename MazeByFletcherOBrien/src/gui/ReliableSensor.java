/**
 * 
 */
package gui;

import generation.CardinalDirection;
import generation.Floorplan;
import generation.Maze;
import gui.Robot.Direction;

/**
 * @author Fletcher O'Brien
 * Class: ReliableSensor
 * Responsibilities: Always give distance to nearest wall
 * Collaborators: DistanceSensor, ReliableRobot, Floorplan
 */
public class ReliableSensor implements DistanceSensor{
	
	//the current maze the sensor dwells within
	private Maze curmaze;
	//the floorplan of the maze the sensor is in
	private Floorplan floorp;
	//the direction the sensor is set to face
	private Direction curdir;
	
	/*maze variable points to maze sensor is in
	*direction variable is cardinal direction sensor is facing
	*energy variable is consumption cost for sensing
	*operation variable is whether or not the sensor is currently operational
	*/

	/**
	 * Gives the distance from the sensor to the nearest wall by taking the position given and, as if taking steps, counts how many cells
	 * in the maze it crosses before it runs into a wall. Also subtracts from the given power supply based on the cost of using a sensor.
	 * @return 0 if the wall is in front of the sensor, the distance from the sensor to a wall, or int max value if looking out the exit
	 * @throws exception if any parameter is null, the sensor is down, insufficient powersupply, or if current position is out of range
	 */
	@Override
	public int distanceToObstacle(int[] currentPosition, CardinalDirection currentDirection, float[] powersupply)
			throws Exception {
		//takes current position of robot and direction to calculate distance to nearest wall and returns it.
		//return maxvalue if looking out exit
		//reduces powersupply by set amount each time this method is called
		/*@throws Exception with message 
		 * SensorFailure if the sensor is currently not operational
		 * PowerFailure if the power supply is insufficient for the operation
		 * @throws IllegalArgumentException if any parameter is null
		 * or if currentPosition is outside of legal range
		 * ({@code currentPosition[0] < 0 || currentPosition[0] >= width})
		 * ({@code currentPosition[1] < 0 || currentPosition[1] >= height}) 
		 * @throws IndexOutOfBoundsException if the powersupply is out of range
		 * ({@code powersupply < 0}) 
		 */
		int count = 0;
		int x = currentPosition[0];
		int y = currentPosition[1];
		while (!curmaze.hasWall(currentPosition[x], currentPosition[y], currentDirection)) {
			count++;
			if (currentDirection == CardinalDirection.North) {
				y--;
			} else if (currentDirection == CardinalDirection.East) {
				x++;
			} else if (currentDirection == CardinalDirection.West) {
				x--;
			} else if (currentDirection == CardinalDirection.South) {
				y++;
			}
		}
		return count;
	}

	/**
	 * Sets the maze variable to the given maze parameter. Also sets the floorplan variable for ease of access.
	 */
	@Override
	public void setMaze(Maze maze) {
		//sets the maze variable to the maze the robot is in
		curmaze = maze;
		floorp = maze.getFloorplan();
	}

	/**
	 * Sets the direction of this sensor to the given Direction parameter.
	 */
	@Override
	public void setSensorDirection(Direction mountedDirection) {
		//sets the direction of this sensor to the one given
		curdir = mountedDirection;
	}

	/**
	 * Returns the instructed set energy cost for using a sensor to return distance to nearest wall
	 * @return sensing cost
	 */
	@Override
	public float getEnergyConsumptionForSensing() {
		//returns the energy cost for consumption
		return 1;
	}

	/**
	 * Starts the failure and repair process, but a reliable robot does not need this.
	 */
	@Override
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		//OPTIONAL P3
		
	}

	/**
	 * Stops the failure and repair process, but a reliable robot does not need this because his sensors never fail.
	 */
	@Override
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException {
		//OPTIONAL P3
		
	}

}

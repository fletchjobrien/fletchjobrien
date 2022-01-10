package gui;

import generation.CardinalDirection;
import generation.Floorplan;

/**
 * @author Fletcher O'Brien
 * Class: ReliableRobot
 * Responsibilities: Have reliable sensors
 * Collaborators: Robot, ReliableSensor
 */
public class ReliableRobot implements Robot{
	
	//floorplan of current maze for testing exit
	private Floorplan floorp;
	//controller variable points to input method to control robot
	private Controller robotcontrol;
	//forward sensor variable
	private DistanceSensor forwards;
	//right sensor variable
	private DistanceSensor rights;
	//backward sensor variable
	private DistanceSensor backwards;
	//left sensor variable
	private DistanceSensor lefts;
	//current position variable is current position in maze (x,y)
	private int[] currentpos = new int[2];
	//current direction variable is where the robot is facing
	private CardinalDirection currentdir;
	//energy variable gives energy in battery
	private float[] energy = new float[1];
	//rotatecost variable is cost for full rotation 90 degrees
	private float rotatecost;
	//stepcost variable is cost for robot to take 1 step
	private float stepcost;
	//odometer variable is current path length from start to robot's current position
	private int odometer;
	//jumpcost variable is cost for robot to jump over a wall
	private int jumpcost;
	
	/**
	 * Constructor method sets the energy level to 3500 as per instruction
	 */
	public ReliableRobot() {
		energy[0] = 3500;
	}

	/**
	 * Sets the controller variable to the given controller
	 */
	@Override
	public void setController(Controller controller) {
		//set controller to keyboard input
		robotcontrol = controller;
	}

	/**
	 * Sets a sensor variable to the given DistanceSensor based on the Direction parameter. If there is already a sensor
	 * facing that way then the method replaces it.
	 */
	@Override
	public void addDistanceSensor(DistanceSensor sensor, Direction mountedDirection) {
		//adds a sensor facing a direction. if there is already a sensor facing that way replace it.
		if (mountedDirection.equals(Direction.FORWARD)) {
			forwards = sensor;
		} else if (mountedDirection.equals(Direction.RIGHT)) {
			rights = sensor;
		} else if (mountedDirection.equals(Direction.LEFT)) {
			lefts = sensor;
		} else if (mountedDirection.equals(Direction.BACKWARD)) {
			backwards = sensor;
		}
	}

	/**
	 * Returns the current position of the ReliableRobot in the maze in (x, y) format
	 * @return an array containing two values, [x, y]
	 */
	@Override
	public int[] getCurrentPosition() throws Exception {
		// return the current position of the robot on the floorplan in a (x,y) format
		currentpos = robotcontrol.getCurrentPosition();
		return currentpos;
		
		// TODO THROW EXCEPTION
	}

	/**
	 * Returns the current direction the robot is facing, which is tracked in the variable currentdir.
	 * @return current direction of robot
	 */
	@Override
	public CardinalDirection getCurrentDirection() {
		// returns current direction robot is facing
		return currentdir;
	}

	/**
	 * Returns the current battery level
	 * @return the energy variable
	 */
	@Override
	public float getBatteryLevel() {
		// return battery level variable. if the level is at or less than 0, end the game
		return energy[0];
	}

	/**
	 * Sets the energy variable to the float value given, used for changing the current battery level
	 */
	@Override
	public void setBatteryLevel(float level) {
		//set battery level variable
		energy[0] = level;
	}

	/**
	 * Returns the pre-set cost for a full 90* rotation
	 * @return the rotatecost variable
	 */
	@Override
	public float getEnergyForFullRotation() {
		//return energy cost for rotating
		return rotatecost;
	}

	/**
	 * Returns the energy cost for taking a single step
	 * @return the stepcost variable
	 */
	@Override
	public float getEnergyForStepForward() {
		//return energy cost for move
		return stepcost;
	}

	/**
	 * Returns the current path length of the robot, which is tracked throughout operation in the odometer variable
	 * @return the odometer variable
	 */
	@Override
	public int getOdometerReading() {
		//return current path length (amount of steps taken so far variable)
		return odometer;
	}

	/**
	 * Sets the odometer variable to equal zero, used for reseting odometer
	 */
	@Override
	public void resetOdometer() {
		//sets odometer to 0
		odometer = 0;
	}

	/**
	 * Rotates the robot left, right, or 180* based on Turn variable given. If an unrecognized turn type is attempted the robot does nothing.
	 */
	@Override
	public void rotate(Turn turn) {
		//sets the current direction based on the direction turning.
		//ex if facing north and there is a right turn, changes current direction to east
		
		if (turn.equals(Turn.RIGHT)) {
			currentdir.rotateClockwise();
		} else if (turn.equals(Turn.LEFT)) {
			currentdir.rotateClockwise();
			currentdir.rotateClockwise();
			currentdir.rotateClockwise();
		} else if (turn.equals(Turn.AROUND)) {
			currentdir.oppositeDirection();
		}
	}

	/**
	 * Changes the current position of the robot on the floorplan based on current direction and distance.
	 * If the robot crashes into a wall due to this method, end the game.
	 */
	@Override
	public void move(int distance) {
		//changes the current position of the robot on the floorplan based on current direction and distance.
		//if the robot crashes into a wall, end the game
		if (distanceToObstacle(Direction.FORWARD) >= distance) {
			for (int i = 0; i < distance; i++) {
				takestep();
				energy[0] -= stepcost;
				
				// TODO check if ran into wall
				if (hasStopped() == true) {
					System.out.println("END GAME! ROBO STOPPED");
				}
			}
		}
		
		if (hasStopped() == true) {
			System.out.println("END GAME! ROBO STOPPED");
		}
	}
	
	/**
	 * Changes the actual current position value of the robot based on the direction its facing.
	 */
	private void takestep() {
		if (currentdir == CardinalDirection.North) {
			currentpos[1] -= 1;
		} else if (currentdir == CardinalDirection.East) {
			currentpos[0] += 1;
		}  else if (currentdir == CardinalDirection.West) {
			currentpos[0] -= 1;
		}  else if (currentdir == CardinalDirection.South) {
			currentpos[1] += 1;
		}
	}

	/**
	 * Changes the robots position by 1 cell based on the current direction, and does NOT end the game if moving through a wall.
	 * If this move causes the robot to stop then the game ends.
	 */
	@Override
	public void jump() {
		//changes the robots position by 1 based on the current direction.
		//does not end game if moving through wall
		takestep();
		energy[0] -= jumpcost;
		
		if (hasStopped() == true) {
			System.out.println("END GAME! ROBO STOPPED");
		}
	}

	/**
	 * Checks if the robot's position is the same as the exit position and returns a boolean value based on this.
	 * @return true if the robot is at the exit, false if not
	 */
	@Override
	public boolean isAtExit() {
		//return true if current position is equal to position of exit in maze
		if (floorp.isExitPosition(currentpos[0], currentpos[1])) {
			return true;
		}
		return false;
	}

	/**
	 * Check if the robot is in an enclosed area with no exit using the isInRoom method in FloorPlan.
	 * @return true if the robot is in a room, false if not.
	 */
	@Override
	public boolean isInsideRoom() {
		//return true if there is no path out of room (use maze methods)
		if (floorp.isInRoom(currentpos[0], currentpos[1])) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if the robot has stopped for any reason.
	 * @return true if the robot is out of energy, hits a wall, or is stuck in a room
	 */
	@Override
	public boolean hasStopped() {
		//returns stopped variable, which is set to true if the robot runs out of energy or hits a wall.
		//a stopped robot cannot rotate or move.
		if (energy[0] <= 0) {
			stopFailureAndRepairProcess(Direction.FORWARD);
			stopFailureAndRepairProcess(Direction.LEFT);
			stopFailureAndRepairProcess(Direction.RIGHT);
			stopFailureAndRepairProcess(Direction.BACKWARD);
			return true;
		}
		return false;
	}

	/**
	 * Calculates the distance from the robot to the nearest wall in the given direction by using the robot's sensors
	 * @return the distance to the nearest obstacle in the maze
	 * @throws if there is no sensor is the direction parameter given, or if the sensor in that direction is undergoing a failure
	 */
	@Override
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		//gives distance from robot to nearest wall in the direction given. returns integer.maxvalue if looking out exit
		//throw exception if no sensor in current direction or sensor broken
		try {
			if (direction == Direction.FORWARD) {
				return forwards.distanceToObstacle(currentpos, currentdir, energy);
			} else if (direction == Direction.RIGHT) {
				return rights.distanceToObstacle(currentpos, currentdir, energy);
			} else if (direction == Direction.LEFT) {
				return lefts.distanceToObstacle(currentpos, currentdir, energy);
			} else if (direction == Direction.BACKWARD) {
				return backwards.distanceToObstacle(currentpos, currentdir, energy);
			}
		} catch (Exception e) {
			
		}
		return -1;
	}

	/**
	 * Checks if the robot can see the exit in the given direction by using sensors to see if it can see the infinity outside the maze
	 * @return true if the sensor returns int max value, false if not
	 * @throws if there is no sensor in the given direction, or the sensor in that direction has failed
	 */
	@Override
	public boolean canSeeThroughTheExitIntoEternity(Direction direction) throws UnsupportedOperationException {
		//returns true if distancetoobstacle returns integer.maxvalue
		//throws exception if no sensor in current direction or sensor broken
		if (distanceToObstacle(direction) == Integer.MAX_VALUE) {
			return true;
		}
		return false;
	}

	/**
	 * Begins the failure and repair process threads for the sensor in the given direction.
	 * @throws if there is no sensor in the given direction, or if the sensor in that direction has already begun the failure and repair
	 * process
	 */
	@Override
	public void startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		if (direction == Direction.FORWARD) {
			forwards.startFailureAndRepairProcess(2, 4);
		} else if (direction == Direction.LEFT) {
			lefts.startFailureAndRepairProcess(2, 4);
		} else if (direction == Direction.RIGHT) {
			rights.startFailureAndRepairProcess(2, 4);
		} else if (direction == Direction.BACKWARD) {
			backwards.startFailureAndRepairProcess(2, 4);
		}
		
	}

	/**
	 * Stops the failure and repair process for the sensor in the given direction
	 * @throws if there is no sensor in the given direction, or if the sensor in that direction is already not undergoing the failure and
	 * repair process
	 */
	@Override
	public void stopFailureAndRepairProcess(Direction direction) throws UnsupportedOperationException {
		if (direction == Direction.FORWARD) {
			forwards.stopFailureAndRepairProcess();
		} else if (direction == Direction.LEFT) {
			lefts.stopFailureAndRepairProcess();
		} else if (direction == Direction.RIGHT) {
			rights.stopFailureAndRepairProcess();
		} else if (direction == Direction.BACKWARD) {
			backwards.stopFailureAndRepairProcess();
		}
	}

}

/**
 * 
 */
package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import generation.Maze;
import gui.Robot.Direction;

/**
 * @author Fletcher O'Brien
 * Class: WallFollower
 * Responsibilities: Hug the left wall until the exit is found
 * Collaborators: RobotDriver
 */
public class WallFollower implements RobotDriver {
	
	//robot variable
	private Robot ro;
	//maze variable
	private Maze m;
	//battery variable
	private int battery;
	//pathlength variable
	private int pathlength;
	//gamerunning variable
	public boolean gameended;

	/**
	 * Sets the robot variable equal to the given robot parameter. If the robot variable is already set then replace it.
	 */
	@Override
	public void setRobot(Robot r) {
		//set the robot variable to r
		ro = r;
	}

	/**
	 * Sets the maze variable equal to the given maze parameter, replacing if the maze variable has been already set.
	 */
	@Override
	public void setMaze(Maze maze) {
		//set the maze variable to maze
		m = maze;
	}

	/**
	 * Drives the robot to the exit by calling drive1Step2Exit until the game ends, by winning or losing. Checks if it is possible for the
	 * robot to reach the exit.
	 * @return true if the robot can drive to exit, false if impossible
	 * @throws if the robot can not get to the exit
	 */
	@Override
	public boolean drive2Exit() throws Exception {
		//call drive1step2exit until at exit
		while (!gameended) {
			drive1Step2Exit();
		}
		return false;
	}
	
	/**
	 * Checks if a sensor on the robot has failed. If it has, the robot will turn to use a working sensor instead, and then turn back to
	 * face its original direction. This method also sets a variable on all sensors other than the failed one to keep them from failing
	 * until the failed sensor is repaired.
	 */
	public void sensorFailed() {
		//checks if a sensor on the robo has failed
		//if a sensor fails, turn the robot to use a working sensor, then turn it back to its original direction
		//do not allow other sensors to fail until the failed sensor is repaired
	}

	/**
	 * First: Uses a sensor to detect if a wall is adjacent to the robot's left.
	 * Second: Uses a sensor to detect if a wall is directly in front of the robot.
	 * Third: If there is a wall to the left but no wall directly in front, the robot takes a step forward.
	 * If there is a wall to the left AND a wall directly in front, the robot turns to the right and goes back to the second step. (doesn't need to check if wall to the left because we know there is one)
	 * If there is no wall to the left and no wall directly in front, the robot turns left and takes a step. (ends up doing this twice, moving in a U shape around a wall) If the robot does this twice and there is still no walls to the left or right, it will walk in a straight line until it reaches a wall, then turn right.
	 * If there is no wall to the left and there is a wall directly in front, the robot turns right.
	 * These three steps repeat until the robot reaches the exit, or it is revealed that...
	 * @return true if the robot can take a step, false if not
	 * @throws the robot is in a room, the robot cannot step in any direction, the robot crashes, or the robot is out of energy
	 */
	@Override
	public boolean drive1Step2Exit() throws Exception {
		//use sensor to scan if wall is to left, or rotate to use a working sensor then rotate back
		if (ro.distanceToObstacle(Direction.LEFT) == 0) {
			//KeyListener.keyPressed(KeyEvent.VK_UP);
		}
		//follow the wall to the left until you hit a wall, there is no left wall, or you reach the exit
		//if you hit a wall, turn right and continue 1 step
		//if there is no longer a wall to the left, turn left and continue 1 step
		//if looping, stop because you are in a room
		//reduce energy depending on moves
		return false;
	}

	/**
	 * Returns the amount of energy consumed so far by subtracting the starting battery from the current one
	 * @return amount of energy expended so far
	 */
	@Override
	public float getEnergyConsumption() {
		//return energy consumed so far
		return 0;
	}

	/**
	 * Returns the path length of the robot, counted everytime the robot moves
	 * @return the current path length
	 */
	@Override
	public int getPathLength() {
		//return path length so far
		return 0;
	}

}

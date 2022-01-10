/**
 * 
 */
package gui;

import generation.Maze;

/**
 * @author Fletcher O'Brien
 * Class: Wizard
 * Responsibilities: Find the neighboring cell closer to exit
 * Collaborators: Maze, RobotDriver
 */
public class Wizard implements RobotDriver{
	
	//robot variable points to robot type
	//maze variable points to maze robot is in
	//energy variable counts energy consumption
	//path length variable counts path length

	/**
	 * Sets the robot variable to the given robot r
	 */
	@Override
	public void setRobot(Robot r) {
		//Sets the robot variable to the given robot
		
	}

	/**
	 * Sets the maze variable to given maze 'maze'
	 */
	@Override
	public void setMaze(Maze maze) {
		//sets the maze variable to the given maze
		
	}
	
	/**
	 * Automatically finds the exit by using the cell closest to exit method in the maze class, and drives the robot towards it
	 * by calling the drive1Step2Exit method until the robot reaches the exit.
	 * @throws if the robot runs out of energy, crashes into a wall, goes in a loop, or is stuck in a room
	 */
	@Override
	public boolean drive2Exit() throws Exception {
		//automatically finds the exit by using a method in the maze class and drives the robo toward it
		//if the robot runs out of energy throw exception
		//if the robot crashes throw exception
		//if the robot can't find the exit (or just goes in circles) RETURN false
		return false;
	}

	/**
	 * Automatically finds the exit by using the cell closest to exit method in the maze class, and drives the robot one step
	 * towards it.
	 * @throws if the robot runs out of energy, crashes, goes in a loop, or is stuck in a room
	 */
	@Override
	public boolean drive1Step2Exit() throws Exception {
		//automatically finds the exit by using a method in the maze class and drives the robot ONE STEP toward it
		//if robot runs out of energy throw exception
		//if robot crashes throw exception
		//if robot can't find the exit or just goes in circles return false
		return false;
	}

	/**
	 * Returns the energy consumed by taking the starting battery level and subtracting it from the current battery level
	 * @return energy consumed so far
	 */
	@Override
	public float getEnergyConsumption() {
		//returns the energy of the robot at the starting position minus the energy at the exit position.
		//used a measure of efficiency for maze solving method
		return 0;
	}

	/**
	 * Returns the current path length by calling a variable which counts how many steps the robot has taken so far (the variable
	 * is accurate because everytime the robot takes a step it is increased by 1).
	 * @return the current length of the robot's path by counting cells crossed
	 */
	@Override
	public int getPathLength() {
		//returns the amount of steps taken to reach the exit by counting how many cells the robot passed through to reach the exit.
		return 0;
	}

}

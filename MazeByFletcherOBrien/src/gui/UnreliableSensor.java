/**
 * 
 */
package gui;

import java.util.concurrent.TimeUnit;

import generation.CardinalDirection;
import generation.Maze;
import gui.Robot.Direction;

/**
 * @author Fletcher O'Brien
 * Class: UnreliableSensor
 * Responsibilities: Sometimes give distance to nearest wall
 * Collaborators: ReliableSensor, UnreliableRobot, Floorplan
 */
public class UnreliableSensor extends ReliableSensor {
	
	//gameended is true if the game has ended, false if it is still ongoing
	public boolean gameended;
	//failed is true if the sensor is currently down
	public boolean failed;
	//whether or not another sensor just failed
	public boolean allowedtofail = true; //if another sensor is failed, robot will set this to false until it is repaired.
	
	/**
	 * Runs a separate thread from the main one to continuously fail and repair the sensor based on the values given. If another
	 * sensor is currently down, then this sensor cannot fail until it is repaired.
	 */
	@Override
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		
		//create and run this on separate thread
		Thread t1 = new Thread(new Runnable() {
		    @Override
		    public void run() {
		    	
			        if (!failed && allowedtofail) {
			        	try {
							TimeUnit.SECONDS.sleep(meanTimeBetweenFailures);
							failed = true;
							TimeUnit.SECONDS.sleep(meanTimeToRepair);
							failed = false;
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
		    }
		});  
		t1.start();
		
		
	}
	
	/**
	 * Ends the thread running the failure and repair process if the game ends or another sensor is down.
	 */
	@Override
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException {
		//if gamerunning = false then stop failure and repair process;
		gameended = true;
		allowedtofail = false;
	}
}

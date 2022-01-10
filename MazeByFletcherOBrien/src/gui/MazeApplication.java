/**
 * 
 */
package gui;

import generation.Order;
import gui.Robot.Direction;

import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JFrame;


/**
 * This class is a wrapper class to startup the Maze game as a Java application
 * 
 * This code is refactored code from Maze.java by Paul Falstad, www.falstad.com, Copyright (C) 1998, all rights reserved
 * Paul Falstad granted permission to modify and use code for teaching purposes.
 * Refactored by Peter Kemper
 * 
 * TODO: use logger for output instead of Sys.out
 */
public class MazeApplication extends JFrame {

	// not used, just to make the compiler, static code checker happy
	private static final long serialVersionUID = 1L;
	
	// developments vs production version
	// for development it is more convenient if we produce the same maze over an over again
	// by setting the following constant to false, the maze will only vary with skill level and algorithm
	// but not on its own
	// for production version it is desirable that we never play the same maze 
	// so even if the algorithm and skill level are the same, the generated maze should look different
	// which is achieved with some random initialization
	private static final boolean DEVELOPMENT_VERSION_WITH_DETERMINISTIC_MAZE_GENERATION = false;

	/**
	 * Constructor
	 */
	public MazeApplication() {
		init(null);
	}

	/**
	 * Constructor that loads a maze from a given file or uses a particular method to generate a maze
	 * @param parameter can identify a generation method (Prim, Kruskal, Eller)
     * or a filename that stores an already generated maze that is then loaded, or can be null
	 */
	public MazeApplication(String parameter) {
		init(parameter);
	}

	/**
	 * Instantiates a controller with settings according to the given parameter.
	 * @param parameter can identify a generation method (Prim, Kruskal, Eller)
	 * or a filename that contains a generated maze that is then loaded,
	 * or can be null
	 * @return the newly instantiated and configured controller
	 */
	 Controller createController(String parameter) {
		 
		 //split the parameter into three parts -g mazegenerator -d driver -r xxxx
		 //parameter = "-g Prim -d Wallfollower -r 0000"
		 String[] params = new String[3];
		 if (parameter != null) {
			 params = parameter.split(" -");
		 }
		 
		 for (String s : params) {
			 System.out.println(s);
		 }
		 
		 
	    // need to instantiate a controller to return as a result in any case
	    Controller result = new Controller();
	    // can decide if user repeatedly plays the same mazes or 
	    // if mazes are different each and every time
	    // set to true for testing purposes
	    // set to false for playing the game
	    if (DEVELOPMENT_VERSION_WITH_DETERMINISTIC_MAZE_GENERATION)
	    	result.setDeterministic(true);
	    else
	    	result.setDeterministic(false);
	    String msg = null; // message for feedback
	    // Case 1: no input
	    if (params[0] == null) {
	        msg = "MazeApplication: maze will be generated with a randomized algorithm."; 
	    }
	    // Case 2: Prim
	    else if ("-g Prim".equalsIgnoreCase(params[0]))
	    {
	        msg = "MazeApplication: generating random maze with Prim's algorithm.";
	        result.setBuilder(Order.Builder.Prim);
	    }
	    // Case 3 a and b: Eller, Kruskal, Boruvka or some other generation algorithm
	    else if ("-g Kruskal".equalsIgnoreCase(params[0]))
	    {
	    	// TODO: for P2 assignment, please add code to set the builder accordingly
	        throw new RuntimeException("Don't know anybody named Kruskal ...");
	    }
	    else if ("-g Eller".equalsIgnoreCase(params[0]))
	    {
	    	// TODO: for P2 assignment, please add code to set the builder accordingly
	        throw new RuntimeException("Don't know anybody named Eller ...");
	    }
	    else if ("-g Boruvka".equalsIgnoreCase(params[0]))
	    {
	    	// TODO: for P2 assignment, please add code to set the builder accordingly
	        throw new RuntimeException("Don't know anybody named Eller ...");
	    }
	    // Case 4: a file
	    else {
	        File f = new File(parameter) ;
	        if (f.exists() && f.canRead())
	        {
	            msg = "MazeApplication: loading maze from file: " + parameter;
	            result.setFileName(parameter);
	            return result;
	        }
	        else {
	            // None of the predefined strings and not a filename either: 
	            msg = "MazeApplication: unknown parameter value: " + parameter + " ignored, operating in default mode.";
	        }
	    }
	    
	    UnreliableRobot joe = new UnreliableRobot();
	    
	    if (params[1] == null) {
	    	//if there is no driver default to manual
	    	result.setRobotAndDriver(null, null);
	    } else if ("d Wizard".equalsIgnoreCase(params[1])) {
	    	//set to wizard driver
	    	Wizard joedriver = new Wizard();
	    	result.setRobotAndDriver(joe, joedriver);
	    	System.out.println("MazeApplicaton: using Wizard driver");
	    } else if ("d Wallfollower".equalsIgnoreCase(params[1])) {
	    	//set to wallfollower driver
	    	WallFollower joedriver = new WallFollower();
	    	result.setRobotAndDriver(joe, joedriver);
	    	System.out.println("MazeApplicaton: using Wallfollower driver");
	    } else if ("d Manual".equalsIgnoreCase(params[1])) {
	    	//set to manual
	    	result.setRobotAndDriver(null, null);
	    	System.out.println("MazeApplicaton: using Manual driver");
	    }
	    

    	ReliableSensor frontr = new ReliableSensor();
    	ReliableSensor leftr = new ReliableSensor();
    	ReliableSensor rightr = new ReliableSensor();
    	ReliableSensor backr = new ReliableSensor();
    	
    	UnreliableSensor frontu = new UnreliableSensor();
    	UnreliableSensor leftu = new UnreliableSensor();
    	UnreliableSensor rightu = new UnreliableSensor();
    	UnreliableSensor backu = new UnreliableSensor();
	    
	    if (params[2] == null) {
	    	//all sensors are reliable by default
	    	//front, left, right, back
	    	joe.addDistanceSensor(frontr, Direction.FORWARD);
	    	joe.addDistanceSensor(leftr, Direction.LEFT);
	    	joe.addDistanceSensor(rightr, Direction.RIGHT);
	    	joe.addDistanceSensor(backr, Direction.BACKWARD);
	    	System.out.println("MazeApplicaton: F,L,R,B sensors reliable");
	    } else if ("r 0000".equalsIgnoreCase(params[2])) {
	    	//all sensors are unreliable
	    	joe.addDistanceSensor(frontu, Direction.FORWARD);
	    	joe.addDistanceSensor(leftu, Direction.LEFT);
	    	joe.addDistanceSensor(rightu, Direction.RIGHT);
	    	joe.addDistanceSensor(backu, Direction.BACKWARD);
	    	System.out.println("MazeApplicaton: no reliable sensors");
	    } else if ("r 1000".equalsIgnoreCase(params[2])) {
	    	//front sensor is reliable
	    	joe.addDistanceSensor(frontr, Direction.FORWARD);
	    	joe.addDistanceSensor(leftu, Direction.LEFT);
	    	joe.addDistanceSensor(rightu, Direction.RIGHT);
	    	joe.addDistanceSensor(backu, Direction.BACKWARD);
	    	System.out.println("MazeApplicaton: Forward sensor reliable");
	    } else if ("r 0100".equalsIgnoreCase(params[2])) {
	    	//left sensor is reliable
	    	joe.addDistanceSensor(frontu, Direction.FORWARD);
	    	joe.addDistanceSensor(leftr, Direction.LEFT);
	    	joe.addDistanceSensor(rightu, Direction.RIGHT);
	    	joe.addDistanceSensor(backu, Direction.BACKWARD);
	    	System.out.println("MazeApplicaton: Left sensor reliable");
	    } else if ("r 0010".equalsIgnoreCase(params[2])) {
	    	//right sensor is reliable
	    	joe.addDistanceSensor(frontu, Direction.FORWARD);
	    	joe.addDistanceSensor(leftu, Direction.LEFT);
	    	joe.addDistanceSensor(rightr, Direction.RIGHT);
	    	joe.addDistanceSensor(backu, Direction.BACKWARD);
	    	System.out.println("MazeApplicaton: Right sensor reliable");
	    } else if ("r 0001".equalsIgnoreCase(params[2])) {
	    	//back sensor is reliable
	    	joe.addDistanceSensor(frontu, Direction.FORWARD);
	    	joe.addDistanceSensor(leftu, Direction.LEFT);
	    	joe.addDistanceSensor(rightu, Direction.RIGHT);
	    	joe.addDistanceSensor(backr, Direction.BACKWARD);
	    	System.out.println("MazeApplicaton: Backward sensor reliable");
	    } else if ("r 1100".equalsIgnoreCase(params[2])) {
	    	//front, left sensors are reliable
	    	joe.addDistanceSensor(frontr, Direction.FORWARD);
	    	joe.addDistanceSensor(leftr, Direction.LEFT);
	    	joe.addDistanceSensor(rightu, Direction.RIGHT);
	    	joe.addDistanceSensor(backu, Direction.BACKWARD);
	    	System.out.println("MazeApplicaton: F,L sensors reliable");
	    } else if ("r 1010".equalsIgnoreCase(params[2])) {
	    	//front, right sensors are reliable
	    	joe.addDistanceSensor(frontr, Direction.FORWARD);
	    	joe.addDistanceSensor(leftu, Direction.LEFT);
	    	joe.addDistanceSensor(rightr, Direction.RIGHT);
	    	joe.addDistanceSensor(backu, Direction.BACKWARD);
	    	System.out.println("MazeApplicaton: F,R sensors reliable");
	    } else if ("r 1001".equalsIgnoreCase(params[2])) {
	    	//front, back sensors are reliable
	    	joe.addDistanceSensor(frontr, Direction.FORWARD);
	    	joe.addDistanceSensor(leftu, Direction.LEFT);
	    	joe.addDistanceSensor(rightu, Direction.RIGHT);
	    	joe.addDistanceSensor(backr, Direction.BACKWARD);
	    	System.out.println("MazeApplicaton: F,B sensors reliable");
	    } else if ("r 0110".equalsIgnoreCase(params[2])) {
	    	//left, right sensors are reliable
	    	joe.addDistanceSensor(frontu, Direction.FORWARD);
	    	joe.addDistanceSensor(leftr, Direction.LEFT);
	    	joe.addDistanceSensor(rightr, Direction.RIGHT);
	    	joe.addDistanceSensor(backu, Direction.BACKWARD);
	    	System.out.println("MazeApplicaton: L,R sensors reliable");
	    } else if ("r 0101".equalsIgnoreCase(params[2])) {
	    	//left, back sensors are reliable
	    	joe.addDistanceSensor(frontu, Direction.FORWARD);
	    	joe.addDistanceSensor(leftr, Direction.LEFT);
	    	joe.addDistanceSensor(rightu, Direction.RIGHT);
	    	joe.addDistanceSensor(backr, Direction.BACKWARD);
	    	System.out.println("MazeApplicaton: L,B sensors reliable");
	    } else if ("r 0011".equalsIgnoreCase(params[2])) {
	    	//right, back sensors are reliable
	    	joe.addDistanceSensor(frontu, Direction.FORWARD);
	    	joe.addDistanceSensor(leftu, Direction.LEFT);
	    	joe.addDistanceSensor(rightr, Direction.RIGHT);
	    	joe.addDistanceSensor(backr, Direction.BACKWARD);
	    	System.out.println("MazeApplicaton: R,B sensors reliable");
	    } else if ("r 1110".equalsIgnoreCase(params[2])) {
	    	//front, left, right sensors are reliable
	    	joe.addDistanceSensor(frontr, Direction.FORWARD);
	    	joe.addDistanceSensor(leftr, Direction.LEFT);
	    	joe.addDistanceSensor(rightr, Direction.RIGHT);
	    	joe.addDistanceSensor(backu, Direction.BACKWARD);
	    	System.out.println("MazeApplicaton: F,L,R sensors reliable");
	    } else if ("r 1101".equalsIgnoreCase(params[2])) {
	    	//front, left, back sensors are reliable
	    	joe.addDistanceSensor(frontr, Direction.FORWARD);
	    	joe.addDistanceSensor(leftr, Direction.LEFT);
	    	joe.addDistanceSensor(rightu, Direction.RIGHT);
	    	joe.addDistanceSensor(backr, Direction.BACKWARD);
	    	System.out.println("MazeApplicaton: F,L,B sensors reliable");
	    } else if ("r 1011".equalsIgnoreCase(params[2])) {
	    	//front, right, back sensors are reliable
	    	joe.addDistanceSensor(frontr, Direction.FORWARD);
	    	joe.addDistanceSensor(leftu, Direction.LEFT);
	    	joe.addDistanceSensor(rightr, Direction.RIGHT);
	    	joe.addDistanceSensor(backr, Direction.BACKWARD);
	    	System.out.println("MazeApplicaton: F,R,B sensors reliable");
	    } else if ("r 0111".equalsIgnoreCase(params[2])) {
	    	//left, right, back sensors are reliable
	    	joe.addDistanceSensor(frontu, Direction.FORWARD);
	    	joe.addDistanceSensor(leftr, Direction.LEFT);
	    	joe.addDistanceSensor(rightr, Direction.RIGHT);
	    	joe.addDistanceSensor(backr, Direction.BACKWARD);
	    	System.out.println("MazeApplicaton: L,R,B sensors reliable");
	    } else if ("r 1111".equalsIgnoreCase(params[2])) {
	    	//all sensors are reliable
	    	joe.addDistanceSensor(frontr, Direction.FORWARD);
	    	joe.addDistanceSensor(leftr, Direction.LEFT);
	    	joe.addDistanceSensor(rightr, Direction.RIGHT);
	    	joe.addDistanceSensor(backr, Direction.BACKWARD);
	    	System.out.println("MazeApplicaton: F,L,R,B sensors reliable");
	    }
	    
	    // controller instanted and attributes set according to given input parameter
	    // output message and return controller
	    System.out.println(msg);
	    return result;
	}

	/**
	 * Initializes some internals and puts the game on display.
	 * @param parameter can identify a generation method (Prim, Kruskal, Eller)
     * or a filename that contains a generated maze that is then loaded, or can be null
	 */
	private void init(String parameter) {
	    // instantiate a game controller and add it to the JFrame
	    Controller controller = createController(parameter);
		add(controller.getPanel()) ;
		// instantiate a key listener that feeds keyboard input into the controller
		// and add it to the JFrame
		KeyListener kl = new SimpleKeyListener(this, controller) ;
		addKeyListener(kl) ;
		// set the frame to a fixed size for its width and height and put it on display
		setSize(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT+22) ;
		setVisible(true) ;
		// focus should be on the JFrame of the MazeApplication and not on the maze panel
		// such that the SimpleKeyListener kl is used
		setFocusable(true) ;
		// start the game, hand over control to the game controller
		controller.start();
	}
	
	/**
	 * Main method to launch Maze game as a java application.
	 * The application can be operated in three ways. 
	 * 1) The intended normal operation is to provide no parameters
	 * and the maze will be generated by a randomized DFS algorithm (default). 
	 * 2) If a filename is given that contains a maze stored in xml format. 
	 * The maze will be loaded from that file. 
	 * This option is useful during development to test with a particular maze.
	 * 3) A predefined constant string is given to select a maze
	 * generation algorithm, currently supported is "Prim".
	 * @param args is optional, first string can be a fixed constant like Prim or
	 * the name of a file that stores a maze in XML format
	 */
	public static void main(String[] args) {
	    JFrame app ; 
		switch (args.length) {
		case 1 : app = new MazeApplication(args[0]);
		break ;
		case 0 : 
		default : app = new MazeApplication() ;
		break ;
		}
		app.repaint() ;
	}

}

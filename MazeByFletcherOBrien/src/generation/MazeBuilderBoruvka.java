package generation;

import java.util.ArrayList;

public class MazeBuilderBoruvka extends MazeBuilder implements Runnable {
	
	
	private ArrayList<Integer> wallweights = new ArrayList<Integer>();
	private ArrayList<Wallboard> walls = new ArrayList<Wallboard>();
	private ArrayList<Wallboard> rooms = new ArrayList<Wallboard>();
	
	public MazeBuilderBoruvka() {
		super();
		System.out.println("MazeBuilderBoruvka uses Boruvka's algorithm to generate maze.");
	}
	
	//method: generate weights
	
	//randomly assign weight to every internal wall board and store it in an array
	
	public void generateWeights() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Wallboard wallboard = new Wallboard(x, y, CardinalDirection.East);
				for (CardinalDirection cd : CardinalDirection.values()) {
					wallboard.setLocationDirection(x, y, cd);
					if (floorplan.canTearDown(wallboard)) // 
					{
						walls.add(new Wallboard(x, y, cd));
						wallweights.add((int) Math.random() * 100);
					}
				}
			}
		}
	}
	
	
	//method: get edge weight
	
	//get the value of the edge weights from private array in method and returns it
	
	public int getEdgeWeight(int x, int y, CardinalDirection cd) {
		for (Wallboard wb : walls) {
			if (wb.getX() == x && wb.getY() == y && wb.getDirection() == cd) {
				return wallweights.get(walls.indexOf(wb));
			}
		}
		
		System.out.println("We couldn't find the edge you were looking for");
		return -1;
	}
	
	
	//method: generate pathways
	
	//for each room, get the lowest cost wall by checking the randomly assigned weight of each wall of the room
	
	//repeat the above process until there is only one 'room' left
	
	//randomly add an entrance and exit to the maze
	
	
	//TO DO: psuedocode for test cases, finish below, create boruvka test class, javadoc then tag and release final submission
	
	@Override
	protected void generatePathways() {
		Wallboard cheapest;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (CardinalDirection cd : CardinalDirection.values()) {
					cheapest = new Wallboard(x, y, cd);
				}
			}
		}
	}
	
	
}

package generation;

import generation.Order;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

//NOTE: Tests must wait for maze builder thread to terminate before testing!


public class MazeFactoryTest {

	//TEST 1: Maze has one exit, no more
	
	//TEST 2: There are no rooms without entrances/exits
	
	//TEST 3: You can reach the exit from the start (check project pdf)
	
	//TEST 4: It is a perfect maze (has correct number of walls for n cells)
	
	//TEST 5: Maze has one entrance/start
	
	//Test all methods and such
	
	final class TestOrder implements Order{
		
		public int getSeed() {
			return 11;
		}

		@Override
		public int getSkillLevel() {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public Builder getBuilder() {
			// TODO Auto-generated method stub
			return Order.Builder.Prim;
		}

		@Override
		public boolean isPerfect() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public void deliver(Maze mazeConfig) {
			
		}

		@Override
		public void updateProgress(int percentage) {
			percentage = 100;
			
		}
		
	}
	
	final class TestFakeOrder implements Order{
		
		public int getSeed() {
			return 11;
		}

		@Override
		public int getSkillLevel() {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public Builder getBuilder() {
			// TODO Auto-generated method stub
			return Order.Builder.Kruskal;
		}

		@Override
		public boolean isPerfect() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public void deliver(Maze mazeConfig) {
			
		}

		@Override
		public void updateProgress(int percentage) {
			percentage = 100;
			
		}
		
	}
	
	/**
	 * Test case: 
	 * <p>
	 * Method under test:
	 * <p>
	 * Correct behavior:
	 */
	@Test
	public final void testSomething() {
		//assertTrue, False, Equal, NotNull, etc.
	}
	
	/**
	 * Test case: Correctness of order method
	 * <p>
	 * Method under test: order()
	 * <p>
	 * Correct behavior: Sets internal fields to the given order
	 */
	@Test
	public final void testordermethod() {
		//test if factory busy does it automatically stop
		//check that cases correctly fire and it returns true
		
		
		//Test if the order will return false if it's busy with another order
		TestOrder test = new TestOrder();
		MazeFactory maze = new MazeFactory();
		assertTrue(maze.order(test));
		assertFalse(maze.order(test));
		
		//Test if given order with unrecognized builder returns false
		TestFakeOrder faketest = new TestFakeOrder();
		maze = new MazeFactory();
		assertFalse(maze.order(faketest));
	}
	
	/**
	 * Test case: 
	 * <p>
	 * Method under test:
	 * <p>
	 * Correct behavior:
	 */
	@Test
	public final void testcancel() {
		//check that buildthread, builder, and currentorder are all null after call
		MazeFactory maze = new MazeFactory();
		TestOrder test = new TestOrder();
		
		maze.order(test);
		maze.cancel();
		//assertNull(maze.buildThread);
	}
	
	/**
	 * Test case: 
	 * <p>
	 * Method under test:
	 * <p>
	 * Correct behavior:
	 */
	@Test
	public final void testwaitTillDelivered() {
		//check if successfully joins build thread/waits until thread is delivered
	}
	
	/**
	 * Test case: 
	 * <p>
	 * Method under test:
	 * <p>
	 * Correct behavior:
	 */
	@Test
	public final void testbuildOrder() {
		//assertTrue, False, Equal, NotNull, etc.
	}
	
	
	
	
	
	
}

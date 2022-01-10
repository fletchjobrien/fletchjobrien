package edu.wm.cs.cs301.game2048;

public class State implements GameState {

	int[][] board;
	
	public State(GameState currentState) {
		
	}

	public State() {
		
	}

	@Override
	public int getValue(int x, int y) {
		return board[x][y];
	}

	@Override
	public void setValue(int x, int y, int value) {
		board[x][y] = value;
	}

	@Override
	public void setEmptyBoard() {
		board = new int[4][4];
	}
	
	public int unoccupiedTile() {
		int x = (int) Math.floor(Math.random() * 4);
		int y = (int) Math.floor(Math.random() * 4);
		if (board[x][y] != 0) {
			return unoccupiedTile();
		}
		return x;
	}

	@Override
	public boolean addTile() {
		if (isFull()!=true) {
			int x = unoccupiedTile();
			
			int tv = (int) Math.floor(Math.random() * 2);
			if (tv == 0) {
				tv = 2;
			} else {
				tv = 4;
			}
			
			for (int y = 0; y < 4; y++) {
				if (board[x][y] == 0) {
					board[x][y] = tv;
					y = 5;
				}
			}
			return true;
			
		}
		
		return false;
		
	}

	@Override
	public boolean isFull() {
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (board[x][y] == 0) {
					return false;
				}
				
			}
		}
		 
		return true;
	}

	@Override
	public boolean canMerge() {
		for (int x = 0; x < 4 ; x++) {
			for (int y = 0; y < 3; y++) {
				if (board[x][y] == board[x][y+1]) {
					return true;
				}
			}
		}
		
		for (int y = 0; y < 4 ; y++) {
			for (int x = 0; x < 3; x++) {
				if (board[x][y] == board[x+1][y]) {
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean reachedThreshold() {
		for (int x = 0; x < 4 ; x++) {
			for (int y = 0; y < 4; y++) {
				if (board[x][y] == 2048) {
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public int up() {
		
		int score = 0;
		
		System.out.println("Moved up.");
		for (int x = 0; x < 4; x++) {
			for (int y = 1; y < 4; y++) {
				if (board[x][y] != 0) {
					if (board[x][y-1] == 0) {
						board[x][y-1] = board[x][y];
						board[x][y] = 0;
						x=0;
						y=0;
					}
				}
			}
		}
		//move up if the space is empty
		//move up if the block above is equal, combine
		for (int x = 0; x < 4; x++) {
			for (int y = 1; y < 4; y++) {
				if (board[x][y] != 0) {
					if (board[x][y-1] == board[x][y]) {
						board[x][y-1] *= 2;
						board[x][y] = 0;
						score += board[x][y-1];
					}
				}
				
			}
		}
		for (int x = 0; x < 4; x++) {
			for (int y = 1; y < 4; y++) {
				if (board[x][y] != 0) {
					if (board[x][y-1] == 0) {
						board[x][y-1] = board[x][y];
						board[x][y] = 0;
						x=0;
						y=0;
					}
				}
			}
		}
		
		return score;
		
	}

	@Override
	public int down() {
		int score = 0;
		
		System.out.println("Moved down.");
		for (int x = 3; x >= 0; x--) {
			for (int y = 2; y >= 0; y--) {
				if (board[x][y] != 0) {
					if (board[x][y+1] == 0) {
						board[x][y+1] = board[x][y];
						board[x][y] = 0;
						x=3;
						y=3;
					}
				}
			}
		}
		//move down if the space is empty
		//move down if the block below is equal, combine
		for (int x = 3; x >= 0; x--) {
			for (int y = 2; y >= 0; y--) {
				if (board[x][y] != 0) {
					if (board[x][y+1] == board[x][y]) {
						board[x][y+1] *= 2;
						board[x][y] = 0;
						score += board[x][y+1];
					}
				}
				
			}
		}
		for (int x = 3; x >= 0; x--) {
			for (int y = 2; y >= 0; y--) {
				if (board[x][y] != 0) {
					if (board[x][y+1] == 0) {
						board[x][y+1] = board[x][y];
						board[x][y] = 0;
						x=3;
						y=3;
					}
				}
			}
		}
		
		return score;
		
	}

	@Override
	public int right() {
		int score = 0;
		
		System.out.println("Moved right.");
		for (int x = 2; x >= 0; x--) {
			for (int y = 0; y < 4; y++) {
				if (board[x][y] != 0) {
					if (board[x+1][y] == 0) {
						board[x+1][y] = board[x][y];
						board[x][y] = 0;
						x = 2;
						y = -1;
					}
				}
			}
		}
		//move right all the way +if the space is empty
		//move right if the block right is equal, combine
		for (int x = 2; x >= 0; x--) {
			for (int y = 0; y < 4; y++) {
				if (board[x][y] != 0) {
					if (board[x+1][y] == board[x][y]) {
						board[x+1][y] *= 2;
						board[x][y] = 0;
						score += board[x+1][y];
					}
				}
				
			}
		}
		for (int x = 2; x >= 0; x--) {
			for (int y = 0; y < 4; y++) {
				if (board[x][y] != 0) {
					if (board[x+1][y] == 0) {
						board[x+1][y] = board[x][y];
						board[x][y] = 0;
						x = 2;
						y = -1;
					}
				}
			}
		}
		
		return score;
	}

	@Override
	public int left() {
		int score = 0;
		
		System.out.println("Moved left.");
		for (int x = 1; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (board[x][y] != 0) {
					if (board[x-1][y] == 0) {
						board[x-1][y] = board[x][y];
						board[x][y] = 0;
						x=1;
						y=-1;
					}
				}
			}
		}
		//move left all the way if the space is empty
		//move left if the block left is equal, combine
		for (int x = 1; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (board[x][y] != 0) {
					if (board[x-1][y] == board[x][y]) {
						board[x-1][y] *= 2;
						board[x][y] = 0;
						score += board[x-1][y];
					}
				}
				
			}
		}
		for (int x = 1; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (board[x][y] != 0) {
					if (board[x-1][y] == 0) {
						board[x-1][y] = board[x][y];
						board[x][y] = 0;
						x=1;
						y=-1;
					}
				}
			}
		}
		return score;
	}

}

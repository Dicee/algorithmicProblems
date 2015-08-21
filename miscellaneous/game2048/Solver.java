package miscellaneous.game2048;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class Solver {
	private final Random rd = new Random();
	
	private static final Direction[] dirs = Direction.values();
	
	private static final int CACHE_SIZE = 15;
	private static final Map<Integer,Integer> CACHED_POWERS_OF_TWO = new HashMap<>(10);
	static {
		int pow = 2;
		for (int i=1, level=1 ; i<=CACHE_SIZE ; i++, level += i, pow *= 2) CACHED_POWERS_OF_TWO.put(pow,level);
		System.out.println(CACHED_POWERS_OF_TWO);
	}
	
	private final double coeffStraightDirs = 2;
	private final double coeffObliqueDirs = 1;

	public Direction nextMove(GameState gameState) {
		return dirs[rd.nextInt(dirs.length)];
	}
	
	public double computeHeuristic(GameState gameState) {
		double score = 0;
		for (int i=0 ; i<GameState.BOARD_SIZE ; i++) 
			for (int j=0 ; j<GameState.BOARD_SIZE ; j++) {
				Optional<Integer> state = gameState.get(i,j);
				double straightScore = computeScore(state,getNeighbourIfExists(gameState,i,j,Direction.RIGHT)) + 
						computeScore(state,getNeighbourIfExists(gameState,i,j,Direction.DOWN));
				double obliqueScore = computeScore(state,getNeighbourIfExists(gameState,i,j,Direction.DOWN_RIGHT)) +
						computeScore(state,getNeighbourIfExists(gameState,i,j,Direction.UP_RIGHT));
				score += coeffStraightDirs*straightScore + coeffObliqueDirs*obliqueScore;
			}
		return score;
	}

	private static double computeScore(Optional<Integer> tile0, Optional<Integer> tile1) {
		if (tile0.isPresent() && tile1.isPresent()) {
			int level0 = CACHED_POWERS_OF_TWO.get(tile0.get());
			int level1 = CACHED_POWERS_OF_TWO.get(tile1.get());
			return 20d/(Math.abs(level0 - level1) + 1);
		}
		return 0;
	}

	private static Optional<Integer> getNeighbourIfExists(GameState gameState, int i, int j, Direction dir) {
		int m = i + dir.x, n = j + dir.y;
		return gameState.isInBounds(n,m) ? gameState.get(n,m) : Optional.empty();
	}

	public static void main(String[] args) throws InterruptedException {
		Solver solver = new Solver();
		
		Game2048 game = Game_Panel.popGameWindow();
		
		while (true) {
//			System.out.println(Arrays.deepToString(solver.tiles));
			Thread.sleep(1000);
			
			GameState state = game.getState();
			System.out.println(state + "\n");
			game.move(solver.nextMove(state));
		}
	}


}

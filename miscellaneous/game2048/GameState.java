package miscellaneous.game2048;

import static miscellaneous.utils.check.Check.areEqual;
import static miscellaneous.utils.check.Check.notNull;
import static miscellaneous.utils.collection.ArrayUtils.toPrettyString;
import static miscellaneous.utils.math.MathUtils.isBetween;

import java.util.Optional;

import scala.actors.threadpool.Arrays;

public class GameState {
	public static final int	BOARD_SIZE	= 4;
	private final Tile[][] tiles;

	public GameState(Tile[][] tiles) { 
		areEqual(BOARD_SIZE,notNull(tiles)   .length);
		areEqual(BOARD_SIZE,notNull(tiles[0]).length);
		this.tiles = tiles; 
	}
	
	public Optional<Integer> get(int i, int j) { 
		return tiles[i][j] == null ? Optional.empty() : Optional.of(tiles[i][j].getValue());
	}
	
	public boolean isInBounds(int i, int j) { return isBetween(0,i,tiles.length) && isBetween(0,j,tiles[0].length); }
	
	@Override
	public String toString() { return toPrettyString(tiles); }
	
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (o == null || o.getClass() != getClass()) return false;
		GameState that = (GameState) o;
		return Arrays.deepEquals(tiles,that.tiles);
	}
	
	@Override
	public int hashCode() { return Arrays.deepHashCode(tiles); }
}

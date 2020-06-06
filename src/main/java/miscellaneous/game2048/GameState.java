package miscellaneous.game2048;

import static com.dici.check.Check.areEqual;
import static com.dici.check.Check.notNull;
import static com.dici.collection.ArrayUtils.toPrettyString;
import static com.dici.math.MathUtils.isBetween;

import java.util.Arrays;
import java.util.Optional;

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

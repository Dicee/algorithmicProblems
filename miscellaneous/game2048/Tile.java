package miscellaneous.game2048;

import java.util.Random;

import miscellaneous.utils.check.Check;

public class Tile {
	private static final Random rd = new Random();
	
	private final int value;
	
	public Tile() { this(2*(rd.nextInt(2) + 1)); }
	
	Tile(int value) { 
		checkIsPowerOfTwo(value);
		this.value = value;
	}

	private void checkIsPowerOfTwo(int value) {
		Check.isGreaterOrEqual(value, 2);
		while (value >= 2) value = value >> 1;
		Check.areEqual(1, value);
	}
	
	public Tile merge(Tile that) {
		Check.areEqual(value, that.value);
		return new Tile(2*value);
	}
	
	public int getValue() { return value; }

	@Override
	public String toString() { return String.valueOf(value); }
}

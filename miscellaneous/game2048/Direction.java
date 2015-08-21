package miscellaneous.game2048;

public enum Direction {
	UP        (-1, 0),
	DOWN      ( 1, 0),
	LEFT      ( 0,-1),
	RIGHT     ( 0, 1),
	UP_LEFT   (-1,-1),
	UP_RIGHT  (-1, 1),
	DOWN_LEFT ( 1,-1),
	DOWN_RIGHT( 1, 1);
	
	public int x, y;
	
	private Direction (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Direction opposed() {
	    switch (this) {
	    	case UP         : return DOWN;
	        case DOWN       : return UP;
	        case LEFT       : return RIGHT;
	        case RIGHT      : return LEFT;
	        case UP_LEFT    : return UP_RIGHT;
	        case UP_RIGHT   : return UP_LEFT;
	        case DOWN_LEFT  : return DOWN_RIGHT;
	        case DOWN_RIGHT : return DOWN_LEFT;
	        default         : throw new IllegalArgumentException("Bad direction : " + this);
	    }
	}
}

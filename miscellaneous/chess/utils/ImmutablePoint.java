package miscellaneous.chess.utils;

public class ImmutablePoint {
    public final int x, y;

    public ImmutablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public ImmutablePoint moveX(int dx)      { return new ImmutablePoint(x + dx, y     ); }
    public ImmutablePoint moveY(int dy)      { return new ImmutablePoint(x     , y + dy); }
    public ImmutablePoint move (Delta delta) { return moveX(delta.dx).moveY(delta.dy)     ; }
    
    @Override public String toString() { return String.format("(%d, %d)", x, y); }
}

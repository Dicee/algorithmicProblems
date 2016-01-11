package miscellaneous.chess.utils;

public class Delta {
    public final int dx, dy;

    public Delta(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
    
    public Delta times(int length ) { return new Delta(dx * length , dy * length ); }
    public Delta plus (Delta delta) { return new Delta(dx + delta.dx, dy + delta.dy); }
    
    @Override public String toString() { return String.format("Delta(%d, %d)", dx, dy);}
}

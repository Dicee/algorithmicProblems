package miscellaneous.utils.image;

import static miscellaneous.utils.math.MathUtils.isBetween;

import java.awt.image.BufferedImage;
import java.util.Objects;


public class Point {
	public int x, y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public boolean isInImage(BufferedImage im) { return isInBounds(im.getWidth(),im.getHeight()); }
	public boolean isInBounds(int w, int h)    { return isBetween(0,x,w) && isBetween(0,y,h)    ; }

	public Point translate(Point p) { return new Point(p.x + x,p.y + y); }
	
	@Override
	public int hashCode() { return Objects.hash(x,y); }

	@Override
	public boolean equals(Object o) {
		if (this == o)                               return true;
		if (o == null || getClass() != o.getClass()) return false;
		Point that = (Point) o;
		return x == that.x && y == that.y;
	}
}

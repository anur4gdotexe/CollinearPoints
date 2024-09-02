import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
	private final int x, y;
	
	public Point(int x, int y) {
		if (x < 0 || y < 0 || x > 32767 || y > 32767) {
			throw new IllegalArgumentException("Arguments out of range");
		}
		this.x = x;
		this.y = y;
	}
	
	public void draw() {
		StdDraw.point(x, y);
	}
	
	public void drawTo(Point that) {
		StdDraw.line(this.x, this.y, that.x, that.y);
	}
	
	public String toString() {
		return "(" + String.valueOf(x) +", "+ String.valueOf(y) + ")";
	}
	
	public int compareTo(Point that) {
		if ((this.y > that.y) || (this.y == that.y && this.x > that.x)) return 1;
		else if (this.y == that.y && this.x == that.x) return 0;
		return -1;
	}
	
	public double slopeTo(Point that) {
		if (compareTo(that) == 0) return -1.0/0;
		if (this.x == that.x) return 1.0/0;
		if (this.y == that.y) return 0.0;
		return ((double)this.y - (double)that.y)/((double)this.x - (double)that.x);
	}
	
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point p, Point q) {
            return Double.compare(slopeTo(p), slopeTo(q));
        }
    }
}
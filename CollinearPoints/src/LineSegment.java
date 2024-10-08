public class LineSegment {
	private final Point p;
	private final Point q;
	
	public LineSegment (Point p, Point q) {
		if (p == null || q == null) {
			throw new IllegalArgumentException("Argument to line segment constructor is null");
		}
		if (p.compareTo(q) == 0) {
			throw new IllegalArgumentException("Arguments to line segment constructor are same");
		}
		
		this.p = p;
		this.q = q;
	}
	
	public void draw() {
		p.drawTo(q);
	}
	
	public String toString() {
		return p.toString() + " -> " + q.toString();
	}
}
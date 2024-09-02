public class BruteCollinearPoints {
	private Point[] points;
	private int n;
	private double[][] slopes;
	private LineSegment[] lineSegments;
	private int numberOfSegments;

	public BruteCollinearPoints(Point[] points) {
		if (isNull(points) || isRepeated(points)) {
			throw new IllegalArgumentException("Inappropriate argument to the constructor");
		}
		
		this.points = points;
		n = points.length;
		slopes = new double[n][n];
		lineSegments = new LineSegment[0];
		numberOfSegments = 0;

		getSlopes();

		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n - 2; j++) {
				for (int k = j + 1; k < n - 1; k++) {
					for (int l = k + 1; l < n; l++) {
						if (slopes[i][j] == slopes[i][k] && slopes[i][j] == slopes[i][l]) {
							
							int[] arr = {i, j, k, l};
							sort(arr);
							LineSegment segment = new LineSegment(points[arr[0]], points[arr[3]]);
							if (!duplicate(segment)) {
								add(segment);
							}
						}
					}
				}
			}
		}
	}

	public int numberOfSegments() {
		return numberOfSegments;
	}
	
	public LineSegment[] segments() {
		return lineSegments;
	}
	
	private void getSlopes() {
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				slopes[i][j] = points[i].slopeTo(points[j]);
			}
		}
	}

	private void add(LineSegment segment) {
		if (numberOfSegments == lineSegments.length) {
			resize(lineSegments.length + 1);
		}
		lineSegments[numberOfSegments++] = segment;
	}

	private void resize(int capacity) {
		LineSegment[] copy = new LineSegment[capacity];
		for (int i = 0; i < numberOfSegments; i++) {
			copy[i] = lineSegments[i];
		}

		lineSegments = copy;
	}

	private boolean duplicate(LineSegment segment) {
		for (int i = 0; i < numberOfSegments; i++) {
			LineSegment arrSegment = lineSegments[i];

			if (arrSegment.toString().compareTo(segment.toString()) == 0)
				return true;
		}
		return false;
	}
	
	private boolean isNull(Point[] points) {
		if (points == null) return true;
		for (Point point : points) {
			if (point == null) return true;
		}
		
		return false;
	}
	
	private boolean isRepeated(Point[] points) {
		for (int i = 0; i < points.length; i++) {
			for (int j = i+1; j < points.length; j++) {
				if (points[i].compareTo(points[j]) == 0) return true;
			}
		}
		
		return false;
	}
	
	private void sort(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
            for (int j = i; j > 0 && less(arr[j], arr[j-1]); j--) {
                exch(arr, j, j-1);
            }
        }
	}
	
	private boolean less(int v, int w) {
        return points[v].compareTo(points[w]) < 0;
    }
	
	private void exch(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
}
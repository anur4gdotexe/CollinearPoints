public class FastCollinearPoints {
	private static Point[] pts;
	private int n;
	private LineSegment[] lineSegments;
	private int numberOfSegments;
	
	public FastCollinearPoints(Point[] points) {
		
		if (isNull(points) || isRepeated(points)) {
			throw new IllegalArgumentException("Inappropriate argument to the constructor");
		}
		
		pts = points;
		n = pts.length;
		lineSegments = new LineSegment[0];
		numberOfSegments = 0;
		
		
		for (int i = 0; i < n; i++) {
			
			int[] temp = new int[n-1];
			for (int t = 0, en = 0; t < n-1; en++) {
				if (en == i) continue;
				temp[t++] = en;
			}
			// temp = {0, 1, 2, ..., n-2, n-1}
			int[] aux = new int[n-1];
			Merge merge = new Merge();
			merge.sort(pts[i], temp, aux, 0, n-2);
			
			int count = 0, end1 = i, end2 = i;
			
			for (int j = 0; j < n-2; j++) {

				if (pts[temp[j]].slopeTo(pts[i]) != pts[temp[j+1]].slopeTo(pts[i])) {
					if (count >= 2) {
						end1 = isSmaller(pts[end1], pts[temp[j]]) ? end1 : temp[j];
						end2 = isGreater(pts[end2], pts[temp[j]]) ? end2 : temp[j];
						
						LineSegment segment = new LineSegment(pts[end1], pts[end2]);
						if (!duplicate(segment)) add(segment);
					}
					count = 0;
					end1 = i; end2 = i;
					continue;
				}
				count ++;
				end1 = isSmaller(pts[end1], pts[temp[j]]) ? end1 : temp[j];
				
				end2 = isGreater(pts[end2], pts[temp[j]]) ? end2 : temp[j];
			}
			
			if (count >= 2) {
				end1 = isSmaller(pts[end1], pts[temp[n-2]]) ? end1 : temp[n-2];
				end2 = isGreater(pts[end2], pts[temp[n-2]]) ? end2 : temp[n-2];
				
				LineSegment segment = new LineSegment(pts[end1], pts[end2]);
				if (!duplicate(segment)) add(segment);
			}
		}
	}
	
	public int numberOfSegments() {
		return numberOfSegments;
	}
	
	public LineSegment[] segments() {		
		return lineSegments;
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
	
	private boolean isNull(Point[] pts) {
		if (pts == null) return true;
		for (Point point : pts) {
			if (point == null) return true;
		}
		
		return false;
	}
	
	private boolean isRepeated(Point[] pts) {
		for (int i = 0; i < pts.length; i++) {
			for (int j = i+1; j < pts.length; j++) {
				if (pts[i].compareTo(pts[j]) == 0) return true;
			}
		}
		
		return false;
	}
	
	private boolean isSmaller(Point v, Point w) {
		return v.compareTo(w) < 0;
	}
	
	private boolean isGreater(Point v, Point w) {
		return v.compareTo(w) > 0;
	}
	

	private class Merge{
	    private void merge(Point p, int[] a, int[] aux, int lo, int mid, int hi) {
	        assert isSorted(p, a, lo, mid);
	        assert isSorted(p, a, mid+1, hi);
	        
	        for (int k = lo; k <= hi; k++) {
	            aux[k] = a[k];
	        }

	        int i = lo, j = mid+1;
	        for (int k = lo; k <= hi; k++) {
	            if (i > mid) a[k] = aux[j++];
	            else if (j > hi) a[k] = aux[i++];
	            else if (less(p, aux[j], aux[i])) a[k] = aux[j++];
	            else a[k] = aux[i++];
	        }

	        assert isSorted(p, a, lo, hi);
	    }

	    private void sort(Point p, int[] a, int[] aux,int lo, int hi) {
	    	if (hi <= lo + 6) {
	    		Insertion ins = new Insertion();
	    		ins.sort(p, a, lo, hi);
	    		return;
	    	}
	    	
	        int mid = lo + (hi - lo) / 2;
	        sort(p, a, aux, lo, mid);
	        sort(p, a, aux, mid + 1, hi);
	        merge(p, a, aux, lo, mid, hi);
	    }

	    private boolean less(Point p, int v, int w) {
	        return p.slopeTo(pts[v]) - p.slopeTo(pts[w]) < 0;
	    }

	    private boolean isSorted(Point p, int[] a, int lo, int hi) {
	        for (int i = lo + 1; i <= hi; i++)
	            if (less(p, a[i], a[i-1])) return false;
	        return true;
	    }
	}
	
	private class Insertion{
		private void sort(Point p, int[] a, int lo, int hi) {
	        for (int i = lo; i < hi + 1; i++) {
	            for (int j = i; j > 0 && less(p, a[j], a[j-1]); j--) {
	                exch(a, j, j-1);
	            }
	        }
		}
		
	    private boolean less(Point p, int v, int w) {
	        return p.slopeTo(pts[v]) - p.slopeTo(pts[w]) < 0;
	    }
		
		private void exch(int[] a, int i, int j) {
			int temp = a[i];
			a[i] = a[j];
			a[j] = temp;
		}
	}
}
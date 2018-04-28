// Makes simple polygons.
// The "count" tab lets you draw points and move them,
// and it counts the number of simple polygons on the sets

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;

public class TriangulateHW {
	static int POINTWID = 2;	// size of points
	static int numPoints = 100;	// Number of points

	// the x and y arrays hold the coordinates
	// the B array is the order of the points in the polygon
	// You want to fill the C array with the simple polygon
	static double x[] = new double[200];
	static double y[] = new double[200];

	static int B[] = new int[200]; // the permutation matrix
	static int C[] = new int[200]; // the one that becomes simple

	// Variables for the minimal convexulation
	static int numTriangulationChords;
	static ArrayList<int[]> triangulationChords;

	static SimpleFrame myFrame;

	public static void main(String args[]) {
		makePolygons();
		
		// Create the frame to draw on
		myFrame = new SimpleFrame();
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setSize(800, 800);
		myFrame.setLocation(400, 100);
		myFrame.setVisible(true);
		

	}

	public static void makePolygons() {
		// Build an array of random points in the unit square
		for (int i = 0; i < numPoints; i++) {
			x[i] = Math.random();
			y[i] = Math.random();// Sample program
			B[i] = i; // default permutation
		}


		createSimplePolygon();
		triangulationChords = createTriangulation();
		
		
	}

	/*
	 * Creates a simply polygon by iteratively removing crossings.
	 * Total polygon perimeter is a monovariant that decreases with
	 * each step of the algorithm, proving that the process will
	 * terminate in a a crossing-free polygon.
	 */
	public static void createSimplePolygon() {
		// Initialize the C[] array with the identity permutation
		for (int i = 0; i < numPoints; i++)
			C[i] = i;

		// Find crossings, and eliminate them
		boolean hasCrossing = true;
		while (hasCrossing) {
			hasCrossing = false;
			for (int i = 0; (i < numPoints - 2); i++){
				for (int j = i + 2; j < numPoints; j++){
					if (crosses(x[C[i]], y[C[i]], x[C[i + 1]], y[C[i + 1]],
							x[C[j]], y[C[j]], x[C[(j + 1) % numPoints]],
							y[C[(j + 1) % numPoints]])) {
						hasCrossing = true;
						int k = i + 1;
						int l = j;
						while (l > k) {
							int temp = C[k];
							C[k] = C[l];
							C[l] = temp;
							l--;
							k++;
						}
					}
				}
			}
		}
	}


	/**
	 * YOUR HOMEWORK HERE
	 *
	 * This finds a triangulation
	 */
	public static ArrayList<int[]> createTriangulation(){

		HashMap<Point, Integer> rmap = new HashMap<>();
		ArrayList<int[]> triChords = new ArrayList<int[]>();
		for(int i=0; i<numPoints;i++) {
			points.add(new Point(x[C[i]], y[C[i]]));
			rmap.put(points.get(i), C[i]);
		}
		triangulate(0);

		for(Point[] ch: chordArray){
			int[] arr = {rmap.get(ch[0]), rmap.get(ch[1])};
			triChords.add(arr);
		}
		return triChords;
	}




	/**
	 * Decides whether the chord from point s to e enters the polygon P as it leaves vertex x
	 * @param P  The array giving the order of the simple polygon's vertices
	 * @param s  the start index of the chord, in the array P
	 * @param e  the end index of the chord, in the array P
	 * @param n  the number of sides of the polygon
	 * @return  true if the chord enters P as it leaves s. 
	 * 
	 * Note that the polygon is assumed to be given in counter-clockwise order.
	 */
	public static boolean chordInside(int[] P, int s, int e, int n){
		int before = (s + n - 1) % n;
		int after  = (s + 1) % n;
		if(sigma(x[P[before]], y[P[before]], x[P[s]], y[P[s]], x[P[after]], y[P[after]]) > 0){
			if(sigma(x[P[before]], y[P[before]], x[P[s]], y[P[s]], x[P[e]], y[P[e]]) > 0 &&
					sigma(x[P[e]], y[P[e]], x[P[s]], y[P[s]], x[P[after]], y[P[after]]) > 0)
				return true;
			else 
				return false;
		} else {
			if(sigma(x[P[before]], y[P[before]], x[P[s]], y[P[s]], x[P[e]], y[P[e]]) > 0 ||
					sigma(x[P[e]], y[P[e]], x[P[s]], y[P[s]], x[P[after]], y[P[after]]) > 0)
				return true;
			else 
				return false;
		}
	}


	/**
	 * Decides whether the chord from point s to e crosses any edges of P
	 * @param P  The array giving the order of the simple polygon's vertices
	 * @param s  the start index of the chord, in the array P
	 * @param e  the end index of the chord, in the array P
	 * @param n  the number of points on the polygon
	 * @return  true if the chord enters P as it leaves s.
	 */
	public static boolean chordCrossesBoundary(int[] P, int s, int e, int n){
		for(int i = 0; i < n; i++){
			if(crosses(x[P[i]], y[P[i]], x[P[(i+1)%n]], y[P[(i+1)%n]], x[P[s]], y[P[s]], x[P[e]], y[P[e]]))
				return true;
		}
		return false;
	}



	/**
	 * Finds whether traveling A->B->C makes a right- or left-turn
	 * @param Ax coordinates of the point A
	 * @param Ay
	 * @param Bx coordinates of the point B
	 * @param By
	 * @param Cx coordinates of the point C
	 * @param Cy
	 * @return  sigma(  (Ax, Ay),  (Bx, By),  (Cx, Cy)  ), which is in the set {-1, 0, 1} for {right, collinear, left} resp.
	 */
	public static int sigma(double Ax, double Ay, double Bx, double By, double Cx, double Cy){
		double det = ((Bx-Ax)*(Cy-Ay) - (By-Ay)*(Cx-Ax));
		if(det < 0)
			return -1;
		else if(det > 0)
			return 1;
		else 
			return 0; 
	}


	/**
	 * Returns the signed area (half the sum of the 2x2 determinants) of the polygon described by P 
	 * @param P Array of indices into the x[] and y[] arrays, giving a simple polygon
	 * @param n Number of points in the polygon, in case its not all of the points
	 * @return  the area of the polygon. Positive if the points are traversed counter-clockwise, 
	 * otherwise negative. 
	 */
	public static double signedArea(int[] P, int n){
		double rv = 0;
		for(int i = 0; i < n; i++)
			rv += x[P[i]]*y[P[(i+1)%n]] - x[P[(i+1)%n]]*y[P[i]];
		return rv/2;
	}

	/**
	 * Returns the sum of the lengths of two segments sharing a common point
	 * @param i the index into the x[] and y[] arrays of the first point
	 * @param j the index into the x[] and y[] arrays of the second point
	 * @param k the index into the x[] and y[] arrays of the third point
	 * @return distance(i, j) + distance(j, k)
	 */
	public static double dis2(int i, int j, int k) {
		return Math.sqrt((x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j])
				* (y[i] - y[j]))
				+ Math.sqrt((x[k] - x[j]) * (x[k] - x[j]) + (y[k] - y[j])
						* (y[k] - y[j]));
	}

	/**
	 * Returns the distance between two points
	 * @param i the index into the x[] and y[] arrays of the first point
	 * @param j the index into the x[] and y[] arrays of the second point
	 * @return the distance between the two points
	 */
	public static double dis(int i, int j) {
		return Math.sqrt((x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j])
				* (y[i] - y[j]));
	}

	/*
	 * Naive crosses method. Assumes points are in general position, which
	 * is good enough for this project.
	 * Returns true if (a,b)-(c,d) crosses (e,f)-(g,h)
	 */
	public static boolean crosses(double a, double b, double c, double d,
			double e, double f, double g, double h) {
		double det1a = (c - a) * (f - b) - (e - a) * (d - b);
		double det1b = (c - a) * (h - b) - (g - a) * (d - b);
		double det2a = (e - g) * (b - h) - (a - g) * (f - h);
		double det2b = (e - g) * (d - h) - (c - g) * (f - h);
		return ((det1a * det1b < 0) && (det2a * det2b < 0));
	}


	/**
	 * If the polygon P is traversed counter-clockwise, it leaves it alone, and otherwise reverses it.
	 * @param P  The indices of the polygon, assumed to be simple
	 */
	public static void makeCounterclockwise(int[] P, int n){
		if(signedArea(P, n) < 0){ // reverse the indices
			for(int i = 0; i < n/2; i++){
				int tmp = P[i];
				P[i] = P[n-1-i];
				P[n-1-i] = tmp;
			}
		}
	}


	/**
	 * The frame that displays this application's GUI.
	 *
	 */
	public static class SimpleFrame extends JFrame {
		public static JSlider numPointsSlider;

		public SimpleFrame() {
			super("Triangulate a Simple Polygon");
			Container content = getContentPane();
			content.setLayout(new java.awt.BorderLayout());
			JTabbedPane tabbedPane = new JTabbedPane();
			tabbedPane.setPreferredSize(new java.awt.Dimension(300, 400));
			tabbedPane.addTab("Simple", new SimplePanel());
			tabbedPane.addTab("Triangulation", new TriangulationPanel());
			content.add(tabbedPane, java.awt.BorderLayout.CENTER);

			// Slider for the number of points
			numPointsSlider = new JSlider(
					javax.swing.SwingConstants.HORIZONTAL, 3, 180, numPoints);
			numPointsSlider
			.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(
						javax.swing.event.ChangeEvent evt) {
					numPointsSliderStateChanged(evt);
				}
			});
			content.add(numPointsSlider, java.awt.BorderLayout.SOUTH);
		}

		private void numPointsSliderStateChanged(
				javax.swing.event.ChangeEvent evt) {
			numPoints = numPointsSlider.getValue();
			points.clear();
			chords.clear();
			chordArray.clear();
			//rmap.clear();

			makePolygons();
			repaint();
		}
	}



	/**
	 * Displays a simple polygon on the point set.
	 *
	 */
	public static class SimplePanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			// First set the scaling to fit the window
			Dimension size = getSize();
			int Xwid = (int) (0.95 * size.width);
			int Ywid = (int) (0.95 * size.height);

			// First draw the segments
			g2.setColor(Color.black);
			for (int i = 0; i < numPoints; i++) {
				if (i == 0)
					g2.setColor(Color.black);
				if (i == 1)
					g2.setColor(Color.black);
				if (i > 1)
					g2.setColor(Color.black);
				g2.drawLine((int) (Xwid * x[C[i]]), size.height - (int) (Ywid * y[C[i]]),
						(int) (Xwid * x[C[(i + 1) % numPoints]]),
						size.height - (int) (Ywid * y[C[(i + 1) % numPoints]]));
			}
			// Now draw the points
			for (int i = 0; i < numPoints; i++) {
				g2.fillRect((int) (Xwid * x[i]) - POINTWID, size.height - (int) (Ywid * y[i])
						- POINTWID, 2 * POINTWID + 1, 2 * POINTWID + 1);
			}
		}
	}



	/**
	 * Displays the very simple polygon decomposed into convex polygons
	 *
	 */
	public static class TriangulationPanel extends JPanel{
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			// First set the scaling to fit the window
			Dimension size = getSize();
			int Xwid = (int) (0.95 * size.width);
			int Ywid = (int) (0.95 * size.height);

			// First draw the segments from the C[] array, 
			// corresponding to the  simple polygon.
			g2.setColor(Color.black);
			for (int i = 0; i < numPoints; i++) {
				g2.drawLine((int) (Xwid * x[C[i]]), size.height - (int) (Ywid * y[C[i]]),
						(int) (Xwid * x[C[(i + 1) % numPoints]]),
						size.height - (int) (Ywid * y[C[(i + 1) % numPoints]]));
			}
			// Now draw the points
			for (int i = 0; i < numPoints; i++) {
				g2.fillRect((int) (Xwid * x[i]) - POINTWID, size.height - (int) (Ywid * y[i])
						- POINTWID, 2 * POINTWID + 1, 2 * POINTWID + 1);
			}

			// Now draw the chords found in our createTriangulation function
			g2.setColor(Color.blue);
			for(int i = 0; i < numPoints - 3; i++){
				int[] chord = triangulationChords.get(i);
				g2.drawLine((int) (Xwid * x[chord[0]]), size.height - (int) (Ywid * y[chord[0]]),
						(int) (Xwid * x[chord[1]]),
						size.height - (int) (Ywid * y[chord[1]]));
			}
		}
	}

	/**
	 * Fixes the number to a correct index for the points list
	 * @param i the number given
	 * @return fixed number to be index for the points list
	 */
	static int i(int i){
		return i%points.size();
	}

	/**
	 * Checks if a chord to be used in a triangulation is valid or not
	 * @param ind The index of the first point from which the chord extend (the index of the second is ind+2)
	 * @return true if the chord from ind -> ind+2 crosses any other chord of triangulation or edge, otherwise, return false
	 */
	static boolean intersects(int ind) {
		for(int i=0;i<points.size()-4;i++) {
			if(intersects(points.get(i(ind)), points.get(i(ind+2)), points.get(i(ind+3+i)), points.get(i(ind+4+i)))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if two line segments intersect or not
	 * @param a Point 1, segment 1
	 * @param b Point 2, segment 1
	 * @param c Point 1, segment 2
	 * @param d Point 2, segment 2
	 * @return true if the two segments (a-b and c-d) intersect, otherwise return false
	 */
	static boolean intersects(Point a, Point b, Point c, Point d) {
		if(sigma(a,b,c)!=sigma(a,b,d) && sigma(c,d,a)!=sigma(c,d,b))
			return true;
		else
			return false;
	}

	/**
	 * Returns the sigma of three points
	 * @param a Point 1
	 * @param b Point 2
	 * @param c Point 3
	 * @return 1 if a-b-c is CCW turn, 0 if they are linear, or -1 if a-b-c makes a CW turn
	 */
	static double sigma(Point a, Point b, Point c) {
		if(((b.x-a.x)*(c.y-a.y) - (c.x-a.x)*(b.y-a.y))==0)
			return 0;
		else
			return ((b.x-a.x)*(c.y-a.y) - (c.x-a.x)*(b.y-a.y))>0?1:-1;
	}

	/**
	 * Calculates the area of a polygon described by the ordered point set, pts
	 * @param pts The points set (ordered) making the polygon
	 * @return Area of the polygon
	 */
	static double area(ArrayList<Point> pts){
		double area = 0;
		for(int i=0;i<pts.size();i++){
			if(i!=pts.size()-1)
				area+=pts.get(i).x*pts.get(i+1).y - pts.get(i).y*pts.get(i+1).x;
			else
				area+=pts.get(i).x*pts.get(0).y - pts.get(i).y*pts.get(0).x;
		}
		area = Math.abs(area)/2;
		return area;
	}

	// list of the chords for triangulation
	static ArrayList<Chord> chords = new ArrayList<>();
	// points: updated 
	static ArrayList<Point> points = new ArrayList<>();
	// reverse mapping (point to its original place in the x,y arrays)
	
	// to hold chords making up the triangulation
	static ArrayList<Point[]> chordArray = new ArrayList<>();
	
	
//	static void fillPoints() {
//		for(int i=0; i<numPoints;i++) {
//			points.add(new Point(x[C[i]], y[C[i]]));
//			rmap.put(points.get(i), C[i]);
//		}
//	}


	// POINT Class
	public static class Point{
		double x,y;
		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}

	// CHORD Class
	public static class Chord{
		double x1,y1,x2,y2;
		public Chord(Point x, Point y) {
			this.x1 = x.x;
			this.x2 = y.x;
			this.y1 = x.y;
			this.y2 = y.y;
		}
	}

	static ArrayList<Point> copy = new ArrayList<>();;

/**
 * Fills up the chords list for triangulation
 * @param i Starting index for triangulation
 */
	static void triangulate(int i) {
	    			points.clear();
			chords.clear();
			chordArray.clear();
			//rmap.clear();
		copy.clear();
		if(points.size()==3)
			return;
		else {
			for(int f=0;f<points.size();f++) {
				copy.add(points.get(f));
			}
			copy.remove(i(i+1));
			if(area(copy)<area(points) && !intersects(i)){
				chords.add(new Chord(points.get((i)), points.get(i(i+2))));
				Point[] arr = {points.get((i)),points.get(i(i+2))};
				chordArray.add(arr);
				points.remove(i(i+1));
			}
			triangulate(i(i+1));
			return;
		}
	}
}
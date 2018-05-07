import java.io.*;
class Triangulate {
	static class Point3D {
		double x,y,z;

		public Point3D(double xx, double yy, double zz){
			x = xx;
			y = yy;
			z = zz;
		}

	}

	public static void createSTL() throws IOException{
		File file = new File ("STL.txt");
		PrintWriter printWriter = new PrintWriter (file);
		Point3D p1 = new Point3D(0.0,0.0,0.0);
		Point3D p2 = new Point3D(100.0,0.0,0.0);
		Point3D p3 = new Point3D(100.0,100.0,0.0);
		Point3D p4 = new Point3D(0.0,100.0,0.0);
		Point3D p5 = new Point3D(0.0,0.0,50.0);
		Point3D p6 = new Point3D(100.0,0.0,50.0);
		Point3D p7 = new Point3D(100.0,100.0,50.0);
		Point3D p8 = new Point3D(0.0,100.0,50.0);

		Point3D[] face1 = {p1,p2,p3,p4};
		Point3D[] face2 = {p5,p6,p7,p8};

		printWriter.println("solid Minkowski");

		for(int i=0;i<face1.length;i++){
			if(i!=face1.length-1){
				Point3D P = face1[i];
				Point3D q = face1[i+1];
				Point3D r = face2[i];
				Point3D Q = new Point3D(q.x-P.x, q.y-P.y, q.z-P.z);
				Point3D R = new Point3D(r.x-P.x, r.y-P.y, r.z-P.z);
				Point3D n = new Point3D(Q.y*R.z - Q.z*R.y,
					-(Q.x*R.z - Q.z*R.x),
					Q.x*R.y - Q.y*R.x);
				double nLength = Math.sqrt(n.x*n.x + n.y*n.y + n.z*n.z);
				Point3D N = new Point3D(n.x/nLength, n.y/nLength, n.z/nLength);


				printWriter.println("  facet normal " + n.x + " " + n.y + " " + n.z);
				printWriter.println("    outer loop");
				printWriter.println("      vertex " + P.x + " " + P.y + " " + P.z);
				printWriter.println("      vertex " + q.x + " " + q.y + " " + q.z);
				printWriter.println("      vertex " + r.x + " " + r.y + " " + r.z);
				printWriter.println("    endloop");
				printWriter.println("  endfacet");

				P = face2[i];
				q = face2[i+1];
				r = face1[i+1];

				Q = new Point3D(q.x-P.x, q.y-P.y, q.z-P.z);
				R = new Point3D(r.x-P.x, r.y-P.y, r.z-P.z);
				n = new Point3D(Q.y*R.z - Q.z*R.y,
					-(Q.x*R.z - Q.z*R.x),
					Q.x*R.y - Q.y*R.x);
				nLength = Math.sqrt(n.x*n.x + n.y*n.y + n.z*n.z);
				N = new Point3D(n.x/nLength, n.y/nLength, n.z/nLength);


				printWriter.println("  facet normal " + n.x + " " + n.y + " " + n.z);
				printWriter.println("    outer loop");
				printWriter.println("      vertex " + P.x + " " + P.y + " " + P.z);
				printWriter.println("      vertex " + q.x + " " + q.y + " " + q.z);
				printWriter.println("      vertex " + r.x + " " + r.y + " " + r.z);
				printWriter.println("    endloop");
				printWriter.println("  endfacet");
			}
			else{
				Point3D P = face1[i];
				Point3D q = face1[0];
				Point3D r = face2[i];
				Point3D Q = new Point3D(q.x-P.x, q.y-P.y, q.z-P.z);
				Point3D R = new Point3D(r.x-P.x, r.y-P.y, r.z-P.z);
				Point3D n = new Point3D(Q.y*R.z - Q.z*R.y,
					-(Q.x*R.z - Q.z*R.x),
					Q.x*R.y - Q.y*R.x);
				double nLength = Math.sqrt(n.x*n.x + n.y*n.y + n.z*n.z);
				Point3D N = new Point3D(n.x/nLength, n.y/nLength, n.z/nLength);


				printWriter.println("  facet normal " + n.x + " " + n.y + " " + n.z);
				printWriter.println("    outer loop");
				printWriter.println("      vertex " + P.x + " " + P.y + " " + P.z);
				printWriter.println("      vertex " + q.x + " " + q.y + " " + q.z);
				printWriter.println("      vertex " + r.x + " " + r.y + " " + r.z);
				printWriter.println("    endloop");
				printWriter.println("  endfacet");

				P = face2[i];
				q = face2[0];
				r = face1[0];

				Q = new Point3D(q.x-P.x, q.y-P.y, q.z-P.z);
				R = new Point3D(r.x-P.x, r.y-P.y, r.z-P.z);
				n = new Point3D(Q.y*R.z - Q.z*R.y,
					-(Q.x*R.z - Q.z*R.x),
					Q.x*R.y - Q.y*R.x);
				nLength = Math.sqrt(n.x*n.x + n.y*n.y + n.z*n.z);
				N = new Point3D(n.x/nLength, n.y/nLength, n.z/nLength);


				printWriter.println("  facet normal " + n.x + " " + n.y + " " + n.z);
				printWriter.println("    outer loop");
				printWriter.println("      vertex " + P.x + " " + P.y + " " + P.z);
				printWriter.println("      vertex " + q.x + " " + q.y + " " + q.z);
				printWriter.println("      vertex " + r.x + " " + r.y + " " + r.z);
				printWriter.println("    endloop");
				printWriter.println("  endfacet");
			}





		}













		printWriter.println ("endsolid");
		printWriter.close (); 
	}

	public static void main(String[] args) throws IOException{
		createSTL();
	}
}
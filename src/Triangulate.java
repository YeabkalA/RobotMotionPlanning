import java.io.*;
public class Triangulate {

	static File file;
	static class Point3D {
		double x,y,z;

		public Point3D(double xx, double yy, double zz){
			x = xx;
			y = yy;
			z = zz;
		}

	}

	public static void createSTL(Point[] f1, Point[] f2, double l1, double l2) throws IOException{
		file = Stage.file;

		Point3D[] face1 = new Point3D[f1.length];

		Point3D[] face2 = new Point3D[f2.length];

		for(int i=0;i<f1.length;i++){
			face1[i] = new Point3D(f1[i].getX(), f1[i].getY(), l1);
		}

		for(int i=0;i<f1.length;i++){
			face2[i] = new Point3D(f2[i].getX(), f2[i].getY(), l2);
		}

		

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


				Stage.printWriter.println("  facet normal " + n.x + " " + n.y + " " + n.z);
				Stage.printWriter.println("    outer loop");
				Stage.printWriter.println("      vertex " + P.x + " " + P.y + " " + P.z);
				Stage.printWriter.println("      vertex " + q.x + " " + q.y + " " + q.z);
				Stage.printWriter.println("      vertex " + r.x + " " + r.y + " " + r.z);
				Stage.printWriter.println("    endloop");
				Stage.printWriter.println("  endfacet");

				P = face2[i];
				q = face1[i+1];
				r = face2[i+1];

				Q = new Point3D(q.x-P.x, q.y-P.y, q.z-P.z);
				R = new Point3D(r.x-P.x, r.y-P.y, r.z-P.z);
				n = new Point3D(Q.y*R.z - Q.z*R.y,
					-(Q.x*R.z - Q.z*R.x),
					Q.x*R.y - Q.y*R.x);
				nLength = Math.sqrt(n.x*n.x + n.y*n.y + n.z*n.z);
				N = new Point3D(n.x/nLength, n.y/nLength, n.z/nLength);


				Stage.printWriter.println("  facet normal " + n.x + " " + n.y + " " + n.z);
				Stage.printWriter.println("    outer loop");
				Stage.printWriter.println("      vertex " + P.x + " " + P.y + " " + P.z);
				Stage.printWriter.println("      vertex " + q.x + " " + q.y + " " + q.z);
				Stage.printWriter.println("      vertex " + r.x + " " + r.y + " " + r.z);
				Stage.printWriter.println("    endloop");
				Stage.printWriter.println("  endfacet");
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


				Stage.printWriter.println("  facet normal " + n.x + " " + n.y + " " + n.z);
				Stage.printWriter.println("    outer loop");
				Stage.printWriter.println("      vertex " + P.x + " " + P.y + " " + P.z);
				Stage.printWriter.println("      vertex " + q.x + " " + q.y + " " + q.z);
				Stage.printWriter.println("      vertex " + r.x + " " + r.y + " " + r.z);
				Stage.printWriter.println("    endloop");
				Stage.printWriter.println("  endfacet");

				P = face2[i];
				q = face1[0];
				r = face2[0];

				Q = new Point3D(q.x-P.x, q.y-P.y, q.z-P.z);
				R = new Point3D(r.x-P.x, r.y-P.y, r.z-P.z);
				n = new Point3D(Q.y*R.z - Q.z*R.y,
					-(Q.x*R.z - Q.z*R.x),
					Q.x*R.y - Q.y*R.x);
				nLength = Math.sqrt(n.x*n.x + n.y*n.y + n.z*n.z);
				N = new Point3D(n.x/nLength, n.y/nLength, n.z/nLength);


				Stage.printWriter.println("  facet normal " + n.x + " " + n.y + " " + n.z);
				Stage.printWriter.println("    outer loop");
				Stage.printWriter.println("      vertex " + P.x + " " + P.y + " " + P.z);
				Stage.printWriter.println("      vertex " + q.x + " " + q.y + " " + q.z);
				Stage.printWriter.println("      vertex " + r.x + " " + r.y + " " + r.z);
				Stage.printWriter.println("    endloop");
				Stage.printWriter.println("  endfacet");
			}





		}













		Stage.printWriter.println ("endsolid");
		Stage.printWriter.close (); 
	}

	public static void main(String[] args) throws IOException{
		//createSTL();

	}
}
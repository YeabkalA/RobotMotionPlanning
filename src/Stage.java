import java.util.*;
import java.io.*;

public class Stage {

	static File file;
	static PrintWriter printWriter;
	public ArrayList<Polygon> figures;

	public Stage() {
		this.figures = new ArrayList<Polygon>();
	}

	void add(Polygon p){
		this.figures.add(p);
	}



	public static boolean createMainSTL(HashMap<Double, Stage> levels, String directory, boolean stage) throws IOException {
		System.out.println("Printing!!!!!!");

		file = new File("../STLs/"+directory);

		double[] lowerBase = Arrays.stream("0 0 1000 0 1000 1000 0 1000".split(" ")).mapToDouble(Double::parseDouble).toArray();
		Polygon base = new Polygon(lowerBase);

		printWriter = new PrintWriter(file);
		printWriter.println("solid minkowski");

		if(stage) Triangulate.createSTL(base.toPtArr(),base.toPtArr(),0,360);

		for(double i=0.0;i<360.0;i+=5.0){
			Stage s1 = levels.get(i);
			Stage s2 = levels.get(i+5);

			for(int j=0;j<s1.figures.size()-1;j++){
				Point[] p1 = s1.figures.get(j).toPtArr();
				Point[] p2 = s2.figures.get(j).toPtArr();
				Triangulate.createSTL(p1,p2,i,i+5);
			}
		}
		
		printWriter.println ("endsolid");
		printWriter.close();

		return true;
	}



}
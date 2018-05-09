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



	public static void createMainSTL(HashMap<Double, Stage> levels) throws IOException{
		file = new File("src/Main4.STL");

		// create the stage


		double[] lowerBase = Arrays.stream("0 0 10000 0 10000 10000 0 10000".split(" ")).mapToDouble(Double::parseDouble).toArray();
		Polygon base = new Polygon(lowerBase);

		



		printWriter = new PrintWriter(file);
		printWriter.println("solid minkowski");

		//Triangulate.createSTL(base.toPtArr(),base.toPtArr(),0,360);
		for(double i=0.0;i<360.0;i+=5.0){
			Stage s1 = levels.get(i);
			Stage s2 = levels.get(i+5);

			System.out.println(s1.figures.size() + "<<<<<<<<<<<<");

			for(int j=0;j<s1.figures.size()-1;j++){
				Point[] p1 = s1.figures.get(j).toPtArr();
				//System.out.println(Arrays.toString(p1));
				Point[] p2 = s2.figures.get(j).toPtArr();
				//System.out.println(Arrays.toString(p2));
				Triangulate.createSTL(p1,p2,i,i+5);
				System.out.println("******************************");
			}


		}
		
		

		//printWriter.println ("endsolid");
		printWriter.close();
	}

}
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
		file = new File("Main.STL");
		printWriter = new PrintWriter(file);
		printWriter.println("solid minkowski");
		for(double i=5.0;i<360.0;i+=5){
			Stage s1 = levels.get(i);
			Stage s2 = levels.get(i+5);

			System.out.println(s1.figures.size() + "<<<<<<<<<<<<");

			for(int j=0;j<s1.figures.size()-1;j++){
				Point[] p1 = s1.figures.get(j).toPtArr();
				System.out.println(Arrays.toString(p1));
				Point[] p2 = s2.figures.get(j).toPtArr();
				System.out.println(Arrays.toString(p2));
				Triangulate.createSTL(p1,p2,i,i+5);
				System.out.println("******************************");
			}


		}

		printWriter.println ("endsolid");
		printWriter.close();
	}

}
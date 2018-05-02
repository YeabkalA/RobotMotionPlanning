import java.util.*;

/**
 * Polygon
 * described as list of vectors making up the polygon
 */
public class Polygon{

	private ArrayList<Vector> vectors = new ArrayList<>();

    private Point origin;

    public void setOrigin() {
        origin = new Point(vectors.get(0).getx1(), vectors.get(0).gety1());
    }

    public Point getOrigin() {
        return origin;
    }

	public void addVector(Vector v){
		this.vectors.add(v);
	}

    public ArrayList<Vector> getVectors() {
        return vectors;
    }

	public Polygon(){
	}


	public void add(Vector a){
		this.vectors.add(a);
	}

    /**
     * @param vertices the array of {x1,y1,x2,y2,...} values making up the polygon
     *Constructs Polygon from array of x,y coordinates of its vertices
     */
    public Polygon(double[] vertices){
    	for(int i=0;i<vertices.length-3;i+=2){
    		double x1 = vertices[i];
    		double y1 = vertices[i+1];
    		double x2 = vertices[i+2];
    		double y2 = vertices[i+3];
    		Vector vec = new Vector(x1,y1,x2,y2);
    		this.vectors.add(vec);
    	}
    	this.vectors.add(new Vector(vertices[vertices.length-2],vertices[vertices.length-1],vertices[0],vertices[1]));
        setOrigin();
    }

    public Polygon(ArrayList<Vector> vectors) {
        this.vectors = vectors;
    }

    /**
     * Prints the polygon 
     */
    public void print(){
    	for(Vector v: this.vectors){
    		System.out.println("From " + v.x1 + "," + v.y1 + " to " + v.x2 + "," + v.y2);
    	}
    }

    public void translate(Point o) {

        ArrayList<Vector> origVecs = getVectors();
        for(Vector v: origVecs) v.translate(o.getX(),o.getY());
        this.vectors = origVecs;

    }

    public void rotate(int degree){
    //.........................
    }

    /**
     * @return the position vectors derived from the vectors making up the polygon
     */
    public Vector[] getPosVecs(){
    	Vector[] ret = new Vector[this.vectors.size()];
    	int count = 0;
    	for(Vector v: this.vectors){
    		ret[count] = v.getPosVector();
    		count++;
    	}
    	return ret;
    }

    /**
     * @param p polygon with whom this is calculated for Minkowski sum
     * @return the Minkowski sum of this with p
     */
    
    public Polygon getMinkowski(Polygon p){
    	Vector[] posVecs = this.getPosVecs();
    	Vector[] posSec = p.getPosVecs();

    	Vector[] posVectorsMink = new Vector[posVecs.length + posSec.length];
    	for(int i=0;i<posVecs.length;i++) posVectorsMink[i] = posVecs[i];
    		int count = posVecs.length;
    	for(int i=0;i<posSec.length;i++){
    		posVectorsMink[count] = posSec[i];
    		count++;
    	}

    	//Arrays.sort(posVectorsMink,Comparator.comparing(s->s.getAngle()));
    	Arrays.sort(posVectorsMink);
    	for(Vector v: posVectorsMink) v.print();
    		System.out.println("End of check");

    	int fir=0,sec=0;

    	Polygon MinkowskiSum = new Polygon();
    	MinkowskiSum.addVector(posVectorsMink[0]);
    	for(int i=1;i<posVectorsMink.length;i++){
    		Vector previous = MinkowskiSum.getVectors().get(i-1);
    		MinkowskiSum.add(new Vector(previous.getx2(), previous.gety2(),previous.getx2()+posVectorsMink[i].getX(), previous.gety2()+posVectorsMink[i].getY()));
    	}
    	return MinkowskiSum;
    }

    public static void main(String[] args){
    	Scanner scan = new Scanner(System.in);
    	double[] arr1 = Arrays.stream(scan.nextLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
    	double[] arr2 = Arrays.stream(scan.nextLine().split(" ")).mapToDouble(Double::parseDouble).toArray();

    	Polygon pol1 = new Polygon(arr1);
    	Polygon pol2 = new Polygon(arr2);
    	Polygon ms = pol1.getMinkowski(pol2);

    	ms.print();







    }
}


import java.util.*;
class Minkowski{
    static Scanner scan = new Scanner(System.in);

    /*Polygon
      Defined by the vectors of its edge
    */
    static class Polygon{
	
	private ArrayList<Vector> vectors = new ArrayList<>();
	
	public void addVector(Vector v){
	    this.vectors.add(v);
	}
	
	public Polygon(){
	}

	public ArrayList<Vector> getVectors(){
	    return this.vectors;
	}

	public void add(Vector v){
	    this.vectors.add(v);
	}

	/*
	  Construct Polygon from array of x,y coordinates of its vertices
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
	}

	
	public void print(){
	    for(Vector v: this.vectors){
		System.out.println("From " + v.x1 + "," + v.y1 + " to " + v.x2 + "," + v.y2);
	    }
	}

	public Vector[] getPosVecs(){
	    Vector[] ret = new Vector[this.vectors.size()];
	    int count = 0;
	    for(Vector v: this.vectors){
		ret[count] = v.getPosVector();
		count++;
	    }
	    return ret;
	}

	
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

	    Arrays.sort(posVectorsMink,Comparator.comparing(s->s.getAngle()));
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
    }
	    
    
    static class Vector{
	double x1,y1,x2,y2;
	private double x,y;

	public double getx1(){
	    return this.x1;
	}
	public double getx2(){
	    return this.x2;
	}
	public double gety1(){
	    return this.y1;
	}
	public double gety2(){
	    return this.y2;
	}

	public double getX(){
	    return this.x2-this.x1;
	}
	public double getY(){
	    return this.y2-this.y1;
	}

	public boolean lessThan(Vector v2){
	    Vector v1 = this;
	    return v1.getAngle()<v2.getAngle();
	}

	public double getAngle(){
	    double x = this.getX();
	    double y = this.getY();
	    double angle = (double)Math.toDegrees(Math.atan(y*1.0/x));
	    switch(this.getQuadrant()){
	    case 2:
		angle = 180-angle;
		break;
	    case 3:
		angle = 180+angle;
		break;
	    case 4:
		angle = 360-angle;
		break;
	    }
	    return angle;
		
	}

	public int getQuadrant(){
	    Vector myPos = this.getPosVector();
	    return myPos.y2>0?(myPos.x2>0?1:3):(myPos.x2>0?4:1);
	}
	    


	public double slope(){
	    return this.getY()/this.getX();
	}
	
	public Vector(double xCord1, double yCord1, double xCord2, double yCord2){
	    this.x1 = xCord1;
	    this.y1 = yCord1;
	    this.x2 = xCord2;
	    this.y2 = yCord2;
	}
	public Vector getPosVector(){
	    return new Vector(0,0,this.x2-this.x1, this.y2-this.y1);
	}
	public void print(){
	    System.out.printf("(%f,%f)-->(%f,%f)\n",this.x1,this.y1,this.x2,this.y2);
	}
    }

    static boolean test() throws Exception{
	String inp = scan.nextLine();
	double[] vertices = Arrays.stream(inp.split(" ")).mapToDouble(Double::parseDouble).toArray();
	if(vertices.length%2!=0 || vertices.length<6) throw new Exception();
	System.out.println(Arrays.toString(vertices));
	Polygon pol = new Polygon(vertices);
	pol.print();
	return true;
    }

    static void testMinkowski(){
	String inp = scan.nextLine();
	double[] vertices1 = Arrays.stream(inp.split(" ")).mapToDouble(Double::parseDouble).toArray();
	double[] vertices2 = Arrays.stream(inp.split(" ")).mapToDouble(Double::parseDouble).toArray();
	
	if(vertices1.length%2!=0 || vertices1.length<6 || vertices2.length%2!=0 || vertices2.length<6) return;
	Polygon pol1 = new Polygon(vertices1), pol2 =  new Polygon(vertices2);
	pol1.getMinkowski(pol2).print();
    }

    public static void main(String[] args){
	try{
	    test();
	}
	catch (Exception e){
	}

	testMinkowski();
    }
}
    

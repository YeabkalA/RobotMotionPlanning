import java.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * Polygon
 * described as list of vectors making up the polygon
 */
public class Polygon{

	ArrayList<Vector> vectors = new ArrayList<>();

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

public static Polygon rotatePolygon(Polygon p, double deg){
    p.setOrigin();
    Point pointOfRotation = p.getOrigin();
    ArrayList<Point> pts = new ArrayList<>();
    pts.add(pointOfRotation);
    ArrayList<Vector> vectors = p.getVectors();
    for(int i=0;i<vectors.size()-1;i++){
        Vector vec = vectors.get(i);
        pts.add(new Point(vec.getx2(), vec.gety2()));
    }
    Point[] rotatedPoints = new Point[pts.size()];
    for(int i=0;i<pts.size();i++){
        rotatedPoints[i] = rotate(p.getOrigin(), pts.get(i), deg);
    }
    ArrayList<Vector> newVectors = new ArrayList<>();

    for(int i=0;i<rotatedPoints.length-1;i++){
        newVectors.add(new Vector(rotatedPoints[i].getX(), rotatedPoints[i].getY(), rotatedPoints[i+1].getX(), rotatedPoints[i+1].getY()));
    }
    newVectors.add(new Vector(rotatedPoints[rotatedPoints.length-1].getX(), rotatedPoints[rotatedPoints.length-1].getY(), rotatedPoints[0].getX(), rotatedPoints[0].getY()));
    return new Polygon(newVectors);

}

public static Point rotate(Point center , Point point, double angle) {

    angle = angle* (Math.PI/180); 
    double rotatedX = Math.cos(angle) * (point.getX() - center.getX()) - Math.sin(angle) * (point.getY()-center.getY()) + center.getX();
    double rotatedY = Math.sin(angle) * (point.getX() - center.getX()) + Math.cos(angle) * (point.getY() - center.getY()) + center.getY();
    Point rPoint = new Point(rotatedX, rotatedY);

    return rPoint;

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

    /**
     * Translates the polygon by o.x, o.y
     */
    public void translate(Point o) {
        ArrayList<Vector> origVecs = getVectors();
        for(Vector v: origVecs) v.translate(o.getX(),o.getY());
            this.vectors = origVecs;
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
    
    public Point[] toPtArr(){
        setOrigin();
        ArrayList<Point> pts = new ArrayList<>();
        pts.add(getOrigin());
        ArrayList<Vector> vectors = getVectors();
        for(int i=0;i<vectors.size()-1;i++){
            Vector vec = vectors.get(i);
            pts.add(new Point(vec.getx2(), vec.gety2()));
        }
        Point[] ret = new Point[pts.size()];

        for(int i=0;i<pts.size();i++){
            ret[i] = pts.get(i);
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

    	Arrays.sort(posVectorsMink);

    	int fir=0,sec=0;

    	Polygon MinkowskiSum = new Polygon();
    	MinkowskiSum.addVector(posVectorsMink[0]);
    	for(int i=1;i<posVectorsMink.length;i++){
    		Vector previous = MinkowskiSum.getVectors().get(i-1);
    		MinkowskiSum.add(new Vector(previous.getx2(), previous.gety2(),previous.getx2()+posVectorsMink[i].getX(), previous.gety2()+posVectorsMink[i].getY()));
    	}
    	return MinkowskiSum;
    }


    // for testing purposes, draws a polygon
    static class Drawer extends JFrame {
        JPanel panel;

        public Drawer(Polygon p) {

            setLayout(new BorderLayout());
            setBackground(Color.BLACK);

            panel = new JPanel() {
                public void paintComponent(Graphics g) {
                    Color[] colors = {Color.BLUE, Color.RED, Color.YELLOW,Color.GREEN};
                    for(double j=0;j<=360;j+=45){
                        g.setColor(new Color(((int)j+50)%255,  ((int)j+20)%255, ((int)j+100)%255));
                        Polygon q = rotatePolygon(p,j);
                        ArrayList<Vector> vectors = q.getVectors();
                        ArrayList<Point> pts = new ArrayList<>();
                        pts.add(new Point(vectors.get(0).getx1(), vectors.get(0).gety1()));
                        for(int i=0;i<vectors.size()-1;i++){
                            Vector vec = vectors.get(i);
                            pts.add(new Point(vec.getx2(), vec.gety2()));
                        }                        
                        int con = 1;

                        System.out.println("*************" + pts.size());
                        int constant = 150;
                        for(int i=0;i<pts.size()-1;i++){
                            g.drawLine(constant*(int)pts.get(i).getX(), constant*(int)pts.get(i).getY(), constant*(int)pts.get(i+1).getX(), constant*(int)pts.get(i+1).getY());
                        }

                        g.drawLine(constant*(int)pts.get(pts.size()-1).getX(), constant*(int)pts.get(pts.size()-1).getY(), constant*(int)pts.get(0).getX(), constant*(int)pts.get(0).getY());
                    }
                }
            };
            panel.setPreferredSize(new Dimension(2000,2000));
            add(panel,BorderLayout.CENTER);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.pack();
            this.setVisible(true);
        }
    }
}



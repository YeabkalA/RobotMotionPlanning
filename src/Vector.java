
/**
 * Vector
 * described by the starting and end points' coordinates
 */
public class Vector implements Comparable<Vector>{

    public void translate(double x, double y){
      this.x1 = this.x1+x;
      this.x2 = this.x2+x;
      this.y1 = this.y1+y;
      this.y2 = this.y2+y;
    }

    /**
     * @param starting and end point x and y coordinates
     * constructs a vector extending from (x1,y1)->(x2,y2)
     */
    public Vector(double xCord1, double yCord1, double xCord2, double yCord2){
       this.x1 = xCord1;
       this.y1 = yCord1;
       this.x2 = xCord2;
       this.y2 = yCord2;
   }

    // (x1,y1) - start point of the vector
    // (x2,y2) - end point of the vector
    double x1,y1,x2,y2;

    /**
     * @return x value of starting point
     */
    public double getx1(){
       return this.x1;
   }


    /**
     * @return x value of end point
     */
    public double getx2(){
       return this.x2;
   }


    /**
     * @return y value of starting point
     */
    public double gety1(){
       return this.y1;
   }


    /**
     * @return y value of end point
     */
    public double gety2(){
       return this.y2;
   }

    /**
     * @return the delta x for the vector
     */
    public double getX(){
       return this.x2-this.x1;
   }

    /**
     * @return the delta y for the vector
     */
    public double getY(){
       return this.y2-this.y1;
   }

   public void rotate(double deg){

   }

   // compares vectors by angle from +x axis
   // Dr. Hochberg helped use sigmas in making this comparing method work
   public int compareTo(Vector v){
        int result = 0;
        double dx = this.getX();
        double dy = this.getY();
        double vdx = v.getX();
        double vdy = v.getY();

        if(dy == 0 && vdy == 0){
            if(dx > 0 && vdx < 0) result = -1;
            else if(dx < 0 && vdx > 0) result =  1;
            else result =  0;
        }
        else if(dy == 0){
            if(dx > 0) result = -1;
            else if(dx < 0 && vdy > 0) result = 1;
            else if(dx < 0 && vdy < 0) result = -1;
        }
        else if(vdy == 0){
            if(vdx > 0) result = 1;
            else if(vdx < 0 && dy > 0) result = -1;
            else if(vdx < 0 && dy < 0) result = 1;
        }
        else if(vdy == 0 && vdx > 0) result = 1;
        else if(dy > 0 && vdy < 0) result =  -1;
        else if(dy < 0 && vdy > 0) result =  1;
        else if(dx * vdy - dy * vdx == 0) result = 0;
        else if(dx * vdy - dy * vdx > 0) result = -1;
        else result =  1;
        return result;
   }

    /**
     * @return returns the slope of the segment extending from the start to the end point of the vector
     */
    
    public double slope(){
       return this.getY()/this.getX();
   }


    /**
     * @return the position vector derived form the vector
     */
    public Vector getPosVector(){
       return new Vector(0,0,this.x2-this.x1, this.y2-this.y1);
   }

    /**
     * prints the vector as (starting)->(end)
     */
    public void print(){
       System.out.printf("(%f,%f)-->(%f,%f)\n",this.x1,this.y1,this.x2,this.y2);
   }
}

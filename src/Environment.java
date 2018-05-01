import java.util.*;
public class Environment {

    static Scanner scan = new Scanner(System.in);

    // the Minkowski sums of each obstacle with the robot
    Polygon robot;
    int robotSize;
    int numObstcl;
    ArrayList<Polygon> obstacles;

    void addRobotData(){
	System.out.println("NOW UPDATING ROBOT...");
	System.out.println("What is the size of your robot?");
	this.robotSize = Integer.parseInt(scan.nextLine());
	double[] vertices = Arrays.stream(scan.nextLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
	this.robot = new Polygon(vertices);	    
    }

    void rotateRobot(double degree){
	//.........................
    }
				    

    void addObstaclesData(){
	System.out.println("NOW UPDATING OBSTACLES...");
	System.out.println("How many obstacles are there on your board?");
	this.numObstcl = Integer.parseInt(scan.nextLine());
	obstacles = new ArrayList<>();
	for(int i=0;i<this.numObstcl;i++){
	    double[] vertices = Arrays.stream(scan.nextLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
	    this.obstacles.add(new Polygon(vertices));
	}
    }

    public static void main(String[] args){
	Environment env = new Environment();
	env.addRobotData();
	env.addObstaclesData();
	for(Polygon o: env.obstacles) o.print();
	HashMap<Double, Environment> levels;
    }
}

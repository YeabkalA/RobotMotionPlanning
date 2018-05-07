import java.util.*;
import java.io.*;
public class Environment {


    static Scanner scan = new Scanner(System.in);
    static HashMap<Double, Stage> levels;

    // the Minkowski sums of each obstacle with the robot
    Polygon robot;
    int robotSize;
    int numObstcl;
    ArrayList<Polygon> obstacles;
    HashMap<Polygon, Point> obstacleOriginMap;

    public Polygon getRobot() {
    	return robot;
    }

    public ArrayList<Polygon> getObstacles() {
    	return obstacles;
    }

    void addRobotData(){
		System.out.println("NOW UPDATING ROBOT...");
		System.out.println("What is the size of your robot?");
		this.robotSize = Integer.parseInt(scan.nextLine());
		double[] vertices = Arrays.stream(scan.nextLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
		this.robot = new Polygon(vertices);	    
    }


				    

    void addObstaclesData(){
    	obstacleOriginMap = new HashMap<>();
		System.out.println("NOW UPDATING OBSTACLES...");
		System.out.println("How many obstacles are there on your board?");
		this.numObstcl = Integer.parseInt(scan.nextLine());
		obstacles = new ArrayList<>();
		for(int i=0;i<this.numObstcl;i++){
		    double[] vertices = Arrays.stream(scan.nextLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
		    Polygon obstacle = new Polygon(vertices);
		    this.obstacles.add(obstacle);
		    Point origin = new Point(vertices[0], vertices[1]);
		    this.obstacleOriginMap.put(obstacle, origin);
		}
    }

    public static void buildStage(Environment env) throws IOException {
    	levels = new HashMap<>();
    	for(double i=0.0;i<=360.0;i+=5.0) {
    		Stage stage = new Stage();
    		Polygon robot = Polygon.rotatePolygon(env.getRobot(),i);
    		for(Polygon p: env.getObstacles()) {
    			Polygon minkSum = new Polygon();
    			minkSum = p.getMinkowski(robot);
    			minkSum.translate(p.getOrigin());
    			stage.figures.add(minkSum);
    		}
    		levels.put(i,stage);
    	}


        
       
    }

    public static void main(String[] args) throws IOException{

		Environment env = new Environment();
		env.addRobotData();
		env.addObstaclesData();
		buildStage(env);
		Stage.createMainSTL(levels);
		
		
    }
}

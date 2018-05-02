import java.util.*;
public class Environment {


    static Scanner scan = new Scanner(System.in);
    static HashMap<Integer, Stage> levels;

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

    static void buildStage(Environment env) {
    	levels = new HashMap<>();
    	for(int i=5;i<360;i+=5) {
    		Stage stage = new Stage();
    		Polygon robot = env.getRobot();
    		robot.rotate(i);
    		for(Polygon p: env.getObstacles()) {
    			Polygon minkSum = new Polygon();
    			minkSum = p.getMinkowski(robot);
    			minkSum.translate(p.getOrigin());
    			stage.add(minkSum);
    		}
    		levels.put(i,stage);
    	}
    }

    public static void main(String[] args){

		Environment env = new Environment();
		env.addRobotData();
		env.addObstaclesData();
		buildStage(env);
		System.out.println(levels.size());
		for(Polygon o: env.obstacles) o.print();
		
    }
}

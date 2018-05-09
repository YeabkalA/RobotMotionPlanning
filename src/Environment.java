import java.util.*;
import java.io.*;
public class Environment {


    static Scanner scan = new Scanner(System.in);
    static HashMap<Double, Stage> levels;

    // the robot!
    Polygon robot;

    // number of obstacles
    int numObstcl;

    // list of all obstacles
    ArrayList<Polygon> obstacles;

    public Polygon getRobot() {
    	return robot;
    }

    public ArrayList<Polygon> getObstacles() {
    	return obstacles;
    }

    void addRobotData(){
		System.out.println("NOW UPDATING ROBOT...");
		System.out.println("Enter the coordinates of the robot's vertices, separated by spaces.");
		double[] vertices = Arrays.stream(scan.nextLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
		this.robot = new Polygon(vertices);	    
    }


				    

    void addObstaclesData() {
		System.out.println("NOW UPDATING OBSTACLES...");
		System.out.println("How many obstacles are there on your board?");
		this.numObstcl = Integer.parseInt(scan.nextLine());
		obstacles = new ArrayList<>();
		for(int i=0;i<this.numObstcl;i++){
            System.out.printf("Now entering data for obstacle #%d\n", i+1);
		    double[] vertices = Arrays.stream(scan.nextLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
		    Polygon obstacle = new Polygon(vertices);
		    this.obstacles.add(obstacle);
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

    public static void beginDataInput() throws Exception{
        String display = "MINKOWSKI LEVELS BUILDER";
        for(int i=0;i<display.length();i++){
            Thread.sleep(100);
            System.out.print(display.charAt(i));
        }
        Thread.sleep(500);
        System.out.println("\n---------------------\n");
    }

    public static void main(String[] args) throws Exception{

		Environment env = new Environment();
        System.out.println("PLEASE ENTER THE DIRECTORY OF THE STL FILE WHERE YOU WANT YOUR FINAL RESULTS TO BE SAVED:");
        String savingDir = scan.nextLine();
        beginDataInput();
		env.addRobotData();
		env.addObstaclesData();
        System.out.println("Do you want the stage's frame to be included in the STL file? y/n?");
        String stageFrame = scan.nextLine();
		buildStage(env);
		if(stageFrame.equals("y") || stageFrame.equals("Y"))
            Stage.createMainSTL(levels, savingDir, true);
        else 
            Stage.createMainSTL(levels, savingDir, false);		
    }
}

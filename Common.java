import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/*
 * Class that can run multiple TSP solvers. Also handles output and input of result and problem.
 * Assignment 5 of STEP at Google Tokyo
 * 
 * @author Naho Kitade
 */
public class Common{

	
	/* 
	 * Reads the specified file input and creates a CityRoute object encoding the file information.
	 * @param filename file input with specified format. First line -> "x,y" followed by the x, y 
	 * coordinates of the cities to solve the TSP for.
	 * @return the CityRoute encoding the file given.
	 */
	public static CityRoute read_input(String filename) throws FileNotFoundException{
		FileReader reader = new FileReader(filename);
		Scanner in = new Scanner(reader);
		String nextLine;
		int counter = 0;
		CityRoute newRoute = new CityRoute();
		
		// skip first line.
		if(in.hasNextLine()){
			in.nextLine();
		}
		
		// read every line 
		while(in.hasNextLine()) {
			nextLine = in.nextLine();
			String[] coordinates = nextLine.split(",");
			// creates city out of info and adds it to overall route.
			City newCity = new City(Double.parseDouble(coordinates[0]), (Double.parseDouble(coordinates[1])), counter);
			newRoute.addCity(newCity);
			counter++;
		}
		return newRoute;
	}

	/*
	 * Writes out the CityRoute given to specified format output file.
	 * First line: "index", then the CityId of the CityRoute follows each consecutive line.
	 * @param fileName file name of the output file
	 * @param solvedRoute the CityRoute to output
	 * @return null
	 */
	public static void writeOutput(String fileName, CityRoute solvedRoute) throws IOException{
		FileWriter writer = new FileWriter(fileName);
		PrintWriter out = new PrintWriter(writer);

		out.println("index"); 

		for(City city : solvedRoute.getRoute()){
			out.println(city.getId());
		}

		out.flush();
		
		// always cleanup
		out.close();
		writer.close();       
	}

	/*
	 * The main function that runs all the solving algorithms written. 
	 * Comment in and out the algorithms, for different algorithms. 
	 */
	public static void main(String[] args) throws IOException{
		CityRoute newRoute = read_input("../input_5.csv");
		double routeDistance = newRoute.routeDistance();
		System.out.println(routeDistance);	
		//CityRoute solvedRoute = GreedyTSPSolver.greedySolver(newRoute);
		//CityRoute solvedRoute = RandomTSPSolver.randomSolver(newRoute);
		//CityRoute solvedRoute = GeneticTSPSolver.GeneticSolver(newRoute);
		CityRoute solvedRoute = FurthestInsersionTSPSolver.furthestInsersionMiltiSolver(newRoute);
		//CityRoute solvedRoute = FurthestInsersionTSPSolver.furthestInsersionSolver(newRoute, -1);
		//CityRoute solvedRoute = SATSPSolver.SAMultiSolver(newRoute);
		//CityRoute solvedRoute = AllPermTSPSolver.allPermSolver(newRoute);
		solvedRoute = SATSPSolver.SASolver(solvedRoute);
		System.out.println(solvedRoute);
		routeDistance = solvedRoute.routeDistance();
		System.out.println(routeDistance);
		writeOutput("../solution_yours_5.csv", solvedRoute);


	}
}

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


public class Common{

	public static Set<String> permuteSet(String string){
		Set<String> permuts = new HashSet<String>();
		if (string.length() == 0){
			return permuts;
		}

		Character insChar = string.charAt(0);

		if (string.length() == 1){
			permuts.add(String.valueOf(insChar));
		}

		else{
			Set<String> permSet = permuteSet(string.substring(1));
			for (String perms : permSet){
				for (int i = 0; i <= perms.length(); i++){
					permuts.add(perms.substring(0, i) + insChar + perms.substring(i));
				}
			}
		}
		return permuts;
	}


	public static CityRoute read_input(String filename) throws FileNotFoundException{
		FileReader reader = new FileReader(filename);
		Scanner in = new Scanner(reader);
		String nextLine;
		int counter = 0;
		CityRoute newRoute = new CityRoute();
		if(in.hasNextLine()){
			in.nextLine();
		}

		// read every line 
		while(in.hasNextLine()) {
			nextLine = in.nextLine();
			String[] coordinates = nextLine.split(",");
			City newCity = new City(Double.parseDouble(coordinates[0]), (Double.parseDouble(coordinates[1])), counter);
			newRoute.addCity(newCity);
			counter++;
		}
		return newRoute;
	}

	public static void writeOutput(String fileName, CityRoute solvedRoute) throws IOException{
		FileWriter writer = new FileWriter(fileName);
		PrintWriter out = new PrintWriter(writer);

		out.println("index"); 

		for(City city : solvedRoute.getRoute()){
			out.println(city.getId());
		}

		//Flush the output to the file
		out.flush();

		//Close the Print Writer
		out.close();

		//Close the File Writer
		writer.close();       
	}

	public static void main(String[] args) throws IOException{
		CityRoute newRoute = read_input("Coordinates/input_6.csv");
		//Random randInt = new Random();
		double routeDistance = newRoute.routeDistance();
		System.out.println(routeDistance);	
		//System.out.println(newRoute);
		//CityRoute solvedRoute = GreedyTSPSolver.greedySolver(newRoute);
		//CityRoute solvedRoute = RandomTSPSolver.randomSolver(newRoute);
		CityRoute solvedRoute = SATSPSolver.SASolver(newRoute);
		//CityRoute solvedRoute = GeneticTSASolver.GeneticSolver(newRoute);
		//CityRoute solvedRoute = FurthestInsersionTSPSolver.furthestInsersionMiltiSolver(newRoute);
		//CityRoute solvedRoute = FurthestInsersionTSPSolver.furthestInsersionSolver(newRoute, -1);
		//CityRoute solvedRoute = SATSPSolver.SAMultiSolver(newRoute);
		//CityRoute solvedRoute = AllPermTSPSolver.allPermSolver(newRoute);
		System.out.println(solvedRoute);
		routeDistance = solvedRoute.routeDistance();
		System.out.println(routeDistance);
		writeOutput("solution_yours_60.csv", solvedRoute);


	}
}
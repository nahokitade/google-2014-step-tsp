import java.util.Random;

/*
 * Class that implements the primitive Random Algorithm to solve TSP.
 * Assignment 5 of STEP at Google Tokyo
 * 
 * @author Naho Kitade
 */
public class RandomTSPSolver{
	
	/*
	 * Creates a random sequence of Cities that represents a route of the TSP.
	 * @param solvingRoute initial route we are starting with.
	 * @return a random route
	 */
	public static CityRoute randomSolver(CityRoute solvingRoute){
		CityRoute solvedRoute;
		solvedRoute = new CityRoute();
		Random randInt = new Random();
		int nextNodeInd;
		
		while(!solvingRoute.getRoute().isEmpty()){
			// get a random city not yet in the route and add to route
			nextNodeInd = randInt.nextInt(solvingRoute.getRoute().size());
			solvedRoute.addCity(solvingRoute.getRoute().get(nextNodeInd));
			solvingRoute.getRoute().remove(nextNodeInd);
		}
		
		return solvedRoute;
	}
	
	
}
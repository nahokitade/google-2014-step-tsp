import java.util.ArrayList;
import java.util.Random;

/*
 * Class that implements the Simulated Annealing Algorithm to solve TSP.
 * Assignment 5 of STEP at Google Tokyo
 * 
 * @author Naho Kitade
 */
public class SATSPSolver{
	private static final double STARTINGTEMP = 10000000;
	private static final double COOLING = 0.0001;
	private static final double ENDINGTEMP = 1;


	/*
	 * Solves the TSP with SA multiple times and returns the best try. 
	 * @param solvingRoute starting route
	 * @return the solved TSP via multiple SA
	 */
	public static CityRoute SAMultiSolver(CityRoute solvingRoute){
		CityRoute bestRoute = CityRoute.cloneRoute(solvingRoute);
		CityRoute candidateRoute;
		CityRoute solvingRouteClone;
		for(int i = 0; i < 10; i++){
			solvingRouteClone = CityRoute.cloneRoute(solvingRoute);
			candidateRoute = SASolver(solvingRouteClone);
			if(candidateRoute.routeDistance() < bestRoute.routeDistance()){
				bestRoute = candidateRoute;
			}
		}
		return bestRoute;
	}
	

	/*
	 * Solves TSP through Simulated Annealing algorithm.
	 * @param solvingRoute starting route
	 * @return the solved TSP via SA algorithm 
	 */
	@SuppressWarnings("unchecked")
	public static CityRoute SASolver(CityRoute solvingRoute){
		int changeCountBest = 0;
		int changeCount = 0;
		int iterCount = 0;
		
		Random randInt = new Random();
;
		CityRoute bestRoute = CityRoute.cloneRoute(solvingRoute);
		double temp = STARTINGTEMP;
		double cooling = COOLING;
		int[] chosenNodes;
		CityRoute prevRoute = CityRoute.cloneRoute(bestRoute);
		double prevDistance = bestRoute.routeDistance();
		CityRoute newRoute;
		double newDistance;

		while(temp > ENDINGTEMP){
			newRoute = CityRoute.cloneRoute(prevRoute);
			
			//Can comment in and out below code for different neighbor choosing.
			//chosenNodes = chooseConsecRandom(randInt, prevRoute.getRoute().size());
			chosenNodes = chooseNodeRandom(randInt, prevRoute.getRoute().size());
			
			switchNodes(chosenNodes[0], chosenNodes[1], newRoute);
			prevDistance = prevRoute.routeDistance();
			newDistance = newRoute.routeDistance();
			
			// decide if you accept the neighbor route
			if(acceptanceDecider(prevDistance, newDistance, temp) > Math.random()){
				if(newDistance < bestRoute.routeDistance()){
					bestRoute = newRoute; 
					changeCountBest++;
				}
				changeCount++;
				prevRoute = newRoute;
			}
			
			temp *= 1 - cooling; 
			iterCount++;
		}
		// prints out final progress to console
		System.out.println("Iteration Count: " + iterCount);
		System.out.println("Change Count: " + changeCount);
		System.out.println("Change Count2: " + changeCountBest);
		
		return bestRoute;
	}

	/*
	 * choose two random numbers within 0 ~ limit
	 * @param randInd random number generator
	 * @param limit the largest possible random number
	 * @return array of 2 ints of distinct random numbers 
	 */
	public static int[] chooseNodeRandom(Random randInt, int limit){
		int[] chosenNodes = new int[2];
		int firstInt = randInt.nextInt(limit);
		int secondInt = -1;
		chosenNodes[0] = firstInt;
		while(secondInt == -1 || firstInt == secondInt){
			secondInt = randInt.nextInt(limit);
		}
		chosenNodes[1] = secondInt;
		return chosenNodes;
	}
	
	/*
	 * choose two random numbers within 0 ~ limit that is consecutive
	 * @param randInd random number generator
	 * @param limit the largest possible random number
	 * @return array of 2 ints of random consecutive numbers
	 */
	public static int[] chooseConsecRandom(Random randInt, int limit){
		int[] chosenNodes = new int[2];
		int firstInt = randInt.nextInt(limit);
		int secondInt = (firstInt + 1) % limit;
		chosenNodes[0] = firstInt;
		chosenNodes[1] = secondInt;
		return chosenNodes;
	}

	/*
	 * Switch the two cities at the given index
	 * @param ind1 first index
	 * @param ind2 second index
	 * @param swappingRoute the route to swap the Cities
	 * @return null
	 */
	public static void switchNodes(int ind1, int ind2, CityRoute swapingRoute){
		ArrayList<City> cities = swapingRoute.getRoute();
		City tempCity = cities.get(ind1);
		cities.set(ind1, cities.get(ind2));
		cities.set(ind2, tempCity);
	}

	/*
	 * Outputs a probability value for the next neighbor. Always outputs 1.0 (100%) with better neighbor.
	 * @param prevDistance the overall score of original route
	 * @param newDistance the overall score of the new route
	 * @param temperature current temperature of the SA
	 * @return the probability value of accepting new neighbor solution
	 */
	private static double acceptanceDecider(double prevDistance, double newDistance, double temperature){
		if(newDistance - prevDistance < 0){
			return 1.0;
		}
		// mathematical expression commonly used to calculate acceptance probability
		double acceptanceProbability = Math.exp((prevDistance - newDistance) / temperature);
		return acceptanceProbability;
	}


}
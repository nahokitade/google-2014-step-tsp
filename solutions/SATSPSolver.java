import java.util.ArrayList;
import java.util.Random;

public class SATSPSolver{
	private static final double STARTINGTEMP = 100000;
	private static final double COOLING = 0.0001;
	private static final double ENDINGTEMP = 0.00001;
	// lowering temperature
	// starting temperature

	// make a random start graph
	// while temperature is higher than some number:
	// 	take two random nodes
	// 	switch nodes
	// 	run through acceptance heuristic to see accept
	// 	accept if accept, dont if not.
	// 	lower temperature


	// functions:
	// cloning CityRoute function
	// heuristic function
	// 
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
	
	@SuppressWarnings("unchecked")
	public static CityRoute SASolver(CityRoute solvingRoute){
		int changeCountBest = 0;
		int changeCount = 0;
		int iterCount = 0;
		
		Random randInt = new Random();
		//CityRoute bestRoute = GreedyTSPSolver.greedySolver(solvingRoute);
		//CityRoute bestRoute = RandomTSPSolver.randomSolver(solvingRoute);
		//CityRoute bestRoute = GeneticTSASolver.GeneticSolver(solvingRoute);
		CityRoute bestRoute = FurthestInsersionTSPSolver.furthestInsersionSolver(solvingRoute, 9);
		double temp = STARTINGTEMP;
		double cooling = COOLING;
		int[] chosenNodes;
		CityRoute prevRoute = CityRoute.cloneRoute(bestRoute);
		double prevDistance = bestRoute.routeDistance();
		CityRoute newRoute;
		double newDistance;

		while(temp > ENDINGTEMP){
			newRoute = CityRoute.cloneRoute(prevRoute);
			//chosenNodes = chooseConsecRandom(randInt, prevRoute.getRoute().size());
			chosenNodes = chooseNodeRandom(randInt, prevRoute.getRoute().size());
			
			switchNodes(chosenNodes[0], chosenNodes[1], newRoute);
			prevDistance = prevRoute.routeDistance();
			newDistance = newRoute.routeDistance();
			
			//System.out.println(newDistance - prevDistance);
			
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
		System.out.println("Iteration Count: " + iterCount);
		System.out.println("Change Count: " + changeCount);
		System.out.println("Change Count2: " + changeCountBest);
		
		//fixRouteRotation(bestRoute);
		return bestRoute;
	}

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
	
	public static int[] chooseConsecRandom(Random randInt, int limit){
		int[] chosenNodes = new int[2];
		int firstInt = randInt.nextInt(limit);
		int secondInt = (firstInt + 1) % limit;
		chosenNodes[0] = firstInt;
		chosenNodes[1] = secondInt;
		return chosenNodes;
	}

	public static void switchNodes(int ind1, int ind2, CityRoute swapingRoute){
		ArrayList<City> cities = swapingRoute.getRoute();
		City tempCity = cities.get(ind1);
		cities.set(ind1, cities.get(ind2));
		cities.set(ind2, tempCity);
	}

	private static double acceptanceDecider(double prevDistance, double newDistance, double temperature){
		if(newDistance - prevDistance < 0){
			return 1.0;
		}
		double acceptanceProbability = 0; //Math.exp((prevDistance - newDistance) / temperature);
		return acceptanceProbability;
	}


}

/*
 * Class that implements the Furthest Insersion Algorithm to solve TSP.
 * Assignment 5 of STEP at Google Tokyo
 * 
 * @author Naho Kitade
 */
public class FurthestInsersionTSPSolver{

	/*
	 * Solves the TSP using a Furthest insertion algorithm. 
	 * @param solvingRoute the initial route to be solved
	 * @param initialCityInd the index to start the algorithm. If it is a negative number 
	 * or an int larger than the solvingRoute length, a random number is used.
	 * @return a CityRoute result after Furthest Insertion has been performed
	 */
	public static CityRoute furthestInsersionSolver(CityRoute solvingRoute, int initialCityInd){
		City insertingCity;
		CityRoute constructingRoute = new CityRoute();
		CityRoute remainingRoute = CityRoute.cloneRoute(solvingRoute);
		
		//pick random starting point add to route
		if(initialCityInd < 0 || initialCityInd > remainingRoute.getRoute().size()){
			initialCityInd = (int) (Math.random() * remainingRoute.getRoute().size());
		}
		
		// first city to start from
		insertingCity = remainingRoute.getRoute().remove(initialCityInd);
		constructingRoute.addCity(insertingCity);

		//find furthest point. add to route
		insertingCity = getFurthest(constructingRoute, remainingRoute);
		constructingRoute.addCity(insertingCity);
		
		/*
		 * while route < route solving route length
		 * find the node that has longest shortest length
		 * find the place to insert. Cik + Ckj - Cij = f where f is smallest.
		 * insert the new node
		 */
		while(constructingRoute.getRoute().size() < solvingRoute.getRoute().size()){
			insertingCity = getFurthest(constructingRoute, remainingRoute);
			leastCostInsert(insertingCity, constructingRoute);
		}
		return constructingRoute;
	}
	
	/*
	 * Calls furthestInsersion multiple times to get the best route from the best 
	 * starting point.
	 * @param solvingRoute the initial route to be solved
	 * @return a CityRoute result after Furthest Insertion has been performed at the
	 * optimum starting point
	 */
	public static CityRoute furthestInsersionMiltiSolver(CityRoute solvingRoute){
		CityRoute bestRoute = null;
		CityRoute candidateRoute;
		int bestIndex = -1;
		
		for(int i = 0; i < solvingRoute.getRoute().size(); i++){
			candidateRoute = furthestInsersionSolver(solvingRoute, i);
			if(bestRoute == null || candidateRoute.routeDistance() < bestRoute.routeDistance()){
				bestRoute = candidateRoute;
				bestIndex = i;
			}
			// for purposes of the user to remember the best route to start
			System.out.println("Current route start: " + i + " Best route start: " + bestIndex);
			System.out.println("Current route distance: " + candidateRoute.routeDistance());
		}
		System.out.println("Best Index for farthest insersion is: " + bestIndex);
		return bestRoute;
	}

	/*
	 * Gets the city that has the furthest distance from any node in the already constructed route.
	 * @param constructingRoute route that you are constructing 
	 * @param remainingRoute the rest of the Nodes not yet in the route but should be
	 * @return the City that has the furthest distance from any node in the already constructed route.
	 */
	public static City getFurthest(CityRoute constructingRoute, CityRoute remainingRoute){
		double shortestDis = -1;
		double currentDis;
		City currentNode1;
		City currentNode2;
		City furthestCity = null;
		double furthestCityDis = -1;

		// go through every node in remaining route and find its shortest distance to a node in the constructingRoute
		
		for(int i = 0; i < remainingRoute.getRoute().size(); i++){
			currentNode1 = remainingRoute.getRoute().get(i);
			for(int j = 0; j < constructingRoute.getRoute().size(); j++){
				currentNode2 = constructingRoute.getRoute().get(j);
				currentDis = City.calculateDistance(currentNode1.getX(), currentNode2.getX(), currentNode1.getY(), currentNode2.getY());
				if(shortestDis == -1 || shortestDis > currentDis){
					shortestDis = currentDis;
				}
			}
		  
			// find the node with the furthest shortest distance Node within the remainingRoute.
			if(furthestCityDis == -1 || shortestDis > furthestCityDis){
				furthestCityDis = shortestDis;
				furthestCity = currentNode1;
			}
			shortestDis = -1;	// reset shortestDis for next loop
		}
		return furthestCity;
	}

	/*
	 * Inserts the given City into the constructingRoute at the least cost position.
	 * @param insertCity the city to insert into the constructing route
	 * @param constructingRoute the already made route to add to
	 * @return null
	 */
	public static void leastCostInsert(City insertCity, CityRoute constructingRoute){
		double leastDistance = -1;
		double curDistance;
		int insBefore = 0;
		City city1;
		City city2;

		// go through two adjacent nodes within the constructing route and find the nodes with the smallest cost
		for(int i = 1; i < constructingRoute.getRoute().size(); i++){
			city1 = constructingRoute.getRoute().get(i - 1);
			city2 = constructingRoute.getRoute().get(i);
			curDistance = insertCost(city1, city2, insertCity);
			if(leastDistance == -1 || leastDistance > curDistance){
				insBefore = i;
				leastDistance = curDistance;
			}
		}
		// dont forget to try the final and first nodes in the cycle.
		city1 = constructingRoute.getRoute().get(0);
		city2 = constructingRoute.getRoute().get(constructingRoute.getRoute().size() - 1);
		curDistance = insertCost(city1, city2, insertCity);
		if(leastDistance == -1 || leastDistance > curDistance){
			insBefore = 0;
			leastDistance = curDistance;
		}
		
		// add the city between the smallest cost pair.
		constructingRoute.addCity(insBefore, insertCity);
	}

	/*
	 * Calculates the cost function, f = Cik + Ckj - Cij where i and j are two cities already in the
	 * route, and k the city to insert.
	 * @param city1 first city already in route
	 * @param city2 second city already in route
	 * @param insertCity the city to be inserted into route
	 * @return the calculated cost function
	 */
	public static double insertCost(City city1, City city2, City insertCity){
		//Cik + Ckj - Cij = f
		double cost = City.calculateDistance(city1.getX(), insertCity.getX(), city1.getY(), insertCity.getY()) + 
				City.calculateDistance(city2.getX(), insertCity.getX(), city2.getY(), insertCity.getY()) -
				City.calculateDistance(city1.getX(), city2.getX(), city1.getY(), city2.getY());
		return cost;
	}
}
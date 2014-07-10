public class FurthestInsersionTSPSolver{

	public static CityRoute furthestInsersionSolver(CityRoute solvingRoute, int initialCityInd){
		City insertingCity;
		CityRoute constructingRoute = new CityRoute();
		CityRoute remainingRoute = CityRoute.cloneRoute(solvingRoute);

		if(initialCityInd < 0 || initialCityInd > remainingRoute.getRoute().size()){
			initialCityInd = (int) (Math.random() * remainingRoute.getRoute().size());
		}
		//pick random starting point add to route
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
			System.out.println("Current route start: " + i + " Best route start: " + bestIndex);
			System.out.println("Current route distance: " + candidateRoute.routeDistance());
		}
		System.out.println("Best Index for farthest insersion is: " + bestIndex);
		return bestRoute;
	}

	public static City getFurthest(CityRoute constructingRoute, CityRoute remainingRoute){
		double shortestDis = -1;
		double currentDis;
		City currentNode1;
		City currentNode2;
		City furthestCity = null;
		double furthestCityDis = -1;

		for(int i = 0; i < remainingRoute.getRoute().size(); i++){
			currentNode1 = remainingRoute.getRoute().get(i);
			for(int j = 0; j < constructingRoute.getRoute().size(); j++){
				currentNode2 = constructingRoute.getRoute().get(j);
				currentDis = City.calculateDistance(currentNode1.getX(), currentNode2.getX(), currentNode1.getY(), currentNode2.getY());
				if(shortestDis == -1 || shortestDis > currentDis){
					shortestDis = currentDis;
				}
			}
			if(furthestCityDis == -1 || shortestDis > furthestCityDis){
				furthestCityDis = shortestDis;
				furthestCity = currentNode1;
			}
			shortestDis = -1;
		}
		return furthestCity;
	}

	public static void leastCostInsert(City insertCity, CityRoute constructingRoute){
		double leastDistance = -1;
		double curDistance;
		int insBefore = 0;
		City city1;
		City city2;

		
		for(int i = 1; i < constructingRoute.getRoute().size(); i++){
			city1 = constructingRoute.getRoute().get(i - 1);
			city2 = constructingRoute.getRoute().get(i);
			curDistance = insertCost(city1, city2, insertCity);
			if(leastDistance == -1 || leastDistance > curDistance){
				insBefore = i;
				leastDistance = curDistance;
			}
		}
		city1 = constructingRoute.getRoute().get(0);
		city2 = constructingRoute.getRoute().get(constructingRoute.getRoute().size() - 1);
		curDistance = insertCost(city1, city2, insertCity);
		if(leastDistance == -1 || leastDistance > curDistance){
			insBefore = 0;
			leastDistance = curDistance;
		}
		constructingRoute.addCity(insBefore, insertCity);
	}

	public static double insertCost(City city1, City city2, City insertCity){
	//Cik + Ckj - Cij = f
		double cost = City.calculateDistance(city1.getX(), insertCity.getX(), city1.getY(), insertCity.getY()) + 
				City.calculateDistance(city2.getX(), insertCity.getX(), city2.getY(), insertCity.getY()) -
				City.calculateDistance(city1.getX(), city2.getX(), city1.getY(), city2.getY());
		return cost;
	}
}
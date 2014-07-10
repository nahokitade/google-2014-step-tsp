/*
 * Class that implements the Greedy (Nearest Neighbor) Algorithm to solve TSP.
 * Assignment 5 of STEP at Google Tokyo
 * 
 * @author Naho Kitade
 */
public class GreedyTSPSolver{

	/*
	 * Solves TSP using the Nearest Neighbor algorithm. 
	 * @param solvingRoute the starting route to solve
	 * @return the CityRoute solved through NN algorithm.
	 */
	public static CityRoute greedySolver(CityRoute solvingRoute){
		CityRoute solvedRoute = new CityRoute();
		double distanceBetween;
		City nextCity = null;
		City nextCandidateCity;
		double shortestDistance;
		
		// add first City to returning route.
		City currentCity = solvingRoute.getRoute().get(0);
		solvingRoute.getRoute().get(currentCity.getId()).setVisited();
		solvedRoute.addCity(currentCity);
		
		// keep finding the nearest neighbor of the current city, and add to the returning route
		while (solvedRoute.getRoute().size() < solvingRoute.getRoute().size()){
			shortestDistance = -1;
			for(int i = 0; i < solvingRoute.getRoute().size(); i++){
				if(i == currentCity.getId()) continue;
				nextCandidateCity = solvingRoute.getRoute().get(i);
				if(nextCandidateCity.isVisited()) continue;
				distanceBetween = City.calculateDistance(currentCity.getX(), nextCandidateCity.getX(), currentCity.getY(), nextCandidateCity.getY());
				if(shortestDistance == -1 || distanceBetween < shortestDistance){
					nextCity = nextCandidateCity;
					shortestDistance = distanceBetween;
				}
			}
			currentCity = nextCity;
			solvedRoute.addCity(currentCity);
			solvingRoute.getRoute().get(nextCity.getId()).setVisited();
		}
		return solvedRoute;
	}
}
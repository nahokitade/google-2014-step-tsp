public class GreedyTSPSolver{

	public static CityRoute greedySolver(CityRoute solvingRoute){
		CityRoute solvedRoute = new CityRoute();
		City currentCity = solvingRoute.getRoute().get(0);
		solvingRoute.getRoute().get(currentCity.getId()).setVisited();
		solvedRoute.addCity(currentCity);
		
		double distanceBetween;
		
		City nextCity = null;
		City nextCandidateCity;
		
		double shortestDistance;
		
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
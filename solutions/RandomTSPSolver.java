import java.util.Random;

public class RandomTSPSolver{
	public static CityRoute randomSolver(CityRoute solvingRoute){
		CityRoute solvedRoute;
		solvedRoute = new CityRoute();
		Random randInt = new Random();
		int nextNodeInd;
		
		while(!solvingRoute.getRoute().isEmpty()){
			nextNodeInd = randInt.nextInt(solvingRoute.getRoute().size());
			solvedRoute.addCity(solvingRoute.getRoute().get(nextNodeInd));
			solvingRoute.getRoute().remove(nextNodeInd);
		}
		
		//solvedRoute.addCity(solvedRoute.getRoute().get(0));
		
		return solvedRoute;
	}
	
	
}
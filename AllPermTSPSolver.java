import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/*
 * Class that implements the exact permutation algorithm for TSP
  * Assignment 5 of STEP at Google Tokyo
 * 
 * @author Naho Kitade
 */
public class AllPermTSPSolver{

	/*
	 * Tries all permutation of the route to find exact solution. Can only do this until
	 * the stack over flows in permuteIntSet.
	 * @param solvingRoute the route to solve
	 * @return CityRoute of the exact solution to TSP
	 */
	public static CityRoute allPermSolver(CityRoute solvingRoute){
		ArrayList<City> bestRoute = null;
		double bestRouteDis = -1;
		double candidateDis;
		
		// make all permutations and try them all to find shortest path.
		Set<ArrayList<City>> allRoutePerms = permuteIntSet(solvingRoute.getRoute());
		for(ArrayList<City> cityArray : allRoutePerms){
			candidateDis = City.arrayCityDistance(cityArray);
			if(candidateDis < bestRouteDis || bestRouteDis == -1){
				bestRoute = cityArray;
				bestRouteDis = City.arrayCityDistance(cityArray);
			}
		}
		CityRoute solvedRoute = new CityRoute();
		solvedRoute.setRoute(bestRoute);
		return solvedRoute;
	}


	/*
	 * recursively creates a set of ArrayLists of Cities which consists of all permutations 
	 * of a given ArrayList of Cities.
	 * @param permCity the ArrayList of Cities to construct all permutations
	 * @return Set of AttayList of Cities containing all permutations of the original.
	 */
	@SuppressWarnings("unchecked")
	public static Set<ArrayList<City>> permuteIntSet(ArrayList<City> permCity){
		Set<ArrayList<City>> returnSet = new HashSet<ArrayList<City>>();
		Set<ArrayList<City>> restPerm;
		ArrayList<City> subPermCity = new ArrayList<City>();
		City insCity;
		ArrayList<City> leftCityClone;
		
		if(permCity.size() == 0) return returnSet;

		// base case
		if(permCity.size() == 1){
			returnSet.add(permCity);
			return returnSet;
		}
		// recursive case
		else{
			insCity = permCity.get(0);
			subPermCity = (ArrayList<City>) permCity.clone();
			subPermCity.remove(0);																	// shrink problem by one Node 
			restPerm = permuteIntSet(subPermCity);									// permute the shrunken problem
			// insert the insCity at every possible position possible for every word in the 
			// above set and create a new Set consisting of all those words.
			for(ArrayList<City> leftInts : restPerm){
				for(int i = 0; i <= leftInts.size(); i++){
					leftCityClone = (ArrayList<City>) leftInts.clone();
					leftCityClone.add(i, insCity);
					returnSet.add(leftCityClone);
				}
			}
			return returnSet;
		}
	}
	
}
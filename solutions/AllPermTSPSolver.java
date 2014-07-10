import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AllPermTSPSolver{

	public static CityRoute allPermSolver(CityRoute solvingRoute){
		ArrayList<City> bestRoute = null;
		double bestRouteDis = -1;
		double candidateDis;
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


	@SuppressWarnings("unchecked")
	public static Set<ArrayList<City>> permuteIntSet(ArrayList<City> permCity){
		Set<ArrayList<City>> returnSet = new HashSet<ArrayList<City>>();
		Set<ArrayList<City>> restPerm;
		ArrayList<City> subPermCity = new ArrayList<City>();
		City insCity;
		ArrayList<City> leftCityClone;
		if(permCity.size() == 0) return returnSet;

		if(permCity.size() == 1){
			returnSet.add(permCity);
			return returnSet;
		}
		else{
			insCity = permCity.get(0);
			subPermCity = (ArrayList<City>) permCity.clone();
			subPermCity.remove(0);
			restPerm = permuteIntSet(subPermCity);
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

	public static void main(String[] args) throws FileNotFoundException{
		//		ArrayList<Integer> permInts = new ArrayList<Integer>();
		//		permInts.add(0);
		//		permInts.add(1);
		//		permInts.add(2);
		//		System.out.println(permuteIntSet(permInts));
		CityRoute newRoute = Common.read_input("Coordinates/input_0.csv");
		System.out.println(permuteIntSet(newRoute.getRoute()));
	}


}
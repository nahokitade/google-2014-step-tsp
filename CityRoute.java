import java.util.ArrayList;

/*
 * Class that defines a city route data structure.
 * Assignment 5 of STEP at Google Tokyo
 * 
 * @author Naho Kitade
 */
public class CityRoute{
	private ArrayList<City> route;
	
	/*
	 * Creates a new CityRoute. The route itself is blank.
	 * @return new blank CityRoute object 
	 */
	public CityRoute(){
		route = new ArrayList<City>();	
	}
	
	/*
	 * Creates a new CityRoute of a set route length. The route itself is blank.
	 * @return new blank set length CityRoute object 
	 */
	public CityRoute(int length){
		route = new ArrayList<City>(length);	
	}	
	
	/*
	 * Getter for the route.
	 * @returns route of the CityRoute
	 */
	public ArrayList<City> getRoute(){
		return route;
	}

	/*
	 * Setter for the route.
	 * @returns null
	 */
	public void setRoute(ArrayList<City> newRoute){
		route = newRoute;
	}

	/*
	 * Adds a given city onto the end of the route.
	 * @return null
	 */
	public void addCity(City newCity){
		this.route.add(newCity);
	}
	
	/*
	 * Adds a given city to the specified index of the route.
	 * @return null
	 */
	public void addCity(int index, City newCity){
		this.route.add(index, newCity);
	}
	
	/*
	 * Sets a given city at the specified index of the route.
	 * @return null
	 */
	public void setCity(int index, City newCity){
		this.route.set(index, newCity);
	}

	/*
	 * Returns the distance of the CityRoute.
	 * @return the distance of the whole route
	 */
	public double routeDistance(){
		double totalDistance = City.arrayCityDistance(route);
		return totalDistance;
	}
	
	/*
	 * Returns an exact clone of the given route.
	 * @return clone of a given route
	 */
	@SuppressWarnings("unchecked")
	public static CityRoute cloneRoute(CityRoute originalRoute){
		CityRoute clonedRoute = new CityRoute();
		clonedRoute.setRoute((ArrayList<City>) originalRoute.getRoute().clone());
		return clonedRoute;
	}
	
	/*
	 * String representation of CityRoute. Only its city id in array format.
	 * @return String representation of CityRoute
	 */
	public String toString(){
		return route.toString();
	}
	
}
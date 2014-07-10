import java.util.ArrayList;

public class CityRoute{
	private ArrayList<City> route;
	
	public CityRoute(){
		route = new ArrayList<City>();	
	}
	
	public CityRoute(int length){
		route = new ArrayList<City>(length);	
	}	
	
	public ArrayList<City> getRoute(){
		return route;
	}

	public void setRoute(ArrayList<City> newRoute){
		route = newRoute;
	}

	public void addCity(City newCity){
		this.route.add(newCity);
	}
	
	public void addCity(int index, City newCity){
		this.route.add(index, newCity);
	}
	
	public void setCity(int index, City newCity){
		this.route.set(index, newCity);
	}

	public double routeDistance(){
		double totalDistance = City.arrayCityDistance(route);
		return totalDistance;
	}
	
	@SuppressWarnings("unchecked")
	public static CityRoute cloneRoute(CityRoute originalRoute){
		CityRoute clonedRoute = new CityRoute();
		clonedRoute.setRoute((ArrayList<City>) originalRoute.getRoute().clone());
		return clonedRoute;
	}
	
	public String toString(){
		return route.toString();
	}
	
}
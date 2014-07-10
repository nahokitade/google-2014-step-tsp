import java.util.ArrayList;

/*
 * Class that defines a city data structure.
 * Assignment 5 of STEP at Google Tokyo
 * 
 * @author Naho Kitade
 */
public class City{
	private double x;
	private double y;
	private boolean visited;
	private int id; 
	
	/*
	 * Constructor of City. The visited instance variable is set to false by default
	 * @param xCoor the x coordinate of the city
	 * @param yCoor the y coordinate of the city
	 * @param id the number id of the city
	 * @return a new City object
	 */
	public City(double xCoor, double yCoor, int id){
		this.x = xCoor;
		this.y = yCoor;
		this.id = id;
		this.visited = false;
	}
	
	/*
	 * Getter for id
	 * @return id of the City
	 */
	public int getId() {
		return id;
	}

	/*
	 * Setter for id
	 * @return null
	 */
	public void setId(int id) {
		this.id = id;
	}

	/*
	 * sets visited variable to true
	 * @return null
	 */
	public void setVisited(){
		this.visited = true;
	}
	
	/*
	 * Returns a boolean saying whether the City has been visited
	 * @return whether the city has been visited
	 */
	public boolean isVisited(){
		return visited;
	}
	
	/*
	 * Getter for x coordinate
	 * @return x coordinate of the City
	 */
	public double getX(){
		return x;
	}
	
	/*
	 * Getter for y coordinate
	 * @return y coordinate of the City
	 */
	public double getY(){
		return y;
	}
	
	/*
	 * Calculates the distance of an array list of cities.
	 * Assumes adjacent cities are connected by edges.
	 * 
	 * @param arrayCity array list of Cities to calculate length of tour
	 * @return distance of the whole tour
	 */
	public static double arrayCityDistance(ArrayList<City> arrayCity){
		double totalDistance = 0;
		City city1;
		City city2;
		City firstCity;
		City lastCity;
		for(int i = 1; i < arrayCity.size(); i++){
			city1 = arrayCity.get(i - 1);
			city2 = arrayCity.get(i);
			totalDistance += calculateDistance(city1.getX(), city2.getX(), city1.getY(), city2.getY());
		}
		
		// need to end the tour by calculating distance between first and last city in array list
		firstCity = arrayCity.get(0);
		lastCity = arrayCity.get(arrayCity.size() - 1);
		totalDistance += calculateDistance(firstCity.getX(), lastCity.getX(), firstCity.getY(), lastCity.getY());
		return totalDistance;
	}
	
	/*
	 * Calculates the distance between two cities. 
	 * @param x1 and y1 the x and y coordinates of city1
	 * @param x2 and y2 the x and y coordinates of city2
	 * @return distance between city1 and city2
	 */
	public static double calculateDistance(double x1, double x2, double y1, double y2){
		double xDiffSq;
		double yDiffSq;
		xDiffSq = Math.pow(x2 - x1, 2);
		yDiffSq = Math.pow(y2 - y1, 2);
		return Math.sqrt(xDiffSq + yDiffSq);
	}
	
	/*
	 * String representation of City. Only its city id.
	 * @return String representation of City
	 */
	public String toString(){
		return  id + "";
	}
}
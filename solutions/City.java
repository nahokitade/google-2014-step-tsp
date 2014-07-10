import java.util.ArrayList;

public class City{
	private double x;
	private double y;
	private boolean visited;
	private int id; 
	
	public City(double xCoor, double yCoor, int id){
		this.x = xCoor;
		this.y = yCoor;
		this.id = id;
		this.visited = false;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setVisited(){
		this.visited = true;
	}
	
	public boolean isVisited(){
		return visited;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
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
		firstCity = arrayCity.get(0);
		lastCity = arrayCity.get(arrayCity.size() - 1);
		totalDistance += calculateDistance(firstCity.getX(), lastCity.getX(), firstCity.getY(), lastCity.getY());
		return totalDistance;
	}
	
	public static double calculateDistance(double x1, double x2, double y1, double y2){
		double xDiffSq;
		double yDiffSq;
		xDiffSq = Math.pow(x2 - x1, 2);
		yDiffSq = Math.pow(y2 - y1, 2);
		return Math.sqrt(xDiffSq + yDiffSq);
	}
	
	public String toString(){
		return  id + "";// + " : " + x + " : " + y;
	}
}
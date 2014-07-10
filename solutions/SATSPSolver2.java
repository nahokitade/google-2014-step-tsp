import java.util.ArrayList;
import java.util.Random;

			
public class SATSPSolver2{
// Set initial temp
	@SuppressWarnings("unchecked")
	public static CityRoute SASolver2(CityRoute solvingRoute){
		int[] chosenNodes;
        double temp = 1000000;

        // Cooling rate
        double coolingRate = 0.0001;

        Random randInt = new Random();
    		//CityRoute currentSolution = GreedyTSPSolver.greedySolver(solvingRoute);
        CityRoute currentSolution = RandomTSPSolver.randomSolver(solvingRoute);
        
        System.out.println("Initial solution distance: " + currentSolution.routeDistance());

        // Set as current best
        CityRoute best = new CityRoute();
        best.setRoute((ArrayList<City>) currentSolution.getRoute().clone());
        
        // Loop until system has cooled
        while (temp > 1) {
            // Create new neighbour tour
        	CityRoute newSolution = new CityRoute();
        	newSolution.setRoute((ArrayList<City>) currentSolution.getRoute().clone());

    			chosenNodes = SATSPSolver.chooseNodeRandom(randInt, currentSolution.getRoute().size());
    			SATSPSolver.switchNodes(chosenNodes[0], chosenNodes[1], newSolution);

            // Get energy of solutions
            double currentEnergy = currentSolution.routeDistance();
            double neighbourEnergy = newSolution.routeDistance();

            // Decide if we should accept the neighbour
            if (acceptanceProbability(currentEnergy, neighbourEnergy, temp) > Math.random()) {
                currentSolution = new CityRoute();
                currentSolution.setRoute((ArrayList<City>) newSolution.getRoute().clone());
            }

            // Keep track of the best solution found
            if (currentSolution.routeDistance() < best.routeDistance()) {
                best = new CityRoute();
                best.setRoute((ArrayList<City>) currentSolution.getRoute().clone()); 
            }
            
            // Cool system
            temp *= 1-coolingRate;
        }
        return best;
	}
  // Calculate the acceptance probability
  public static double acceptanceProbability(double energy, double newEnergy, double temperature) {
      // If the new solution is better, accept it
      if (newEnergy < energy) {
          return 1.0;
      }
      // If the new solution is worse, calculate an acceptance probability
      return Math.exp((energy - newEnergy) / temperature);
  }
}
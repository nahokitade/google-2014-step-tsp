import java.util.ArrayList;

/*
 * Class that implements the Genetic Algorithm to solve TSP.
 * Assignment 5 of STEP at Google Tokyo
 * 
 * @author Naho Kitade
 */
public class GeneticTSPSolver{
	private static final int SELECTIONSIZE = 5;

	/*
	 * Private class that defines a population of CityRoutes.
	 */
	private static class RoutePop{
		private ArrayList<CityRoute> population;
		private static final double MUTATIONRATE = 0.015;

		/*
		 * Constructor for a new RoutePop object. Initializes an empty population
		 * @return new RoutePop object
		 */
		public RoutePop(){
			population = new ArrayList<CityRoute>();
		}

		/*
		 * Adds a new route to the population.
		 * @param route the CityRoute to add to the population.
		 */
		public void addRoute(CityRoute route){
			population.add(route);
		}

		/*
		 * initializes the population with a given population size with the nodes in the initialRoute.
		 * The routes are going to be created randomly.
		 * @param popSize population size of the population to initialize
		 * @param initialRoute first route to create the population
		 * @return null
		 */
		public void initialize(int popSize, CityRoute initialRoute){
			CityRoute clonedInitRoute;
			for(int i = 0; i < popSize; i++){
				clonedInitRoute = CityRoute.cloneRoute(initialRoute);
				population.add(RandomTSPSolver.randomSolver(clonedInitRoute));
			}
		}

		/*
		 * initializes the population with a given population size with the nodes in the initialRoute.
		 * The routes are going to be created through insertion solver.
		 * @param popSize population size of the population to initialize
		 * @param initialRoute first route to create the population
		 * @return null
		 */
		public void initialize2(int popSize, CityRoute initialRoute){
			CityRoute clonedInitRoute;
			for(int i = 0; i < popSize; i++){
				clonedInitRoute = CityRoute.cloneRoute(initialRoute);
				population.add(FurthestInsersionTSPSolver.furthestInsersionSolver(clonedInitRoute, -1));
			}
		}

		/*
		 * returns the best route (shortest total distance) within a population.
		 * @return the best route (shortest total distance) within a population.
		 */
		public CityRoute bestRoute(){
			CityRoute bestRoute = population.get(0);
			CityRoute curRoute;
			for(int i = 1; i < population.size(); i++){
				curRoute = population.get(i);
				if(bestRoute.routeDistance() > curRoute.routeDistance()){
					bestRoute = CityRoute.cloneRoute(curRoute);    
				}
			}
			return bestRoute;
		}

		/*
		 * Mutate the population (swap mutation) with the supplied MUTATIONRATE
		 * @return null
		 */
		public void mutatePop(){
			int swapIndex;
			for(CityRoute route : population){
				for(int i = 0; i < route.getRoute().size(); i++){
					if(Math.random() < MUTATIONRATE){
						swapIndex = (int) (Math.random() * route.getRoute().size());
						SATSPSolver.switchNodes(i, swapIndex, route);
					}
				}
			}
		}

		/*
		 * Selects the relatively good route out of the population. (through group competition)
		 * Takes selectionSize amounts of random Cities, and returns the best route out of the group.
		 * @param selectionSize size of the group to select the best.
		 * @return returns the best route out of the group
		 */
		public CityRoute naturalSelection(int selectionSize){
			RoutePop survivors = new RoutePop();
			int randomRoute;
			for(int i = 0; i < selectionSize; i++){
				randomRoute = (int) (Math.random() * population.size());
				survivors.addRoute(population.get(randomRoute));
			}
			return survivors.bestRoute();
		}

		/*
		 * Getter for the population.
		 * @return population stored as instance variable
		 */
		public ArrayList<CityRoute> getPop() {
			return population;
		}
	}

	/*
	 * Take two parents and mesh their DNA (routes) to make a baby.
	 * Takes an interval of parent1 and adds it to the same indexes of the baby. Then 
	 * takes rest of the Cities that has not been added to the baby from parent2's route
	 * in the same order as parent2.
	 * @param parent1 first parent for baby
	 * @param parent2 second parent for baby
	 * @return returns a new baby CityRoute
	 */
	public static CityRoute makeBaby(CityRoute parent1, CityRoute parent2){
		CityRoute baby = new CityRoute(parent1.getRoute().size());
		for (int i = 0; i < parent1.getRoute().size(); i++) {
			baby.addCity(null);
		}
		// decides randomly the first and last City index of the interval and
		// copies that onto the baby. 
		int index1 = (int) (Math.random() * parent1.getRoute().size());
		int index2 = (int) (Math.random() * parent1.getRoute().size());
		if(index1 < index2){
			for(int i = index1; i < index2; i++){
				baby.setCity(i, parent1.getRoute().get(i));
			}
		}
		// Since first is greater than second, this means we must wrap around the route 
		// to get the interval.
		else if(index1 > index2){
			for(int n = index1; n < parent1.getRoute().size(); n++){
				baby.setCity(n, parent1.getRoute().get(n));
			}
			for(int j = 0; j < index2; j++){
				baby.setCity(j, parent1.getRoute().get(j));
			}
		}
		// get the rest of the nodes that the baby is lacking from parent2.
		for(int k = 0; k < parent2.getRoute().size(); k++){
			if(!baby.getRoute().contains(parent2.getRoute().get(k))){
				for(int m = 0; m < baby.getRoute().size(); m++){		
					if(baby.getRoute().get(m) == null){
						baby.setCity(m, parent2.getRoute().get(k));
						break;
					}
				}
			}
		}
		return baby;
	}

	/*
	 * Evolves the given population by one generation. ie, Creates babies
	 * and mutates the given population.
	 * @param population the population to evolve one generation
	 * @return The evolved population
	 */
	public static RoutePop evolvePop(RoutePop population){
		CityRoute parent1;
		CityRoute parent2;
		CityRoute baby;
		RoutePop evolvedPop = new RoutePop();
		CityRoute bestRoute = population.bestRoute();
		for(int i = 1; i < population.getPop().size(); i++){
			parent1 = population.naturalSelection(SELECTIONSIZE);
			parent2 = population.naturalSelection(SELECTIONSIZE);
			baby = makeBaby(parent1, parent2);
			evolvedPop.addRoute(baby);
		}
		evolvedPop.mutatePop();
		evolvedPop.addRoute(bestRoute);
		return evolvedPop;
	}

	/* Solves TSP with Genetic Algorithm
	 * @param solvingRoute The initial route we are solving
	 * @return the CityRoute solved by Genetic Algorithm
	 */
	public static CityRoute GeneticSolver(CityRoute solvingRoute){
		RoutePop solvedRoute = new RoutePop();
		solvedRoute.initialize2(100, solvingRoute);
		for(int i = 0; i < 1000; i++){
			solvedRoute = evolvePop(solvedRoute);
		}
		return solvedRoute.bestRoute();
	}

}
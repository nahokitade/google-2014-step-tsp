import java.util.ArrayList;

public class GeneticTSASolver{
	private static final int SELECTIONSIZE = 5;
	
	private static class RoutePop{
		private ArrayList<CityRoute> population;
		private static final double MUTATIONRATE = 0.015;
		
		public RoutePop(){
			population = new ArrayList<CityRoute>();
		}
		
		public void addRoute(CityRoute route){
			population.add(route);
		}

		public void initialize(int popSize, CityRoute initialRoute){
			CityRoute clonedInitRoute;
			for(int i = 0; i < popSize; i++){
				clonedInitRoute = CityRoute.cloneRoute(initialRoute);
				population.add(RandomTSPSolver.randomSolver(clonedInitRoute));
			}
		}
		
		public void initialize2(int popSize, CityRoute initialRoute){
			CityRoute clonedInitRoute;
			for(int i = 0; i < popSize; i++){
				clonedInitRoute = CityRoute.cloneRoute(initialRoute);
				population.add(FurthestInsersionTSPSolver.furthestInsersionSolver(clonedInitRoute, -1));
			}
		}

		public CityRoute bestRoute(){
			CityRoute bestRoute = population.get(0);
			CityRoute curRoute;
			for(int i = 1; i < population.size(); i++){
				curRoute = population.get(i);
				if(bestRoute.routeDistance() > curRoute.routeDistance()){
					bestRoute = CityRoute.cloneRoute(curRoute);    // maybe can refactor
				}
			}
			return bestRoute;
		}

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

		public CityRoute naturalSelection(int selectionSize){
			RoutePop survivors = new RoutePop();
			int randomRoute;
			for(int i = 0; i < selectionSize; i++){
				randomRoute = (int) (Math.random() * population.size());
				survivors.addRoute(population.get(randomRoute));
			}
			return survivors.bestRoute();
		}

		public ArrayList<CityRoute> getPop() {
			return population;
		}
	}

	public static CityRoute makeBaby(CityRoute parent1, CityRoute parent2){
		CityRoute baby = new CityRoute(parent1.getRoute().size());
		 for (int i = 0; i < parent1.getRoute().size(); i++) {
			 baby.addCity(null);
		 }
		int index1 = (int) (Math.random() * parent1.getRoute().size());
		int index2 = (int) (Math.random() * parent1.getRoute().size());
		if(index1 < index2){
			for(int i = index1; i < index2; i++){
				baby.setCity(i, parent1.getRoute().get(i));
			}
		}
		else if(index1 > index2){
			for(int n = index1; n < parent1.getRoute().size(); n++){
				baby.setCity(n, parent1.getRoute().get(n));
			}
			for(int j = 0; j < index2; j++){
				baby.setCity(j, parent1.getRoute().get(j));
			}
		}
		for(int k = 0; k < parent2.getRoute().size(); k++){
			if(!baby.getRoute().contains(parent2.getRoute().get(k))){
				for(int m = 0; m < baby.getRoute().size(); m++){		// can probs make this better with some math
					if(baby.getRoute().get(m) == null){
						baby.setCity(m, parent2.getRoute().get(k));
						break;
					}
				}
			}
		}
		return baby;
	}

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
	
	public static CityRoute GeneticSolver(CityRoute solvingRoute){
		RoutePop solvedRoute = new RoutePop();
		solvedRoute.initialize2(100, solvingRoute);
		for(int i = 0; i < 1000; i++){
			solvedRoute = evolvePop(solvedRoute);
		}
		return solvedRoute.bestRoute();
	}
	
}
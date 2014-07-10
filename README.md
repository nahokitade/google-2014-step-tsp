Google 2014 STEP - Travelling Salesman Problem Challenges
-----

This repository includes the assignment for Google 2014 Step Class 5 & 6.

- Guide  http://bit.ly/google-2014-step-tsp
- Leaderboard: http://bit.ly/google-2014-step-tsp-leaderboard
- Visualizer Demo: http://hayatoito.github.io/google-2014-step-tsp/visualizer/


Implemented the following algorithms:

- Random
- Greedy
- All Permutations
- Simulated Annealing 
- Genetic Algorithm
- Furthest Insertion

- Random
Not optimum at all. The easiest but worse algorithm out of all

- Greedy
Relatively good algorithm. Solves TSP quite quickly and the result is much
better than Random. Some other more complicated algorithms cannot beat this.

- All Permutations
The only exact algorithm. Does not solve any TSP with more than 10 or so cities
because of stack overflow (uses recursion)

- Simulated Annealing
Starting the Annealing from a greedy solution sometimes improves results with
small city counts but barely does nothing for the larger city counts. Starting
with a random solution takes very long and does worse than Greedy with too many
cities.

- Genetic Algorithm
Basically does as well as SA. Quite slow (as slow as SA) and usually does not
beat greedy with random initial population.

- Furthest Insertion
Best out of all other implemented algorithm. Other algorithm tends to create
crosses but furthest insertion does not. The solution changes with different 
starting nodes, and from experience, I have found that the best solutions start
with the cities closest to the edge of where all the other cities are. 

Comments on the codes should describe/give ideas for how the algorithm works. 






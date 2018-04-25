package eightqueens;

import java.util.List;

import genetic.Cromosome;
import genetic.StopCondition;

public class EightQueensStopCondition implements StopCondition{
	
	int rounds = 0;
	int maxRounds;
	
	public EightQueensStopCondition(int maxRounds) {
		super();
		this.maxRounds = maxRounds;
	}
	
	private boolean foundSolution(List<Cromosome> population) {
		for (int i = 0; i < population.size(); i++) {
			if (population.get(i).getScore() == 8) {
				System.out.println("Found solution: " + population.get(i));
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean shouldStop(List<Cromosome> population) {
		rounds++;
		System.out.println("ROUND " + rounds);
		
		return foundSolution(population) || rounds > maxRounds;
	}

}

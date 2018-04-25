package genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {

	private Random random;
	private float crossoverRate;
	private int populationLimit;
	private float mutationRate;

	public GeneticAlgorithm(float crossoverRate, int populationLimit, float mutationRate) {
		this.crossoverRate = crossoverRate;
		this.populationLimit = populationLimit;
		this.mutationRate = mutationRate;
		this.random = new Random();
	}
	
	public List<Cromosome> evolve(List<Cromosome> initialPopulation, StopCondition stopCondition) {
		List<Cromosome> population = new ArrayList<Cromosome>();
		population.addAll(initialPopulation);
		
		while(!stopCondition.shouldStop(population)) {
			population = getNextGeneration(population);
		}
		
		return population;
	}
	
	public List<Cromosome> getNextGeneration(List<Cromosome> population) {
		List<Cromosome> selected = selectMany(population, population.size());
		crossover(selected);
		
		List<Cromosome> nextGeneration;
		if (selected.size() > populationLimit) {
			nextGeneration = selectMany(selected, populationLimit);
		}
		else {
			nextGeneration = selected;
		}
		
		performMutations(nextGeneration);
		
		return nextGeneration;
	}

	private List<Cromosome> selectMany(List<Cromosome> population, int size) {
		List<Cromosome> selected = new ArrayList<Cromosome>();
		for (int i = 0; i < size; i++) {
			selected.add(select(population));
		}
		return selected;
	}
	
	private Cromosome select(List<Cromosome> population) {
		float percent = random.nextFloat() * 100;
		for (Cromosome c : population) {
			float relativeScore = getRelativeScore(c, population);
			if (percent < relativeScore) {
				return c;
			}
			percent = percent - relativeScore;
		}
		return population.get(0);
	}
	
	private float getRelativeScore(Cromosome c, List<Cromosome> population) {
		return 100 * c.getScore() / getTotalScore(population);
	}
	
	private float getTotalScore(List<Cromosome> population) {
		float total = 0;
		for (Cromosome c : population) {
			total = total + c.getScore();
		}
		return total;
	}
	
	private Cromosome getRandomCromosome(List<Cromosome> population) {
		return population.get(random.nextInt(population.size()));
	}
	
	private void crossover(List<Cromosome> population) {
		int times = (int) (Math.round( population.size() * crossoverRate / 2  ));
		for(int i = 0; i < times; i ++) {
			Cromosome c1 = getRandomCromosome(population);
			Cromosome c2 = getRandomCromosome(population);
			List<Cromosome> children = c1.crossover(c2);
			for(Cromosome child : children) {
				child.repair();
			}
			population.addAll(children);
		}
	}
	
	private void performMutations(List<Cromosome> population) {
		int times = (int) (Math.ceil( population.size() * mutationRate));
		
		for(int i = 0; i < times; i++) {
			getRandomCromosome(population).mutate();
		}
	}
	
}

package eightqueens;

import java.util.List;

import javax.swing.JOptionPane;

import genetic.Cromosome;
import genetic.GeneticAlgorithm;
import genetic.StopCondition;

public class Main {
	
	static final float CROSSOVER_RATE = 0.8f;
	static final int POPULATION_SIZE = 50;
	static final float MUTATION_RATE = 0.2f;
	static final int GENERATION_LIMIT = 1000;

	public static void main(String[] args) {
		
		List<Cromosome> initialPopulation = EightQueensCromosome.generatePopulation(POPULATION_SIZE);
		StopCondition stopCondition = new EightQueensStopCondition(GENERATION_LIMIT);
		GeneticAlgorithm ga = new GeneticAlgorithm(CROSSOVER_RATE, POPULATION_SIZE, MUTATION_RATE);
		List<Cromosome> finalPopulation = ga.evolve(initialPopulation, stopCondition);
		
		EightQueensCromosome solution = extractEightQueenSolution(finalPopulation);
		if (solution == null) {
			JOptionPane.showMessageDialog(null, "Could not find a solution");
		}
		else {
			new EightQueensDisplay( solution);
		}
		
	}
	
	static EightQueensCromosome extractEightQueenSolution(List<Cromosome> population) {
		for(Cromosome c : population) {
			if (c.getScore() == 8) {
				return (EightQueensCromosome) c;
			}
		}
		return null;
	}
	
}

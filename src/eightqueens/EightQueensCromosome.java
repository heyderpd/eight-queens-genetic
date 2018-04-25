package eightqueens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import genetic.Cromosome;

public class EightQueensCromosome implements Cromosome{

	private List<Integer> positions;

	private static Random random = new Random();
	
	public EightQueensCromosome(List<Integer> positions) {
		this.positions = new ArrayList<Integer>();
		this.positions.addAll(positions);
	}
	
	private boolean isInDanger(int position) {
		int queen = this.positions.get(position);
		for (int i = 0; i < this.positions.size() ; i++) {
			if (i == position) continue;
			int colsDistance = Math.abs(position - i);
			if (queen + colsDistance == this.positions.get(i)) {
				return true;
			}
			if (queen - colsDistance == this.positions.get(i)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public float getScore() {
		int score = 8;
		for (int i = 0; i < this.positions.size(); i++) {
			if (isInDanger(i)) {
				score = score - 1;
			}
		}
		return score;
	}
	
	public List<Integer> getPositions() {
		List<Integer> positions = new ArrayList<Integer>();
		positions.addAll(this.positions);
		return positions;
	}
	
	private boolean isDuplicate(int position) {
		int value = this.positions.get(position);
		for (int i = 0; i < this.positions.size(); i++) {
			if (i == position) continue;
			if (value == this.positions.get(i)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void mutate() {
		int position1 = random.nextInt(8);
		int position2 = random.nextInt(8);
		
		int value1 = this.positions.get(position1);
		int value2 = this.positions.get(position2);
		
		this.positions.set(position1, value2);
		this.positions.set(position2, value1);
	}

	@Override
	public void repair() {
		for (int i = 0; i < this.positions.size(); i++) {
			if (isDuplicate(i)) {
				repair(i);
			}
		}
	}
	
	private void repair(int position) {
		this.positions.set(position, findUnusedValue());
	}
	
	private static int getRandomValue() {
		return random.nextInt(8) + 1;
	}

	private Integer findUnusedValue() {
		int randomValue = getRandomValue();
		int rounds = 0;
		while (this.positions.contains(randomValue) && rounds < 100) {
			rounds ++;
			randomValue = getRandomValue();
		}
		return randomValue;
	}

	@Override
	public List<Cromosome> crossover(Cromosome other) {
		EightQueensCromosome otherCrom = (EightQueensCromosome) other;
		List<Cromosome> children = new ArrayList<Cromosome>();
		
				
		List<Integer> child1 = new ArrayList<Integer>();
		child1.addAll(this.positions.subList(0, 4));
		child1.addAll(otherCrom.positions.subList(4,  8));
		children.add(new EightQueensCromosome(child1));
		
		List<Integer> child2 = new ArrayList<Integer>();
		child2.addAll(otherCrom.positions.subList(0,  4));
		child2.addAll(this.positions.subList(4, 8));
		children.add(new EightQueensCromosome(child2));
		
		return children;
	}

	@Override
	public boolean checkIntegrity() {
		if (this.positions == null) {
			return false;
		}
		if (this.positions.size() != 8) {
			return false;
		}
		for (Integer position : this.positions) {
			if (position == null || position < 0 || position > 8) {
				return false;
			}
		}
		if (new HashSet<>(this.positions).size() != this.positions.size()) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		String joined = "[";
		for (int i = 0; i < this.positions.size(); i++) {
			joined = joined + (i == 0 ? "" : ", ") + this.positions.get(i);
		}
		return joined + "]";
	}
	
	static List<Cromosome> generatePopulation(int size) {
		List<Cromosome> population = new ArrayList<Cromosome>(); 
		
		for (int i = 0; i < size; i++) {			
			Cromosome c = new EightQueensCromosome(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1));
			c.repair();
			population.add(c);
		}
		
		return population;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((positions == null) ? 0 : positions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EightQueensCromosome other = (EightQueensCromosome) obj;
		if (positions == null) {
			if (other.positions != null)
				return false;
		} else if (!positions.equals(other.positions))
			return false;
		return true;
	}

}

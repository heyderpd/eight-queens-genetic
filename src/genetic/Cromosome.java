package genetic;

import java.util.List;

public interface Cromosome {

	float getScore();
	void repair();
	void mutate();
	boolean checkIntegrity();
	List<Cromosome> crossover(Cromosome other);
	
}

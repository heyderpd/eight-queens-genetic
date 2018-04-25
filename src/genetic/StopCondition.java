package genetic;

import java.util.List;

public interface StopCondition {

	boolean shouldStop(List<Cromosome> population);
	
}

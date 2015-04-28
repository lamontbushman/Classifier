import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Classifies everything the same (Iris-setosa)
 * @author Lamont Bushman
 */
public class HardCodedClassifier extends Classifier{
	private static final long serialVersionUID = 1L;

	@Override
	public void buildClassifier(Instances instances) throws Exception {
	}
	
	/**
	 * Arbitrarily chooses one of the classes (Iris-setosa) to return.
	 */
	@Override
	public double classifyInstance(Instance instance) {
		return 0;
	}
}

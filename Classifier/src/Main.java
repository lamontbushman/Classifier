import weka.classifiers.Evaluation;
import weka.core.Debug.Random;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.instance.RemovePercentage;

/**
 * Reads in the iris data supplied via the command line, 
 * randomizes it, trains the data on 70% of the data,
 * tests it on 30%, and displays the results.
 * @author Lamont Bushman
 */
public class Main {
	private Instances trainingData;
	private Instances testData;
	private static final double TESTING_PERCENTAGE = 30;
	
	/**
	 * 1. Reads in the data.
	 * 2. Randomizes the data.
	 * 3. Separates the data between training data (70%) and testing data (30%).
	 * @param fileName the name of the CSV file.
	 */
	Main(String fileName){
		Instances instances = null;
		try {
			DataSource source = new DataSource(fileName);
			instances = source.getDataSet();
			//The class is in the fifth column.
			instances.setClassIndex(4);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Couldn't open the dataset.");
			System.exit(0);
		}
		
		instances.randomize(new Random());
		
		RemovePercentage rp = new RemovePercentage();
		//Sets the percentage to remove from instances.
		rp.setPercentage(TESTING_PERCENTAGE);
		try {
			rp.setInputFormat(instances);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("Couldn't set the input format for RemovePercentage.");
		}
		try {
			trainingData = RemovePercentage.useFilter(instances,rp);
			//Couldn't figure out how to invert the selection w/o creating a separate object.
			rp = new RemovePercentage();
			rp.setPercentage(TESTING_PERCENTAGE);
			rp.setInputFormat(instances);
			//Inverts the percentage. Removes (100 - TESTING_PERCENTAGE)%.
			rp.setInvertSelection(true);
			testData = RemovePercentage.useFilter(instances, rp);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Couldn't filter the dataset.");
			System.exit(0);
		}
	}
	
	/**
	 * tests the data against the HardCodedClassifier.
	 */
	public void runHardCodedTest() {
		Evaluation eval;
		try {
			eval = new Evaluation(trainingData);
			eval.evaluateModel(new HardCodedClassifier(), testData);
			System.out.println(eval.toSummaryString("\nResults\n=======\n", false));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Couldn't evaluate data.");
		}
	}
	
	/**
	 * Initializes the class with args[0] and tests the HardCodedClassifier.
	 * @param args the CSV file
	 */
	public static void main(String args[]) {
		if(args.length != 1) {
			System.out.println("Usage: provide a file for the dataset.\n"
					+ "The columns of the CSV file shall be: Sepal Length, Sepal Width, "
					+ "Petal Length, Petal Width, and Class.");
			System.exit(0);
		}
		
		Main main = new Main(args[0]);
		main.runHardCodedTest();
	}
}

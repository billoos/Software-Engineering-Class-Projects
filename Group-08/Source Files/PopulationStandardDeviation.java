import java.util.ArrayList;
import java.util.List;

public class PopulationStandardDeviation extends Formula {
	String name = "Population Standard Deviation";
	String formula = "σ = ([Σ(x - u)2]/N)^1/2";
	String description = "The amount of variation of dispersion of a set of values in the population";
	boolean orderedPair = false;
	Mean mean = new Mean();

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getFormula() {
		return formula;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public boolean getOrderedPair() {
		return orderedPair;
	}

	@Override
	public String calculate(List<Double> data) {
		double sum = 0;
		double average = Double.parseDouble(mean.calculate(data));
		if (!(data.isEmpty())) {
			for (int i = 0; i < data.size(); i++) {
				sum += Math.pow((data.get(i) - average), 2);
			}
			return Double.toString(Math.sqrt(sum / (data.size())));
		}
		return null;
	}

	@Override
	public String calculate(double[][] data) {
		// TODO Auto-generated method stub
		return null;
	}

	// test bed main
	public static void main(String[] args) {
		System.out.println("Population Standard Deviation");
		PopulationStandardDeviation tb = new PopulationStandardDeviation();
		List<Double> data = new ArrayList<Double>();
		data.add((double) 1);
		data.add((double) -6);
		data.add((double) 5);
		data.add((double) 4);
		data.add((double) 3);
		data.add((double) 15);
		// System.out.println(tb.calculate(data));
		System.out.println(tb.getFormula());
		// expected output = 6.209312003399052
	}
}

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

public class SampleStandardDeviation extends Formula {
	String name = "Sample Standard Deviation";
	String formula = "σ = ([Σ(x - u)2]/N-1)^1/2";
	String description = "The amount of variation or dispersion of a set of values in a sample";
	boolean orderedPair = false;
	StandardDeviation standardDeviation = new StandardDeviation();

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
		if (!(data.isEmpty())) {
			return Double.toString(standardDeviation.evaluate(super.ConvertToArray(data)));
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
		System.out.println("Standard Deviation");
		SampleStandardDeviation tb = new SampleStandardDeviation();
		List<Double> data = new ArrayList<Double>();
		data.add((double) 1);
		data.add((double) -6);
		data.add((double) 5);
		data.add((double) 4);
		data.add((double) 3);
		data.add((double) 15);
		// System.out.println(tb.calculate(data));
		System.out.println(tb.getFormula());
		// expected output = 6.801960501698511
	}
}

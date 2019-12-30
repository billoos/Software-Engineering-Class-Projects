import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinimumValue extends Formula {
	String name = "Minimum Value";
	String formula = "Smallest number in data set";// is there a formula
	String description = "The smallest numerical value in a data set";
	boolean orderedPair = false;

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
		if (!(data.isEmpty()))
			return Double.toString(Collections.min(data));
		return null;
	}

	@Override
	public String calculate(double[][] data) {
		// TODO Auto-generated method stub
		return null;
	}

	// test bed main
	public static void main(String[] args) {
		System.out.println("Minimum");
		MinimumValue tb = new MinimumValue();
		List<Double> data = new ArrayList<Double>();
		data.add((double) 1);
		data.add((double) 6);
		data.add((double) -5);
		data.add((double) 4);
		data.add((double) 3);
		data.add((double) 2);
		System.out.println(tb.calculate(data));
		// expected output = -5
	}
}

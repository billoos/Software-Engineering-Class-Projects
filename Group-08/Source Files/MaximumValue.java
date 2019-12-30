import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaximumValue extends Formula {
	String name = "Maximum Value";
	String formula = "Largest value in the data set";
	String description = "The largest numerical value in a data set";
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
		return formula;
	}

	@Override
	public String calculate(List<Double> data) {
		if (!(data.isEmpty()))
			return Double.toString(Collections.max(data));
		return null;
	}

	@Override
	public boolean getOrderedPair() {
		return orderedPair;
	}

	@Override
	public String calculate(double[][] data) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		System.out.println("Maximum");
		MaximumValue tb = new MaximumValue();
		List<Double> data = new ArrayList<Double>();
		data.add((double) 1);
		data.add((double) 6);
		data.add((double) 5);
		data.add((double) 4);
		data.add((double) 15);
		data.add((double) 2);
		System.out.println(tb.calculate(data));
		// expected output = 15
	}
}

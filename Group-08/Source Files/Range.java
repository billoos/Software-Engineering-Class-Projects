import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Range extends Formula {
	String name = "Range";
	String formula = "Largest value - smallest value";
	String description = "The range (difference between) the smallest and largest value in the data set";
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
		Collections.sort(data);
		if (!(data.isEmpty()))
			return Double.toString(data.get(data.size() - 1) - data.get(0));
		return null;
	}

	@Override
	public String calculate(double[][] data) {
		// TODO Auto-generated method stub
		return null;
	}

	// test bed main
	public static void main(String[] args) {
		System.out.println("Range");
		Range tb = new Range();
		List<Double> data = new ArrayList<Double>();
		data.add((double) 3);
		data.add((double) 6);
		data.add((double) 5);
		data.add((double) 4);
		data.add((double) 3);
		data.add((double) 15);
		System.out.println(tb.calculate(data));
		// expected output = 12
	}
}

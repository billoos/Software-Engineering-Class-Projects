import java.util.List;
import java.util.ArrayList;

public class Mean extends Formula {
	String name = "Mean";
	String formula = "sum of terms / number of term";
	String description = "Calculates the average of a set of data points";
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
	public String calculate(double[][] data) {
		return null;
	}

	@Override
	public String calculate(List<Double> data) {
		double sum = 0;
		if (!(data.isEmpty())) {
			for (int i = 0; i < data.size(); i++)
				sum = sum + data.get(i);
			// return sum / data.size();
			return Double.toString(sum / data.size());
		}
		return null;
	}

	// test bed main
	public static void main(String[] args) {
		System.out.println("Mean");
		Mean tb = new Mean();
		List<Double> data = new ArrayList<Double>();
		data.add((double) 10);
		data.add((double) 10);
		data.add((double) 20);
		data.add((double) 40);
		data.add((double) 70);
		System.out.println(data);
		System.out.println(tb.calculate(data));
		// expected output = 30
	}
}

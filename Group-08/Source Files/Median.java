import java.util.ArrayList;
import java.util.List;

public class Median extends Formula {
	String name = "Median";
	String formula = "(n + 1) / 2";
	String description = "The middle number of the data set";
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String calculate(List<Double> data) {
		if (!(data.isEmpty())) {
			sort(data);// data must be in sorted order
			if (data.size() % 2 != 0)// if odd number of elements
				return Double.toString((double) data.get(data.size() / 2));
			return Double.toString((double) (data.get(data.size() / 2) + data.get(data.size() / 2 - 1)) / 2);
		}
		return null;
	}

	// test bed main
	public static void main(String[] args) {
		System.out.println("Median");
		Median tb = new Median();
		List<Double> data = new ArrayList<Double>();
		data.add((double) 1);
		data.add((double) 2);
		data.add((double) 3);
		data.add((double) 4);
		data.add((double) 5);
		data.add((double) 6);
		System.out.println(tb.calculate(data));
		// expected output = 3.5
	}
}
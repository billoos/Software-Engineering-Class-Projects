import java.util.Collections;
import java.util.List;

public abstract class Formula {

	public abstract String getName();
	public abstract String getFormula();
	public abstract String getDescription();
	public abstract boolean getOrderedPair();
	public abstract String calculate(List<Double> data);
	public abstract String calculate(double[][] data);

	// Convert ArrayList of Double objects to primitive Double array
	public double[] ConvertToArray(List<Double> data) {
		if (!(data.isEmpty())) {
			double[] arr = new double[data.size()];
			for (int i = 0; i < data.size(); i++)// converting Double to primitive double
				arr[i] = data.get(i);
			return arr;
		}
		return null;
	}

	@Override
	public String toString() {
		return this.getName();
	}

	// Sort the ArrayList
	public void sort(List<Double> data) {
		Collections.sort(data);
	}
}

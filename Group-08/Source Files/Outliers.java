import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Outliers extends Formula {
	String name = "Outliers";
	String formula = " x <= or x>= q1 - q3";
	String description = "Data points that lie far outside the normal data trends";
	boolean orderedPair = false;
	Median median = new Median();

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
		List<Double> outliers = new ArrayList<Double>();
		List<Double> data1 = new ArrayList<Double>();
		List<Double> data2 = new ArrayList<Double>();
		String returnValue = "";
		if (data.size() % 2 == 0) {
			data1 = data.subList(0, data.size() / 2);
			data2 = data.subList(data.size() / 2, data.size());
		} else {
			data1 = data.subList(0, data.size() / 2);
			data2 = data.subList(data.size() / 2 + 1, data.size());
		}
		double q1 = Double.parseDouble(median.calculate(data1));
		double q3 = Double.parseDouble(median.calculate(data2));
		double iqr = q3 - q1;
		double lowerFence = q1 - 1.5 * iqr;
		double upperFence = q3 + 1.5 * iqr;
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i) < lowerFence || data.get(i) > upperFence) {
				outliers.add(data.get(i));
				returnValue = returnValue + data.get(i) + ", ";
			}
		}
		return returnValue;
	}

	@Override
	public String calculate(double[][] data) {
		// TODO Auto-generated method stub
		return null;
	}

	// test bed main
	public static void main(String[] args) {
		System.out.println("Outliers");
		Outliers tb = new Outliers();
		List<Double> data = new ArrayList<Double>();
		data.add((double) 87);
		data.add((double) 75);
		data.add((double) 79);
		data.add((double) 87);
		data.add((double) 88);
		data.add((double) 85);
		data.add((double) 89);
		data.add((double) 90);
		data.add((double) 75);
		data.add((double) 79);
		data.add((double) 105);
		String out = tb.calculate(data);
		System.out.println(out);
		// expected output 105
	}
}

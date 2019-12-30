import java.util.ArrayList;
import java.util.List;

public class Mode extends Formula {
	String name = "Mode";
	String formula = "Number that appears most often";// is there really a formula???
	String description = "The number that appears most often in a data set";
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
		if (!(data.isEmpty())) {
			double mode = data.get(0);
			int maxCount = 0;
			for (int i = 0; i < data.size(); i++) {
				double value = data.get(i);
				int count = 1;
				for (int j = 0; j < data.size(); j++) {// checking for recurrences of i
					if (data.get(j) == value) {
						count++;
					}
					if (count > maxCount) {// if there was more of number n, n in new mode
						mode = value;
						maxCount = count;
					}
				}
			}
			return Double.toString(mode);
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
		System.out.println("Mode");
		Mode tb = new Mode();
		List<Double> data = new ArrayList<Double>();
		data.add((double) 2);
		data.add((double) 6);
		data.add((double) 5);
		data.add((double) 4);
		data.add((double) 6);
		data.add((double) 1);
		System.out.println(tb.calculate(data));
		// expected output = 6
	}
}

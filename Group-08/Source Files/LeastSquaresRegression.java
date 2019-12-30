import java.util.List;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public class LeastSquaresRegression extends Formula {
	String name = "Least-Square Regression";
	String formula = "";
	String description = "";
	boolean orderedPair = true;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String calculate(double[][] data) {
		SimpleRegression regression = new SimpleRegression();
		regression.addData(data);
		if (regression.getIntercept() < 0)
			return "y = " + regression.getSlope() + "x " + regression.getIntercept();
		else
			return "y = " + regression.getSlope() + "x + " + regression.getIntercept();
	}

	// test bed main
	public static void main(String[] args) {
		System.out.println("Least-Squares Regression");
		LeastSquaresRegression tb = new LeastSquaresRegression();
		double[][] arr = new double[][] { { 0, -1 }, { 1, 0.2 }, { 2, 0.9 }, { 3, 2.1 } };// new double[10][20]
		System.out.println(tb.calculate(arr));
		// expected output = y = 1.0x -0.95
	}
}

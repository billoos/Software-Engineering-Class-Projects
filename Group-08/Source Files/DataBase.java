import java.util.ArrayList;
import java.util.List;

public class DataBase {
	public List<Formula> formulaList = new ArrayList<Formula>();
	private Mean mean = new Mean();
	private Median median = new Median();
	private Mode mode = new Mode();
	private MinimumValue minimum = new MinimumValue();
	private MaximumValue maximum = new MaximumValue();
	private Range range = new Range();
	private SampleStandardDeviation sampleStandardDeviation = new SampleStandardDeviation();
	private PopulationStandardDeviation populationStandardDeviation = new PopulationStandardDeviation();
	private Outliers outliers = new Outliers();

	DataBase() {
		formulaList.add(mean);
		formulaList.add(median);
		formulaList.add(mode);
		formulaList.add(minimum);
		formulaList.add(maximum);
		formulaList.add(range);
		formulaList.add(sampleStandardDeviation);
		formulaList.add(populationStandardDeviation);
		formulaList.add(outliers);
	}

	public Formula returnObject(String objectName) {
		for (int i = 0; i < formulaList.size(); i++) {
			if (objectName == formulaList.get(i).getName()) {
				return formulaList.get(i);
			}
		}
		return null;
	}

	public int objectIndex(String objectName) {
		for (int i = 0; i < formulaList.size(); i++) {
			if (objectName == formulaList.get(i).getName()) {
				return i;
			}
		}
		return 0;
	}
}

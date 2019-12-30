import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class CalculatorGUI extends JFrame {
	public static CalculatorGUI calculatorFrame = new CalculatorGUI();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTextPane answerTextBox;
	private JLabel enterDataLabel;
	private static JLabel formulaLabel;
	private JTextPane dataInput;
	private JComboBox<String> selectFormulaDropDown = new JComboBox<String>();
	private List<Double> data = new ArrayList<Double>();
	private DataBase dataBase = new DataBase();
	private JButton selectFormulaButton;
	private JButton selectDataSetButton;
	private JButton backButton;
	static boolean updated = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenuGUI.menuFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CalculatorGUI() {
		int interval = 1000; // repeating every 1000 ms
		new Timer(interval, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (formulaLabel.getText() != selectFormulaDropDown.getSelectedItem()
						&& formulaLabel.getText() != "Formula" && !updated) {
					selectFormulaDropDown.setSelectedIndex(dataBase.objectIndex(formulaLabel.getText()));
					answerTextBox.setText(
							dataBase.returnObject(selectFormulaDropDown.getSelectedItem().toString()).getFormula());
					updated = true;
				}
			}
		}).start();
		for (int i = 0; i < dataBase.formulaList.size(); i++) {
			selectFormulaDropDown.addItem(dataBase.formulaList.get(i).getName());
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 572, 261);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton calculateButton = new JButton("Calculate");
		calculateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (formulaLabel.getText() != selectFormulaDropDown.getSelectedItem()) {
					selectFormulaDropDown.setSelectedIndex(dataBase.objectIndex(formulaLabel.getText()));
				}
				formulaLabel.setText(selectFormulaDropDown.getSelectedItem().toString());
				String str = dataInput.getText();
				if (!(str.isEmpty())) {
					String[] ns = str.split(",");
					for (String s : ns) {
						data.add(Double.parseDouble(s));// splitting input string to the digits
					}
					int index = 0;
					for (int i = 0; i < dataBase.formulaList.size(); i++) {
						if (dataBase.formulaList.get(i).getName() == selectFormulaDropDown.getSelectedItem().toString()) {// getting the formula from the data base
							index = i;
							break;
						}
					}
					answerTextBox.setText(dataBase.formulaList.get(index).calculate(data));// setting the calculation output
				}
				data.clear();
			}
		});
		calculateButton.setBounds(371, 191, 170, 23);
		contentPane.add(calculateButton);

		@SuppressWarnings("rawtypes")
		JComboBox dataSheetDropDown = new JComboBox();
		dataSheetDropDown.setBounds(116, 100, 97, 20);
		contentPane.add(dataSheetDropDown);

		dataInput = new JTextPane();
		dataInput.setText("Select formula" + "\nEnter data ex. 1,2,3,4");
		dataInput.setBounds(427, 100, 114, 81);
		contentPane.add(dataInput);

		formulaLabel = new JLabel("Formula");
		formulaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		formulaLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		formulaLabel.setBounds(9, 11, 537, 43);
		contentPane.add(formulaLabel);

		enterDataLabel = new JLabel("Enter Data");
		enterDataLabel.setHorizontalAlignment(SwingConstants.CENTER);
		enterDataLabel.setBounds(412, 80, 134, 20);
		contentPane.add(enterDataLabel);

		JButton formulaInfoButton = new JButton("Formula Information");
		formulaInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calculatorFrame.setVisible(false);
				GlossaryGUI.glossaryFrame.setVisible(true);
			}
		});
		formulaInfoButton.setBounds(189, 191, 170, 23);
		contentPane.add(formulaInfoButton);

		selectFormulaDropDown.setBounds(9, 100, 97, 20);
		contentPane.add(selectFormulaDropDown);

		selectFormulaButton = new JButton("Select Formula");
		selectFormulaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				formulaLabel.setText(selectFormulaDropDown.getSelectedItem().toString());// getting formula name from formula drop down
				Formula temp = dataBase.returnObject(selectFormulaDropDown.getSelectedItem().toString());// get formula object from data base
				answerTextBox.setText(temp.getFormula());
				dataInput.setText("");
				updated = true;
			}
		});
		selectFormulaButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		selectFormulaButton.setBounds(9, 80, 97, 21);
		contentPane.add(selectFormulaButton);

		selectDataSetButton = new JButton("Select Data Set");
		selectDataSetButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		selectDataSetButton.setBounds(116, 80, 97, 21);
		contentPane.add(selectDataSetButton);

		backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calculatorFrame.setVisible(false);
				MainMenuGUI.menuFrame.setVisible(true);
			}
		});
		backButton.setBounds(9, 191, 170, 23);
		contentPane.add(backButton);

		answerTextBox = new JTextPane();
		answerTextBox.setText("z = (x \u2013 \u03BC) / (\u03C3 / \u221An)");
		answerTextBox.setBounds(230, 100, 187, 81);
		contentPane.add(answerTextBox);
	}

	public static void setCalculator(String formula) {
		formulaLabel.setText(formula);
		updated = false;
	}
}

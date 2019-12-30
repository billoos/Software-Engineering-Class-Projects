import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class GlossaryGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static GlossaryGUI glossaryFrame = new GlossaryGUI();
	private DataBase dataBase = new DataBase();
	private JPanel contentPane;
	private JPanel panel_1;
	private JScrollPane scrollPane_1;
	private JTextField searchBoxTextBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GlossaryGUI frame = new GlossaryGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GlossaryGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 472, 323);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(0, 0, 455, 284);
		contentPane.add(scrollPane_1);

		panel_1 = new JPanel();
		scrollPane_1.setViewportView(panel_1);
		panel_1.setBackground(Color.WHITE);
		panel_1.setForeground(Color.WHITE);
		panel_1.setPreferredSize(new Dimension(600, 800));
		panel_1.setLayout(null);

		JLabel titleLabel = new JLabel("Statistical Glossary");
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		titleLabel.setBounds(131, 11, 146, 22);
		panel_1.add(titleLabel);

		searchBoxTextBox = new JTextField();
		searchBoxTextBox.setHorizontalAlignment(SwingConstants.CENTER);
		searchBoxTextBox.setText("Search for Formla");
		searchBoxTextBox.setBounds(141, 34, 136, 20);
		panel_1.add(searchBoxTextBox);
		searchBoxTextBox.setColumns(10);

		JLabel formulaLabel = new JLabel("Formula");
		formulaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		formulaLabel.setBounds(10, 65, 46, 14);
		panel_1.add(formulaLabel);

		JLabel descriptionLabel = new JLabel("Description");
		descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		descriptionLabel.setBounds(167, 65, 66, 14);
		panel_1.add(descriptionLabel);

		JLabel options = new JLabel("Options");
		options.setHorizontalAlignment(SwingConstants.CENTER);
		options.setBounds(333, 65, 46, 14);
		panel_1.add(options);

		JLabel meanFormulaLabel = new JLabel("Mean");
		meanFormulaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		meanFormulaLabel.setBounds(10, 90, 111, 50);
		panel_1.add(meanFormulaLabel);

		JLabel lblMeanDescription = new JLabel(convertToMultiline(dataBase.returnObject("Mean").getDescription()));
		lblMeanDescription.setBounds(131, 90, 146, 50);
		panel_1.add(lblMeanDescription);

		JButton calculateMeanButton = new JButton("Calculate Mean");
		calculateMeanButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				glossaryFrame.setVisible(false);
				CalculatorGUI.setCalculator("Mean");
				CalculatorGUI.calculatorFrame.setVisible(true);
			}
		});
		calculateMeanButton.setBounds(287, 104, 146, 23);
		panel_1.add(calculateMeanButton);

		JLabel medianFormulaLabel = new JLabel("Median");
		medianFormulaLabel.setBounds(10, 151, 111, 70);
		panel_1.add(medianFormulaLabel);

		JLabel medianDescriptionLabel = new JLabel(
				convertToMultiline(dataBase.returnObject("Median").getDescription()));
		medianDescriptionLabel.setBounds(131, 151, 146, 70);
		panel_1.add(medianDescriptionLabel);

		JButton btnCalculateMedian = new JButton("Calculate Median");
		btnCalculateMedian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				glossaryFrame.setVisible(false);
				CalculatorGUI.setCalculator("Median");
				CalculatorGUI.calculatorFrame.setVisible(true);
			}
		});
		btnCalculateMedian.setBounds(287, 175, 146, 23);
		panel_1.add(btnCalculateMedian);

		JLabel modeFormulaLabel = new JLabel("Mode");
		modeFormulaLabel.setBounds(10, 232, 111, 50);
		panel_1.add(modeFormulaLabel);

		JLabel modeDescriptionLabel = new JLabel(convertToMultiline(dataBase.returnObject("Mode").getDescription()));
		modeDescriptionLabel.setBounds(131, 232, 146, 50);
		panel_1.add(modeDescriptionLabel);

		JButton btnCalculateMode = new JButton("Calculate Mode");
		btnCalculateMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				glossaryFrame.setVisible(false);
				CalculatorGUI.setCalculator("Mode");
				CalculatorGUI.calculatorFrame.setVisible(true);
			}
		});
		btnCalculateMode.setBounds(287, 246, 146, 23);
		panel_1.add(btnCalculateMode);

		JLabel minimumFormulaLabel = new JLabel("Minimum");
		minimumFormulaLabel.setBounds(10, 293, 111, 50);
		panel_1.add(minimumFormulaLabel);

		JLabel minimumDescriptionLabel = new JLabel(
				convertToMultiline(dataBase.returnObject("Minimum Value").getDescription()));
		minimumDescriptionLabel.setBounds(131, 293, 146, 50);
		panel_1.add(minimumDescriptionLabel);

		JButton btnCalculateMinimum = new JButton("Calculate Minimum");
		btnCalculateMinimum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				glossaryFrame.setVisible(false);
				CalculatorGUI.setCalculator("Minimum Value");
				CalculatorGUI.calculatorFrame.setVisible(true);
			}
		});
		btnCalculateMinimum.setBounds(287, 307, 146, 23);
		panel_1.add(btnCalculateMinimum);

		JLabel maximumFormulaLabel = new JLabel("Maximum Value");
		maximumFormulaLabel.setBounds(10, 354, 111, 50);
		panel_1.add(maximumFormulaLabel);

		JLabel maximumDescriptionLabel = new JLabel(
				convertToMultiline(dataBase.returnObject("Maximum Value").getDescription()));
		maximumDescriptionLabel.setBounds(131, 354, 146, 50);
		panel_1.add(maximumDescriptionLabel);

		JButton btnCalculateMaximum = new JButton("Calculate Maximum");
		btnCalculateMaximum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				glossaryFrame.setVisible(false);
				CalculatorGUI.setCalculator("Maximum");
				CalculatorGUI.calculatorFrame.setVisible(true);
			}
		});
		btnCalculateMaximum.setBounds(287, 368, 146, 23);
		panel_1.add(btnCalculateMaximum);

		JLabel rangeFormulaLabel = new JLabel("Range");
		rangeFormulaLabel.setBounds(10, 415, 111, 60);
		panel_1.add(rangeFormulaLabel);

		JLabel rangeDescriptionLabel = new JLabel(convertToMultiline(dataBase.returnObject("Range").getDescription()));
		rangeDescriptionLabel.setBounds(131, 415, 146, 60);
		panel_1.add(rangeDescriptionLabel);

		JButton btnCalculateRange = new JButton("Calculate Range");
		btnCalculateRange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				glossaryFrame.setVisible(false);
				CalculatorGUI.setCalculator("Range");
				CalculatorGUI.calculatorFrame.setVisible(true);
			}
		});
		btnCalculateRange.setBounds(287, 434, 146, 23);
		panel_1.add(btnCalculateRange);

		JLabel sampleStandardDeviationLabel = new JLabel(convertToMultiline("Sample Standard Deviation"));
		sampleStandardDeviationLabel.setBounds(10, 486, 111, 50);
		panel_1.add(sampleStandardDeviationLabel);

		JLabel sampleStandardDeviationDescriptionLabel = new JLabel(
				convertToMultiline(dataBase.returnObject("Sample Standard Deviation").getDescription()));
		sampleStandardDeviationDescriptionLabel.setBounds(131, 486, 146, 50);
		panel_1.add(sampleStandardDeviationDescriptionLabel);

		JButton btnCalculateSampleStandard = new JButton(convertToMultiline("Calculate Sample Standard Deviation"));
		btnCalculateSampleStandard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				glossaryFrame.setVisible(false);
				CalculatorGUI.setCalculator("Sample Standard Deviation");
				CalculatorGUI.calculatorFrame.setVisible(true);
			}
		});
		btnCalculateSampleStandard.setBounds(287, 486, 146, 50);
		panel_1.add(btnCalculateSampleStandard);

		JLabel populationStandardDeviationFormulaLabel = new JLabel(
				convertToMultiline("Population Standard Deviation"));
		populationStandardDeviationFormulaLabel.setBounds(10, 547, 111, 50);
		panel_1.add(populationStandardDeviationFormulaLabel);

		JLabel lblTheAmountOf = new JLabel(
				convertToMultiline(dataBase.returnObject("Population Standard Deviation").getDescription()));
		lblTheAmountOf.setBounds(131, 547, 146, 50);
		panel_1.add(lblTheAmountOf);

		JButton btnCalculatePopulationStandard = new JButton(
				convertToMultiline("Calculate Population Standard Deviation"));
		btnCalculatePopulationStandard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				glossaryFrame.setVisible(false);
				CalculatorGUI.setCalculator("Population Standard Deviation");
				CalculatorGUI.calculatorFrame.setVisible(true);
			}
		});
		btnCalculatePopulationStandard.setBounds(287, 547, 146, 50);
		panel_1.add(btnCalculatePopulationStandard);

		JLabel outliersFormulaLabel = new JLabel("Outliers");
		outliersFormulaLabel.setBounds(10, 608, 111, 50);
		panel_1.add(outliersFormulaLabel);

		JLabel outliersDescriptionLabel = new JLabel(
				convertToMultiline(dataBase.returnObject("Outliers").getDescription()));
		outliersDescriptionLabel.setBounds(131, 608, 146, 50);
		panel_1.add(outliersDescriptionLabel);

		JButton btnCalculateOutliers = new JButton("Calculate Outliers");
		btnCalculateOutliers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				glossaryFrame.setVisible(false);
				CalculatorGUI.setCalculator("Outliers");
				CalculatorGUI.calculatorFrame.setVisible(true);
			}
		});
		btnCalculateOutliers.setBounds(287, 622, 146, 23);
		panel_1.add(btnCalculateOutliers);

		JButton btnBackToMenu = new JButton("Back to Menu");
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				glossaryFrame.setVisible(false);
				MainMenuGUI.menuFrame.setVisible(true);
			}
		});
		btnBackToMenu.setBounds(131, 692, 146, 23);
		panel_1.add(btnBackToMenu);
	}

	public static String convertToMultiline(String orig) {
		return "<html>" + orig.replaceAll("\n", "<br>");
	}
}

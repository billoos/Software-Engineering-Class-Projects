import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenuGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static MainMenuGUI menuFrame = new MainMenuGUI();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					menuFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainMenuGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 308);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton statisticalGlossaryButton = new JButton("Statistical Glossary");
		statisticalGlossaryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuFrame.setVisible(false);
				GlossaryGUI.glossaryFrame.setVisible(true);
			}
		});
		statisticalGlossaryButton.setBounds(10, 11, 414, 75);
		contentPane.add(statisticalGlossaryButton);

		JButton dataSheetsButton = new JButton("Data Sheets");
		dataSheetsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuFrame.setVisible(false);
				DataSheetGUI.dataFrame.setVisible(true);
			}
		});
		dataSheetsButton.setBounds(10, 97, 414, 75);
		contentPane.add(dataSheetsButton);

		JButton statisticalCalculatorButton = new JButton("Statistical Calculator");
		statisticalCalculatorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuFrame.setVisible(false);
				CalculatorGUI.calculatorFrame.setVisible(true);
			}
		});
		statisticalCalculatorButton.setBounds(10, 183, 414, 75);
		contentPane.add(statisticalCalculatorButton);
	}
}

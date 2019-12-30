import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class DataSheetGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static DataSheetGUI dataFrame = new DataSheetGUI();
	private JTextField answerTextbox;
	private JTextField dataTextbox;
	private static List<Double> data = new ArrayList<Double>();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					dataFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static List<Double> getData() {
		return data;
	}

	public DataSheetGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton mainMenuButton = new JButton("Main Menu");
		mainMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataFrame.setVisible(false);
				MainMenuGUI.menuFrame.setVisible(true);
			}
		});
		mainMenuButton.setBounds(300, 250, 100, 25);
		contentPane.add(mainMenuButton);

		JButton enterButton = new JButton("Enter Data");
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String answer = answerTextbox.getText();
				try {
					Double num = Double.parseDouble(answer);
					data.add(num);
					String text = dataTextbox.getText();
					dataTextbox.setText(text + " " + num);
					answerTextbox.setText("");
				} catch (Exception e1) {
					ErrorGUI errorGUI = new ErrorGUI();
					errorGUI.run();
				}
			}
		});
		enterButton.setBounds(200, 25, 100, 25);
		contentPane.add(enterButton);

		answerTextbox = new JTextField(10);
		answerTextbox.setBounds(100, 25, 75, 25);
		contentPane.add(answerTextbox);

		dataTextbox = new JTextField(30);
		dataTextbox.setBounds(100, 100, 200, 25);
		contentPane.add(dataTextbox);
		dataTextbox.setEditable(false);

		JButton clearButton = new JButton("Clear Data");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataTextbox.setText("");
				data.clear();
			}
		});

		clearButton.setBounds(200, 55, 100, 25);
		contentPane.add(clearButton);
	}
}

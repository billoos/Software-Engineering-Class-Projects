
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ErrorGUI {

	public void run() {
		String message = "Error, please enter numbers";
		JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
	}
}
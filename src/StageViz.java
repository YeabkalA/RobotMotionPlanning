import javax.swing.JFrame;
import javax.swing.JPanel;

class StageViz extends JJFrame {

	JPanel mainPanel;

}

public StageViz() {
	setLayout(new BorderLayout());

	mainPanel = new JPanel() {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

		}
	}

	mainPanel
}
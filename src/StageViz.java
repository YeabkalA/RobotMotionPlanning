import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



class StageViz extends JFrame {

	JPanel mainPanel;



	public StageViz() {
		setLayout(new BorderLayout());

		mainPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);

			}
		};
	}
}
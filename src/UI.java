import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FileDialog;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.util.Random;

public class UI extends JFrame {
	JPanel panel;

	String file = "";
	
	public static void main(String[] args) throws IOException{
		new UI();
	}

	public UI() throws IOException {
		setLayout(new BorderLayout());
		JButton explore = new JButton("Explore File");
		JButton generateSTL = new JButton("Generate STL");
		FileDialog dialog = new FileDialog((JFrame)null, "Select File to Open");


		generateSTL.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){

				try {
					//if(file.equals("")) return;
					System.out.println("The selected file is " + file);
					Environment env = Environment.buildEnvironmentFromFile(new File(file));
					boolean successfulParse = Stage.createMainSTL(Environment.buildLevel(env), "UISTL.STL", false);
					if(successfulParse){
						JOptionPane.showMessageDialog(getContentPane(),
							"Successfully Generated STL!");
					}
					else {
						JOptionPane.showMessageDialog(getContentPane(),
							"STL cannot be generated.");

					}
								}
				catch (IOException err) {

				}
			}
		});
		explore.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dialog.setMode(FileDialog.LOAD);
				dialog.setVisible(true);
				file = dialog.getFile();
			}
		});
		getContentPane().add(explore, BorderLayout.CENTER);
		getContentPane().add(generateSTL, BorderLayout.WEST);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
}
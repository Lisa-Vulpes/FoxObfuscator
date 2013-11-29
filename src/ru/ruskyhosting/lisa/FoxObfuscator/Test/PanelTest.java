package ru.ruskyhosting.lisa.FoxObfuscator.Test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PanelTest extends JFrame implements ActionListener{
	JPanel panel0;
	JPanel panel1;
	JPanel panel2;
	JButton button;
	
	public static void main(String[] args) {
		PanelTest panelTest = new PanelTest();
		panelTest.setVisible(true);
	}
	
	PanelTest() {
		final int WINDOW_WIDTH = 400;
		final int WINDOW_HEIGHT = 400;
		setTitle("PanelTest");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLocationRelativeTo(null);
		
		panelInitialize();
		setColor();
		setPanel();
		setButton();
		panelFinalize();
	}
	
	void panelInitialize() {
		panel0 = new JPanel();
		panel1 = new JPanel();
		panel2 = new JPanel();
	}
	
	void setColor() {
		panel0.setBackground(Color.BLACK);
		panel1.setBackground(Color.RED);
		panel2.setBackground(Color.BLUE);
	}
	
	void setButton() {
		button = new JButton("Change Panel");
		button.addActionListener(this);
	}
	
	void setPanel() {
		panel0.add(panel1);
		panel0.add(panel2);
		panel1.setVisible(true);
		panel2.setVisible(false);
	}
	
	public void actionPerformed(ActionEvent actionEvent) {
		if (actionEvent.getSource() == button) {
			if (panel1.isVisible()) {
				panel1.setVisible(false);
				panel2.setVisible(true);
			} else {
				panel1.setVisible(true);
				panel2.setVisible(false);
			}
		}
	}

	void panelFinalize() {
		panel0.add(button);
		getContentPane().add(panel0, BorderLayout.CENTER);
	}
}

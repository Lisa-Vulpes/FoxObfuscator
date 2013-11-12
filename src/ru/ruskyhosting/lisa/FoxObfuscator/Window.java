/**----------------------------------------------------------------------------
* Fox Obfuscator
* Copyright 2013 Lisa
* ----------------------------------------------------------------------------
*
* The MIT License
*
* Permission is hereby granted, free of charge, to any person obtaining
* a copy of this software and associated documentation files (the
* "Software"), to deal in the Software without restriction, including
* without limitation the rights to use, copy, modify, merge, publish,
* distribute, sublicense, and/or sell copies of the Software, and to
* permit persons to whom the Software is furnished to do so, subject to
* the following conditions:
*
* The above copyright notice and this permission notice shall be
* included in all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
* EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
* MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
* NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
* LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
* OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
* WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
* ---------------------------------------------------------------------------*/

package ru.ruskyhosting.lisa.FoxObfuscator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Window extends JFrame implements ActionListener {
	final Engine engine = new Engine();
	final int width = 600;
	final int height = 600;
	JPanel[] panel;
	JMenu[] menu;
	JMenuItem[] menuItem1;
	JMenuItem[] menuItem2;
	JTextArea[] textArea;
	JScrollPane[] scrollPane;
	JButton[] button;
	JRadioButton[] radio;
	JButton infoButton;
	final String[] keyCharArray = { "%", "@", "#", "$", "<" };
	final String dialogTitle = "Fox JavaScript Obfuscator beta1.0";
	final String information = "Fox JavaScript Obfuscator beta1.0 (http://www.firefly.kutc.kansai-u/ac/jp/~lisa7/) \r\n"
					+ "Cross-platform JavaScript obufuscator, like Gumbler code. \r\n"
					+ "\r\n"
					+ "Copyright (C) 2013 Lisa Earthlight - lisa_kud@mail.ru \r\n";

	public Window(String programName) {
		setTitle(programName);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(width, height);
		setLocationRelativeTo(null);

		/*
		 * 画面左上のアイコンを設定する。
		 */
		ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("icon.png"));
		ImageIcon icon2 = new ImageIcon(getClass().getClassLoader().getResource("icon_small.png"));
		setIconImage(icon.getImage());

		// メニューバーを作成する．
		JMenuBar menuBar = new JMenuBar();

		// メニューを作成する．
		String[] menuArray = { "File", "Setting" };
		menu = new JMenu[menuArray.length];
		for (int i = 0; i < menuArray.length; i++) {
			System.out.println(menuArray[i]);
			menu[i] = new JMenu(menuArray[i]);
		}

		// メニューの内容を作成する．
		// Fileメニュー
		String[] fileMenuArray = { "New", "Load", "Save", "Exit" };
		menuItem1 = new JMenuItem[fileMenuArray.length];
		for (int i = 0; i < fileMenuArray.length; i++) {
			menuItem1[i] = new JMenuItem(fileMenuArray[i]);
		}
		// Settingメニュー
		String[] settingMenuArray = { "Escape", "Replace" };
		menuItem2 = new JMenuItem[settingMenuArray.length];
		for (int i = 0; i < settingMenuArray.length; i++) {
			menuItem2[i] = new JMenuItem(settingMenuArray[i]);
		}

		// Declaration of panels.
		panel = new JPanel[5];
		for (int i = 0; i < panel.length; i++) {
			panel[i] = new JPanel();
		}

		// Definition of size of panels.
		// Panel number increases from top to bottom except 0.
		// 0 : base panel
		// 1 : option panel
		// 2 : input text area panel
		// 3 : buttons panel
		// 4 : output text area panel
		int[] panelHeightArray = { 600, 50, 250, 50, 250 };
		for (int i = 0; i < panelHeightArray.length; i++) {
			// Width is fixed.
			panel[i].setPreferredSize(new Dimension(600, panelHeightArray[i]));
		}

		// Definition of panel color to panels.
		// It will delete when release.
		/*
		 * @Deprecated Color[] panelColorArray = {Color.BLACK, Color.RED,
		 * Color.ORANGE, Color.YELLOW, Color.CYAN}; for (int i = 0; i <
		 * panelColorArray.length; i++) {
		 * panel[i].setBackground(panelColorArray[i]); }
		 */

		// Declaration of text area.
		// 0 : input text area
		// 1 : output text area
		textArea = new JTextArea[2];
		for (int i = 0; i < textArea.length; i++) {
			textArea[i] = new JTextArea();
		}

		// Definition of text area.
		// To enable scroll text area, finally textArea changes and renames to
		// scrollPane.
		scrollPane = new JScrollPane[textArea.length];
		for (int i = 0; i < textArea.length; i++) {
			// Set font.
			textArea[i].setFont(new Font("Arial", Font.PLAIN, 20));
			// Set line-wrap.
			textArea[i].setLineWrap(true);
			// Set tab size for 2em.
			textArea[i].setTabSize(2);
			// Enable scroll on text area.
			scrollPane[i] = new JScrollPane(textArea[i]);
		}

		// Declaration of buttons.
		// 0 : clear input text area
		// 1 : obfuscate
		// 2 : decode
		// 3 : clear output text area
		button = new JButton[4];
		String[] buttonLabelArray = { "Clear", "Obfuscate", "Decode", "Clear" };
		for (int i = 0; i < button.length; i++) {
			button[i] = new JButton(buttonLabelArray[i]);
		}

		// Definition of buttons action.
		for (int i = 0; i < button.length; i++) {
			button[i].addActionListener(this);
		}

		// Definition of radio buttons.
		// Radio buttons are in use set key character.
		// Key Character means character code's head character likes "%"09.
		radio = new JRadioButton[5];
		for (int i = 0; i < radio.length; i++) {
			if (i == 0) {
				// Default selected is "%".
				radio[i] = new JRadioButton(keyCharArray[i], true);
			} else {
				// Others are "@", "$", "#", "<".
				radio[i] = new JRadioButton(keyCharArray[i]);
			}
		}

		// Definition of information button.
		infoButton = new JButton(icon2);
		infoButton.addActionListener(this);
		infoButton.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		infoButton.setContentAreaFilled(false);
		infoButton.setFocusPainted(false);

		// Definition of radio button group.
		// All radio buttons are same group.
		ButtonGroup group = new ButtonGroup();
		for (int i = 0; i < radio.length; i++) {
			group.add(radio[i]);
		}

		// メニューバーにメニューを設置する．
		for (int i = 0; i < menu.length; i++) {
			menuBar.add(menu[i]);
		}

		// メニューにコンテンツを設置する．
		for (int i = 0; i < menuItem1.length; i++) {
			menu[0].add(menuItem1[i]);
		}
		for (int i = 0; i < menuItem2.length; i++) {
			menu[1].add(menuItem2[i]);
		}

		// メニューバーを設置する．
		setJMenuBar(menuBar);

		// Set all panels with BoxLayout.
		// panel[0] is base panel.
		panel[0].setLayout(new BoxLayout(panel[0], BoxLayout.PAGE_AXIS));
		for (int i = 1; i < panel.length; i++) {
			panel[0].add(panel[i]);
		}

		// Set option panel with GridLayout.
		panel[1].setLayout(new GridLayout(1, 6));
		for (int i = 0; i < radio.length; i++) {
			panel[1].add(radio[i]);
		}
		// Set information button.
		panel[1].add(infoButton);

		// Set input text area with BoxLayout.
		// This makes full size of text area.
		panel[2].setLayout(new BoxLayout(panel[2], BoxLayout.PAGE_AXIS));
		panel[2].add(scrollPane[0]);

		// Set buttons with GridLayout.
		panel[3].setLayout(new GridLayout(1, 4));
		for (int i = 0; i < button.length; i++) {
			panel[3].add(button[i]);
		}

		// Set output text area with BoxLayout.
		// This makes full size of text area.
		panel[4].setLayout(new BoxLayout(panel[4], BoxLayout.PAGE_AXIS));
		panel[4].add(scrollPane[1]);

		// Set base panel.
		getContentPane().add(panel[0], BorderLayout.CENTER);
	}

	// Definition of action performance of buttons.
	public void actionPerformed(ActionEvent event) {
		// Left clear button.
		// Clear input text area.
		if (event.getSource() == button[0]) {
			textArea[0].setText("");
		}
		// Obfuscate button.
		// Obfuscate text on input text area.
		// And output obfuscated code to output text area.
		if (event.getSource() == button[1]) {
			for (int i = 0; i < radio.length; i++) {
				if (radio[i].isSelected()) {
					engine.setKeyChar(keyCharArray[i]);
				}
			}
			// If output text area is blank, no action.
			if (!textArea[0].getText().isEmpty()) {
				textArea[1].setText(engine.obfuscateText(textArea[0].getText()));
			}
		}
		// Decode button.
		// Decode text on output text area.
		// And output decoded code to input text area.
		if (event.getSource() == button[2]) {
			for (int i = 0; i < radio.length; i++) {
				if (radio[i].isSelected()) {
					engine.setKeyChar(keyCharArray[i]);
				}
			}
			// If input text area is blank, no action.
			if (!textArea[1].getText().isEmpty()) {
				textArea[0].setText(engine.decodeText(textArea[1].getText()));
			}
		}
		// Right clear button.
		// Clear output text area.
		if (event.getSource() == button[3]) {
			textArea[1].setText("");
		}
		// Info button.
		// Show information dialog.
		if (event.getSource() == infoButton) {
			JOptionPane.showMessageDialog(this, information, dialogTitle, JOptionPane.INFORMATION_MESSAGE);
		}
	}
}

/**----------------------------------------------------------------------------
* Fox Obfuscator
* Copyright 2013 Lisa
* -----------------------------------------------------------------------------
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
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * メインウインドウを設定するクラスです。
 * 
 * @author Lisa
 */
// TODO: カードパネルを用いて難読化方式が切り替えられるようにする。
public class WindowTest extends JFrame implements ActionListener {
	private final static int WINDOW_WIDTH = 600;
	private final static int WINDOW_HEIGHT = 600;
	
	private CardLayout cardLayout;
	private JPanel[] panel;
	private JMenuBar menuBar;
	private JMenu[] menu;
	private JMenuItem[] menuItem1;
	private JRadioButtonMenuItem[] menuItem2;
	private ButtonGroup menuButtonGroup;
	private ButtonGroup optionButtonGroup;
	private JTextArea[] textArea;
	private JScrollPane[] scrollPane;
	private JButton[] button;
	
	/**
	 * メインウインドウのコンストラクタです。
	 */
	public WindowTest() {
		setTitle("Fox Obfuscator beta1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLocationRelativeTo(null);
		
		setPanel();
		setMenu();
		setPanelLayout();
		setOption();
		setPanelColor();
		setTextArea();
		setButton();
		windowFinalize();
	}
	
	/**
	 * メインウインドウに使用するパネルをセットします。
	 */
	private void setPanel() {
		/* 使用するパネルの定義 
		 *  0: ベースパネル
		 *  1: オプションパネル(エスケープ処理)
		 *  2: テキストパネル(上)
		 *  3: ボタンパネル
		 *  4: テキストパネル(下)
		 *  5: オプションパネル(文字列置換)
		 */
		panel = new JPanel[6];
		int[] panelHeightArray = {600, 50, 250, 50, 250, 50};
		for (int i = 0; i < panel.length; i++) {
			panel[i] = new JPanel();
			panel[i].setPreferredSize(new Dimension(600, panelHeightArray[i]));
		}
		
		/* ベースパネルに子パネルを設置 */
		panel[0].setLayout(new BoxLayout(panel[0], BoxLayout.PAGE_AXIS));
		for (int i = 1; i < panel.length; i++) {
			panel[0].add(panel[i]);
		}
		
		/* カードレイアウトの初期化 */
		cardLayout = new CardLayout();
	}
	
	/**
	 * オプションパネルをセットします。
	 */
	private void setOption() {
		
	}
	
	private void setMenu() {
		menuBar = new JMenuBar();
		
		/* メニューを作成 */
		String menuLabelArray[] = {"File", "Setting"};
		menu = new JMenu[menuLabelArray.length];
		for (int i = 0; i < menuLabelArray.length; i++) {
			menu[i] = new JMenu(menuLabelArray[i]);
			menuBar.add(menu[i]);
		}
		
		/* Fileメニューの項目を設置 */
		String menuItemLabelArray1[] = {"New", "Load", "Save", "Exit"};
		menuItem1 = new JMenuItem[menuItemLabelArray1.length];
		for (int i = 0; i < menuItemLabelArray1.length; i++) {
			menuItem1[i] = new JMenuItem(menuItemLabelArray1[i]);
			menuItem1[i].addActionListener(this);
			menu[0].add(menuItem1[i]);
		}
		
		/* Settingメニューの項目を設置 */
		String menuItemLabelArray2[] = {"Escape", "Replace"};
		menuItem2 = new JRadioButtonMenuItem[menuItemLabelArray2.length];
		menuButtonGroup = new ButtonGroup();
		for (int i = 0; i < menuItemLabelArray2.length; i++) {
			menuItem2[i] = new JRadioButtonMenuItem(menuItemLabelArray2[i]);
			menuItem2[i].addActionListener(this);
			menuButtonGroup.add(menuItem2[i]);
			menu[1].add(menuItem2[i]);
		}
		menuItem2[0].setSelected(true);
		
		setJMenuBar(menuBar);

		
	}
	
	/**
	 * 子パネルの設置レイアウトをセットします。
	 */
	private void setPanelLayout() {
		panel[1].setLayout(new GridLayout(1, 6));
		panel[2].setLayout(new BoxLayout(panel[2], BoxLayout.PAGE_AXIS));
		panel[3].setLayout(new GridLayout(1, 3));
		panel[4].setLayout(new BoxLayout(panel[4], BoxLayout.PAGE_AXIS));
	}
	
	/**
	 * テキストエリアをセットします。
	 */
	//TODO: 右クリックでコピー等ができるようにする。
	private void setTextArea() {
		/* テキストエリアの定義 */
		textArea = new JTextArea[2];
		for (int i = 0; i < textArea.length; i++) {
			textArea[i] = new JTextArea();
		}
		
		/* テキストエリアでのスクロール操作を可能に */
		scrollPane = new JScrollPane[textArea.length];
		for (int i = 0; i < textArea.length; i++) {
			textArea[i].setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
			textArea[i].setLineWrap(true);
			textArea[i].setTabSize(4);
			scrollPane[i] = new JScrollPane(textArea[i]);
		}
		
		/* テキストエリアの設置 */
		panel[2].add(scrollPane[0]);
		panel[4].add(scrollPane[1]);
	}
	
	/**
	 * ボタンをセットします。
	 */
	private void setButton() {
		panel[3].setLayout(new GridLayout(1, 4));
		button = new JButton[3];
		String[] buttonLabelArray = {"Clear", "Obfuscate", "Clear"};
		for (int i = 0; i < button.length; i++) {
			button[i] = new JButton(buttonLabelArray[i]);
//			button[i].addActionListener(this);
			panel[3].add(button[i]);
		}
		
	}
	
	/**
	 * 確認のためパネルに色をつけます。
	 * 
	 * @deprecated リリース時には削除
	 */
	private void setPanelColor() {
		panel[0].setBackground(Color.BLACK);
		panel[1].setBackground(Color.RED);
		panel[2].setBackground(Color.YELLOW);
		panel[3].setBackground(Color.ORANGE);
		panel[4].setBackground(Color.BLUE);
		panel[5].setBackground(Color.CYAN);
	}
	
	/**
	 * ベースパネルを配置してウインドウを完成させます。
	 */
	private void windowFinalize() {
		getContentPane().add(panel[0], BorderLayout.CENTER);
	}
	
	/**
	 * アクションイベントです。
	 * 
	 * @param actionEvent アクションイベント
	 */
	public void actionPerformed(ActionEvent actionEvent) {
		if (actionEvent.getSource() == menuItem1[0]) {
			textArea[0].setText("");
			textArea[1].setText("");
		}
		if (actionEvent.getSource() == menuItem1[1]) {
			// TODO: ファイルの読み込み
		}
		if (actionEvent.getSource() == menuItem1[2]) {
			// TODO: ファイルの保存
		}
		if (actionEvent.getSource() == menuItem1[3]) {
			System.exit(0);
		}
		if (actionEvent.getSource() == menuItem2[0]) {
			// TODO: エスケープオプションに切り替え
			panel[0].remove(panel[5]);
			panel[0].add(panel[1]);
		}
		if (actionEvent.getSource() == menuItem2[1]) {
			// TODO: 文字列置換オプションに切り替え
			panel[0].remove(panel[1]);
			panel[0].add(panel[5]);
		}
		if (actionEvent.getSource() == button[0]) {
			textArea[0].setText("");
		}
		// TODO: ボタンの機能の実装
	}

}

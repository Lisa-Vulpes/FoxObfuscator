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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * メインウインドウを設定するクラスです。
 * 
 * @author Lisa
 */
public class WindowTest extends JFrame {
	private final static int WINDOW_WIDTH = 600;
	private final static int WINDOW_HEIGHT = 600;
	
	private JPanel panel[];
	private JTextArea textArea[];
	private JScrollPane scrollPane[];
	
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
		setPanelLayout();
		setPanelColor();
		setTextArea();
		windowFinalize();
	}
	
	/**
	 * メインウインドウに使用するパネルをセットします。
	 */
	private void setPanel() {
		/* 使用するパネルの定義 */
		panel = new JPanel[5];
		int[] panelHeightArray = {600, 50, 250, 50, 250};
		for (int i = 0; i < panel.length; i++) {
			panel[i] = new JPanel();
			panel[i].setPreferredSize(new Dimension(600, panelHeightArray[i]));
		}
		
		/* ベースパネルに子パネルを設置 */
		panel[0].setLayout(new BoxLayout(panel[0], BoxLayout.PAGE_AXIS));
		for (int i = 1; i < panel.length; i++) {
			panel[0].add(panel[i]);
		}
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
	 * 確認のためパネルに色をつけます。
	 * @deprecated リリース時には削除
	 */
	private void setPanelColor() {
		panel[0].setBackground(Color.BLACK);
		panel[1].setBackground(Color.RED);
		panel[2].setBackground(Color.YELLOW);
		panel[3].setBackground(Color.ORANGE);
		panel[4].setBackground(Color.BLUE);
	}
	
	/**
	 * ベースパネルを配置してウインドウを完成させます。
	 */
	private void windowFinalize() {
		getContentPane().add(panel[0], BorderLayout.CENTER);
	}

}

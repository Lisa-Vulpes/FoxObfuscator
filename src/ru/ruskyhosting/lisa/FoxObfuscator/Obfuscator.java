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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * JavaScriptの難読化を行うクラスです。
 * 
 * @author Lisa
 *
 */
public class Obfuscator {
	private String code = null;
	private String keyCharacter = null;
	private ArrayList<String> keyCharacterList = null;

	/**
	 * 文字列置換によって難読化を行うコンストラクタです。
	 * 
	 * @param code 難読化を行いたいコード
	 */
	public Obfuscator(String code) {
		this.code = code;
		this.keyCharacterList = getObfuscateCharacter();
	}
	
	/**
	 * 文字列のエスケープ処理によって難読化を行うコンストラクタです。
	 * 
	 * @param code 難読化を行いたいコード
	 * @param keyCharacter エスケープ文字として使用する文字
	 */
	public Obfuscator(String code, String keyCharacter) {
		this.code = code;
		this.keyCharacter = keyCharacter;
	}
	
	/**
	 * 文字列置換によって難読化を行います。
	 * 
	 * @return 難読化が施されたコード。
	 */
	public String replaceObfuscation() {
		String replaceObfuscatedCode = null;

		/*
		 * 難読化に使用する文字の個数を乱数で決める。
		 * 個数は必ず4以上とする。
		 */
		int keyCharacterNumber = keyCharacterList.size();
		int quantyKeyCharacter = 0;
		Random random = new Random();
		while (true) {
			quantyKeyCharacter = random.nextInt(keyCharacterNumber);
			if (3 < quantyKeyCharacter) {
				break;
			}
		}
		
		/*
		 * 難読化に使用する文字を決める。
		 * 難読化文字定義リストをシャッフルし、頭から先に決めた
		 * 使用個数分を難読化に使用する。
		 */
		String[] keyCharacters = new String[quantyKeyCharacter];
		Collections.shuffle(keyCharacterList);
		for (int i = 0; i < quantyKeyCharacter; i++) {
			keyCharacters[i] = keyCharacterList.get(i);
		}
		
		/*
		 * 決まった難読化文字を用いてコードを難読化する。
		 * ここでは60％の確率で難読化文字が入る。
		 */
		StringBuilder stringBuilder = new StringBuilder();
		char ch[] = code.toCharArray();
		int threshold = 60;
		int randomNumberForObfuscateRatio = 0;
		int randomNumberForKeyCharacter = 0;
		
		stringBuilder.append("'");
		int counter = 0;
		while (counter < ch.length) {
			randomNumberForObfuscateRatio = random.nextInt(100);
			randomNumberForKeyCharacter = random.nextInt(keyCharacters.length);
			if (randomNumberForObfuscateRatio < threshold) {
				stringBuilder.append(keyCharacters[randomNumberForKeyCharacter]);
			} else {
				stringBuilder.append(ch[counter]);
				counter++;
			}
		}
		
		/*
		 * エスケープ文字の挿入などのコード整形処理。
		 */
		stringBuilder.append("'.replace(/");
		for (int i = 0; i < keyCharacters.length; i++) {
			if (isNeedsEscape(keyCharacters[i])) {
				stringBuilder.append("\\" + keyCharacters[i]);
			} else {
				stringBuilder.append(keyCharacters[i]);
			}
			if (i != keyCharacters.length - 1) {
				stringBuilder.append("|");
			}
		}
		stringBuilder.append("/ig, '');");
		replaceObfuscatedCode = stringBuilder.toString();
		
		return replaceObfuscatedCode;
	}
	
	/**
	 * 文字列のエスケープ処理によって難読化を行います。
	 * 
	 * @return エスケープ処理が施されたコード。
	 */
	public String escapeObfuscation() {
		String escapeObfuscatedCode = null;
		
		/*
		 * エスケープ処理は文字ごとに行う必要があるため、1文字ずつに分ける。
		 * 後に正規表現により比較作業を行うため、String型の配列に格納する。
		 */
		char ch[] = code.toCharArray();
		String[] choppedCode = new String[ch.length];
		for (int i = 0; i < choppedCode.length; i++) {
			choppedCode[i] = String.valueOf(ch[i]);
		}
		
		/*
		 * エスケープ処理の文字が%以外の時は最後にReplace処理を行う必要があるため
		 * %以外のときはReplaceメソッドの準備をする。
		 */
		StringBuilder stringBuilder = new StringBuilder();
		if (keyCharacter.matches("%")) {
			stringBuilder.append("eval(unscape('");
		} else {
			stringBuilder.append("eval(unscape(('");
		}
		
		/*
		 * エスケープコードへの変換を行う。
		 * 一部文字はうまく変換できない(09→9)ため、別途処理が必要となる。
		 * 具体的には、変換後の文字数が2文字以下の場合は、頭に0をつける。
		 */
		String s = null;
		for (int i = 1; i < ch.length; i++) {
			s = Integer.toHexString(ch[i]);
			if (s.length() < 2) {
				stringBuilder.append(keyCharacter + "0" + Integer.toHexString(ch[i])); 
			} else {
				stringBuilder.append(keyCharacter + Integer.toHexString(ch[i]));
			}
			
			/*
			 * コードの終端を整える。
			 * エスケープ文字が%以外の場合は、Replaceメソッドを挿入する。
			 * また、エスケープ文字にエスケープ処理が必要な場合は、処理を行う。
			 */
			if (i == (code.length() - 1)) {
				if (keyCharacter.matches("%")) {
					stringBuilder.append("'));");
				} else {
					if (isNeedsEscape(keyCharacter)) {
						stringBuilder.append("').replace(/\\" + keyCharacter + "/g, '%')));");
					} else {
						stringBuilder.append("').replace(/" + keyCharacter + "/g, '%')));");
					}
				}
			}
		}
		escapeObfuscatedCode = stringBuilder.toString();
		
		return escapeObfuscatedCode;
	}
	
	/**
	 * エスケープ処理による難読化に使用する文字のリスト取得します。
	 * 
	 * @return 難読化に使用する文字のリスト
	 */
	private ArrayList<String> getObfuscateCharacter() {
		ArrayList<String> keyCharacterList = new ArrayList<String>();
		keyCharacterList.add("(");
		keyCharacterList.add(")");
		keyCharacterList.add("$");
		keyCharacterList.add("^");
		keyCharacterList.add("!");
		keyCharacterList.add("#");
		keyCharacterList.add("&");
		keyCharacterList.add("@");
		
		return keyCharacterList;
	}
	
	/**
	 * 対象となる難読化文字にエスケープ処理が必要かどうかを判定します。
	 * エスケープ文字が必要ならtrueを、それ以外ならfalseを返します。
	 * 
	 * @param character 判定を行いたい難読化文字
	 * @return エスケープ文字が必要ならtrue、それ以外ならfalse
	 */
	private boolean isNeedsEscape(String character) {
		if (character.matches("\\(")) {
			return true;
		} else if (character.matches("\\)")) {
			return true;
		} else if (character.matches("\\$")) {
			return true;
		} else if (character.matches("\\^")) {
			return true;
		} else if (character.matches("\\!")) {
			return true;
		} else {
			return false;
		}
	}
}

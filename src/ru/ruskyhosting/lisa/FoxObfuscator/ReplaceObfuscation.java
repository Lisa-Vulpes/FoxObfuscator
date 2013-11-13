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
 * 文字列置換によって難読化を行うクラスです。
 *  
 * @author Lisa
 * @deprecated 代用クラス作成のため
 */
public class ReplaceObfuscation {
	private String targetCode = null;
	private ArrayList<String> keyCharacterList = null;
	
	/**
	 * 文字列置換によって難読化を行うコンストラクタです。
	 * 
	 * @param code 難読化を行いたいコード
	 */
	ReplaceObfuscation(String code) {
		targetCode = code;
		keyCharacterList = getObfuscateCharacter();
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
		String[] keyCharacter = new String[quantyKeyCharacter];
		Collections.shuffle(keyCharacterList);
		for (int i = 0; i < quantyKeyCharacter; i++) {
			keyCharacter[i] = keyCharacterList.get(i);
		}
		
		/*
		 * 決まった難読化文字を用いてコードを難読化する。
		 * ここでは60％の確率で難読化文字が入る。
		 */
		StringBuilder stringBuilder = new StringBuilder();
		char ch[] = targetCode.toCharArray();
		int threshold = 60;
		int randomNumberForObfuscateRatio = 0;
		int randomNumberForKeyCharacter = 0;
		
		stringBuilder.append("'");
		int counter = 0;
		while (counter < ch.length) {
			randomNumberForObfuscateRatio = random.nextInt(100);
			randomNumberForKeyCharacter = random.nextInt(keyCharacter.length);
			if (threshold < randomNumberForObfuscateRatio) {
				stringBuilder.append(keyCharacter[randomNumberForKeyCharacter]);
			} else {
				stringBuilder.append(ch[counter]);
				counter++;
			}
		}
		
		/*
		 * エスケープ文字の挿入などのコード整形処理。
		 */
		stringBuilder.append("'.replace(/");
		for (int i = 0; i < keyCharacter.length; i++) {
			if (isNeedsEscape(keyCharacter[i])) {
				stringBuilder.append("\\" + keyCharacter[i]);
			} else {
				stringBuilder.append(keyCharacter[i]);
			}
			if (i != keyCharacter.length - 1) {
				stringBuilder.append("|");
			}
		}
		stringBuilder.append("/ig, '');");
		replaceObfuscatedCode = stringBuilder.toString();
		
		return replaceObfuscatedCode;
	}
	
	/**
	 * 難読化に使用する文字のリスト取得します。
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

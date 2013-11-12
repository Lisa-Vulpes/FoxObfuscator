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

public class ReplaceObfuscation {
	private String targetCode = null;
	private ArrayList<String> keyCharacterList = null;
	
	ReplaceObfuscation(String code) {
		targetCode = code;
		keyCharacterList = getObfuscateCharacter();
	}
	
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
		int i = 0;
		while (i < ch.length) {
			randomNumberForObfuscateRatio = random.nextInt(100);
			randomNumberForKeyCharacter = random.nextInt(keyCharacter.length);
			if (threshold < randomNumberForObfuscateRatio) {
				stringBuilder.append(keyCharacter[randomNumberForKeyCharacter]);
			} else {
				stringBuilder.append(ch[i]);
				i++;
			}
		}
		
		/*
		 * コードの終わりを整える。
		 */
		stringBuilder.append("'.replace(/");
		
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
}

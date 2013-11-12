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

/**
 * 文字列のエスケープ処理によって難読化を行うクラスです。
 * 
 * @author Lisa
 */
public class EscapeObfuscation {
	private String targetCode = null;
	private String escapeKeyCharacter = null;
	
	/**
	 * 文字列のエスケープ処理によって難読化を行うコンストラクタです。
	 * 
	 * @param code エスケープ処理を施したいコード
	 * @param keyCharacter エスケープ文字として使いたい文字
	 */
	EscapeObfuscation(String code, String keyCharacter) {
		targetCode = code;
		escapeKeyCharacter = keyCharacter;
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
		char ch[] = targetCode.toCharArray();
		String[] choppedCode = new String[ch.length];
		for (int i = 0; i < choppedCode.length; i++) {
			choppedCode[i] = String.valueOf(ch[i]);
		}
		
		/*
		 * エスケープ処理の文字が%以外の時は最後にReplace処理を行う必要があるため
		 * %以外のときはReplaceメソッドの準備をする。
		 */
		StringBuilder stringBuilder = new StringBuilder();
		if (escapeKeyCharacter.matches("%")) {
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
				stringBuilder.append(escapeKeyCharacter + "0" + Integer.toHexString(ch[i])); 
			} else {
				stringBuilder.append(escapeKeyCharacter + Integer.toHexString(ch[i]));
			}
			
			/*
			 * コードの終端を整える。
			 * エスケープ文字が%以外の場合は、Replaceメソッドを挿入する。
			 * また、エスケープ文字にエスケープ処理が必要な場合は、処理を行う。
			 */
			if (i == (targetCode.length() - 1)) {
				if (escapeKeyCharacter.matches("%")) {
					stringBuilder.append("'));");
				} else {
					if (isNeedsEscape(escapeKeyCharacter)) {
						stringBuilder.append("').replace(/\\" + escapeKeyCharacter + "/g, '%')));");
					} else {
						stringBuilder.append("').replace(/" + escapeKeyCharacter + "/g, '%')));");
					}
				}
			}
		}
		escapeObfuscatedCode = stringBuilder.toString();
		
		return escapeObfuscatedCode;
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

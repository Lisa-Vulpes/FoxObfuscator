package ru.ruskyhosting.lisa.FoxObfuscator;

import javax.xml.bind.DatatypeConverter;

public class Engine {
	// Definition extra characters.
	final String TAB = "09";
	final String LINE_SEPARATOR = "0a";
	// Definition defualt key character.
	String _keyChar = "%";

	Engine() {

	}

	public void setKeyChar(String keyChar) {
		_keyChar = keyChar;
	}

	public String obfuscateText(String _text) {
		// Obfuscated code will return by using StringBuilder;
		StringBuilder sb = new StringBuilder();

		// Change text to Char array.
		char ch[] = _text.toCharArray();
		// Make String array per 1 character from ch[].
		// This String array uses to check key character and etc.
		String[] choppedText = new String[_text.length()];
		for (int i = 0; i < _text.length(); i++) {
			choppedText[i] = String.valueOf(ch[i]);
		}

		// Obfuscate text with escaping.
		for (int i = 0; i < _text.length(); i++) {
			// Set top of the code.
			if (i == 0) {
				// Insert JavaScript code to enable execute.
				if (_keyChar == "%") {
					// If key character is "%", no needs replace method.
					sb.append("eval(unescape('");
				} else {
					// If key character isn't "%", it needs more "(".
					// Because of to replacet "%" with replace method.
					sb.append("eval(unescape(('");
				}
			}

			// Measures to line separator character.
			if (choppedText[i].matches(System.getProperty("line.separator"))) {
				// If line separator character has detected, ignore.
			} else {
				// Change character to binary code likes "%0A".
				sb.append(_keyChar + Integer.toHexString(ch[i]));
			}

			// Set bottom of the code.
			if (i == (_text.length() - 1)) {
				// Insert JavaScript code to enable execute.
				if (_keyChar == "%") {
					// If key character is "%", no needs replace method.
					sb.append("'));");
				} else {
					// If key character isn't "%", it needs replace method.
					// This section will add like "'.)replace(/@/g, '%')));".
					sb.append("').replace(/" + _keyChar + "/g, '%')));");
				}
			}
		}
		return new String(sb).replace(_keyChar + "9", _keyChar + "09").replace(_keyChar + "a", _keyChar + "0a");
	}

	public String decodeText(String _text) {
		// Obfuscated code will return by using StringBuilder;
		StringBuilder sb = new StringBuilder();

		// Change text to Char array.
		char ch[] = _text.toCharArray();
		// Make String array per 1 character from ch[].
		// This String array uses to check key character and etc.
		String choppedText[] = new String[_text.length()];
		for (int i = 0; i < _text.length(); i++) {
			choppedText[i] = String.valueOf(ch[i]);
		}

		// Decode text with binary transportation.
		// Use while because it needs variable count up i.
		int i = 0;
		while (i < _text.length()) {
			// Deprecated cause of hard coding.
			// Checking tab and line separator characters.
			// First step : Reach key character, check next character.
			if (choppedText[i].matches(_keyChar)) {
				// Case next character is "9", this might means it's tab code.
				if (choppedText[i + 1].matches("9")) {
					sb.append(new String(DatatypeConverter.parseHexBinary(TAB)));
					i = i + 2;
				} else if (choppedText[i + 1].matches("a")) {
					// Case next character is "a", this might means line
					// separator character.
					sb.append(new String(DatatypeConverter.parseHexBinary(LINE_SEPARATOR)));
					i = i + 2;
				} else if (choppedText[i + 1].matches("/")) {
					// Case next character is ".", this might means likes
					// "/@'/'g"
					// So no needs more decoding.
					break;
				} else {
					// Case next character is "0~F", this might correctly binary
					// code.
					sb.append(new String(DatatypeConverter.parseHexBinary(choppedText[i + 1] + choppedText[i + 2])));
					i = i + 3;
				}
			} else {
				// If it's note key character, no needs to decoding.
				i++;
			}
		}
		return new String(sb);
	}
}

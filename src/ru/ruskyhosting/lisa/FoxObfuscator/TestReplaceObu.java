package ru.ruskyhosting.lisa.FoxObfuscator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TestReplaceObu {
	// 難読化に使用する文字候補の定義．難読化文字定義配列．
	final static String[] KEY_CHARS_ARRAY = { "(", ")", "$", "^", "!", "#", "&", "@" };

	public void replaceObufuscate(String code) {
		/* 難読化に使用する文字の個数を決める． */
		/* 文字の個数は必ず4個以上とする． */
		Random random = new Random();

		// 難読化に使用する文字の個数を決める．
		int quantyKeyChars = 0;
		while (true) {
			quantyKeyChars = random.nextInt(KEY_CHARS_ARRAY.length);

			// もし乱数が3以下なら再抽選．
			if (quantyKeyChars > 3) {
				break;
			}
		}

		/* 難読化に使用する文字の種類を決める． */
		/* ランダムで選定し，重複は無しとする． */

		// 0～KEY_CHARS_ARRAY.lengthの重複なしの乱数表を作成する．
		// 表の数字を元に難読化に使用する文字を決める．
		ArrayList<Integer> numberList = new ArrayList<Integer>();
		for (int i = 0; i < KEY_CHARS_ARRAY.length; i++) {
			numberList.add(i);
		}
		Collections.shuffle(numberList);

		// 難読化に使用される文字が決まる．
		String[] keyChars = new String[quantyKeyChars];
		for (int i = 0; i < quantyKeyChars; i++) {
			keyChars[i] = KEY_CHARS_ARRAY[numberList.get(i)];
		}

		/* 用意されたテキストを一文字ずつ分ける． */
		char ch[] = code.toCharArray();

		/* 60%の確率で難読化文字が入る． */
		/* 難読化前後にはreplaceメソッド処理の記述が行われる． */

		// 難読化された文字列はStringBuilderを用いて構成する．
		StringBuilder sb = new StringBuilder();
		int probability = 0; // 確率計算に用いる変数の初期化．
		int randomNumber = 0; // 挿入される難読化文字を決めるための乱数変数の初期化．
		int j = 0; // while文に用いるカウンター変数の初期化．
		sb.append("'"); // 文頭に ' を追加．
		while (true) {
			// 0～100の乱数を作成．
			probability = random.nextInt(100);
			// 挿入される難読化文字を決めるための乱数変数を作成．
			randomNumber = random.nextInt(keyChars.length);
			try {
				// 乱数が40以上なら難読化文字を挿入(=60%の確率で難読化文字が入る)
				if (probability > 40) {
					// 挿入される難読化文字は1つではない
					sb.append(keyChars[randomNumber]);
				} else {
					sb.append(ch[j]);
					// 元の文の文字が入った場合は次に行く．
					j++;
				}
				// 例外が発生したら無限ループを抜けるというとんでもない処理．
			} catch (ArrayIndexOutOfBoundsException e) {
				break;
			}
		}

		/* JavaScriptととして実行できるようにreplaceメソッドを挿入． */
		sb.append("'.replace(/");
		for (int i = 0; i < keyChars.length; i++) {
			// 一部文字はエスケープ処理が必要なのでそれを行う．
			if (isNeedsEscape(keyChars[i])) {
				sb.append("\\" + keyChars[i]);
			} else {
				sb.append(keyChars[i]);
			}
			// 難読化文字をパイプで分ける．
			if (i != keyChars.length - 1) {
				sb.append("|");
			}
		}
		sb.append("/ig, '')");

		// 難読化された文字列を出力．
		System.out.println(sb.toString());
	}

	/* エスケープが必要な文字を判定する． */
	boolean isNeedsEscape(String character) {
		// 判定は難読化文字定義配列から参照する．
		for (int i = 0; i < 5; i++) {
			if (character.matches("\\" + KEY_CHARS_ARRAY[i])) {
				return true;
			}
		}
		return false;
	}
}
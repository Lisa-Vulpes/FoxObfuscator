package ru.ruskyhosting.lisa.FoxObfuscator.Test;

import ru.ruskyhosting.lisa.FoxObfuscator.Obfuscator;

public class ObfuscatorTest {
	public static void main(String[] args) {
		String code = "<script>";
		Obfuscator obfuscator = new Obfuscator(code);
		String result = obfuscator.replaceObfuscation();
		System.out.println(result);

	}

}

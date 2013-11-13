package ru.ruskyhosting.lisa.FoxObfuscator;

/**
 * メソッド等を個別にテストしたときに使うテストクラスです。
 * 
 * @author Lisa
 *
 */
public class MethodTest {
	public static void main(String[] args) {
		String code = "Hello World";
		
		Obfuscator obfuscator1 = new Obfuscator(code);
		Obfuscator obfuscator2 = new Obfuscator(code, "#");
		
		System.out.println(obfuscator1.replaceObfuscation());
		System.out.println(obfuscator2.escapeObfuscation());
	}
}

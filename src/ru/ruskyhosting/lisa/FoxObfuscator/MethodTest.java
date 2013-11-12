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
		ReplaceObfuscation replaceObfuscation = new ReplaceObfuscation(code);
		System.out.println(replaceObfuscation.replaceObfuscation());
	}
}

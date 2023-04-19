package com.ezenrent.util.io;

public class Out {
	
	/* 
	 * 라인 그리는 메서드
	 * 사용법 : Out.line("=", 20) 
  	 * 출력값 : ====================
	 */
	public static void line(String str, int cnt) {
		for (int i = 1; i<=cnt; i++) {
			System.out.print(str);
		}
		System.out.println();
	}
	
	/*
	 * 에러 메시지 출력하는 메서드
	 * 사용법 : Out.line("=", 20, 오류입니다)
	 * 출력값 :
	 *  ====================
	 *   ✖ 오류입니다
	 *  ====================
	 */ 
	public static void error(String str, int cnt, String err) {
		line(str, cnt);
		System.out.println(" ✖ " + err);
		line(str, cnt);
	}
	
	/* 
	 * 제목 출력하는 메서드
	 * 사용법 : Out.title("=", 10, "메인")
	 * 출력값 : ========== [ 메인 ] ==========
	 */
	
	public static void title(String str, int cnt, String title) {
		System.out.println();
		for (int i = 1; i<=cnt; i++) {
			System.out.print(str);
		}
		System.out.print(" [ " + title + " ] ");
		for (int i = 1; i<=cnt; i++) {
			System.out.print(str);
		}
		System.out.println();
	}
}

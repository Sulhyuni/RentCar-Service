package com.ezenrent.util.io;

import java.util.Scanner;

public class In {
private static Scanner scanner = new Scanner(System.in);
	
	// 키보드로 입력받는 메서드
	// 사용방법 : Main.scanner.nextLine() 썼던 자리에 In.getString()만 쓰면 된다.
	public static String getString() {
		return scanner.nextLine();
	}
	
	// 항목을 출력하고 키보드로 문자열 형태의 데이터 받는 메서드
	// 사용방법 : In.getString("메뉴")라고 입력하면 
	// 메뉴 ▶ 가 출력되고 바로 옆에 데이터를 입력 할 수 있다. 
	public static String getString(String str) {
		System.out.print(str + " ▶ ");
		return getString();
	}
}

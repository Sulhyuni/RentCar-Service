package com.ezenrent.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.ezenrent.util.io.Out;

public class DB {
	// DB 정보 192.168.0.120
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String ID = "rentcar";
	private static final String PW = "rentcar";

	// 드라이버 확인을 저장하는 변수 선언
	private static boolean checkDriver = false;

	// 한번만 드라이버가 있는 확인하는 메서드
	static {
//		System.out.println("DB 클래스 static 초기화블럭 진행 -----");
		try {
			Class.forName(DRIVER);
			checkDriver = true;
//			System.out.println("드라이버 확인완료");

		} catch (Exception e) {
//			e.printStackTrace();
			Out.error("=", 50, "예기치 못한 오류가 발생하였습니다. 다시 한 번 시도해 주세요. " + "\n ✖ 오류가 지속될 시 admin@naver.com으로 연락 바랍니다.");
		}

	}

	// connection 메서드
	public static Connection getConnection() throws Exception {
//			System.out.println("DB.getConnection()");
		if (!checkDriver)
			Out.error("=", 50, "예기치 못한 오류가 발생하였습니다. 다시 한 번 시도해 주세요. " + "\n ✖ 오류가 지속될 시 admin@naver.com으로 연락 바랍니다.");
		return DriverManager.getConnection(URL, ID, PW);
	}

	// 7 닫기 connection 과 PreparedStatement
	// DB.close 생성하시면 됩니다
	public static void close(Connection con, PreparedStatement pstmt) throws Exception {
		try {
			if (con != null)
				con.close();
			if (pstmt != null)
				pstmt.close();
		} catch (Exception e) {
//			e.printStackTrace();
			Out.error("=", 50, "예기치 못한 오류가 발생하였습니다. 다시 한 번 시도해 주세요. " + "\n ✖ 오류가 지속될 시 admin@naver.com으로 연락 바랍니다.");
		}
	}

	// 7 닫기 connection 과 PreparedStatement, ResultSet
	// DB.close 생성하시면 됩니다
	public static void close(Connection con, PreparedStatement pstmt, ResultSet rs) throws Exception {
		try {
			if (con != null)
				con.close();
			if (pstmt != null)
				pstmt.close();
			if (rs != null)
				rs.close();
		} catch (Exception e) {
//			e.printStackTrace();
			Out.error("=", 50, "예기치 못한 오류가 발생하였습니다. 다시 한 번 시도해 주세요. " + "\n ✖ 오류가 지속될 시 admin@naver.com으로 연락 바랍니다.");
		}
	}
}

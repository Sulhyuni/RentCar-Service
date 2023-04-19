package com.ezenrent.main;

import com.ezenrent.car.controller.CarController;
import com.ezenrent.member.controller.LoginController;
import com.ezenrent.member.controller.MemberController;
import com.ezenrent.member.controller.MemberThread;
import com.ezenrent.member.vo.LoginVO;
import com.ezenrent.notice.controller.NoticeController;
import com.ezenrent.rent.controller.RentController;
import com.ezenrent.review.controller.ReviewController;
import com.ezenrent.util.io.In;
import com.ezenrent.util.io.Out;

public class Main {
	
	// 로그인 VO
	public static LoginVO login = null;
	
	// 관리자인지 확인하는 메서드
	public static boolean isAdmin() {
		return (login != null) && (login.getGradeNo() == 9);
	}
	// 메인 메서드
	public static void main(String[] args) {

		// 휴면계정 자동 전환 스레드
		new Thread(new MemberThread()).start();
		
		// 프로그램 실행 시 환영 문구 출력
		System.out.println("\n╭╼|══════════════════════════════════════════════|╾╮");
		System.out.println("|              Welcome To Ezen Rent !              |");
		System.out.println("╰╼|══════════════════════════════════════════════|╾╯");
	
		// 무한반복
		while (true) {
			// 비회원일 때 나오는 메인
			if (login == null) {
				System.out.println();
				Out.line("-", 51);
				System.out.println("\t ■ 로그인이 되어있지 않습니다.");
				System.out.println("\t ■ [1.My메뉴]로 이동하여 로그인을 해주세요.");
				Out.line("-", 51);
			// 회원일 때 나오는 메인
			} else {
				System.out.println();
				Out.line("-", 30);
				System.out.println("  🗨 " + login.getId() + "님, 무엇을 도와드릴까요?");
				Out.line("-", 30);
			}
			// 관리자일 때 나오는 메인메뉴
			if (isAdmin()) {
				// 메뉴 출력
				Out.title("=", 6, "Ezen Rent");
				System.out.println("  1.My메뉴 2.관리자메뉴 0.종료");
				Out.line("=", 27);
			// 비회원 또는 회원 일 때 나오는 메인메뉴
			} else {
				// 메뉴 출력
				Out.title("=", 18, "Ezen Rent");
				System.out.println("  1.My메뉴 2.공지사항 3.차량보기 4.대여하기 5.이용후기 0.종료");
				Out.line("=", 51);
			}
			// 관리자일 때 실행하는 switch
			if (isAdmin()) {
				// 메뉴 선택
				switch (In.getString(" ■ 원하시는 항목의 번호를 선택 해 주세요.")) {
				// 메뉴 처리
				case "1":
					// My 메뉴
					(new LoginController()).execute();
					break;
				case "2":
					// 관리자 메뉴 - 무한반복
						q: while (true) {
						Out.title("=", 19, "Ezen Rent");
						// 메뉴 출력
						System.out.println(" 1.회원관리 2.공지관리 3.차량관리 4.대여관리 5.후기관리 0.이전메뉴");
						Out.line("=", 53);
						// 메뉴 선택
						switch (In.getString(" ■ 원하시는 항목의 번호를 선택 해 주세요")) {
						// 메뉴 처리
						case "1":
							// 회원 관리
							(new MemberController()).execute();
							break;
						case "2":
							// 공지 관리
							(new NoticeController()).execute();
							break;
						case "3":
							// 차량 관리
							(new CarController()).execute();
							break;
						case "4":
							// 대여 관리
							(new RentController()).execute();
							break;
						case "5":
							// 리뷰 관리
							(new ReviewController()).execute();
							break;
						case "0":
							break q;
						default:
							System.out.println("\n ✖ 잘못 입력 하셨습니다.");
							System.out.println(" ✖ 1~5번, 0번중에 선택 해 주세요.");
							break;
						} // end of switch
					} // end of while
					break;
				case "0":
					// 시스템 종료
					System.out.println("\n╭╼|══════════════════════════════════════════════|╾╮");
					System.out.println("|              Bye ! See you next time !           |");
					System.out.println("╰╼|══════════════════════════════════════════════|╾╯");
					System.exit(0);
					break;
				default:
					// case에 없는 번호 입력시 출력되는 문구
					System.out.println("\n ✖ 잘못 입력 하셨습니다.");
					System.out.println(" ✖ 1~2번, 0번중에 선택 해 주세요.");
					break;
				} // end of switch
			} // end of if
			// 비회원 또는 회원 일 때 실행하는 switch
			else {
				// 메뉴 입력
				switch (In.getString(" ■ 원하시는 항목의 번호를 선택 해 주세요.")) {
				case "1": 
					// My 메뉴
					(new LoginController()).execute();
					break;
				case "2": 
					// 공지사항
					(new NoticeController()).execute();
					break;
				case "3": 
					// 차량보기
					(new CarController()).execute();
					break;
				case "4": 
					// 대여하기
					(new RentController()).execute();
					break;
				case "5": 
					// 이용후기
					(new ReviewController()).execute();
					break;
				case "0": 
					// 시스템종료
					System.out.println("\n╭╼|══════════════════════════════════════════════|╾╮");
					System.out.println("|              Bye ! See You Next Time !           |");
					System.out.println("╰╼|══════════════════════════════════════════════|╾╯");
					System.exit(0);
					break;
				default:
					System.out.println("\n ✖ 잘못 입력 하셨습니다.");
					System.out.println(" ✖ 1~5번, 0번중에 선택 해 주세요.");
					break;
				} // end of switch
			} // end of if
		} // end of while
	} // end of main()
} // end of class

package com.ezenrent.member.controller;

import java.util.List;

import com.ezenrent.main.Execute;
import com.ezenrent.main.Main;
import com.ezenrent.member.service.MemberDeleteServiceImpl;
import com.ezenrent.member.service.MemberListServiceImpl;
import com.ezenrent.member.service.MemberUpdateServiceImpl;
import com.ezenrent.member.service.MemberViewPwServiceImpl;
import com.ezenrent.member.service.MemberViewServiceImpl;
import com.ezenrent.member.vo.MemberVO;
import com.ezenrent.util.io.In;
import com.ezenrent.util.io.MemberPrint;
import com.ezenrent.util.io.Out;

public class MemberController {
	@SuppressWarnings("unchecked")
	public void execute() {
		// 무한 반복
		while (true) {
			// 예외 처리
			try {
				// 관리자 권한이 있는지 확인
				if (Main.login.getGradeNo() != 9) {
					Out.error("=", 20, " ✖ 관리자만 접근 가능합니다.");
					return;
				} else {
					// 메뉴 출력
					Out.title("=", 20, "회원관리");
					System.out.println(" 1.회원리스트 2.회원정보보기 3.회원정보수정 4.회원삭제 0.이전메뉴 ");
					Out.line("=", 52);
				}
				// 메뉴 처리
				switch (In.getString(" ■ 원하시는 항목의 번호를 선택 해 주세요.")) {
				case "1": // 리스트
					// 무한반복
					while (true) {
						// 메뉴출력
						Out.title("=", 19, "회원검색");
						System.out.println(" 1.전체리스트 2.아이디 3.이름 4.연락처 5.회원상태 0.이전메뉴");
						Out.line("=", 50);
						MemberVO listVO = new MemberVO();
						// 메뉴 처리
						switch (In.getString(" ■ 회원 목록을 가져올 검색 조건을 선택 해 주세요.")) {
						case "1": // 전체 리스트
							List<MemberVO> list = (List<MemberVO>) Execute.run(new MemberListServiceImpl(), listVO);
							MemberPrint.print(list);
							break;
						case "2": // 아이디로 검색
							listVO.setId(In.getString(" ■ 아이디 입력"));
							list = (List<MemberVO>) Execute.run(new MemberListServiceImpl(), listVO);
							MemberPrint.print(list);
							break;
						case "3": // 이름으로 검색
							listVO.setName(In.getString(" ■ 이름 입력"));
							list = (List<MemberVO>) Execute.run(new MemberListServiceImpl(), listVO);
							MemberPrint.print(list);
							break;
						case "4": // 연락처로 검색
							listVO.setTel(In.getString(" ■ 연락처 뒷번호 4자리 입력"));
							list = (List<MemberVO>) Execute.run(new MemberListServiceImpl(), listVO);
							MemberPrint.print(list);
							break;
						case "5": // 회원상태로 검색
							listVO.setStatus(In.getString(" ■ 상태 입력 (정상,휴면,강퇴,탈퇴)"));
							list = (List<MemberVO>) Execute.run(new MemberListServiceImpl(), listVO);
							MemberPrint.print(list);
							break;
						case "0":
							return;
						default:
							System.out.println("\n ✖ 잘못 입력 하셨습니다.");
							System.out.println(" ✖ 1~5번, 0번중에 선택 해 주세요.");
							break;
						} // end of switch
					} // end of while
				case "2": // 회원정보 보기
					// 새로운 객체 생성
					MemberVO viewVO = new MemberVO();
					// 아이디 입력 받아서 변수 str에 저장
					String str = In.getString("\n ■ 확인할 회원 아이디 입력");
					// 생성된 객체에 입력받은 id 세팅
					viewVO.setId(str);
					// 입력받은 id 데이터 가지고 MemberViewSerivice 실행해서 리턴값을 viewVO에 저장
					viewVO = (MemberVO) Execute.run(new MemberViewServiceImpl(), str);
					// viewVO에서 가져온 데이터 출력
					MemberPrint.print(viewVO);
					break;
				case "3": // 회원정보 수정
					// 새로운 객체 생성
					MemberVO updateVO = new MemberVO();
					// 무한 반복
					while (true) {
						// 아이디 입력 받기
						str = In.getString("\n ■ 수정할 회원 아이디 입력");
						// 입력받은 아이디 가지고 MemberViewService 실행해서 updateVO에 저장
						updateVO = (MemberVO) Execute.run(new MemberViewServiceImpl(), str);
						// 수정할 회원정보 보여주기
						MemberPrint.print(updateVO);
						// 회원정보 없으면 반복문 멈추기
						if (updateVO != null)
							break;
					} // end of while
					// 수정할 회원정보 가지고 updateInfo() 메서드 실행
					(new MemberController()).updateInfo(updateVO);
					break;
				case "4": // 회원정보 삭제
					// 새로운 객체 생성
					MemberVO deleteVO = new MemberVO();
					// 삭제할 회원 아이디 입력 받아서 str에 저장
					str = In.getString("\n ■ 삭제 할 회원 아이디 입력");
					// 아이디는 main에 로그인 된 아이디, 비밀번호는 입력받기
					String id = Main.login.getId();
					String inPw = In.getString("\n ■ 관리자 비밀번호를 입력 해 주세요.");
					// 아이디로 MemberViewPwService 실행
					deleteVO = (MemberVO) Execute.run(new MemberViewPwServiceImpl(), id);
					// 비밀번호 일치하는지 확인
					if (inPw.equals(deleteVO.getPw())) {
						int result = (Integer) Execute.run(new MemberDeleteServiceImpl(), str);
						if (result == 1) {
							System.out.println("\n ■ " + str + "님의 모든 정보가 삭제 되었습니다.");
						} else {
							System.out.println("\n ✖ 삭제 실패. 아이디를 다시 한 번 확인 해 주세요.");
						}
					} else {
						System.out.println("\n ✖ 삭제가 되지 않았습니다. 비밀번호를 확인 해 주세요.");
					}
					break;
				case "0": // 이전메뉴
					return;
				default:
					System.out.println("\n ✖ 잘못 입력 하셨습니다.     ");
					System.out.println(" ✖ 1~4번, 0번중에 선택 해 주세요. ");
					break;
				}

			} catch (Exception e) {
//				e.printStackTrace();
				Out.error("=", 50,
						"예기치 못한 오류가 발생하였습니다. 다시 한 번 시도해 주세요. " + "\n ✖ 오류가 지속될 시 admin@naver.com으로 연락 바랍니다.");
			} // end of try~catch
		} // end of while

	} // end of execute()

	// 정보 수정을 위한 메서드
	public void updateInfo(MemberVO vo) throws Exception {
		// 무한반복
		while (true) {
			// 메뉴 출력
			System.out.println();
			Out.line("=", 36);
			System.out.println(" 1.연락처 2.이메일 3.면허번호");
			System.out.println(((Main.isAdmin()) ? " 4.등급 5.상태 " : " ") + "9.변경취소 0.변경완료");
			Out.line("=", 36);
			// 메뉴 처리
			switch (In.getString(" ■ 변경 할 항목을 선택 해 주세요.")) {
			case "1": // 연락처
				// 무한 반복
				while (true) {
					// 수정할 연락처 입력받기
					String updateTel = In.getString(" ■ 수정 할 연락처 (01x-xxxx-xxxx)");
					// 연락처 형식 체크
					if (ConditionCheck.checkTel(updateTel) == false) {
						System.out.println("\n ✖ 연락처 형식이 맞지 않습니다. 아래와 같이 입력 해 주세요.");
						System.out.println(" ✔ 예) 010-1234-5678 ('-'을 포함하여 입력)\n");
					} else {
						vo.setTel(updateTel);
						break;
					} // end of if
				} // end of while
				break;
			case "2": // 이메일
				// 무한반복
				while (true) {
					// 수정할 이메일 입력받기
					String updateEmail = In.getString(" ■ 수정 할 이메일 (nnn@nnn.nnn)");
					// 이메일 형식 체크
					if (ConditionCheck.checkEmail(updateEmail) == false) {
						System.out.println("\n ✖ 이메일 형식이 맞지 않습니다. 아래와 같이 입력 해 주세요.");
						System.out.println(" ✔ 예) abc@naver.com ('@'와 '.'을 포함하여 입력)\n");
					} else {
						vo.setEmail(updateEmail);
						break;
					} // end of if
				} // end of while
				break;
			case "3": // 면허번호
				// 무한반복
				while (true) {
					// 수정할 면허번호 입력 받기
					String updateLicenseNo = In.getString(" ■ 면허번호 (xx-xx-xxxxxx-xx)");
					// 면허번호 형식 체크
					if (ConditionCheck.checkLicenseNo(updateLicenseNo) == false) {
						System.out.println("\n ✖ 면허번호 형식이 맞지 않습니다. 아래와 같이 입력 해 주세요.");
						System.out.println(" ✔ 예) 12-12-123456-12 ('-'을 포함하여 입력)\n");
					} else {
						vo.setLicenseNo(updateLicenseNo);
						break;
					} // end of if
				} // end of while
				break;
			case "0": // 수정완료
				vo.setId(vo.getId());
				int result = (int) Execute.run(new MemberUpdateServiceImpl(), vo);
				if (result == 1) {
					System.out.println("\n ✔ 입력하신 정보로 변경 되었습니다.");
					return;
				} else {
					System.out.println("\n ✖ 정보 변경이 되지 않았습니다. 다시 한 번 시도해 주세요.");
					return;
				}
			case "9":
				System.out.println("\n ✔ 변경이 취소되었습니다.");
				return;
			case "4": // 회원등급
				// 무한반복
				while (true) {
					// 관리자인지 확인
					if (Main.isAdmin()) {
						// 수정할 등급 번호 입력 받기
						String updateGradeNo = In.getString(" ■ 등급 변경 (1,9)");
						// 등급번호 형식 체크
						if (ConditionCheck.checkGradeNo(updateGradeNo) == false) {
							System.out.println("\n ✖ 잘못 입력 하셨습니다. 숫자만 입력 해 주세요.");
							System.out.println(" ✖ [1]또는 [9]만 입력 하실 수 있습니다.\n");
						} else {
							vo.setGradeNo(Integer.parseInt(updateGradeNo));
							break;
						} // end of if - 유효성 확인
					} // end of if - 관리자인지 확인
				} // end of while
				break;
			case "5": // 회원상태
				// 무한반복
				while (true) {
					// 관리자인지 체크
					if (Main.isAdmin()) {
						// 변경할 상태 입력받기
						String updateGradeName = In.getString(" ■ 상태 변경 (정상,휴면,강퇴,탈퇴)");
						// 상태 형식 체크
						if (ConditionCheck.checkGradeName(updateGradeName) == false) {
							System.out.println("\n ✖ [정상,휴면,강퇴,탈퇴] 중 하나를 입력 해 주세요.\n");
						} else {
							vo.setStatus(updateGradeName);
							break;
						} // end of if - 유효성 확인
					} // end of if - 관리자인지 확인
				} // end of while
				break;
			default:
				System.out.println("\n ✖ 잘못 입력 하셨습니다.");
				System.out.println(" ✖ 1~" + ((Main.login.getGradeNo() == 1) ? "3" : "5") + "번, 9번, 0번중에 선택 해 주세요. ");
				break;
			} // end of switch
		} // end of while
	} // end of updateVO()
} // end of class

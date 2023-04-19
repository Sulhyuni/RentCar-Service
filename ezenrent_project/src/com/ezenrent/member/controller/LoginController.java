package com.ezenrent.member.controller;

import com.ezenrent.main.Execute;
import com.ezenrent.main.Main;
import com.ezenrent.member.service.MemberFindIdServiceImpl;
import com.ezenrent.member.service.MemberFindPwServiceImpl;
import com.ezenrent.member.service.MemberIdListServiceImpl;
import com.ezenrent.member.service.MemberLoginServiceImpl;
import com.ezenrent.member.service.MemberUpdatePwServiceImpl;
import com.ezenrent.member.service.MemberUpdateServiceImpl;
import com.ezenrent.member.service.MemberViewPwServiceImpl;
import com.ezenrent.member.service.MemberViewServiceImpl;
import com.ezenrent.member.service.MemberWakeUpServiceImpl;
import com.ezenrent.member.service.MemberWriteServiceImpl;
import com.ezenrent.member.vo.LoginVO;
import com.ezenrent.member.vo.MemberVO;
import com.ezenrent.rent.controller.RentController;
import com.ezenrent.util.io.In;
import com.ezenrent.util.io.MemberPrint;
import com.ezenrent.util.io.Out;

public class LoginController {
	public void execute() {
		// 무한반복
		while (true) {
			// 예외처리
			try {
				// 비회원일 때 나오는 My메뉴
				if (Main.login == null) {
					Out.title("=", 18, "My 메뉴");
					// 메뉴 출력
					System.out.println(" 1.로그인 2.회원가입 3.아이디찾기 4.비밀번호찾기 0.이전메뉴");
					Out.line("=", 48);
					// 회원 또는 관리자일 때 My메뉴 - 회원은 본인의 대여내역을 볼 수 있고 회원탈퇴도 할 수 있다.
				} else if (Main.login != null) {
					Out.title("=", 19, "My 메뉴");
					// 메뉴 출력
					System.out.println(" 1.로그아웃 2.내정보보기 3.내정보변경 4.비밀번호변경 "
							+ ((Main.login.getGradeNo() == 1) ? "\n 5.대여내역 6.회원탈퇴 " : "") + "0.이전메뉴");
					Out.line("=", 50);

				}
				// 비회원 My메뉴
				r: if (Main.login == null) {
					// 메뉴 입력
					switch (In.getString(" ■ 원하시는 항목의 번호를 선택 해 주세요.")) {
					// 메뉴 처리
					case "1": // 로그인
						// LoginVO 객체 생성 후 변수 vo에 저장
						LoginVO vo = new LoginVO();
						System.out.println();
						// 아이디와 비밀번호 키보드로 입력 받아서 생성된 객체에 세팅
						vo.setId(In.getString("아이디"));
						vo.setPw(In.getString("비밀번호"));
						// MemberLoginServiceImpl 실행하여 세팅된 ID와 PW 전달 후 받은 데이터 loginVO 변수에 저장
						LoginVO loginVO = (LoginVO) Execute.run(new MemberLoginServiceImpl(), vo);
						// loginVO로 받은 데이터의 정보 확인
						// 아이디와 비밀번호가 일치하는 회원이 없을 때
						if (loginVO == null) {
							System.out.println("\n ✖ 입력하신 정보가 없습니다.");
							System.out.println(" ✖ 아이디나 비밀번호를 다시 확인 해주시길 바랍니다.");
							System.out.println(" ✖ 비밀번호는 대소문자를 구분하여 입력 바랍니다.");
						// 강퇴 회원일 때
						} else if (loginVO.getStatus().equals("강퇴")) {
							System.out.println("\n ✖ 관리자에 의해 접근이 제한된 회원으로 로그인 할 수 없습니다.");
							System.out.println(" ✖ 로그인을 원하시면 admin@naver.com 으로 문의 바랍니다.");
						// 탈퇴 회원일 때
						} else if (loginVO.getStatus().equals("탈퇴")) {
							System.out.println("\n ✖ 탈퇴한 회원으로 로그인 할 수 없습니다.");
							System.out.println(" ✖ 로그인을 원하시면 회원가입 후 이용 바랍니다.");
						// 휴면 회원일 때
						} else if (loginVO.getStatus().equals("휴면")) {
							System.out.println("\n ✖ 장기간 미접속으로 인해 휴면계정으로 전환된 회원입니다.");
							System.out.println(" ✖ 로그인을 원하시면 가입시 입력 한 정보를 입력 해 주세요.");
							// MemberVO 객체 생성해서 변수 wakeUpVO에 저장
							MemberVO wakeUpVO = new MemberVO();
							// 아이디, 이름, 비밀번호 키보드로 입력 받아서 생성된 객체에 세팅
							wakeUpVO.setId(In.getString(" ■ 아이디"));
							wakeUpVO.setName(In.getString(" ■ 이름"));
							wakeUpVO.setPw(In.getString(" ■ 비밀번호"));
							// 입력받은 아이디 ViewService에 전달해서 넘어온 데이터 wakeUpVO에 저장
							wakeUpVO = (MemberVO) Execute.run(new MemberViewServiceImpl(), wakeUpVO.getId());
							// 저장된 회원의 정보를 MemberWakeupService로 전달 -> 휴면 회원을 정상 회원으로 UPDATE
							int result = (int) Execute.run(new MemberWakeUpServiceImpl(), wakeUpVO);
							// 휴면 해제 성공 or 실패 시 나오는 안내 문구 출력
							if (result == 1) {
								System.out.println("\n ■ 휴면 해제 되었습니다. 다시 로그인 해 주세요.");
							} else {
								System.out.println("\n ✖ 휴면 해제가 되지 않았습니다. 입력 정보를 다시 한 번 확인 해 주세요.");
							}
						} else {
							// 입력받은 아이디와 비밀번호가 일치 할 경우 Main.login에 정보 저장
							Main.login = loginVO;
							System.out.println("\n  ■ " + loginVO.getName() + "님! 환영합니다.");
							System.out.println("  ■ " + loginVO.getGradeName() + " 권한으로 로그인 되었습니다.");
							return;
						}
						break;
					case "2": // 회원가입
						// 새로운 객체 생성해서 writeJoin() 메서드 실행 후 객체에 저장
						MemberVO writeVO = writeJoin();
						// WriteJoin() 메서드에서 회원가입 취소 할 경우
						if (writeVO == null) {
							System.out.println("\n ✖ 회원가입이 취소되었습니다.");
							break;
						// WriteJoin() 메서드에서 회원가입 실행 할 경우
						// WriteJoin() 메서드에서 키보드로 입력받은 데이터 MemberWriteService에 전달
						} else if ((Integer) Execute.run(new MemberWriteServiceImpl(), writeVO) == 1) {
							System.out.println("\n ■ " + writeVO.getId() + "님, 환영합니다! 로그인 후 이용 해 주세요.");
						}
						break;
					case "3": // 아이디찾기
						// 새로운 LoginVO 객체 생성
						LoginVO findIdVO = new LoginVO();
						System.out.println("\n ■ 가입 시 입력한 정보로 아이디를 찾을 수 있습니다.\n");
						// 아이디를 찾기 위한 정보 입력 받기
						// 이름 입력받아서 변수 name에 저장
						String name = In.getString(" ✔ 이름");
						// 생성된 객체에 입력받은 이름 저장
						findIdVO.setName(name);
						// 생성된 객체에 입력받은 이메일 저장
						findIdVO.setEmail(In.getString(" ✔ 이메일"));
						// 입력받은 정보 저장한 findIdVO 데이터를 MemberFindService에 전달하여 실행
						findIdVO = (LoginVO) Execute.run(new MemberFindIdServiceImpl(), findIdVO);
						// 정보가 일치하는 회원이 없을 경우
						if (findIdVO == null) {
							System.out.println("\n ✖ 입력하신 정보와 일치하는 회원이 없습니다.");
							System.out.println(" ✖ 입력 정보를 다시 한 번 확인하여 주세요.");
							break r;
						// 정보와 일치하는 회원이 맞을 경우
						} else {
							System.out.println();
							Out.line("=", 32);
							System.out.println(" ✔ " + name + "님의 아이디는 [" + findIdVO.getId() + "] 입니다.");
							Out.line("=", 32);
							break;
						}
					case "4": // 비밀번호 찾기
						// 새로운 객체 생성
						MemberVO findPwVO = new MemberVO();
						// 안내 문구 출력
						System.out.println("\n ■ 가입 시 입력한 정보로 비밀번호를 찾을 수 있습니다.\n");
						// 아이디, 이름 이메일 입력받아서 각각 id, name, email 변수에 저장
						String id = In.getString(" ✔ 아이디");
						name = In.getString(" ✔ 이름");
						String email = In.getString(" ✔ 이메일");
						// 입력받은 아이디, 이름, 이메일 findPWVo에 세팅
						findPwVO.setId(id);
						findPwVO.setName(name);
						findPwVO.setEmail(email);
						// 세팅된 findPWVO를 가지고 MemberFindPwServiceImpl 실행 후 전달받은 데이터를 다시 findPwVO에 저장
						findPwVO = (MemberVO) Execute.run(new MemberFindPwServiceImpl(), findPwVO);
						// 입력한 정보와 일치하는 조건이 없을 때
						if (findPwVO == null) {
							System.out.println("\n ✖ 입력하신 정보와 일치하는 회원이 없습니다.");
							System.out.println(" ✖ 입력 정보를 다시 한 번 확인하여 주세요.");
							break r;
						// 입력한 정보와 일치하는 조건이 있을 때
						} else {
							// 입력한 아이디를 findPwVO의 id 변수에 저장
							findPwVO.setId(id);
							// ConditionCheck 클래스의 randomPW() 메서드를 실행 해 리턴받은 값을 findPwVO의 pw 변수에 저장
							// ConditionCheck.randomPw() -> 임시 비밀번호 만들어주는 메서드
							findPwVO.setPw(ConditionCheck.randomPw());
							// 전달받은 아이디와 비밀번호로 UpdatePwServiceImpl 실행하여 UPDATE 실행 -> 비밀번호 변경
							Execute.run(new MemberUpdatePwServiceImpl(), findPwVO);
							System.out.println();
							Out.line("=", 38);
							// 임시 비밀번호 발급
							System.out.println(" ✔ " + name + "님, 임시 비밀번호가 발급 되었습니다.");
							System.out.println(" ✔ 로그인 후 비밀번호를 변경하시길 바랍니다.");
							System.out.println(" ✔ 임시 비밀번호 [ " + findPwVO.getPw() + " ]");
							Out.line("=", 38);
							break;
						}
					case "0": // 이전메뉴
						return;
					default:
						System.out.println("\n ✖ 잘못 입력 하셨습니다.");
						System.out.println(" ✖ 1~4번, 0번중에 선택 해 주세요. ");
					} // end of switch

				// 일반회원 My메뉴
				} else if (Main.login != null) {
					// 메뉴선택
					switch (In.getString(" ■ 원하시는 항목의 번호를 선택 해 주세요")) {
					// 메뉴처리
					case "1": // 로그아웃
						Main.login = null;
						System.out.println("\n ✔ 로그아웃 되었습니다.");
						break;
					case "2": // 내정보보기
						// 새로운 객체 생성
						MemberVO viewVO = new MemberVO();
						// id 변수에 현재 로그인 된 아이디 저장
						String id = Main.login.getId();
						// 새로운 객체 아이디에 로그인된 아이디 세팅
						viewVO.setId(id);
						// id 데이터를 가지고 MemberViewService 실행 한 후 리턴값을 viewVO에 저장
						viewVO = (MemberVO) Execute.run(new MemberViewServiceImpl(), id);
						// 가져온 데이터를 MemberPrint.print() 메서드를 이용해 출력
						MemberPrint.print(viewVO);
						break;
					case "3": // 내정보수정
						// 새로운 객체 생성
						MemberVO updateVO = new MemberVO();
						// id 변수에 현재 로그인 된 아이디 저장 
						id = Main.login.getId();
						// 세팅된 viewVO 데이터를 가지고 MemberViewService 실행 한 후 리턴값을 updateVO에 저장
						updateVO = (MemberVO) Execute.run(new MemberViewServiceImpl(), id);
						// 가져온 데이터를 MemberPrint.print() 메서드를 이용해 출력
						MemberPrint.print(updateVO);
						// 가져온 데이터로 MemberController.updateInfo() 메서드 실행
						(new MemberController()).updateInfo(updateVO);
						break;
					case "4": // 비밀번호 변경
						// 새로운 객체 생성
						MemberVO updatePwVO = new MemberVO();
						// id 변수에 현재 로그인 된 아이디 저장
						id = Main.login.getId();
						// id 데이터를 가지고 MemberViewService 실행 한 후 리턴값을 updatePwVO에 저장
						updatePwVO = (MemberVO) Execute.run(new MemberViewPwServiceImpl(), id);
						System.out.println("\n ■ 비밀번호를 변경하시려면 기존 비밀번호를 입력 해 주세요.");
						// 비밀번호 입력 받기
						String inPw = In.getString(" ✔ 기존 비밀번호");
						// 조건 검사
						// 입력받은 비밀번호와 가져온 데이터의 비밀번호가 같을 경우
						if (inPw.equals(updatePwVO.getPw())) {
							// 무한반복
							while (true) {
								// 새로운 비밀번호 입력 받기
								String newPw = In.getString(" ✔ 새로운 비밀번호");
								// 비밀번호 조건 확인하기
								if (ConditionCheck.checkPw(newPw) == false) {
									System.out.println(" ✖ 아래의 입력 조건을 확인하고 다시 입력 해 주세요.");
									System.out.println(" ✔ 입력 조건 : 10~20자, 영문 & 숫자 & 특수문자 모두 포함");
									System.out.println(" ✔ 사용 가능한 특수문자 : ~!@^*-_.,? \n");
								} else {
									// 조건에 맞으면 updatePwVO에 기존 아이디와 새로운 비밀번호 세팅
									updatePwVO.setId(id);
									updatePwVO.setPw(newPw);
									// 세팅한 updatePwVO 데이터를 가지고 MemberUpdatePwService 실행
									int result = (int) Execute.run(new MemberUpdatePwServiceImpl(), updatePwVO);
									if (result == 1) {
										System.out.println("\n ✔ 비밀번호가 변경 되었습니다.");
										break;
									} else {
										System.out.println("\n ✖ 비밀번호가 변경되지 않았습니다. 다시 시도 해 주세요.");
										break;
									}
								}
							} // end of while
							// 입력받은 비밀번호와 가져온 데이터의 비밀번호가 다를 경우
						} else {
							System.out.println("\n ✖ 비밀번호를 다시 한 번 확인 해 주세요.");

						}
						break;
					case "0": // 이전메뉴
						return;
					case "5": // 대여내역  - 일반 회원만 보인다
						// 대여관리 컨트롤러로 이동
						(new RentController()).execute();
						break;
					case "6": // 회원탈퇴 - 일반 회원만 보인다
						// 일반회원이 맞는지 조건 확인
						if (Main.login.getGradeNo() == 1) {
							// 새로운 객체 생성
							MemberVO resignVO = new MemberVO();
							// 변수 id에 로그인 된 아이디 저장
							id = Main.login.getId();
							// 비밀번호 입력 받기
							inPw = In.getString("\n ■ 탈퇴를 원하시면 비밀번호를 입력 해 주세요.");
							// 데이터를 입력받은 변수 id를 가지고 MemberViewService실행 -> 가져온 데이터 resignVO에 저장
							resignVO = (MemberVO) Execute.run(new MemberViewPwServiceImpl(), id);
							// 입력받은 비밀번호와 가져온 데이터의 비밀번호가 같은지 조건 확인
							if (inPw.equals(resignVO.getPw())) {
								// 비밀번호가 같으면 id를 가지고 MemberViewService 실행 -> 가져온 데이터 resignVO에 저장
								resignVO = (MemberVO) Execute.run(new MemberViewServiceImpl(), id);
								// resignVO에 담긴 회원의 정보 중 상태를 '탈퇴'로 변경
								resignVO.setStatus("탈퇴");
								// 변경 된 resignVO를 가지고 MemberUpdateService 실행
								Execute.run(new MemberUpdateServiceImpl(), resignVO);
								System.out.println("\n ✔ 정상적으로 탈퇴가 되었습니다. 로그아웃 합니다.");
								// 탈퇴가 되었으면 로그아웃 처리
								Main.login = null;
								// 메인으로 쫓겨난다
								return;
							// 비밀번호가 다르면 탈퇴 실패
							} else {
								System.out.println("\n ✖ 탈퇴가 되지 않았습니다. 비밀번호를 확인 해 주세요.");
							}
							break;
						}
					default:
						System.out.println("\n ✖ 잘못 입력 하셨습니다.     ");
						// 관리자는 4번까지만 보이고 회원은 6번까지 보인다
						System.out
								.println(" ✖ 1~" + ((Main.login.getGradeNo() == 9) ? "4" : "6") + "번, 0번중에 선택 해 주세요. ");
					} // end of switch
				}
			} catch (Exception e) {
				// 개발자를 위한 오류 메시지
//				e.printStackTrace();
				// 회원을 위한 오류 메시지
				Out.error("=", 50,
						"예기치 못한 오류가 발생하였습니다. 다시 한 번 시도해 주세요. " + "\n ✖ 오류가 지속될 시 admin@naver.com으로 연락 바랍니다.");
			} // end of try~catch
		} // end of while
	} // end of execute

	// 회원 가입 시 정보를 입력받는 메서드
	public MemberVO writeJoin() throws Exception {
		Out.title("=", 18, "회원가입");
		System.out.println(" ■ 가입 항목을 형식에 맞게 작성 해 주세요.");
		System.out.println(" ■ ✔ 표시는 필수 입력 사항이며, 나머지는 선택 사항 입니다.");
		Out.line("=", 48);
		// 새로운 객체 생성
		MemberVO vo = new MemberVO();
		// 무한 반복
		while (true) {
			// 아이디를 입력 받는다.
			String id = In.getString(" ✔ 아이디");
			if ((boolean) (Execute.run(new MemberIdListServiceImpl(), id))) {
				// 중복된 아이디는 아닌지 확인
				System.out.println("\n ✖ 이미 사용중인 아이디 입니다. 다시 입력 해 주세요.\n");
			} else if (!ConditionCheck.checkId(id)) {
				// ConditionCheck.checkId() 메서드의 조건에 맞는지 확인
				System.out.println(" ✖ 입력 조건을 확인하고 다시 입력 해 주세요.");
				System.out.println(" ✔ 입력 조건 : 4~20자, 첫글자는 영문 대소문자, 영문 & 숫자만 입력 가능.\n");
			} else {
				// 조건을 만족하면 입력받은 아이디를 새로운 객체에 세팅
				vo.setId(id);
				break;
			}
		}
		// 무한 반복
		while (true) {
			// 비밀번호를 입력 받음
			String pw = In.getString(" ✔ 비밀번호");
			// 형식에 맞는지 확인
			if (ConditionCheck.checkPw(pw) == false) {
				System.out.println(" ✖ 아래의 입력 조건을 확인하고 다시 입력 해 주세요.");
				System.out.println(" ✔ 입력 조건 : 10~20자, 영문 & 숫자 & 특수문자 모두 포함");
				System.out.println(" ✔ 사용 가능한 특수문자 : ~!@^*-_.,? \n");
			} else {
				// 조건을 만족하면 입력받은 비밀번호를 새로운 객체에 세팅
				vo.setPw(pw);
				break;
			}
		}
		// 무한 반복
		while (true) {
			// 이름을 입력 받음
			String name = In.getString(" ✔ 이름");
			// 형식에 맞는지 확인
			if (ConditionCheck.checkName(name) == false) {
				System.out.println(" ✖ 아래의 입력 조건을 확인하고 다시 입력 해 주세요.");
				System.out.println(" ✔ 입력 조건 : 2~10자, 한글 또는 영문만 가능\n");
			} else {
				// 조건을 만족하면 입력받은 이름을 새로운 객체에 세팅
				vo.setName(name);
				break;
			}
		}
		// 무한 반복
		while (true) {
			// 생년월일 입력 받음
			String birth = In.getString(" ✔ 생년월일 (yyyy-mm-dd)");
			// 예외처리
			try {
				// 형식에 맞는지 확인
				if (ConditionCheck.checkBirth(birth) == false) {
					System.out.println("\n ✖ 날짜 형식이 맞지 않습니다. 아래와 같이 입력 해 주세요.");
					System.out.println(" ✔ 예) 1991-01-01 ('-'을 포함하여 입력)\n");
				} else if (ConditionCheck.getAge(birth) < 20) {
					System.out.println("\n ✖ 19세 미만의 미성년자는 가입 하실 수 없습니다.\n");
				} else {
					// 조건을 만족하면 입력받은 생년월일을 새로운 객체에 세팅
					vo.setBirth(birth);
					break;
				}
			} catch (Exception e) {
				System.out.println("\n ✖ 날짜 형식이 맞지 않습니다. 아래와 같이 입력 해 주세요.");
				System.out.println(" ✔ 예) 1991-01-01 ('-'을 포함하여 입력)\n");
			}
		}
		// 무한 반복
		while (true) {
			// 성별 입력 받기
			String gender = In.getString(" ✔ 성별 (여자, 남자)");
			// 형식에 맞는지 확인
			if (ConditionCheck.checkGender(gender) == false) {
				System.out.println("\n ✖ '여자' 또는 '남자'만 입력 가능 합니다. 정확히 입력 해 주세요.\n");
			} else {
				// 조건을 만족하면 입력받은 성별을 새로운 객체에 세팅
				vo.setGender(gender);
				break;
			}
		}
		// 무한 반복
		while (true) {
			// 연락처 입력 받기
			String tel = In.getString(" ✔ 연락처 (xxx-xxxx-xxxx)");
			// 형식에 맞는지 확인
			if (ConditionCheck.checkTel(tel) == false) {
				System.out.println("\n ✖ 연락처 형식이 맞지 않습니다. 아래와 같이 입력 해 주세요.");
				System.out.println(" ✔ 예) 010-1234-5678 ('-'을 포함하여 입력)\n");
			} else {
				// 조건을 만족하면 입력받은 전화번호를 새로운 객체에 세팅
				vo.setTel(tel);
				break;
			}
		}
		// 무한 반복
		while (true) {
			// 이메일 입력 받기
			String email = In.getString(" ✔ 이메일 (nnn@nnn.nnn)");
			// 형식에 맞는지 확인
			if (ConditionCheck.checkEmail(email) == false) {
				System.out.println("\n ✖ 이메일 형식이 맞지 않습니다. 아래와 같이 입력 해 주세요.");
				System.out.println(" ✔ 예) abc@naver.com ('@'와 '.'을 포함하여 입력)\n");
			} else {
				// 조건을 만족하면 입력받은 이메일을 새로운 객체에 세팅
				vo.setEmail(email);
				break;
			}
		}
		// 무한 반복
		while (true) {
			// 면허번호 입력 받기
			String licenseNo = In.getString(" ✔ 면허번호 (xx-xx-xxxxxx-xx)");
			// 형식메 맞는지 확인
			if (ConditionCheck.checkLicenseNo(licenseNo) == false) {
				System.out.println("\n ✖ 면허번호 형식이 맞지 않습니다. 아래와 같이 입력 해 주세요.");
				System.out.println(" ✔ 예) 12-12-123456-12 ('-'을 포함하여 입력)\n");
			} else {
				// 조건을 만족하면 입력받은 면허번호를 새로운 객체에 세팅
				vo.setLicenseNo(licenseNo);
				break;
			}
		} // end of while licenseNo

		// 무한 반복
		while (true) {
			// 추천인 아이디 입력 받기
			String recommender = In.getString(" 선택) 추천인 아이디");
			// 데이터에 회원인지 확인
			if ((boolean) (Execute.run(new MemberIdListServiceImpl(), recommender)) || recommender.equals("")) {
				break;
			} else if (!ConditionCheck.checkId(recommender)) {
				System.out.println(" ✖ 입력 조건을 확인하고 다시 입력 해 주세요.");
				System.out.println(" ✔ 입력 조건 : 4~20자, 첫글자는 영문 대소문자, 영문 & 숫자만 입력 가능.\n");
			} else {
				System.out.println(" \n ✖ 입력하신 아이디에 대한 회원 정보가 존재하지 않습니다. 다시 한 번 확인 해 주세요.\n");
			}
		} // end of recommender
			
			// 가입 여부 묻기
			System.out.println("\n ■ 입력하신 정보로 가입을 진행하시겠습니까?");
			// 무한 반복
			while (true) {
				e : switch (In.getString(" ■ 1.가입하기 2.취소하기")) {
				case "1":
					// 입력받은 데이터를 담은 객체 리턴
					return vo;
				case "2":
					return null;
				default:
					System.out.println("\n ✖ 잘못 입력 하셨습니다.");
					System.out.println(" ✖ 1,2번중에 선택 해 주세요. \n");
					break e ;
			} // end of switch
		} // end of while
	} // end of writeJoin
} // end of class

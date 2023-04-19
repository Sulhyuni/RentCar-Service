package com.ezenrent.car.controller;

import java.util.List;

import com.ezenrent.car.service.CarDeleteServiceImpl;
import com.ezenrent.car.service.CarListServiceImpl;
import com.ezenrent.car.service.CarPosListServiceImpl;
import com.ezenrent.car.service.CarUpdateServiceImpl;
import com.ezenrent.car.service.CarViewServiceImpl;
import com.ezenrent.car.service.CarWriteServiceImpl;
import com.ezenrent.car.vo.CarVO;
import com.ezenrent.main.Execute;
import com.ezenrent.main.Main;
import com.ezenrent.member.service.MemberViewPwServiceImpl;
import com.ezenrent.member.vo.MemberVO;
import com.ezenrent.util.io.CarPrint;
import com.ezenrent.util.io.In;
import com.ezenrent.util.io.Out;

public class CarController {

	@SuppressWarnings("unchecked")
	public void execute() {

		while (true) {

			try {

				Out.title("=", 12, "차량 보기");

				if (Main.login != null && Main.login.getGradeNo() == 9) { // 관리자일 경우
					System.out.println("0. 이전 메뉴 1. 차량 리스트 2. 차량 상세 보기\n3. 차량 등록 4. 차량   수정 5. 차량    삭제");
				} else { // 비회원이거나 일반 회원
					System.out.println("0. 이전 메뉴 1. 차량 리스트 2. 차량 상세 보기");
				}
				Out.line("=", 37);

				switch (In.getString("메뉴를 입력해 주세요.")) {

				case "0": // 이전 메뉴
					return;

				case "1": // 차량 리스트 - 모두 이용할 수 있음
					// 차량 리스트를 전체 리스트, 대여 가능 차량 리스트로 분류
					CarVO listVO = new CarVO();
					listVO(listVO);
					break;

				case "2": // 차량 상세 보기 - 모두 이용할 수 있음
					List<CarVO> list = (List<CarVO>) Execute.run(new CarListServiceImpl(), null);
					CarPrint.print(list);
					System.out.println();
					long no = Long.parseLong(In.getString("상세하게 볼 차량의 번호를 입력해 주세요."));
					CarVO viewVO = (CarVO) Execute.run(new CarViewServiceImpl(), no);
					CarPrint.print(viewVO);
					break;

				case "3": // 차량 등록 - 관리자만 이용할 수 있음
					if (Main.login != null && Main.login.getGradeNo() == 9) {
						CarVO writeVO = new CarVO();
						System.out.println();
						// 데이터 입력
						writeVO.setCarStatus(In.getString("  '가능', '불가'로 입력해 주세요.\n - 대여  여부"));
						writeVO.setCarType(In.getString(" - 차     종"));
						writeVO.setCarBrand(In.getString(" - 회  사  명"));
						writeVO.setCarModel(In.getString(" - 차  량  명"));
						writeVO.setCarSeat(In.getString(" - 좌     석"));
						writeVO.setCarYear(In.getString(" - 연     식"));
						writeVO.setCarPrice(Long.parseLong(In.getString("  숫자만 입력해 주세요.\n- 일일 대여비")));
						writeVO.setCarFuel(In.getString(" - 연     료"));
						writeVO.setCarOption(In.getString(" - 옵     션"));
						// 관리자 비밀번호 입력
						MemberVO pwVO = new MemberVO();
						String id = Main.login.getId();
						String pw = In.getString("\n관리자 비밀번호를 입력해 주세요");
						pwVO = (MemberVO) Execute.run(new MemberViewPwServiceImpl(), id);
						if (pw.equals(pwVO.getPw())) {
							Integer result = (Integer) Execute.run(new CarWriteServiceImpl(), writeVO);
							if (result == 1) {
								System.out.println("\n■ 차량이 등록되었습니다."); // 비밀번호 O 데이터 O
							} else {
								System.out.println("\n■ 차량을 등록할 수 없습니다. 다시 한번 확인해 주세요."); // 비밀번호 O 데이터 X
							}
						} else {
							System.out.println();
							Out.error("-", 41, "잘못된 비밀번호입니다. 다시 한번 확인해 주세요."); // 비밀번호 X
						}
						list = (List<CarVO>) Execute.run(new CarListServiceImpl(), null);
						CarPrint.print(list);
					}
					break;

				case "4": // 차량 수정 - 관리자만 이용할 수 있음
					if (Main.login != null && Main.login.getGradeNo() == 9) {
						list = (List<CarVO>) Execute.run(new CarListServiceImpl(), null);
						CarPrint.print(list);
						// 번호 입력
						no = Long.parseLong(In.getString("\n수정할 차량의 번호를 입력해 주세요."));
						CarVO updateVO = (CarVO) Execute.run(new CarViewServiceImpl(), no);
						CarPrint.print(updateVO);
						if (updateVO(updateVO).equals("11")) { // 11을 입력하면 수정 적용
							// 관리자 비밀번호 입력
							MemberVO pwVO = new MemberVO();
							String id = Main.login.getId();
							String pw = In.getString("관리자 비밀번호를 입력해 주세요");
							pwVO = (MemberVO) Execute.run(new MemberViewPwServiceImpl(), id);
							if (pw.equals(pwVO.getPw())) {
								Integer result = (Integer) Execute.run(new CarUpdateServiceImpl(), updateVO);
								if (result == 1) {
									System.out.println("\n■ 차량이 수정되었습니다."); // 비밀번호 O 데이터 O
								} else {
									System.out.println("\n■ 차량을 수정할 수 없습니다. 다시 한번 확인해 주세요."); // 비밀번호 O 데이터 X
								}
							} else {
								System.out.println();
								Out.error("-", 41, "잘못된 비밀번호입니다. 다시 한번 확인해 주세요."); // 비밀번호 X
							}
						} else {
							System.out.println("\n■ 차량 수정이 취소되었습니다."); // 차량 수정 취소
						}
						list = (List<CarVO>) Execute.run(new CarListServiceImpl(), null);
						CarPrint.print(list);
					}
					break;

				case "5": // 차량 삭제 - 관리자만 이용할 수 있음
					if (Main.login != null && Main.login.getGradeNo() == 9) {
						list = (List<CarVO>) Execute.run(new CarListServiceImpl(), null);
						CarPrint.print(list);
						no = Long.parseLong(In.getString("\n삭제할 차량의 번호를 입력해 주세요."));
						MemberVO deleteVO = new MemberVO();
						// 현재 로그인되어 있는 아이디 - admin
						String id = Main.login.getId();
						// 현재 로그인되어 있는 관리자 아이디 전달해서 관리자의 비밀번호를 가져오기
						deleteVO = (MemberVO) Execute.run(new MemberViewPwServiceImpl(), id);   
						// 관리자의 비밀번호 입력받기
						String pw = In.getString("관리자 비밀번호를 입력해 주세요");
						// 입력한 비밀번호와 deleteVO에 있는 관리자의 비밀번호가 같을 경우
						if (pw.equals(deleteVO.getPw())) {
							// 해당 번호의 차량을 삭제한다
							Integer result = (Integer) Execute.run(new CarDeleteServiceImpl(), no);
							if (result == 1) {
								System.out.println("\n■ 차량이 삭제되었습니다."); // 비밀번호 O 데이터 O
							} else {
								System.out.println("\n■ 차량을 삭제할 수 없습니다. 다시 한번 확인해 주세요."); // 비밀번호 O 데이터 X
							}
						} else { 
							System.out.println();
							Out.error("-", 41, "잘못된 비밀번호입니다. 다시 한번 확인해 주세요."); // 비밀번호 X
						}
						list = (List<CarVO>) Execute.run(new CarListServiceImpl(), null);
						CarPrint.print(list);
					}
					break;

				default:
					System.out.println();
					Out.error("-", 43, "잘못 입력하셨습니다. 0~"
							+ ((Main.login != null && Main.login.getGradeNo() == 9) ? "5" : "2") + "번 중에서 선택해 주세요.");
					// 관리자면 0~5번, 비회원이나 일반 회원이면 0~2번
					break;
				}

			} catch (Exception e) {
				// TODO: handle exception
				// e.printStackTrace();
				System.out.println();
				Out.error("-", 43, "오류 메시지: " + e.getMessage() + "\n    다시 한번 확인해 주세요.");
				

			} // end of try catch

		} // end of while

	} // end of execute

	@SuppressWarnings("unchecked")
	private void listVO(CarVO listVO) throws Exception {
		// TODO Auto-generated method stub
		while (true) {
			Out.title("=", 14, "차량 리스트");
			System.out.println("0. 이전 메뉴 1. 전체 리스트 2. 대여 가능 차량 리스트");
			Out.line("=", 42);
			switch (In.getString("메뉴를 입력해 주세요")) {
			case "0": // 이전 메뉴
				return;
			case "1": // 전체 리스트
				List<CarVO> list = (List<CarVO>) Execute.run(new CarListServiceImpl(), null);
				CarPrint.print(list);
				break;
			case "2": // 대여 가능 차량 리스트
				List<CarVO> posList = (List<CarVO>) Execute.run(new CarPosListServiceImpl(), null);
				CarPrint.print(posList);
				break;
			default:
				System.out.println();
				Out.error("-", 43, "잘못 입력하셨습니다. 0~2번 중에서 선택해 주세요.");
				break;

			}
		}

	}

	String updateVO(CarVO updateVO) {
		// TODO Auto-generated method stub
		while (true) {
			Out.title("=", 18, "차량 수정");
			System.out.println("1. 대여 여부 2. 차종 3. 회사명 4. 차량명 5. 좌석 6. 연식\n7. 일일 대여비 8. 연료 9. 옵션 10. 수정 취소 11. 수정 완료");
			Out.line("=", 49);
			switch (In.getString("수정할 항목을 선택해 주세요.")) {
			case "1":
				updateVO.setCarStatus(In.getString("\n  '가능', '불가'로 입력해 주세요.\n - 대여 여부"));
				break;
			case "2":
				updateVO.setCarType(In.getString("\n - 차종"));
				break;
			case "3":
				updateVO.setCarBrand(In.getString("\n - 회사명"));
				break;
			case "4":
				updateVO.setCarModel(In.getString("\n - 차량명"));
				break;
			case "5":
				updateVO.setCarSeat(In.getString("\n - 좌석"));
				break;
			case "6":
				updateVO.setCarYear(In.getString("\n - 연식"));
				break;
			case "7":
				updateVO.setCarPrice(Long.parseLong(In.getString("\n  숫자만 입력해 주세요.\n- 일일 대여비")));
				break;
			case "8":
				updateVO.setCarFuel(In.getString("\n - 연료"));
				break;
			case "9":
				updateVO.setCarOption(In.getString("\n - 옵션"));
				break;
			case "10": // 수정 취소
				return "10";
			case "11": // 수정 완료
				return "11";
			default:
				System.out.println();
				Out.error("-", 43, "잘못 입력하셨습니다. 0~11번 중에서 선택해 주세요.");
				break;

			}

		}

	}

} // end of class

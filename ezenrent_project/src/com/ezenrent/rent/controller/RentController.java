package com.ezenrent.rent.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ezenrent.car.service.CarPosListServiceImpl;
import com.ezenrent.car.service.CarUpdateServiceImpl;
import com.ezenrent.car.service.CarViewServiceImpl;
import com.ezenrent.car.vo.CarVO;
import com.ezenrent.main.Execute;
import com.ezenrent.main.Main;
import com.ezenrent.member.controller.ConditionCheck;
import com.ezenrent.member.vo.MemberVO;
import com.ezenrent.rent.service.RentDeleteServiceImpl;
import com.ezenrent.rent.service.RentListServiceImpl;

import com.ezenrent.rent.service.RentUpdateServiceImpl;
import com.ezenrent.rent.service.RentUpdateStatusServiceImpl;
import com.ezenrent.rent.service.RentViewServiceImpl;
import com.ezenrent.rent.service.RentWriteServiceImpl;
import com.ezenrent.rent.vo.RentVO;
import com.ezenrent.util.io.CarPrint;
import com.ezenrent.util.io.In;
import com.ezenrent.util.io.Out;
import com.ezenrent.util.io.RentPrint;

public class RentController {

	@SuppressWarnings({ "unchecked", "unused" })
	public void execute() {

		// 무한루프
		while (true) {
			try {
				// 메뉴출력
				// 비회원은 대여하기 메뉴에 접속 할 수 없도록 한다.
				if (Main.login == null) {
					Out.line("=", 35);
					System.out.println(" 대여하기는 로그인 후 이용 가능합니다.");
					System.out.println(" 홈으로 이동합니다.");
					Out.line("=", 35);
					return;
				} else if (Main.login.getGradeNo() == 1) {
					Out.title("=", 20, "대여하기");
					System.out.println("1.대여리스트 2.상세내역 " + " 3.예약하기 4.예약수정,추가운전자 등록  5.반납&취소하기" + " 0.이전메뉴");
					Out.line("=", 51);
				} else if (Main.login.getGradeNo() == 9) {
					Out.title("=", 15, "대여관리");
					System.out.println("1.모든 회원 대여리스트 2.회원 대여 상세내역 3.회원 예약 취소  0.이전메뉴");
					Out.line("=", 41);
				}

				// 회원 메뉴입력/처리
				if (Main.login.getGradeNo() == 1) {
					switch (In.getString("메뉴 입력")) {
					case "1":
						// 회원 본인대여 리스트
						RentVO vo = new RentVO();
						vo.setId(Main.login.getId());
						List<RentVO> list = (List<RentVO>) Execute.run(new RentListServiceImpl(), vo);
						RentPrint.print(list);
						System.out.println(" ■  대여 중인 차량 리스트를 불러옵니다.");

						break;
					case "2": // 상세내역
						// 본인 예약 리스트 불러온다.
						RentVO viewVO = new RentVO();
						viewVO.setId(Main.login.getId());
						list = (List<RentVO>) Execute.run(new RentListServiceImpl(), viewVO);
						RentPrint.print(list);

						// 본인 예약리스트중 상세보기 할 번호
						long no = Long.parseLong(In.getString(" ■ 상세보기 할 예약번호 입력"));
						viewVO.setRentNo(no);
						viewVO.setId(Main.login.getId());
						viewVO = (RentVO) Execute.run(new RentViewServiceImpl(), no);
						RentPrint.print(viewVO);

						if (list == null) {
							System.out.println("**대여 내역이 없습니다. 메인으로 이동합니다.**");
							return;
						}

						break;
					case "3":
						// 예약하기 write
						// 대여가능 리스트를 불러온다
						// 예약 후 carStatus가 '가능'->'불가' 로 변경되어야 한다.
						RentVO writeVO = new RentVO();
						CarVO carVO = new CarVO();

						// carStatus가 '가능'인 리스트만 불러온다.
						List<CarVO> posList = (List<CarVO>) Execute.run(new CarPosListServiceImpl(), null);
						CarPrint.print(posList);

						// carNo입력, 대여일자,반납일자
						no = Long.parseLong(In.getString("예약할 차량 선택"));
						carVO.setCarNo(no);
						carVO = (CarVO) Execute.run(new CarViewServiceImpl(), no);
						if (carVO == null || carVO.getCarStatus().equals("불가")) {
							System.out.println("**예약불가능한 차량번호이거나 잘못된 번호 입니다.**" + "\n ** 대여 '가능' 한 번호를 입력해주세요.**");
						} else {
							// 3-1 writeVO(writeVO)
							// 보험 - 만21세이상, 만26세이상 선택 가능
							writeVO.setCarNo(no);
							writeVO.setId(Main.login.getId());
							System.out.println("\n+대여장소와 반납장소는 동일합니다.+\n+정확한 장소와 날짜를 작성해주세요+");
							writeVO.setPlace(In.getString("대여장소"));
							// 렌트료 계산 (일일대여료 * 대여일수)+보험료
							String rentalDate = null;
							String returnDate = null;
							while (true) {
								rentalDate = In.getString("대여시작일(yyyy-MM-dd) : ");
								if (ConditionCheck.checkBirth(rentalDate) == false) {
									System.out.println(" 날짜 형식이 맞지 않습니다. ");
								} else {
									writeVO.setRentalDate(rentalDate);
									break;
								}
							}
							while (true) {
								returnDate = In.getString("반납일(yyyy-MM-dd) : ");
								if (ConditionCheck.checkBirth(returnDate) == false) {
									System.out.println(" 날짜 형식이 맞지 않습니다. ");
								} else {
									writeVO.setReturnDate(returnDate);
									break;
								}
							}

							// 보험 선택 switch문
							int insuranceFee = 0;
							while (true) {
								switch (In.getString("보험선택 1.만21세이상 2.만26세이상")) {
								case "1":
									// 21세 30000원으로 지정
									System.out.println("운전자 만21세 이상/ 자기 면책금 30만원");
									insuranceFee = 30000;
									writeVO.setInsurance("운전자 만21세 이상/ 자기 면책금 30만원");
									break;
								case "2":
									// 26세 10000원으로 지정
									System.out.println("운전자 만26세 이상/ 자기 면책금 30만원");
									insuranceFee = 10000;
									writeVO.setInsurance("운전자 만26세 이상/ 자기 면책금 30만원");
									break;
								default:
									Out.error("=", 20, "잘못된 보험 선택입니다.");
									return;
								} // end of switch
								break;
							} // end of while

							System.out.println("일일 렌트료 :" + carVO.getCarPrice() + "원");
							Long totalFee = totalRentalFee(rentalDate, returnDate, carVO.getCarPrice(), insuranceFee);

							System.out.println("총 렌트료 :" + totalFee + "원");
							writeVO.setTotalFee(totalFee);
							Integer result = (Integer) Execute.run(new RentWriteServiceImpl(), writeVO);

							// 예약되면 차량 대여여부'가능'->'불가'로 변경
							carVO.setCarStatus("불가");
							Execute.run(new CarUpdateServiceImpl(), carVO);
							Out.line("=", 41);
							System.out.println("예약되었습니다.");

						} // end of if
						break;

					case "4":
						// 예약수정,추가운전자등록 update
						// 대여일,반납일수정, 추가운전자등록
						// 추가운전자(이름,생년월일, 면허번호 )정보 입력
						viewVO = new RentVO();
						viewVO.setId(Main.login.getId());
						list = (List<RentVO>) Execute.run(new RentListServiceImpl(), viewVO);
						RentPrint.print(list);
						if (list == null)
							return;

						no = Long.parseLong(In.getString("수정할 예약번호"));
						RentVO updateVO = (RentVO) Execute.run(new RentViewServiceImpl(), no);
						RentPrint.print(updateVO);

						if (updateVO == null) {
							Out.error("*", 20, "예약번호 다시 확인해주세요.");
						} else {
							updateVO(updateVO);
							updateVO.setRentNo(no);
							Execute.run(new RentUpdateServiceImpl(), updateVO);

						}
						break;

					case "5":
						// 예약 취소
						vo = new RentVO();
						vo.setId(Main.login.getId());
						list = (List<RentVO>) Execute.run(new RentListServiceImpl(), vo);
						RentPrint.print(list);
						System.out.println(" ■  대여 중인 차량 리스트를 불러옵니다.");
						if (list == null) {
							System.out.println("**대여 내역이 없습니다. 메인으로 이동합니다.**");
							return;
						}

						// 취소,반납시 rentNo 삭제처리 carStatus가 '가능'으로 변경하도록 한다.
						// 취소,반납 할 회원 대여리스트의 rentNo , carNo입력
						RentVO deleteVO = new RentVO();
						long no1 = Long.parseLong(In.getString("취소 할 예약번호"));
						deleteVO.setId(Main.login.getId());
						deleteVO.setRentNo(no1);
						Integer result = (Integer) Execute.run(new RentDeleteServiceImpl(), deleteVO);

						if (result == 0) {
							Out.error("*", 20, "예약번호가 잘못되었습니다. \n 예약번호를 다시 확인해주세요");

						} else {
							long no2 = Long.parseLong(In.getString("취소 할 차량번호"));
							deleteVO.setId(Main.login.getId());
							deleteVO.setCarNo(no2);
							deleteVO.setCarStatus("가능");
							Execute.run(new RentUpdateStatusServiceImpl(), deleteVO);
//						
							System.out.println("**정상적으로 예약이 취소 되었습니다.**");
						}
						break;

					case "0":
						// 이전메뉴
						System.out.println("이전메뉴로 돌아갑니다.");
						return;
					default:
						System.out.println("잘못된 메뉴 입력");
						System.out.println("다시 입력 해주세요.");
						break;

					}// end of switch
				} // end of if

				// 관리자 로그인 시
				else if (Main.login.getGradeNo() == 9) {

					switch (In.getString("메뉴 입력")) {
					case "1":
						// LIST
						RentVO vo = new RentVO();
						List<RentVO> list = (List<RentVO>) Execute.run(new RentListServiceImpl(), vo);
						RentPrint.print(list);
						System.out.println(" ■  모든회원 대여리스트를 불러옵니다.");

						break;
					case "2": // 상세내역
						RentVO viewVO = new RentVO();
						// 모든 회원의 대여 상세내역을 확인 할 수 있다.
						long no = Long.parseLong(In.getString(" ■ 상세보기 할 예약번호 입력"));
						viewVO.setRentNo(no);
						viewVO = (RentVO) Execute.run(new RentViewServiceImpl(), no);
						RentPrint.print(viewVO);

						if (viewVO == null)
							System.out.println("**대여 내역이 없습니다. 메인으로 이동합니다.**");
						break;

					case "3":
						// 상태수정
						// 예약취소
						// 취소,반납 할 회원 대여리스트의 rentNo 입력
						RentVO deleteVO = new RentVO();
						long no1 = Long.parseLong(In.getString("취소 할 예약번호"));
						deleteVO.setRentNo(no1);
						Integer result = (Integer) Execute.run(new RentDeleteServiceImpl(), deleteVO);

						// 차량 상태변경
						long no2 = Long.parseLong(In.getString("취소 할 차량번호"));
						deleteVO.setCarNo(no2);
						deleteVO.setCarStatus("가능");
						Execute.run(new RentUpdateStatusServiceImpl(), deleteVO);
						System.out.println("**회원 예약정보가 정상적으로 취소 되었습니다.**");
						break;
					case "0":
						System.out.println("이전 메뉴로 돌아갑니다.");
						return;
					default:
						Out.error("=", 20, "잘못입력했습니다.");
						break;

					}// 관리자 end of switch
				} // end of else if
			} catch (Exception e) {
				e.printStackTrace();
			} // end of try~catch

		} // end of while
	}// end of execute

	// 4-1 수정항목
	private void updateVO(RentVO vo) {
		while (true) {
			try {
				System.out.println("1.대여일 2.반납일 3.운전자 등록 4.완료 0.이전메뉴");
				switch (In.getString("수정 항목 입력")) {
				case "1":
					vo.setRentalDate(In.getString("대여일(yyyy-MM-dd) :"));
					break;

				case "2":
					vo.setReturnDate(In.getString("반납일(yyyy-MM-dd) :"));
					break;

				case "3":
					vo.setAddDriver(In.getString("이름"));
					while (true) {
						String birth = In.getString("생년월일(yyyy-mm-dd)");
						try {
							if (ConditionCheck.checkBirth(birth) == false) {
								Out.error("*", 20, "날짜 형식이 맞지 않습니다.");
							} else if (ConditionCheck.getAge(birth) < 21) { // 만21세 미만은 등록 할 수 없다.
								Out.error("*", 20, "만 21세미만은 등록 할 수 없습니다.");

							} else {
								vo.setAddBirth(birth);
								break;
							}
						} catch (Exception e) {
							Out.error("*", 20, "날짜 형식이 맞지 않습니다.");
						}

					} // end of while
					while (true) {
						String licenseNo = In.getString("면허번호(xx-xx-xxxxxx-xx)");
						if (ConditionCheck.checkLicenseNo(licenseNo) == false) {
							Out.error("*", 20, "면허번호 형식에 맞지 않습니다.");
						} else {
							vo.setAddLicense(licenseNo);
							break;
						}
					}
				case "4":
					vo.setId(Main.login.getId());
					System.out.println("수정완료 되었습니다.");
					return;
				case "0":

					System.out.println("수정취소. 이전메뉴로 돌아갑니다.");
					return;

				default:
					Out.error("=", 20, "잘못입력했습니다.");
					break;
				}
			} catch (Exception e) {
				Out.error("*", 20, "입력값을 다시 확인해주세요");
				e.printStackTrace();
			}

		} // end of while

	}// end of update vo

	// 렌트료 계산 메서드
	public static long totalRentalFee(String rentalDate, String returnDate, long carPrice, long insurance)
			throws Exception {

		// 타입을 변환
		Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(rentalDate);
		Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(returnDate);

		long diffsec = (endDate.getTime() - startDate.getTime()) / 1000;
		long diffDay = diffsec / (24 * 60 * 60);

		// 총 렌트료 = 하루렌탈료 * 예약일수 + 보험료
		long totalFee = (carPrice * diffDay) + insurance;

		return totalFee;

	}// end of exception

}// end of class

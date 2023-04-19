package com.ezenrent.review.controller;

import java.util.List;

import com.ezenrent.car.service.CarListServiceImpl;
import com.ezenrent.car.service.CarViewServiceImpl;
import com.ezenrent.car.vo.CarVO;
import com.ezenrent.main.Execute;
import com.ezenrent.main.Main;
import com.ezenrent.member.service.MemberFindIdServiceImpl;
import com.ezenrent.member.vo.MemberVO;
import com.ezenrent.rent.service.RentListServiceImpl;
import com.ezenrent.rent.service.RentMyViewServiceImpl;
import com.ezenrent.rent.service.RentViewServiceImpl;
import com.ezenrent.rent.service.RentWriteServiceImpl;
import com.ezenrent.rent.vo.RentVO;
import com.ezenrent.review.service.ReviewDeleteServiceImpl;
import com.ezenrent.review.service.ReviewListServiceImpl;
import com.ezenrent.review.service.ReviewMasterDeleteServiceImpl;
import com.ezenrent.review.service.ReviewRefNoListServiceImpl;
//import com.ezenrent.review.service.ReviewRefNoViewServiceImpl;
import com.ezenrent.review.service.ReviewReplyServiceImpl;
import com.ezenrent.review.service.ReviewUpdateServiceImpl;
import com.ezenrent.review.service.ReviewViewServiceImpl;
import com.ezenrent.review.service.ReviewWriteServiceImpl;
import com.ezenrent.review.vo.ReviewVO;
import com.ezenrent.util.io.In;
import com.ezenrent.util.io.Out;
import com.ezenrent.util.io.ReviewPrint;

public class ReviewController {

	public void execute() {
		// 무한
		while (true) {
			try { // 로그인 상태에 따른 메뉴
				if (Main.login == null) {// 비회원
					Out.title("=", 16, "이용후기");
					System.out.println("1.리뷰리스트 2. 리뷰,답글보기 0. 이전메뉴 ");
					System.out.println("리뷰등록 수정 삭제를 이용하시려면 로그인이 필요합니다.");
					Out.line("=", 45);

				} else if (Main.login.getGradeNo() == 1) { // 회원
					Out.title("=", 10, "리뷰 선택창");
					System.out.println("1. 리뷰리스트 2. 리뷰,답글보기 3. 리뷰등록");
					System.out.println("4. 리뷰수정 5. 리뷰삭제 0. 이전메뉴");
					Out.line("=", 35);
				} else if (Main.login.getGradeNo() == 9) { // 관리자
					Out.title("=", 20, "리뷰 관리창");
					System.out.println("1. 리뷰리스트 2. 리뷰,답글보기 3. 답글쓰기");
					System.out.println("4. 답글수정 5. 리뷰/답글삭제");
					System.out.println("0. 이전메뉴");
					Out.line("=", 50);
				} // end of 로그인 상태에 따른 컨트롤러 메뉴
				// 비회원 이용후기
				if (Main.login == null) {
					switch (In.getString("메뉴입력")) {
					case "1": // 리뷰,답글리스트 리뷰와 답글 전체내역이 보인다
						List<ReviewVO> list = (List<ReviewVO>) Execute.run(new ReviewListServiceImpl(), null);
						ReviewPrint.print(list);
						break;
					case "2":// 리뷰/답글보기 글번호에 맞는 차량정보와 그 차량의 리뷰,답글 보이게	 
						String strNo = In.getString("글번호  ");
						long no = Long.parseLong(strNo);
						ReviewVO viewVO = (ReviewVO) Execute.run(new ReviewViewServiceImpl(), new Object[] { no, 1 });
						ReviewPrint.print(viewVO);
						// 글번호 글에맞는 관련번호 글들을 리스트로 출력
						List<ReviewVO> refNoList = (List<ReviewVO>) Execute.run(new ReviewRefNoListServiceImpl(), viewVO.getRefNo());
						ReviewPrint.print1(refNoList);
						break;
					case "0": // 이전메뉴로 돌아간다.
						return;
					default:
						System.out.println("\n ※ 잘못 입력 하셨습니다.");
						System.out.println("※ 1 ~ 2, 0번만 선택하셔야 합니다.");
						break;
					}// end of 비회원switch

					// 일반회원 이용후기
				} else if (Main.login.getGradeNo() == 1) {
					switch (In.getString("메뉴입력")) {
					case "1": // 리뷰,답글리스트 리뷰와 답글 전체내역이 보인다
						List<ReviewVO> list = (List<ReviewVO>) Execute.run(new ReviewListServiceImpl(), null);
						ReviewPrint.print(list);
				        break;
					case "2": // 리뷰/답글보기 글번호에 맞는 차량정보와 그 차량의 리뷰,답글 보이게	 	
						String strNo = In.getString("글번호  ");
						long no = Long.parseLong(strNo);
						ReviewVO viewVO = (ReviewVO) Execute.run(new ReviewViewServiceImpl(), new Object[] { no, 1 });
						ReviewPrint.print(viewVO);
						@SuppressWarnings("unchecked")
						// 글번호 글에맞는 관련번호 글들을 리스트로 출력
						List<ReviewVO> refNoList = (List<ReviewVO>) Execute.run(new ReviewRefNoListServiceImpl(), viewVO.getRefNo());
						ReviewPrint.print1(refNoList);
						break;
					case "3": // 리뷰등록
						ReviewVO writeVO = new ReviewVO();
					    RentVO rentVo = new RentVO();
					    String id = Main.login.getId();
					    rentVo = (RentVO) Execute.run(new RentMyViewServiceImpl(), id);
					    // 제목 내용만 입력하고 rentVO에서 회원의 렌탈내역정보를 가져온다
					    writeVO.setTitle(In.getString("제목"));
					    writeVO.setContent(In.getString("내용"));
					    writeVO.setId(Main.login.getId());
					    writeVO.setCarNo(rentVo.getCarNo());
					    writeVO.setRentNo(rentVo.getRentNo());
					    writeVO.setCarModel(rentVo.getCarModel());
					    writeVO.setCarType(rentVo.getCarType());
					    writeVO.setCarFuel(rentVo.getCarFuel());
					    Integer result =  (Integer) Execute.run(new ReviewWriteServiceImpl(),writeVO);
					    if (result == 1)
							System.out.println("리뷰글이 등록되었습니다.");
						else
							System.out.println("글이 등록되지 않았습니다. 다시 한번 시도해 주세요");						
						break;
					case "4": // 리뷰수정
						strNo = In.getString("수정할 글번호  ");
					    no = Long.parseLong(strNo);
						// 입력 받은 글번호의 데이터를 가져온다
						ReviewVO updateVO = (ReviewVO) Execute.run(new ReviewViewServiceImpl(), new Object[] { no, 0 });
						ReviewPrint.print1(updateVO);
						// 본인 글만 수정이 가능하다 아니면 예외처리한다.
						if (!Main.login.getId().equals(updateVO.getId())) {
							System.out.println("본인의 글만 수정 가능합니다");
							 break;
						}else { // 본인의 글일 경우
						// 데이터를 수정한다
						updateVO.setTitle(In.getString("제목 "));
						updateVO.setContent(In.getString("내용 "));
						Execute.run(new ReviewUpdateServiceImpl(), updateVO);}
						break;
					case "5": // 리뷰삭제
						strNo = In.getString("삭제할 글번호");
						no = Long.parseLong(strNo);
						ReviewVO deleteVO = new ReviewVO();
						deleteVO.setReNo(no);
						deleteVO = (ReviewVO) Execute.run(new ReviewViewServiceImpl(), new Object[] {no,0});
						// 아이디는 로그인 정보에서 가져온다			
						// DB 정보 삭제하기
						// 본인 글만 삭제가 가능하다 아니면 예외처리한다.
						if (deleteVO.getId().equals(Main.login.getId())) {
							Execute.run(new ReviewDeleteServiceImpl(), deleteVO);
                            break;
						} else { 
							System.out.println("본인의 글만 삭제 가능합니다");							
						}
						break;
					case "0": // 이전 메뉴로 돌아간다.
						return;
					default:
						System.out.println("\n ※ 잘못 입력 하셨습니다.");
						System.out.println("※ 1 ~ 5, 0번만 선택하셔야 합니다.");
						break;
					}// end of 회원switch

					// 관리자 관리창
				} else if (Main.login.getGradeNo() == 9) {
					switch (In.getString("메뉴입력")) {
					case "1": // 리뷰,답글리스트 리뷰와 답글 전체내역이 보인다
						List<ReviewVO> list = (List<ReviewVO>) Execute.run(new ReviewListServiceImpl(), null);
						ReviewPrint.print(list);
						break;
					case "2": // 리뷰/답글보기 글번호에 맞는 차량정보와 그 차량의 리뷰,답글 보이게	 		
						String strNo = In.getString("글번호  ");
						long no = Long.parseLong(strNo);
						ReviewVO viewVO = (ReviewVO) Execute.run(new ReviewViewServiceImpl(), new Object[] { no, 1 });
						ReviewPrint.print(viewVO);
						// 글번호 글에맞는 관련번호 글들을 리스트로 출력
						List<ReviewVO> refNoList = (List<ReviewVO>) Execute.run(new ReviewRefNoListServiceImpl(), viewVO.getRefNo());
						ReviewPrint.print1(refNoList);
						break;
					case "3": // 답글등록
						// 보여줄 글 번호 받기
						strNo = In.getString("답글 작성할 글번호");
						no = Long.parseLong(strNo);	
						// 보여줄 글번호 정보
						viewVO = (ReviewVO) Execute.run(new ReviewViewServiceImpl(), new Object[] {no,1});
						ReviewPrint.print1(viewVO);		
						// 데이터 입력 
						viewVO.setTitle(In.getString("제목"));
						viewVO.setContent(In.getString("내용"));
						viewVO.setCarNo(viewVO.getCarNo());					
						viewVO.setId(Main.login.getId());
						// 관련글번호, 순서번호, 들여쓰기 번호, 부모번호
						viewVO.setRefNo(viewVO.getRefNo());
						viewVO.setOrdNo(viewVO.getOrdNo() +1); // 순서번호 1증가
						viewVO.setLevNo(viewVO.getLevNo() +1); // 들여쓰기번호 1증가
						viewVO.setParentNo(viewVO.getReNo());
						Integer result1 = (Integer) Execute.run(new ReviewReplyServiceImpl(), viewVO);
						if(result1 == 1) System.out.println("답변이 등록되었습니다.");
						else System.out.println("답글이 등록이 되지 않았습니다. 다시 한번 시도해 주세요.");
						break;
					case "4": // 답글수정
						System.out.println("\n※ 개발 중 입니다 이용에 불편을 드려서 죄송합니다 ※");
						
						break;
					case "5": // 리뷰삭제 // 관리자는 본인의 글과 상관없이 삭제가 가능하다.
						strNo = In.getString("삭제할 글번호");
						no = Long.parseLong(strNo);
						ReviewVO deleteVO = new ReviewVO();
						deleteVO.setReNo(no);
						// DB 정보 삭제하기
						Execute.run(new ReviewMasterDeleteServiceImpl(), deleteVO);
						break;
					case "0":
						return;
					default:
						System.out.println("\n ※ 잘못 입력 하셨습니다.");
						System.out.println("※ 1 ~ 8, 0번만 선택하셔야 합니다.");
						break;
					}// end of 관리자switch
				} // end of if
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // end of while

	}// end of execute()

}// end of ReviewController class

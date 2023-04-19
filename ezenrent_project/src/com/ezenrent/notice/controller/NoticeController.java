package com.ezenrent.notice.controller;

import java.util.List;

import com.ezenrent.main.Execute;
import com.ezenrent.main.Main;
import com.ezenrent.member.vo.LoginVO;
import com.ezenrent.notice.service.NoticeCurrentListServiceImpl;
import com.ezenrent.notice.service.NoticeDeleteServiceImpl;
import com.ezenrent.notice.service.NoticeLastListServiceImpl;
import com.ezenrent.notice.service.NoticeListServiceImpl;
import com.ezenrent.notice.service.NoticePreviewServiceImpl;
import com.ezenrent.notice.service.NoticeReservationListServiceImpl;
import com.ezenrent.notice.service.NoticeSearchListServiceImpl;
import com.ezenrent.notice.service.NoticeUpdateServiceImpl;
import com.ezenrent.notice.service.NoticeViewServiceImpl;
import com.ezenrent.notice.service.NoticeWriteServiceImpl;
import com.ezenrent.notice.vo.NoticeVO;
import com.ezenrent.util.io.In;
import com.ezenrent.util.io.NoticePrint;
import com.ezenrent.util.io.Out;

public class NoticeController {

	@SuppressWarnings("unchecked")
	public void execute() {
	//	System.out.println("NoticeController.execute()-------");
		// 관리자 
		boolean Server = Main.login == null || Main.login.getGradeNo() == 1;
		// 비회원과 회원
		boolean isAdmin = Main.login != null && Main.login.getGradeNo() == 9;
		
		while (true) {

			try {
				// 메뉴 출력
				Out.title("-", 40, "공지 사항");
				// 메뉴 입력 및 처리
				//비회원과 회원이 보는 화면 출력
				System.out.println((Server) ? "[1. 공지사항 리스트] [2.공지사항 보기] [0.이전메뉴]" : "");
				// 관리자가 보는 화면 출력
				System.out.print((isAdmin) ? "[1. 공지사항 리스트] [2.공지사항 보기] [0.이전메뉴] \n[3. 공지 등록][4 공지 수정][5.공지 삭제][6.공지사항 검색]" : "[6.공지사항 검색]");
				System.out.println();

				switch (In.getString("메뉴 입력")) {

				case "1":
					// 공지리스트
					if (isAdmin) {
						Out.line("=", 50);
						System.out.println("[1.전체공지] [2.현재공지] [3.예약공지] [4.지난공지]");
						Out.line("=", 50);

						switch (In.getString("메뉴입력")) {
						case "1"://전체공지
							List<NoticeVO> result = (List<NoticeVO>) Execute.run(new NoticeListServiceImpl(), null);
							NoticePrint.print(result);

							break;
						case "2"://현재공지
							List<NoticeVO> result2 = (List<NoticeVO>) Execute.run(new NoticeCurrentListServiceImpl(),
									null);
							NoticePrint.print(result2);

							break;

						case "3"://예약공지
							List<NoticeVO> result3 = (List<NoticeVO>) Execute
									.run(new NoticeReservationListServiceImpl(), null);
							NoticePrint.print(result3);

							break;
						case "4":// 지난공지 
							List<NoticeVO> result4 = (List<NoticeVO>) Execute.run(new NoticeLastListServiceImpl(),
									null);
							NoticePrint.print(result4);

							break;

						default:
							System.out.println("잘못된 메뉴를 선택하셨습니다 \n1~4번 중 선택하세요.");
							break;
						}

					} else if (Server) {
						//비회원과 회원은 현재공지 리스트만 볼수있습니다.
						List<NoticeVO> result = (List<NoticeVO>) Execute.run(new NoticeCurrentListServiceImpl(), null);
						NoticePrint.print(result);

					}

					break;
					
				case "2":
					// 공지 보기

					String strNo = In.getString("어떤 글을 보시겠습니까?");
					long no = Long.parseLong(strNo);
					if (isAdmin) {
						// 관리자는 모든 공지를 볼수있습니다
						NoticeVO viewVO = (NoticeVO) Execute.run(new NoticeViewServiceImpl(), new Object[] { no, 1 });
						NoticePrint.print(viewVO);

					} else if (Server) {
						//비회원과 회원은 현재 공지만 볼수있습니다.
						NoticeVO viewVO = (NoticeVO) Execute.run(new NoticePreviewServiceImpl(),
								new Object[] { no, 1 });
						NoticePrint.print(viewVO);
					}

					break;

				case "6":
					// 공지 검색 
					NoticeVO vo = new NoticeVO();
					// 제목 검색 
					String title = vo.getTitle();
					System.out.println();
					System.out.println("[제목]에서 원하는 검색어를 검색 할수있습니다.");
					title = In.getString("검색어를 입력하세요.");
				if(isAdmin||Server) {	
					List<NoticeVO> result = (List<NoticeVO>) Execute.run(new NoticeSearchListServiceImpl(), title);
					NoticePrint.print(result);	
				}
					break;
				case "0":// 이전메뉴
					Out.line("-", 40);
					System.out.println("++++++++[ 이전메뉴로 돌아갑니다. ]++++++++");
					Out.line("-", 40);
					return;
				case "3":// 공지등록
					if (isAdmin) {

						NoticeVO writeVO = new NoticeVO();
						System.out.println();
						writeVO.setTitle(In.getString("공지제목을 입력하세요."));
						writeVO.setContent(In.getString("공지내용을 입력하세요."));
						System.out.println("yyyy-mm-dd 형식에 맞춰서 입력하세요.");
						writeVO.setStartDate(In.getString("공지시작일(yyyy-mm-dd)"));
						writeVO.setEndDate(In.getString("공지종료일(yyyy-mm-dd)"));
						// 공지사항 작성
						Integer result = (Integer) Execute.run(new NoticeWriteServiceImpl(), writeVO);
						
						if (result == 1) {
							Out.line("=", 50);
							System.out.println("공지사항이 정상적으로 등록되었습니다.");
							Out.line("=", 50);
						} else {
							Out.line("=", 50);
							System.out.println("공지사항이 등록되지않았습니다.");
							System.out.println("형식에 맞는 데이터를 입력하세요.");
							Out.line("=", 50);}
					}
					break;
				case "4":// 공지 수정
					if (isAdmin) {
						// 수정할 글 번호 불러오기
						NoticeVO updateVO = new NoticeVO();
						strNo = In.getString("어떤 글을 수정하시겠습니까?");
						no = Long.parseLong(strNo);

						updateVO = (NoticeVO) Execute.run(new NoticeViewServiceImpl(), new Object[] { no, 0 });

						// 수정 서비스 따로 만들어둠
						updateVO(updateVO);

						Integer result = (Integer) Execute.run(new NoticeUpdateServiceImpl(), updateVO);
						
						break;
					}
				case "5":// 공지 삭제
					if (isAdmin) {
						strNo = In.getString("어떤 글을 삭제하시겠습니까?");
						no = Long.parseLong(strNo);
						Out.line("=", 50);
						System.out.println("삭제하시겠습니까? ");
						System.out.println("[1.예] [2.아니요] ");
						Out.line("=", 50);
						//삭제할 번호 입력
						NoticeVO deleteVO = new NoticeVO();
						deleteVO.setNo(no);
						switch (In.getString("선택")) {
						case "1":
							Integer result = (Integer) Execute.run(new NoticeDeleteServiceImpl(), deleteVO);
							if (result == 1) 
								Out.line("=", 50);
								System.out.println("공지사항이 정상적으로 삭제되었습니다.");
								Out.line("=", 50);
							break;
						case "2":
							Out.line("=", 50);
							System.out.println("삭제를 취소하였습니다.");
							Out.line("=", 50);
							return;

						default:
							break;
						}


					}
				default:
					//Out.error("+", 60, "잘못된 번호를 입력하였습니다. 다시 시도해주세요.");
					break;
				}
			} catch (Exception e) {
				// 개발자를 위한 코드
		//		e.printStackTrace();
				// 사용자를 위한 코드
				Out.error("*", 40, "잘못된 접근입니다.다시 실행해주세요");
				return;
			}
		}

	}// end of execute()

	// 수정할 서비스 만들기

	public void updateVO(NoticeVO vo) throws Exception {
		while (true) {
			Out.line("=", 50);
			System.out.println("[1.공지제목] [2.공지내용] [3.공지시작일]\n[4.공지종료일] [0.수정완료]");
			Out.line("=", 50);
		
				switch (In.getString("수정할곳 번호 입력하세요")) {
				case "1":
					vo.setTitle(In.getString("공지 제목"));

					break;
				case "2":
					vo.setContent(In.getString("공지 내용"));

					break;
				case "3":
					vo.setStartDate(In.getString("공지 시작일"));

					break;
				case "4":
					vo.setEndDate(In.getString("공지 종료일"));

					break;
				case "0":
					
					Out.line("=", 50);
					System.out.println("공지 수정이 정상적으로 완료되었습니다.");
					Out.line("=", 50);
					return;
			
				default:
					Out.error("+", 60, "잘못된 번호를 입력하였습니다. 다시 시도해주세요.");
					break;
				}

		}
	}// end of updateVO
}// end of NoticeController()
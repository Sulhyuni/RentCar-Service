package com.ezenrent.util.io;

import java.util.List;


//import com.ezenrent.review.service.ReviewRefNoViewServiceImpl;
import com.ezenrent.review.vo.ReviewVO;

public class ReviewPrint {
	
	// 리스트
	@SuppressWarnings("null")
	public static void print(List<ReviewVO>list) throws Exception{
//		System.out.println("ReviewPrint.print(list)");
		if(list == null && list.size() == 0)
			throw new Exception("데이터가 존재하지 않습니다");
		System.out.println();
		System.out.println("================[ 리뷰 리스트 ]=======================");
	    System.out.println("\n 번호 | 차이름 | 제목 | 아이디 |   작성일   | 조회수 | 관련번호");
	    for(ReviewVO vo : list) {
	    	System.out.print(" " + vo.getReNo());
	    	System.out.print("  ");
	    	for(int i = 1; i<= vo.getLevNo(); i++)System.out.print("");
	    	System.out.print(" | " + vo.getCarModel());
	    	System.out.print(" | " + vo.getTitle());
	    	System.out.print(" | " + vo.getId());
	    	System.out.print(" | " + vo.getReviewDate());
	    	System.out.print(" | " + vo.getHit());
	    	System.out.print(" | " + vo.getRefNo());
	    	System.out.println();
	    }// end of for
	    Out.line("=", 60);
	}// end of list print

	// 보기
	// 번호에 따른 차 상세정보와 그 차에 대한 리뷰와 답변글이 보이도록한다
	public static void print(ReviewVO vo)throws Exception{
//		System.out.println("ReviewPrint.print(vo)");
		if(vo == null)throw new Exception("데이터가 존재하지 않습니다.");
		Out.line("=", 50);
		System.out.println(" 차량 상세정보");
	    System.out.println(" 차이름 : " + vo.getCarModel());
		System.out.println(" 차종 : " + vo.getCarType() );
		System.out.println(" 연료 : " + vo.getCarFuel());
		System.out.println("================================================================================================");
	}
		
		public static void print1(ReviewVO vo)throws Exception{
//			System.out.println("ReviewPrint.print(vo)");
			if(vo == null)throw new Exception("데이터가 존재하지 않습니다.");
			Out.line("=", 50);
			System.out.println(" 차량 상세정보");
			System.out.println(" 차이름 : " + vo.getCarModel());
			System.out.println(" 차종 : " + vo.getCarType() );
			System.out.println(" 연료 : " + vo.getCarFuel());
			System.out.println("================================================================================================");
			System.out.println(" 제목 :" + vo.getTitle());
			System.out.println(" 내용 : " + vo.getContent());
			System.out.println(" 아이디 : " + vo.getId());
			System.out.println(" 작성일 : " + vo.getReviewDate());
			System.out.println(" 조회수 : " + vo.getHit());
			System.out.println(" 관련번호 : " + vo.getRefNo());
			Out.line("=", 30);
	}// end of view

	// 원글
	@SuppressWarnings("null")
	public static void print1(List<ReviewVO> refNolist )throws Exception{
//		System.out.println("ReviewPrint.print1(list)");
		if(refNolist == null && refNolist.size() == 0)
			throw new Exception("데이터가 존재하지 않습니다");
		for(ReviewVO vo : refNolist) {
			System.out.print(" " + vo.getReNo());
	    	System.out.print("  ");
		for(int i = 1; i<= vo.getLevNo(); i++)System.out.print("");
		System.out.println(" 제목 :" + vo.getTitle());
		System.out.println(" 내용 : " + vo.getContent());
		System.out.println(" 아이디 : " + vo.getId());
		System.out.println(" 작성일 : " + vo.getReviewDate());
		System.out.println(" 조회수 : " + vo.getHit());
		System.out.println(" 관련번호 : " + vo.getRefNo());
		Out.line("=", 30);
		}
	}
	
}// end of reviewprint class

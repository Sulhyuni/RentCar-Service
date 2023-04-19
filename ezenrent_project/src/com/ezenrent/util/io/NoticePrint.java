package com.ezenrent.util.io;

import java.util.List;

import com.ezenrent.notice.vo.NoticeVO;




public class NoticePrint {
	public static void print(List<NoticeVO>list) {
		Out.line("-", 80);
		System.out.println("번호  |         제목          |  공지시작일  |  공지종료일  | 조회수");
		Out.line("-", 80);
		if(list ==null || list.size()==0) {
			Out.line("=", 40);
			System.out.println("데이터가 존재하지않습니다.다시 시도해주세요.");
			Out.line("=", 40);
			return;
		}
		
		for(NoticeVO vo : list) {
			System.out.print(" " + vo.getNo());
			System.out.print("   | " +  vo.getTitle());
			System.out.print("   | " +  vo.getStartDate());
			System.out.print("   | " +  vo.getEndDate());
			System.out.print("   | " +  vo.getHit());
			
			System.out.println();
			
		}
	}
	
	public static void print(NoticeVO vo) {
		
		if(vo==null) {
			Out.line("=", 30);
			System.out.println("데이터가 존재하지않습니다.다시 시도해주세요.");
			Out.line("=", 30);
			return;
		}

			System.out.println("  번호 :" + vo.getNo());
			System.out.println("  제목 : " + vo.getTitle());
			System.out.println("  내용 : " + vo.getContent());
			System.out.println("  공지시작일 : " + vo.getStartDate());
			System.out.println("  공지마감일 :" + vo.getEndDate());
			System.out.println("  공지일 : " + vo.getUpdateDate());
			System.out.println("  조회수 : " + vo.getHit());
			
			System.out.println();
			
		}
		

}

package com.ezenrent.util.io;

import java.util.List;

import com.ezenrent.main.Main;
import com.ezenrent.member.vo.MemberVO;

public class MemberPrint {

	public static void print(List<MemberVO> list) {
		System.out.println();
		Out.line("=", 77);
		System.out.println(" 아이디 |  이름  |  성별  |    연락처    | 상태 |등급|  등급명  | 최근접속일");
		Out.line("―", 77);

		if (list == null || list.size() == 0) {
			System.out.println("\n ✖ 검색 결과가 없습니다.");
			return;
		}
		
		for(int i = 0; i < list.size(); i++) {
			MemberVO vo = list.get(i);
			System.out.print(" "+vo.getId()+" | "+ vo.getName()+" | "+vo.getGender()+" | "
					+vo.getTel()+" | "+vo.getStatus()+" | "+vo.getGradeNo()
					+" | "+vo.getGradeName()+" | "+vo.getConDate());
			System.out.println();
		}
		Out.line("=", 77);
	}

	public static void print(MemberVO vo) {
		if (vo == null) {
			System.out.println("\n ✖ 존재하지 않는 회원입니다.");
			return;
		}
		System.out.println();
		Out.title("=", 16, vo.getName()+"님의 회원정보");
		System.out.println();
		System.out.println(" - 아이디 : " + vo.getId());
		System.out.println(" - 이름 : " + vo.getName());
		System.out.println(" - 성별 : " + vo.getGender());
		System.out.println(" - 생년월일 : " + vo.getBirth());
		System.out.println(" - 연락처 : " + vo.getTel());
		System.out.println(" - 이메일 : " + vo.getEmail());
		System.out.println(" - 면허번호 : " + vo.getLicenseNo());
		if (Main.isAdmin()) {
			System.out.println(" - 최근접속일 : " + vo.getConDate());
			System.out.println(" - 상태 : " + vo.getStatus());
			System.out.println(" - 등급번호 : " + vo.getGradeNo());
			System.out.println(" - 등급이름 : " + vo.getGradeName());
		}
		System.out.println();
		Out.line("=", 52);
	}
}
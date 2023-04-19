package com.ezenrent.member.dao;

import java.util.List;
import com.ezenrent.member.vo.LoginVO;
import com.ezenrent.member.vo.MemberVO;

public interface MemberDAO {
	public LoginVO login(LoginVO vo) throws Exception;
	
	public List<MemberVO> list(MemberVO vo) throws Exception;
	
	public boolean idList(String id) throws Exception;
	
	public MemberVO view(String str) throws Exception;
	
	public MemberVO viewPw(String str) throws Exception;
	
	public Integer write(MemberVO vo) throws Exception;
	
	public Integer update(MemberVO vo) throws Exception;
	
	public Integer updateDate(String str) throws Exception;
	
	public Integer updatePw(MemberVO vo) throws Exception;
	
	public Integer rest(String date) throws Exception;
	
	public Integer wakeUp(MemberVO vo) throws Exception;
	
	public Integer delete(String str) throws Exception;
	
	public LoginVO findId(LoginVO vo) throws Exception;
	
	public MemberVO findPw(MemberVO vo) throws Exception;
}

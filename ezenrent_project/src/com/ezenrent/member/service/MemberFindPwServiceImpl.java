package com.ezenrent.member.service;

import com.ezenrent.main.ServiceInterface;
import com.ezenrent.member.dao.MemberDAO;
import com.ezenrent.member.dao.MemberDAOImpl;
import com.ezenrent.member.vo.MemberVO;

public class MemberFindPwServiceImpl implements ServiceInterface {

	@Override
	public Object service(Object obj) throws Exception {
		MemberDAO dao = new MemberDAOImpl();
		return dao.findPw((MemberVO)obj);
	}

}

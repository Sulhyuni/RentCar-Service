package com.ezenrent.member.service;

import com.ezenrent.main.ServiceInterface;
import com.ezenrent.member.dao.MemberDAO;
import com.ezenrent.member.dao.MemberDAOImpl;
import com.ezenrent.member.vo.LoginVO;

public class MemberLoginServiceImpl implements ServiceInterface {
	@Override
	public Object service(Object obj) throws Exception {
		MemberDAO dao = new MemberDAOImpl();
		LoginVO login = (LoginVO) obj;
		if(dao.updateDate(login.getId()) == 1) {
			return dao.login((LoginVO)obj);
		} else {
			return null;
		}
	}
}

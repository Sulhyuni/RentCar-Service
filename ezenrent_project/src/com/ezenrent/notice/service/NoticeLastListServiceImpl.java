package com.ezenrent.notice.service;

import com.ezenrent.main.ServiceInterface;
import com.ezenrent.notice.dao.NoticeDAO;
import com.ezenrent.notice.dao.NoticeDAOImpl;

public class NoticeLastListServiceImpl implements ServiceInterface {
//지난 공지 리스트 
	@Override
	public Object service(Object obj) throws Exception {
//		System.out.println("LastListServiceImpl.service()");
		NoticeDAO dao = new NoticeDAOImpl();
		return dao.lastList();
	}

}

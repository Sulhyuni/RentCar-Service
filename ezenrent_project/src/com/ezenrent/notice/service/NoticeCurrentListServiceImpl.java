package com.ezenrent.notice.service;

import com.ezenrent.main.ServiceInterface;
import com.ezenrent.notice.dao.NoticeDAO;
import com.ezenrent.notice.dao.NoticeDAOImpl;

public class NoticeCurrentListServiceImpl implements ServiceInterface {
//현재 공지 리스트 서비스 
	@Override
	public Object service(Object obj) throws Exception {
	//	System.out.println("NoticeCurrentListServiceImpl.service()");
		NoticeDAO dao = new NoticeDAOImpl();
		return dao.currentList();
	}

}

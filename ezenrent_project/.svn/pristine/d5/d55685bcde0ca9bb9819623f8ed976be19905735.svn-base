package com.ezenrent.notice.service;

import com.ezenrent.main.ServiceInterface;
import com.ezenrent.notice.dao.NoticeDAO;
import com.ezenrent.notice.dao.NoticeDAOImpl;

public class NoticeSearchListServiceImpl implements ServiceInterface {
// 검색 가능한 리스트 
	@Override
	public Object service(Object obj) throws Exception {
	//	System.out.println("NoticeListServiceImpl.service()");
		NoticeDAO dao = new NoticeDAOImpl();
		return dao.searchNotice((String)obj);
	}

}

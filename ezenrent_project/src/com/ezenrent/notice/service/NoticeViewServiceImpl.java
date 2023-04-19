
package com.ezenrent.notice.service;

import com.ezenrent.main.ServiceInterface;
import com.ezenrent.notice.dao.NoticeDAO;
import com.ezenrent.notice.dao.NoticeDAOImpl;

public class NoticeViewServiceImpl implements ServiceInterface{
//전체 공지 보기 
	@Override
	public Object service(Object obj) throws Exception {
//		System.out.println("NoticeViewServiceImpl.service()");
		// 조회수 증가 
		Object[]objs = (Object[]) obj;
		long no = (long) objs[0];
		int inc = (int) objs[1];
	//DAO 생성 호출해서 넘기기 
		NoticeDAO dao = new NoticeDAOImpl();
		if(inc ==1)dao.increase(no);
		return dao.view(no);
			
	}

}


package com.ezenrent.notice.service;

import com.ezenrent.main.ServiceInterface;
import com.ezenrent.notice.dao.NoticeDAO;
import com.ezenrent.notice.dao.NoticeDAOImpl;
import com.ezenrent.notice.vo.NoticeVO;

public class NoticeWriteServiceImpl implements ServiceInterface{
// 공지 등록 서비스 
	@Override
	public Object service(Object obj) throws Exception {
		NoticeDAO dao = new NoticeDAOImpl();
		return dao.write((NoticeVO)obj);
	}

}

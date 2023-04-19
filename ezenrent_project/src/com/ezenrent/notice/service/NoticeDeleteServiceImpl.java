
package com.ezenrent.notice.service;

import com.ezenrent.main.ServiceInterface;
import com.ezenrent.notice.dao.NoticeDAO;
import com.ezenrent.notice.dao.NoticeDAOImpl;
import com.ezenrent.notice.vo.NoticeVO;

public class NoticeDeleteServiceImpl implements ServiceInterface{
//삭제 서비스 
	@Override
	public Object service(Object obj) throws Exception {
		NoticeDAO dao = new NoticeDAOImpl();
		return dao.delete((NoticeVO)obj);
	}

}

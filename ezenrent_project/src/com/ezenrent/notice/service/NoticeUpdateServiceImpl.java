
package com.ezenrent.notice.service;

import com.ezenrent.main.ServiceInterface;
import com.ezenrent.notice.dao.NoticeDAO;
import com.ezenrent.notice.dao.NoticeDAOImpl;
import com.ezenrent.notice.vo.NoticeVO;

public class NoticeUpdateServiceImpl implements ServiceInterface{
// 수정하는 서비스 
	@Override
	public Object service(Object obj) throws Exception {
		NoticeDAO dao = new NoticeDAOImpl();
		return dao.update((NoticeVO)obj);
	}

}

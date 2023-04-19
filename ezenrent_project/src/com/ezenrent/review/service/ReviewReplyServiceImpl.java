package com.ezenrent.review.service;

import com.ezenrent.main.ServiceInterface;
import com.ezenrent.review.dao.ReviewDAO;
import com.ezenrent.review.dao.ReviewDAOImpl;
import com.ezenrent.review.vo.ReviewVO;

public class ReviewReplyServiceImpl implements ServiceInterface{

	@Override
	public Object service(Object obj) throws Exception {
//		System.out.println("ReviewReply.ServiceImpl.service()");	
		ReviewDAO dao = new ReviewDAOImpl();
		dao.ordNoIncrease((ReviewVO)obj);
		return dao.reply((ReviewVO)obj);
	}

}

package com.ezenrent.review.service;

import com.ezenrent.main.ServiceInterface;
import com.ezenrent.review.dao.ReviewDAO;
import com.ezenrent.review.dao.ReviewDAOImpl;

public class ReviewRefNoListServiceImpl implements ServiceInterface{

	@Override
	public Object service(Object obj) throws Exception {
//		System.out.println("ReviewRefNoListServiceImpl.service()============================");
		ReviewDAO dao = new ReviewDAOImpl();
		return dao.refNolist((long)obj);
	}

}

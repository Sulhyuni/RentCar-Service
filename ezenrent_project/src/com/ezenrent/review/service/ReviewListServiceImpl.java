package com.ezenrent.review.service;

import com.ezenrent.main.ServiceInterface;
import com.ezenrent.review.dao.ReviewDAO;
import com.ezenrent.review.dao.ReviewDAOImpl;

public class ReviewListServiceImpl implements ServiceInterface {
// 리뷰 리스트
	@Override
	public Object service(Object obj) throws Exception {
//		System.out.println("ReviewListServiceImpl.service()============================");
		ReviewDAO dao = new ReviewDAOImpl();
		return dao.list();
	}
}

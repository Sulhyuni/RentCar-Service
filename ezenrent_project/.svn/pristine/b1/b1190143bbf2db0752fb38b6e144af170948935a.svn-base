package com.ezenrent.review.service;

import com.ezenrent.main.ServiceInterface;
import com.ezenrent.review.dao.ReviewDAO;
import com.ezenrent.review.dao.ReviewDAOImpl;

public class ReviewViewServiceImpl implements ServiceInterface {
// 리뷰 보기
	@Override
	public Object service(Object obj) throws Exception {
//		System.out.println("ReviewViewServiceImpl.service()===================");
		Object[] objs = (Object[]) obj;
		long reNo = (long) objs[0];
		int inc = (int) objs[1];
		ReviewDAO dao = new ReviewDAOImpl();
		if (inc == 1)
			dao.increase(reNo);
		return dao.view(reNo);
	}

}

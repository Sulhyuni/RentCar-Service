package com.ezenrent.review.service;

import com.ezenrent.main.ServiceInterface;
import com.ezenrent.review.dao.ReviewDAO;
import com.ezenrent.review.dao.ReviewDAOImpl;
import com.ezenrent.review.vo.ReviewVO;

public class ReviewDeleteServiceImpl implements ServiceInterface {
// 리뷰 삭제
	@Override
	public Object service(Object obj) throws Exception {
//		System.out.println("ReviewDeleteServiceImpl.service()============");
		ReviewDAO dao = new ReviewDAOImpl();
		return dao.delete((ReviewVO) obj);
	}
}
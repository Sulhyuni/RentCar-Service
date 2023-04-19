package com.ezenrent.rent.service;

import com.ezenrent.main.ServiceInterface;
import com.ezenrent.rent.dao.RentDAO;
import com.ezenrent.rent.dao.RentDAOImpl;
import com.ezenrent.rent.vo.RentVO;

public class RentDeleteServiceImpl implements ServiceInterface {

	@Override
	public Object service(Object obj) throws Exception {
		//System.out.println("RentDeleteService.service().....");
		
		RentDAO dao = new RentDAOImpl();
		return dao.delete((RentVO) obj);
	}

}

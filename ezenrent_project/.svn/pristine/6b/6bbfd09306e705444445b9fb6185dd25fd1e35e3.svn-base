package com.ezenrent.rent.service;

import com.ezenrent.main.ServiceInterface;
import com.ezenrent.rent.dao.RentDAO;
import com.ezenrent.rent.dao.RentDAOImpl;

public class RentViewServiceImpl implements ServiceInterface {

	@Override
	public Object service(Object obj) throws Exception {
		//System.out.println("RentViewService.service().....");
		
		
		RentDAO dao = new RentDAOImpl();
		return dao.view((long) obj);
	}

}

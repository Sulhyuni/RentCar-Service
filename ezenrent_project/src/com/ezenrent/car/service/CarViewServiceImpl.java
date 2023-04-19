package com.ezenrent.car.service;

import com.ezenrent.car.dao.CarDAO;
import com.ezenrent.car.dao.CarDAOImpl;
import com.ezenrent.main.ServiceInterface;

public class CarViewServiceImpl implements ServiceInterface {

	@Override
	public Object service(Object obj) throws Exception {
		// TODO Auto-generated method stub
		CarDAO dao = new CarDAOImpl();
		return dao.view((Long) obj);
	}

}

package com.ezenrent.car.service;

import com.ezenrent.car.dao.CarDAO;
import com.ezenrent.car.dao.CarDAOImpl;
import com.ezenrent.car.vo.CarVO;
import com.ezenrent.main.ServiceInterface;

public class CarDeleteServiceImpl implements ServiceInterface {

	@Override
	public Object service(Object obj) throws Exception {
		// TODO Auto-generated method stub
		CarDAO dao = new CarDAOImpl();
		return dao.delete((long) obj);
	}

}

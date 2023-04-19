package com.ezenrent.rent.service;

import com.ezenrent.car.vo.CarVO;
import com.ezenrent.main.Execute;
import com.ezenrent.main.Main;
import com.ezenrent.main.ServiceInterface;
import com.ezenrent.rent.dao.RentDAO;
import com.ezenrent.rent.dao.RentDAOImpl;
import com.ezenrent.rent.vo.RentVO;
import com.ezenrent.util.io.RentPrint;

public class RentUpdateStatusServiceImpl implements ServiceInterface {

	@Override
	public Object service(Object obj) throws Exception {
		//System.out.println("RentUpdateStatusService.service().....");
		
		RentDAO dao = new RentDAOImpl();
		return dao.updateStatus((RentVO) obj);
	}

}




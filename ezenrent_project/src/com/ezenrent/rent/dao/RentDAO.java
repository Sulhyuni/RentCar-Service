package com.ezenrent.rent.dao;

import java.util.List;

import com.ezenrent.car.vo.CarVO;
import com.ezenrent.rent.vo.RentVO;

public interface RentDAO {
	
	
	//list
	public List<RentVO>list(RentVO vo) throws Exception;
	
	//상세 view
	public RentVO view(long no) throws Exception;
	
	// 나의 대여내역 보기
	public RentVO myView(String id) throws Exception;
	
	//예약 write
	public  Integer write (RentVO vo) throws Exception;
	
	//예약 수정 update
	public Integer update(RentVO vo)throws Exception;
	
	//delete
	public Integer delete(RentVO vo)throws Exception;
	
	//status변경 
	public Integer updateStatus(RentVO vo) throws Exception ;
	
	
}

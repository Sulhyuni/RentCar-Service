package com.ezenrent.car.dao;

import java.util.ArrayList;
import java.util.List;

import com.ezenrent.car.vo.CarVO;
import com.ezenrent.member.vo.MemberVO;
import com.ezenrent.util.db.DAO;
import com.ezenrent.util.db.DB;

public class CarDAOImpl extends DAO implements CarDAO {

	// 차량 전체 리스트
	@Override
	public List<CarVO> list() throws Exception {
		// TODO Auto-generated method stub

		List<CarVO> list = null;

		try {

			// 1. 확인 2. 연결
			con = DB.getConnection();
//			System.out.println("1. 확인\n2. 연결");
			// 3. sql
			String sql = "select carNo, carStatus, carType, carBrand, carModel, carSeat, carYear, carPrice from car order by carNo desc";
//			System.out.println("3. sql");
			// 4. 실행 객체, 데이터 세팅
			pstmt = con.prepareStatement(sql);
//			System.out.println("4. 실행 객체, 데이터 세팅");
			// 5. 실행
			rs = pstmt.executeQuery();
//			System.out.println("5. 실행");
			// 6. 저장, 표시
			if (rs != null) {
				while (rs.next()) {
					if (list == null)
						list = new ArrayList<CarVO>();
					CarVO vo = new CarVO();
					vo.setCarNo(rs.getLong("carNo"));
					vo.setCarStatus(rs.getString("carStatus"));
					vo.setCarType(rs.getString("carType"));
					vo.setCarBrand(rs.getString("carBrand"));
					vo.setCarModel(rs.getString("carModel"));
					vo.setCarSeat(rs.getString("carSeat"));
					vo.setCarYear(rs.getString("carYear"));
					vo.setCarPrice(rs.getLong("carPrice"));

					list.add(vo);

				}
//				System.out.println("6. 저장, 표시");
			}

		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
			System.out.println(e.getMessage());
			throw new Exception("차량 리스트 DB 처리 중 오류가 발생했습니다");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt, rs);
//			System.out.println("7. 닫기");
		}

		return list;
	} // end of list

	// 대여 가능 차량 리스트
	@Override
	public List<CarVO> posList() throws Exception {
		// TODO Auto-generated method stub

		List<CarVO> posList = null;

		try {

			// 1. 확인 2. 연결
			con = DB.getConnection();
//			System.out.println("1. 확인\n2. 연결");
			// 3. sql
			String sql = "select carNo, carStatus, carType, carBrand, carModel, carSeat, carYear, carPrice from car where carStatus = '가능' order by carNo desc";
//			System.out.println("3. sql");
			// 4. 실행 객체, 데이터 세팅
			pstmt = con.prepareStatement(sql);
//			System.out.println("4. 실행 객체, 데이터 세팅");
			// 5. 실행
			rs = pstmt.executeQuery();
//			System.out.println("5. 실행");
			// 6. 저장, 표시
			if (rs != null) {
				while (rs.next()) {
					if (posList == null)
						posList = new ArrayList<CarVO>();
					CarVO vo = new CarVO();
					vo.setCarNo(rs.getLong("carNo"));
					vo.setCarStatus(rs.getString("carStatus"));
					vo.setCarType(rs.getString("carType"));
					vo.setCarBrand(rs.getString("carBrand"));
					vo.setCarModel(rs.getString("carModel"));
					vo.setCarSeat(rs.getString("carSeat"));
					vo.setCarYear(rs.getString("carYear"));
					vo.setCarPrice(rs.getLong("carPrice"));

					posList.add(vo);

				}
//				System.out.println("6. 저장, 표시");
			}

		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
			System.out.println(e.getMessage());
			throw new Exception("대여 가능 차량 리스트 DB 처리 중 오류가 발생했습니다");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt, rs);
//			System.out.println("7. 닫기");
		}

		return posList;
	} // end of posList

	// 차량 상세 보기
	@Override
	public CarVO view(long no) throws Exception {
		// TODO Auto-generated method stub

		CarVO vo = null;

		try {

			// 1. 확인 2. 연결
			con = DB.getConnection();
//			System.out.println("1. 확인\n2. 연결");
			// 3. sql
			String sql = "select carNo, carStatus, carType, carBrand, carModel, carSeat, carYear, carPrice, carFuel, carOption from car where carNo = ?";
//			System.out.println("3. sql");
			// 4. 실행 객체, 데이터 세팅
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
//			System.out.println("4. 실행 객체, 데이터 세팅");
			// 5. 실행
			rs = pstmt.executeQuery();
//			System.out.println("5. 실행");
			// 6. 저장, 표시
			if (rs != null && rs.next()) {
				vo = new CarVO();
				vo.setCarNo(rs.getLong("carNo"));
				vo.setCarStatus(rs.getString("carStatus"));
				vo.setCarType(rs.getString("carType"));
				vo.setCarBrand(rs.getString("carBrand"));
				vo.setCarModel(rs.getString("carModel"));
				vo.setCarSeat(rs.getString("carSeat"));
				vo.setCarYear(rs.getString("carYear"));
				vo.setCarPrice(rs.getLong("carPrice"));
				vo.setCarFuel(rs.getString("carFuel"));
				vo.setCarOption(rs.getString("carOption"));

			}
//			System.out.println("6. 저장, 표시");

		} catch (Exception e) {
			// TODO: handle exception
//			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new Exception("차량 상세 보기 DB 처리 중 오류가 발생했습니다");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt, rs);
//			System.out.println("7. 닫기");
		}
		return vo;
	} // end of view

	// 차량 등록
	@Override
	public Integer write(CarVO vo) throws Exception {
		// TODO Auto-generated method stub

		int result = 0;

		try {

			// 1. 확인 2. 연결
			con = DB.getConnection();
//			System.out.println("1. 확인\n2. 연결");
			// 3. sql
			String sql = "insert into car(carNo, carStatus, carType, carBrand, carModel, carSeat, carYear, carPrice, carFuel, carOption) values(car_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//			System.out.println("3. sql");
			// 4. 실행 객체, 데이터 세팅
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getCarStatus());
			pstmt.setString(2, vo.getCarType());
			pstmt.setString(3, vo.getCarBrand());
			pstmt.setString(4, vo.getCarModel());
			pstmt.setString(5, vo.getCarSeat());
			pstmt.setString(6, vo.getCarYear());
			pstmt.setLong(7, vo.getCarPrice());
			pstmt.setString(8, vo.getCarFuel());
			pstmt.setString(9, vo.getCarOption());
//			System.out.println("4. 실행 객체, 데이터 세팅");
			// 5. 실행
			result = pstmt.executeUpdate();
//			System.out.println("5. 실행");
			// 6. 저장, 표시
//			if (result == 1)
//				System.out.println("6. 저장, 표시");
//			else
//				System.out.println("6. 저장, 표시 실패");
		} catch (Exception e) {
			// TODO: handle exception
//			e.printStackTrace();
			// System.out.println(e.getMessage());
			throw new Exception("차량 등록 DB 처리 중 오류가 발생했습니다");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt);
//			System.out.println("7. 닫기");
		}
		return result;
	} // end of write

	// 차량 수정
	@Override
	public Integer update(CarVO vo) throws Exception {
		// TODO Auto-generated method stub

		int result = 0;

		try {

			// 1. 확인 2. 연결
			con = DB.getConnection();
//			System.out.println("1. 확인\n2. 연결");
			// 3. sql
			String sql = "update car set carStatus = ?, carType = ?, carBrand = ?, carModel = ?, carSeat = ?, carYear = ?, carPrice = ?, carFuel = ?, carOption = ? where carNo = ?";
//			System.out.println("3. sql");
			// 4. 실행 객체, 데이터 세팅
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getCarStatus());
			pstmt.setString(2, vo.getCarType());
			pstmt.setString(3, vo.getCarBrand());
			pstmt.setString(4, vo.getCarModel());
			pstmt.setString(5, vo.getCarSeat());
			pstmt.setString(6, vo.getCarYear());
			pstmt.setLong(7, vo.getCarPrice());
			pstmt.setString(8, vo.getCarFuel());
			pstmt.setString(9, vo.getCarOption());
			pstmt.setLong(10, vo.getCarNo());
//			System.out.println("4. 실행 객체, 데이터 세팅");
			// 5. 실행
			result = pstmt.executeUpdate();
//			System.out.println("5. 실행");
			// 6. 저장, 표시
//			if (result == 1)
//				System.out.println("6. 저장, 표시");
//			else
//				System.out.println("6. 저장, 표시 실패");
		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
			System.out.println(e.getMessage());
			throw new Exception("차량 수정 DB 처리 중 오류가 발생했습니다.");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt);
//			System.out.println("7. 닫기");
		}

		return result;
	} // end of update

	// 차량 삭제
	@Override
	public Integer delete(long no) throws Exception {
		// TODO Auto-generated method stub

		int result = 0;

		try {

			// 1. 확인 2. 연결
			con = DB.getConnection();
//			System.out.println("1. 확인\n2. 연결");
			// 3. sql
			String sql = "delete from car where carNo = ?";
			// 4. 실행 객체, 데이터 세팅
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, no);
//			System.out.println("4. 실행 객체, 데이터 세팅");
			// 5. 실행
			result = pstmt.executeUpdate();
//			System.out.println("5. 실행");
			// 6. 저장, 표시
//			if (result == 1)
//				System.out.println("6. 저장, 표시");
//			else
//				System.out.println("6. 저장, 표시 실패");
		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
			System.out.println(e.getMessage());
			throw new Exception("차량 삭제 DB 처리 중 오류가 발생했습니다.");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt);
//			System.out.println("7. 닫기");
		}

		return result;
	} // end of delete

	// 관리자 비밀번호
	@Override
	public MemberVO pw(String id) throws Exception {
		// TODO Auto-generated method stub

		MemberVO vo = null;

		try {

			// 1. 확인 2. 연결
			con = DB.getConnection();
//			System.out.println("1. 확인\n2. 연결");
			// 3. sql
			String sql = "select pw from member where id = ?";
//			System.out.println("3. sql");
			// 4. 실행 객체, 데이터 세팅
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
//			System.out.println("4. 실행 객체, 데이터 세팅");
			// 5. 실행
			rs = pstmt.executeQuery();
//			System.out.println("5. 실행");
			// 6. 저장, 표시
			if (rs != null && rs.next()) {
				vo = new MemberVO();
				vo.setPw(rs.getString("pw"));
			}
//			System.out.println("6. 저장, 표시");

		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
			System.out.println(e.getMessage());
			throw new Exception("관리자 비밀번호 확인 DB 처리 중 오류가 발생했습니다");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt, rs);
//			System.out.println("7. 닫기");
		}
		return vo;
	} // end of pw

} // end of class
